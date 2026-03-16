package com.example.demo.service;

import com.example.demo.model.TradeTransaction;
import com.example.demo.model.UserWallet;
import com.example.demo.model.request.TradeTransactionRequest;
import com.example.demo.model.response.TradeTransactionResponse;
import com.example.demo.repository.BestPricingRepository;
import com.example.demo.repository.TradePairRepository;
import com.example.demo.repository.TradeTransactionRepository;
import com.example.demo.repository.UserWalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeTransactionService {
    private final UserWalletRepository userWalletRepository;
    private final BestPricingRepository bestPricingRepository;
    private final TradeTransactionRepository tradeTransactionRepository;
    private final TradePairRepository tradePairRepository;

    @Transactional
    public TradeTransactionResponse executeTrade(TradeTransactionRequest tradeTransactionRequest) {
        //validate request
        if(tradeTransactionRequest == null || tradeTransactionRequest.getAmount() == null || tradeTransactionRequest.getQuantity() == null)
            throw new IllegalArgumentException("Invalid request");

        //get best price
        var bestPrice = bestPricingRepository.findBestPriceByTracePairId(tradeTransactionRequest.getTracePairId());
        if(bestPrice == null) throw new IllegalArgumentException("No price available for symbol: " + tradeTransactionRequest.getTracePairId());

        var tradePair = tradePairRepository.findById(tradeTransactionRequest.getTracePairId())
                .orElseThrow(() -> new IllegalArgumentException("No trade pair found for id: " + tradeTransactionRequest.getTracePairId()));

        //get user wallet
        var baseWallet = userWalletRepository.findByUserInfoIdAndCurrency(tradeTransactionRequest.getUserInfoId(), tradePair.getBaseCurrency());
        var quoteWallet = userWalletRepository.findByUserInfoIdAndCurrency(tradeTransactionRequest.getUserInfoId(), tradePair.getQuoteCurrency());

        TradeTransaction tradeTransaction = new TradeTransaction();
        tradeTransaction.setUserInfoId(tradeTransactionRequest.getUserInfoId());

        switch (tradeTransactionRequest.getTradeType()){
            case BID ->{ //mua
                //tao vi neu null;
                //tru tien vi baseWaller, -> kiem tra cos du tien khong, object null khong
                // vi + tien -> null thi create, exist + tien,
                validateAmount(baseWallet, tradeTransactionRequest.getAmount().multiply(tradeTransactionRequest.getQuantity()));
                userWalletRepository.save(baseWallet);
                userWalletRepository.save(quoteWallet);

            }
            case ASK ->{
                // cong wallet base, tru tien vi wallet quote
                // tao vi base if null
                validateAmount(quoteWallet, tradeTransactionRequest.getAmount().multiply(tradeTransactionRequest.getQuantity()));

                userWalletRepository.save(baseWallet);
                userWalletRepository.save(quoteWallet);
            }
            default -> throw new IllegalArgumentException("Invalid trade type: " + tradeTransactionRequest.getTradeType());
        }

        tradeTransactionRepository.save(tradeTransaction);
        return new TradeTransactionResponse(tradeTransaction.getId(), "Trade executed successfully");

    }

    private void validateAmount(UserWallet baseWallet, BigDecimal multiply) {
        if(baseWallet.getBalance().compareTo(multiply) < 0)
            throw new IllegalArgumentException("Insufficient balance in wallet: " + baseWallet.getCurrency());
    }
}
