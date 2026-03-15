package com.example.demo.service;

import com.example.demo.model.response.BinanceResponse;
import com.example.demo.model.CombinedPrice;
import com.example.demo.service.convertor.BinanceDataConvertor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FetchBinanceDataService implements BaseFetchDataService {
    private final RestTemplate restTemplate;
    private final BinanceDataConvertor convertor;

    @Value("${provider-source.binance.url}")
    private String binanceUrl;

     @Override
    public List<CombinedPrice> aggregate() throws Exception {
        try {
            var items = restTemplate.getForObject(binanceUrl, BinanceResponse[].class);
            List<CombinedPrice> combinedPrices = new ArrayList<>();
            for(var item: items){
                combinedPrices.add(convertor.transform(item));
            }
            return combinedPrices;
        } catch (Exception e) {
            log.error("Error fetching data from Binance: {}", e.getMessage());
            throw new Exception("Failed to fetch data from Binance", e);
        }
    }
}
