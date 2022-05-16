package com.example.gigablockchain.shit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeTransferRq {
    private String from;
    private String to;
    private BigDecimal balance;
}
