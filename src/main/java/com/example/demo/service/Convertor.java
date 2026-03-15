package com.example.demo.service;

public interface Convertor<I,O> {
    O transform(I input) throws IllegalArgumentException;
}
