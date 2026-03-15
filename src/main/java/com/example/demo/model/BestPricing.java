package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BEST_PRICING")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BestPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_pair_id", nullable = false)
    private long tradePairId;

    @Column(name = "bid_price", nullable = false, precision = 18, scale = 8)
    private BigDecimal bidPrice;

    @Column(name = "ask_price", nullable = false, precision = 18, scale = 8)
    private BigDecimal askPrice;

    @Column(name = "timestamp", nullable = false)
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
