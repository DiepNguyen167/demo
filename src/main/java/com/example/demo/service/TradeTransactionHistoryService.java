package com.example.demo.service;

import com.example.demo.model.TradeTransaction;
import com.example.demo.repository.TradeTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeTransactionHistoryService {
    private final TradeTransactionRepository tradeTransactionRepository;

    public List<TradeTransaction> getTradeTransactionsByUserInfoId(long userInfoId) {
        var tradeTransactions = tradeTransactionRepository.findByUserInfoId(userInfoId);

        return tradeTransactions.stream()
                .toList();
    }
}
