package com.ndirituedwin.Service;

import com.ndirituedwin.Exceptions.SpringRedditException;
import com.ndirituedwin.Model.RefreshToken;
import com.ndirituedwin.Repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private  final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken generaterefreshtoken() {
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return  refreshTokenRepository.save(refreshToken);
    }
    void validaterefreshtoken(String token){
        refreshTokenRepository.findByToken(token).orElseThrow(() -> new SpringRedditException("Invalid Refresh Token"));
    }
   public void deleteToken(String token){
        refreshTokenRepository.deleteByToken(token);
   }



}
