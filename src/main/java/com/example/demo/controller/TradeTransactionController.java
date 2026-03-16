package com.example.demo.controller;

import com.example.demo.model.TradeTransaction;
import com.example.demo.model.request.TradeTransactionRequest;
import com.example.demo.service.TradeTransactionHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade-transaction/")
@RequiredArgsConstructor
@Slf4j
public class TradeTransactionController {
    private final TradeTransactionHistoryService tradeTransactionHistoryService;

    @PostMapping
    void trade(@RequestBody @Valid TradeTransactionRequest tradeRequest) throws Exception {

    }

    @GetMapping(value = "{userInfoId}")
    public List<TradeTransaction> getTradeTransactionsByUserInfoId(@PathVariable long userInfoId) {
        return tradeTransactionHistoryService.getTradeTransactionsByUserInfoId(userInfoId);
    }
}
