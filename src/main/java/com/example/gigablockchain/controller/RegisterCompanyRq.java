package com.example.gigablockchain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCompanyRq {
    private String companyName;
    private String tokenName;
}
