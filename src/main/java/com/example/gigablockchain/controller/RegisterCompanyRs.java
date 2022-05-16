package com.example.gigablockchain.controller;

import com.example.gigablockchain.shit.ActionRs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RegisterCompanyRs extends ActionRs {
    private String adminToken;
    private String name;
    private String tokenName;
}
