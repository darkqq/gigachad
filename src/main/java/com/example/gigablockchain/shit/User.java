package com.example.gigablockchain.shit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private String authToken;
    private String username;
    private BigDecimal balance = BigDecimal.ZERO;
    private List<String> transactions = new ArrayList<>();
    private String wallet;
    private UserRole role;
}
