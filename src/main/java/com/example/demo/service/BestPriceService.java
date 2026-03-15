package com.example.demo.service;

import com.example.demo.model.response.BestPriceResponse;
import com.example.demo.repository.BestPricingRepository;
import com.example.demo.service.convertor.BestPriceResponseConvertor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BestPriceService {
    private final BestPricingRepository bestPricingRepository;
    private final BestPriceResponseConvertor bestPriceResponseConvertor;

    public List<BestPriceResponse> getBestPrices(String symbol) {
        var bestPrices = bestPricingRepository.findBySymbolOrderByTimestampDesc(symbol);
        return bestPrices.stream()
                .map(bestPriceResponseConvertor::transform)
                .filter(Objects::nonNull)
                .toList();
    }

}
