package com.example.gigablockchain.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RegisterUserRs {
    private String token;
    private String transactionId;
    private String wallet;
}
