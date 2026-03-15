package com.example.demo.service;

import com.example.demo.model.HuobiResponse;
import lombok.Data;

import java.util.List;

@Data
public class HuobiWarpper {
    private List<HuobiResponse> data;
}
