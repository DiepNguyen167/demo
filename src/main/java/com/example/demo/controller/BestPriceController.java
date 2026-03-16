package com.example.demo.controller;

import com.example.demo.model.response.BestPriceResponse;
import com.example.demo.service.BestPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/best-prices/")
@RequiredArgsConstructor
@Slf4j
public class BestPriceController {
private final BestPriceService bestPriceService;


    @GetMapping(value = "{symbol}")
    public BestPriceResponse getBestPrices(@PathVariable String symbol) {
        return bestPriceService.getBestPrices(symbol);
    }
}
