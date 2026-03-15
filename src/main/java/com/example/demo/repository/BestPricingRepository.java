package com.example.demo.repository;

import com.example.demo.model.BestPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestPricingRepository extends JpaRepository<BestPricing, Long> {
    BestPricing findByTradePairIdOrderByTimestampDesc(long tradePairId);
}
