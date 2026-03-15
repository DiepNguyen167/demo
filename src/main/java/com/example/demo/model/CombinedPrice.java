package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CombinedPrice {

    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    private BigDecimal bidQuantity;
    private BigDecimal askQuantity;
    private String symbol;
}
