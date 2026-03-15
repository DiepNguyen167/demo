package com.example.demo.service.convertor;

import com.example.demo.model.response.BestPriceResponse;
import com.example.demo.model.BestPricing;
import com.example.demo.repository.TradePairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BestPriceResponseConvertor implements Convertor<BestPricing, BestPriceResponse> {
    private final TradePairRepository tradePairRepository;

@Override
    public BestPriceResponse transform(BestPricing input) throws IllegalArgumentException {
        if(input == null)
            throw new IllegalArgumentException("Input cannot be null");

        var tradePair = tradePairRepository.findById(input.getTradePairId())
                .orElseThrow(() -> new IllegalArgumentException("No trade pair found for id: " + input.getTradePairId()));

        return BestPriceResponse.builder()
                .symbol(tradePair.getBaseCurrency() + tradePair.getQuoteCurrency())
                .bidPrice(input.getBidPrice())
                .askPrice(input.getAskPrice())
                .bidQuantity(input.getBidQuantity())
                .askQuantity(input.getAskQuantity())
                .createdAt(input.getTimestamp())
                .build();
    }
}
