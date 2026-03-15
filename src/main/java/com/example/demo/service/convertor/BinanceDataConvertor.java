package com.example.demo.service.convertor;

import java.util.Objects;

import com.example.demo.model.response.BinanceResponse;
import com.example.demo.model.CombinedPrice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BinanceDataConvertor implements Convertor<BinanceResponse, CombinedPrice> {

    @Override
    public CombinedPrice transform(BinanceResponse input) throws IllegalArgumentException {
        if(Objects.isNull(input) ||
                StringUtils.isBlank(input.getSymbol()) ||
                Objects.isNull(input.getBidPrice()) ||
                Objects.isNull(input.getBidQty()) ||
                Objects.isNull(input.getAskPrice()) ||
                Objects.isNull(input.getAskQty())
        )
            throw new IllegalArgumentException("Invalid input");


        return CombinedPrice.builder()
                .symbol(input.getSymbol())
                .bidPrice(input.getBidPrice())
                .askPrice(input.getAskPrice())
                .askQuantity(input.getAskQty())
                .bidQuantity(input.getBidQty())
                .build();
    }
}
