package com.example.demo.repository;

import com.example.demo.model.TradeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeTransactionResponsitory extends JpaRepository<TradeTransaction, Long> {
}
