package com.example.demo.model.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BestPriceResponse {
    private String symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    private LocalDateTime createdAt;
}
