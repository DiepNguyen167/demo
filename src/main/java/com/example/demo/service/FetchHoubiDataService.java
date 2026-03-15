package com.example.demo.service;

import com.example.demo.model.CombinedPrice;
import com.example.demo.model.HuobiWarpper;
import com.example.demo.service.convertor.HoubiDataConvertor;
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
public class FetchHoubiDataService implements BaseFetchDataService {
    private final RestTemplate restTemplate;
    private final HoubiDataConvertor convertor;

    @Value("${provider-source.huobi.url}")
    private String binanceUrl;

    @Override
    public List<CombinedPrice> aggregate() throws Exception {
        try {
            var data = restTemplate.getForObject(binanceUrl, HuobiWarpper.class);
            if(data == null || data.getData() == null){
                log.error("Received null data from Houbi");
                throw new Exception("Received null data from Houbi");
            }
            var items = data.getData();
            List<CombinedPrice> combinedPrices = new ArrayList<>();
            for(var item: items){
                combinedPrices.add(convertor.transform(item));
            }
            return combinedPrices;
        } catch (Exception e) {
            log.error("Error fetching data from Houbi: {}", e.getMessage());
            throw new Exception("Failed to fetch data from Houbi", e);
        }
    }
}
