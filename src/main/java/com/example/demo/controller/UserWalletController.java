package com.example.demo.controller;

import com.example.demo.model.response.UserWalletResponse;
import com.example.demo.service.UserWalletService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/user-wallets/")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserWalletController {
    private final UserWalletService userWalletService;

    @GetMapping(value = "{userInfoId}")
    public List<UserWalletResponse> getUserWallet(@PathVariable long userInfoId) {
        return userWalletService.getUserWalletsByUserInfoId(userInfoId);
    }
}
