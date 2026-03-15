package com.example.demo.controller;

import com.example.demo.model.request.TradeTransactionRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class TradeTransactionController {

    @PostMapping
    void trade(@RequestBody @Valid TradeTransactionRequest tradeRequest) throws Exception {

    }
}
