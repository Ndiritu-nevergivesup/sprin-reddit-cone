package com.ndirituedwin.security;

import com.ndirituedwin.Exceptions.SpringRedditException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init(){
        try{
            keyStore=KeyStore.getInstance("JKS");
            InputStream resourceStream=getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceStream,"secret".toCharArray());
        }catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
            throw new SpringRedditException("Exception occurred while loading keystore");
        }
    }

    public String generatetoken(Authentication authentication){
        User principal=(User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(privateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();

    }

    private Key privateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog","secret".toCharArray());
        }catch (KeyStoreException|NoSuchAlgorithmException| UnrecoverableKeyException e){
            throw new SpringRedditException("Exception occurred while retrieving public key from the keystore");

        }
    }
    public boolean validateToken(String jwt){
        parser().setSigningKey(getpublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getpublicKey() {
    try {
          return keyStore.getCertificate("springblog").getPublicKey();
    }catch (KeyStoreException e){
        throw new SpringRedditException("n error occurred while retrieving public key from the keystore "+e.getMessage());
    }

    }

    public String getUsernamefromjwt(String token){
        Claims claims=parser()
                .setSigningKey(getpublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();

    }
    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

    public String generatetokenwithusername(String username) {
         return Jwts.builder()
                 .setSubject(username)
                 .setIssuedAt(Date.from(Instant.now()))
                 .signWith(getprivatekey())
                 .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                 .compact();
    }

    private Key getprivatekey() {
        try{
            return keyStore.getKey("springblog","secret".toCharArray());
        }catch (KeyStoreException| NoSuchAlgorithmException| UnrecoverableKeyException e){
            throw new SpringRedditException("Exception occured while retrieving private key from the keystore");
        }
    }

}
