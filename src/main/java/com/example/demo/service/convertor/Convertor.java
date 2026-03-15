package com.example.demo.service.convertor;

public interface Convertor<I,O> {
    O transform(I input) throws IllegalArgumentException;
}
