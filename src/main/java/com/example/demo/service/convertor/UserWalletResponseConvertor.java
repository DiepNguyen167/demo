package com.example.demo.service.convertor;

import com.example.demo.model.UserWallet;
import com.example.demo.model.response.UserWalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserWalletResponseConvertor implements Convertor<UserWallet, UserWalletResponse> {

@Override
    public UserWalletResponse transform(UserWallet input) throws IllegalArgumentException {
        if(input == null)
            throw new IllegalArgumentException("Input cannot be null");

        return UserWalletResponse.builder()
                .currency(input.getCurrency())
                .balance(input.getBalance())
                .build();
    }
}
