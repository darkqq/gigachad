package com.example.gigablockchain.shit;

import com.example.gigablockchain.controller.RegisterCompanyRq;
import com.example.gigablockchain.controller.RegisterCompanyRs;
import com.example.gigablockchain.controller.RegisterUserRs;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.*;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.*;

@Component
@Data
public class CorporationComponent {
    private String companyName;
    private List<User> users = new ArrayList<>();
    private String tokenName;
    private BigDecimal totalTokens = BigDecimal.ZERO;
    private static final String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";


    @PostConstruct
    public void megaShit(){
        User user = new User()
                .setAuthToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .setWallet(UUID.randomUUID().toString())
                .setUsername("admin")
                .setRole(UserRole.ADMIN);
        users.add(user);
    }

    public String createJWT(String id, String issuer, String subject) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = Base64Util.encode("gigachad").getBytes(StandardCharsets.UTF_8);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }


    public User getUserByUsername(String username) throws Exception {
        if(companyName == null){
            throw new Exception("COMPANY_NOT_REGISTERED");
        }
        return users.stream().filter(e -> e.getUsername().equals(username)).findFirst().orElse(null);
    }

    public User getUserByAuthToken(String token) throws Exception {
        if(companyName == null){
            throw new Exception("COMPANY_NOT_REGISTERED");
        }
        return users.stream().filter(e -> e.getAuthToken().equals(token)).findFirst().orElse(null);
    }

    public User getUserByWallet(String wallet) throws Exception {
        if(companyName == null){
            throw new Exception("COMPANY_NOT_REGISTERED");
        }
        return users.stream().filter(e -> e.getWallet().equals(wallet)).findFirst().orElse(null);
    }

    public ActionRs makeTransfer(String token1, String whereId, BigDecimal howMuch) throws Exception {
        if(token1 == null){
            throw new Exception("NOT_AUTHORISED");
        }
        if(companyName == null){
            throw new Exception("COMPANY_NOT_REGISTERED");
        }
        User user = getUserByAuthToken(token1);
        if (user.getBalance().compareTo(howMuch) < 0) {
            throw new Exception("INSUFFICIENT_FUNDS");
        }
        user.setBalance(user.getBalance().subtract(howMuch));
        User userByWallet = getUserByWallet(whereId);
        if (userByWallet == null) {
            throw new Exception("WALLET_NOT_FOUND");
        }
        String transactionId = getRandom256();
        user.getTransactions().add(transactionId);
        userByWallet.getTransactions().add(transactionId);
        userByWallet.setBalance(userByWallet.getBalance().add(howMuch));
        return new ActionRs(transactionId);
    }

    public CompanyDetailsRs getCompanyDetails() {
        return new CompanyDetailsRs(companyName, tokenName, totalTokens);
    }

    public RegisterUserRs enrollUser(String token, String username) throws Exception {
        System.out.println("token: " + token);
        System.out.println("username: " + username);
        if(token == null){
            throw new Exception("NOT_AUTHORISED");
        }
        if(getUserByAuthToken(token) == null || getUserByAuthToken(token).getRole().equals(UserRole.USER)){
            throw new Exception("PERMISSION_DENIED");
        }
        if(companyName == null){
            throw new Exception("COMPANY_NOT_REGISTERED");
        }
        if (getUserByUsername(username) != null) {
            throw new Exception("ALREADY_REGISTERED");
        }
        User user = new User().setUsername(username).setBalance(BigDecimal.ZERO).setWallet(UUID.randomUUID().toString()).setAuthToken(createJWT(username, username, "user")).setRole(UserRole.USER);
        users.add(user);
        return new RegisterUserRs().setToken(user.getAuthToken()).setWallet(user.getWallet()).setTransactionId(getRandom256());
    }

    public RegisterCompanyRs registerCompany(String cName, String tName) throws Exception {
        if(companyName != null){
            throw new Exception("ALREADY_REGISTERED");
        }
        companyName = cName;
        tokenName = tName;
        RegisterCompanyRs rs = new RegisterCompanyRs("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c" ,companyName, tokenName);
        rs.setTransactionId(getRandom256());
        return rs;
    }


    public ActionRs mint(String token ,BigDecimal amount) throws Exception {
        if(token == null){
            throw new Exception("NOT_AUTHORISED");
        }
        if(companyName == null){
            throw new Exception("COMPANY_NOT_REGISTERED");
        }
        if(getUserByAuthToken(token) == null || getUserByAuthToken(token).getRole().equals(UserRole.USER)){
            throw new Exception("PERMISSION_DENIED");
        }
        User user = getUsers().stream().filter(e -> e.getRole().equals(UserRole.ADMIN)).findFirst().orElse(new User());
        user.setBalance(user.getBalance().add(amount));
        totalTokens = totalTokens.add(amount);
        return new ActionRs(getRandom256());
    }

    @SneakyThrows
    public String getRandom256(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);

        return getSHA256Hash(generatedString);
    }


    private String getSHA256Hash(String data) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash); // make it printable
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }

}
