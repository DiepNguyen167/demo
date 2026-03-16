package com.example.demo.service.convertor;

import com.example.demo.model.BestPricing;
import com.example.demo.model.CombinedPrice;
import com.example.demo.model.TradePair;
import com.example.demo.repository.TradePairRepository;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityConvertor implements Convertor<CombinedPrice, BestPricing> {

    private final TradePairRepository repository;

    @Override
    public BestPricing transform(CombinedPrice input) throws IllegalArgumentException {
        if(input == null || input.getSymbol() == null || input.getSymbol().isBlank() || input.getBidPrice() == null || input.getAskPrice() == null)
            throw new IllegalArgumentException("Invalid input");

        String normalizedSymbol = input.getSymbol().trim().toUpperCase(Locale.ROOT);
        TradePair record = repository.findBySymbol(normalizedSymbol);
        if(record == null) return null;

        return BestPricing.builder()
                .tradePairId(record.getId())
                .bidPrice(input.getBidPrice())
                .askPrice(input.getAskPrice())
                .bidQuantity(input.getBidQuantity())
                .askQuantity(input.getAskQuantity())
                .build();
    }

}
