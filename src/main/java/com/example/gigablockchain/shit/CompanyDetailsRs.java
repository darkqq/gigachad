package com.example.gigablockchain.shit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDetailsRs {
    private String name;
    private String tokenName;
    private BigDecimal totalTokens;
}
