package com.example.demo.repository;

import com.example.demo.model.TradeTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {

    Page<TradeTransaction> findAllByUserInfoId(String userInfoId, Pageable pageable);
}
