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
    private final BestPriceService bestPriceService;

    @Transactional
    public TradeTransactionResponse executeTrade(TradeTransactionRequest tradeTransactionRequest) {
        if (tradeTransactionRequest == null || tradeTransactionRequest.getQuantity() == null)
            throw new IllegalArgumentException("Invalid request");

        var tradePair = tradePairRepository.findById(tradeTransactionRequest.getTracePairId())
                .orElseThrow(() -> new IllegalArgumentException("No trade pair found for id: " + tradeTransactionRequest.getTracePairId()));

        var symbol = tradePair.getBaseCurrency() + tradePair.getQuoteCurrency();
        var bestPrice = bestPriceService.getBestPrices(symbol);
        if (bestPrice == null)
            throw new IllegalArgumentException("No best price available for symbol: " + tradeTransactionRequest.getTracePairId());

        BigDecimal bidPrice = bestPrice.getBidPrice();
        BigDecimal askPrice = bestPrice.getAskPrice();

        BigDecimal price = getPrice(tradeTransactionRequest, bidPrice, askPrice);

        var baseWallet = userWalletRepository.findByUserInfoIdAndCurrency(tradeTransactionRequest.getUserInfoId(), tradePair.getBaseCurrency());
        var quoteWallet = userWalletRepository.findByUserInfoIdAndCurrency(tradeTransactionRequest.getUserInfoId(), tradePair.getQuoteCurrency());

        TradeTransaction tradeTransaction = new TradeTransaction();
        tradeTransaction.setUserInfoId(tradeTransactionRequest.getUserInfoId());
        tradeTransaction.setTradeType(tradeTransactionRequest.getTradeType());
        tradeTransaction.setTradePairId(tradeTransactionRequest.getTracePairId());
        tradeTransaction.setPrice(price);
        tradeTransaction.setQuantity(tradeTransactionRequest.getQuantity());
        tradeTransaction.setStatus("EXECUTED");

        switch (tradeTransactionRequest.getTradeType()) {
            case BID -> { // Buy
                BigDecimal totalCost = price.multiply(tradeTransactionRequest.getQuantity());
                if (baseWallet == null)
                    throw new IllegalArgumentException("Base wallet not found for user: " + tradeTransactionRequest.getUserInfoId());
                validateAmount(baseWallet, totalCost);
                baseWallet.setBalance(baseWallet.getBalance().subtract(totalCost));
                if (quoteWallet == null) {
                    quoteWallet = new UserWallet();
                    quoteWallet.setUserInfoId(tradeTransactionRequest.getUserInfoId());
                    quoteWallet.setCurrency(tradePair.getQuoteCurrency());
                    quoteWallet.setBalance(tradeTransactionRequest.getQuantity());
                } else {
                    quoteWallet.setBalance(quoteWallet.getBalance().add(tradeTransactionRequest.getQuantity()));
                }
                userWalletRepository.save(baseWallet);
                userWalletRepository.save(quoteWallet);
            }
            case ASK -> { // Sell
                BigDecimal totalQuantity = tradeTransactionRequest.getQuantity();
                if (quoteWallet == null)
                    throw new IllegalArgumentException("Quote wallet not found for user: " + tradeTransactionRequest.getUserInfoId());
                validateAmount(quoteWallet, totalQuantity);
                quoteWallet.setBalance(quoteWallet.getBalance().subtract(totalQuantity));
                BigDecimal baseAmount = price.multiply(totalQuantity);
                if (baseWallet == null) {
                    baseWallet = new UserWallet();
                    baseWallet.setUserInfoId(tradeTransactionRequest.getUserInfoId());
                    baseWallet.setCurrency(tradePair.getBaseCurrency());
                    baseWallet.setBalance(baseAmount);
                } else {
                    baseWallet.setBalance(baseWallet.getBalance().add(baseAmount));
                }
                userWalletRepository.save(baseWallet);
                userWalletRepository.save(quoteWallet);
            }
            default -> throw new IllegalArgumentException("Invalid trade type: " + tradeTransactionRequest.getTradeType());
        }

        tradeTransactionRepository.save(tradeTransaction);
        return new TradeTransactionResponse(tradeTransaction.getId(), "Trade executed successfully");
    }

    private void validateAmount(UserWallet wallet, BigDecimal requiredAmount) {
        if (wallet.getBalance().compareTo(requiredAmount) < 0)
            throw new IllegalArgumentException("Insufficient balance in wallet: " + wallet.getCurrency());
    }

    private static BigDecimal getPrice(TradeTransactionRequest input, BigDecimal bidPrice, BigDecimal askPrice) {
        return switch (input.getTradeType()) {
            case BID -> bidPrice;
            case ASK -> askPrice;
        };
    }
}

