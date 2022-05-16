package com.example.gigablockchain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MintTokensRq {
    private String token;
    private BigDecimal amount;
}
