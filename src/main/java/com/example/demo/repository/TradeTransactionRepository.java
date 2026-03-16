package com.example.demo.repository;

import com.example.demo.model.TradeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {

    @Query(value = """
            SELECT * FROM TRADE_TRANSACTION tt
            WHERE tt.user_info_id = :userInfoId
     """, nativeQuery = true)
    List<TradeTransaction> findByUserInfoId(long userInfoId);
}
