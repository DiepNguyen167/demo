package com.example.demo.job;

import com.example.demo.model.CombinedPrice;
import com.example.demo.repository.BestPricingRepository;
import com.example.demo.service.convertor.EntityConvertor;
import com.example.demo.service.FetchBinanceDataService;
import com.example.demo.service.FetchHoubiDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FetchPriceDataJob {
    private final FetchHoubiDataService fetchHoubiDataService;
    private final FetchBinanceDataService fetchBinanceDataService;
    private final BestPricingRepository bestPricingRepository;
    private final EntityConvertor entityConvertor;

    @Scheduled(fixedRateString = "${scheduler.fixed-rate}")
    public void execute(){
        try {
            var binanceData = fetchBinanceDataService.aggregate();
            var houbiData = fetchHoubiDataService.aggregate();
            List<CombinedPrice> combinedPrices = new ArrayList<>();
            combinedPrices.addAll(binanceData);
            combinedPrices.addAll(houbiData);

            var entites = combinedPrices.stream()
                    .map(entityConvertor::transform)
                    .filter(Objects::nonNull).toList();

            var timeStamp = LocalDateTime.now();
            entites.forEach(e -> e.setTimestamp(timeStamp));
             log.info("Transformed Entities: {}", entites);
            bestPricingRepository.saveAll(entites);
        } catch (Exception e) {
            log.error("Error executing FetchPriceDataJob: " + e.getMessage());
        }
    }
}
