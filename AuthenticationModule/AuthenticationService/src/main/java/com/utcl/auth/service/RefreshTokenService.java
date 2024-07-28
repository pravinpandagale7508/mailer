package com.utcl.auth.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.utcl.auth.entity.RefreshToken;
import com.utcl.auth.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {
	
	@Value("${authservice.jjwt.refresh_exp}")
	private String expirationTime;
	
    //@Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
//    @Autowired 
//    private UserInfoRepository userInfoRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		super();
		this.refreshTokenRepository = refreshTokenRepository;
	}


	public RefreshToken createRefreshToken(String userId) {
    	Long expirationTimeLong = Long.parseLong(expirationTime);
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(expirationTimeLong))//10
                .build();
        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

}
