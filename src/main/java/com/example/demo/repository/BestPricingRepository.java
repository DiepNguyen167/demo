package com.example.demo.repository;

import com.example.demo.model.BestPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestPricingRepository extends JpaRepository<BestPricing, Long> {

    @Query(value = "SELECT * FROM BEST_PRICING bp " +
            "WHERE bp.trade_pair_id IN " +
            "(SELECT tp.id FROM TRADE_PAIR tp " +
            "WHERE concat(tp.base_currency, tp.quote_currency) = :symbol) " +
            "ORDER BY bp.timestamp DESC", nativeQuery = true)
    List<BestPricing> findBySymbolOrderByTimestampDesc(String symbol);

    @Query(value = """
            SELECT * FROM BEST_PRICING bp
            WHERE bp.trade_pair_id = :tracePairId
            ORDER BY bp.timestamp DESC
            LIMIT 1
""", nativeQuery = true)
    BestPricing findBestPriceByTracePairId(long tracePairId);
}
