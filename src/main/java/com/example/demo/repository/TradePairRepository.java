package com.example.demo.repository;

import com.example.demo.model.TradePair;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TradePairRepository extends JpaRepository<TradePair,Long> {

    @Cacheable(value = "tradePairCache", key = "#symbol")
    @Query(value = "SELECT tp.*\n" +
            "            FROM TRADE_PAIR tp\n" +
            "            WHERE concat(tp.base_currency, tp.quote_currency) = :symbol",nativeQuery = true)
    TradePair findBySymbol(String symbol);
}
