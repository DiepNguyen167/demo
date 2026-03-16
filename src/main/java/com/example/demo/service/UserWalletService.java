package com.example.demo.service;

import com.example.demo.model.response.UserWalletResponse;
import com.example.demo.repository.UserWalletRepository;
import com.example.demo.service.convertor.UserWalletResponseConvertor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserWalletService {
    private final UserWalletRepository userWalletRepository;
    private final UserWalletResponseConvertor userWalletResponseConvertor;

    public List<UserWalletResponse> getUserWalletsByUserInfoId(long userInfoId) {
        var userWallets = userWalletRepository.findByUserInfoId(userInfoId);

        return userWallets.stream()
                .map(userWalletResponseConvertor::transform)
                .filter(Objects::nonNull)
                .toList();
    }
}
