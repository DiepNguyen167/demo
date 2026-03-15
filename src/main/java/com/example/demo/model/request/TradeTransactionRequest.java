package com.example.demo.model.request;

import com.example.demo.contants.TradeType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeTransactionRequest {
    private long userInfoId;
    private long tracePairId;
    @NotNull
    @DecimalMin(value = "0.000000000", message = "Amount must be greater than 0")
    private BigDecimal amount;
    @DecimalMin(value = "0.000000000", message = "Amount must be greater than 0")
    private BigDecimal quantity;
    private TradeType tradeType;
}
