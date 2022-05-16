package com.example.gigablockchain.controller;

import com.example.gigablockchain.shit.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final CorporationComponent corporationComponent;

    @PostMapping(value = "/api/main/token", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActionRs> createToken(@RequestBody RegisterCompanyRq createTokenRq) {
        System.out.println(createTokenRq.toString());
        return ResponseEntity.ok(corporationComponent.registerCompany(createTokenRq.getCompanyName(), createTokenRq.getTokenName()));
    }

    @PostMapping(value = "/api/enroll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterUserRs> registerUser(@RequestParam String token, @RequestBody RegisterUserRq registerUserRq) throws Exception {
        return ResponseEntity.ok(corporationComponent.enrollUser(token, registerUserRq.getUsername()));
    }

    @PostMapping(value = "api/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActionRs> makeTransfer(@RequestBody MakeTransferRq makeTransferRq) throws Exception {
        return ResponseEntity.ok(corporationComponent.makeTransfer(makeTransferRq.getFrom(), makeTransferRq.getTo(), makeTransferRq.getBalance()));
    }

    @GetMapping(value = "/api/info")
    public ResponseEntity<User> getUserInfo(@RequestParam String token) throws Exception {
        return ResponseEntity.ok(corporationComponent.getUserByAuthToken(token));
    }

    @PostMapping(value = "/api/mint", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActionRs> mintTokens(@RequestBody MintTokensRq rq) throws Exception {
        return ResponseEntity.ok(corporationComponent.mint(rq.getToken(), rq.getAmount()));
    }

    @GetMapping(value = "/api/company")
    public ResponseEntity<CompanyDetailsRs> getCompanyInfo() {
        return ResponseEntity.ok(corporationComponent.getCompanyDetails());
    }

    @GetMapping(value = "/service")
    public ResponseEntity<CorporationComponent> service(){
        return ResponseEntity.ok(corporationComponent);
    }


}
