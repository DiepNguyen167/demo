package com.example.demo.service;

import com.example.demo.model.CombinedPrice;
import com.example.demo.model.HuobiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class HoubiDataConvertor implements Convertor<HuobiResponse, CombinedPrice>{

    @Override
    public CombinedPrice transform(HuobiResponse input) throws IllegalArgumentException {
        if(Objects.isNull(input) ||
                StringUtils.isBlank(input.getSymbol()) ||
                Objects.isNull(input.getBid()) ||
                Objects.isNull(input.getBidSize()) ||
                Objects.isNull(input.getAsk()) ||
                Objects.isNull(input.getAskSize())
        )
            throw new IllegalArgumentException("Invalid input");


        return CombinedPrice.builder()
                .symbol(input.getSymbol())
                .bidPrice(input.getBid())
                .askPrice(input.getAsk())
                .askQuantity(input.getAskSize())
                .bidQuantity(input.getBidSize())
                .build();
    }
}
