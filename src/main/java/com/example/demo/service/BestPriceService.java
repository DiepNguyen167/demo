package com.example.demo.service;

import com.example.demo.model.response.BestPriceResponse;
import com.example.demo.repository.BestPricingRepository;
import com.example.demo.service.convertor.BestPriceResponseConvertor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BestPriceService {
    private final BestPricingRepository bestPricingRepository;
    private final BestPriceResponseConvertor bestPriceResponseConvertor;

    public BestPriceResponse getBestPrices(String symbol) {
        var bestPrices = bestPricingRepository.findBestBidAskBySymbolAtLatestTimestamp(symbol);
        if(bestPrices.isEmpty()){
            return null;
        }
        var highestBid = bestPrices.stream()
                .filter(Objects::nonNull)
                .max((bp1, bp2) -> bp1.getBidPrice().compareTo(bp2.getBidPrice()))
                .orElse(null);

        var lowestAsk = bestPrices.stream()
                .filter(Objects::nonNull)
                .min((bp1, bp2) -> bp1.getAskPrice().compareTo(bp2.getAskPrice()))
                .orElse(null);


        return BestPriceResponse.builder()
                .symbol(symbol)
                .bidPrice(highestBid != null ? highestBid.getBidPrice() : null)
                .askPrice(lowestAsk != null ? lowestAsk.getAskPrice() : null)
                .createdAt(bestPrices.get(0).getTimestamp())
                .build();
    }

}
