package com.example.demo.service;

import com.example.demo.model.CombinedPrice;

import java.util.List;

public interface BaseFetchDataService {
    List<CombinedPrice> aggregate() throws Exception;
}
