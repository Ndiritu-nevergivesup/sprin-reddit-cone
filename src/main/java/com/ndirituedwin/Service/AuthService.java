package com.ndirituedwin.Service;

import com.ndirituedwin.Config.AppConfig;
import com.ndirituedwin.Dto.*;
import com.ndirituedwin.Dto.RegisterRequest;
import com.ndirituedwin.Exceptions.SpringRedditException;
import com.ndirituedwin.Model.NotificationEmail;
import com.ndirituedwin.Model.User;
import com.ndirituedwin.Model.VerificationToken;
import com.ndirituedwin.Repository.UserRepository;
import com.ndirituedwin.Repository.VerificationTokenRepository;
import com.ndirituedwin.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository  verificationTokenRepository;
    private final MailService mailService;
    private final  MailContentBuilder mailContentBuilder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final AppConfig appConfig;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, MailService mailService, MailContentBuilder mailContentBuilder, AuthenticationManager authenticationManager, JwtProvider jwtProvider, RefreshTokenService refreshTokenService, AppConfig appConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.mailContentBuilder = mailContentBuilder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
        this.appConfig = appConfig;
    }
//    public static final String ACTIVATION_EMAIL = "http://localhost:9090/api/auth/accountVerification";
    public static final String ACTIVATION_EMAIL = "api/auth/accountVerification";


    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
       String token=  generateverificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + ACTIVATION_EMAIL + "/" + token);
       mailService.sendMail(new NotificationEmail("Please activate your account",user.getEmail(),
               "Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                       +appConfig.getUrl()+"/" +ACTIVATION_EMAIL+ "/" + token
               ));

    }

    private String generateverificationToken(User user) {

        String token= UUID.randomUUID().toString();
        VerificationToken verificationToken=new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;

    }

    public void verifyaccount(String token) {

        Optional<VerificationToken> verificationToken=verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token "));

        fetchuserandenable(verificationToken.get());

    }

    @Transactional
    private void fetchuserandenable(VerificationToken verificationToken) {
    String username=verificationToken.getUser().getUsername();
  User user=  userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with username "+username));
   user.setEnabled(true);
   userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
       Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        log.info(SecurityContextHolder.getContext().setAuthentication(authenticate));
        log.info("authentication  ->",authenticate.getPrincipal());
        System.out.println("authentication  ->  "+authenticate.getPrincipal());
        String token= jwtProvider.generatetoken(authenticate);
        return  AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generaterefreshtoken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }


    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
                refreshTokenService.validaterefreshtoken(refreshTokenRequest.getRefreshToken());
                String token=jwtProvider.generatetokenwithusername(refreshTokenRequest.getUsername());
                return AuthenticationResponse.builder()
                        .authenticationToken(token)
                        .refreshToken(refreshTokenRequest.getRefreshToken())
                        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                        .username(refreshTokenRequest.getUsername())
                        .build();
    }

    public boolean isLoggedIn() {
    Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    return ! (authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
