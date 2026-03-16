package com.example.demo.controller;

import com.example.demo.model.TradeTransaction;
import com.example.demo.model.request.TradeTransactionRequest;
import com.example.demo.model.response.TradeTransactionResponse;
import com.example.demo.service.TradeTransactionHistoryService;
import com.example.demo.service.TradeTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trade-transactions/")
@RequiredArgsConstructor
@Slf4j
public class TradeTransactionController {
    private final TradeTransactionHistoryService tradeTransactionHistoryService;
    private final TradeTransactionService tradeTransactionService;

    @PostMapping
    public TradeTransactionResponse trade(@RequestBody @Valid TradeTransactionRequest tradeRequest) throws Exception {
        return tradeTransactionService.executeTrade(tradeRequest);
    }

    @GetMapping(value = "{userInfoId}")
    public List<TradeTransaction> getTradeTransactionsByUserInfoId(@PathVariable long userInfoId) throws Exception {
        return tradeTransactionHistoryService.getTradeTransactionsByUserInfoId(userInfoId);
    }
}
