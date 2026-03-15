package com.example.demo.model;

import com.example.demo.model.response.HuobiResponse;
import lombok.Data;

import java.util.List;

@Data
public class HuobiWarpper {
    private List<HuobiResponse> data;
}
