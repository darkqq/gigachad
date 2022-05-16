package com.example.gigablockchain.shit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionsRs {
    private List<String> transactions;

}
