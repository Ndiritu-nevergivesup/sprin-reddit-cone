package com.ndirituedwin.Controller;

import com.ndirituedwin.Dto.AuthenticationResponse;
import com.ndirituedwin.Dto.LoginRequest;
import com.ndirituedwin.Dto.RefreshTokenRequest;
import com.ndirituedwin.Dto.RegisterRequest;
import com.ndirituedwin.Service.AuthService;
import com.ndirituedwin.Service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
   private final AuthService authService;
   private final RefreshTokenService refreshTokenService;
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
//    public ResponseEntity<String>  signup(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result){
        public ResponseEntity<String>  signup(@RequestBody RegisterRequest registerRequest){
//        if (result.hasErrors()){
//            Map<String,String> errormap=new HashMap<>();
//            for (FieldError error: result.getFieldErrors()){
//                errormap.put(error.getField(),error.getDefaultMessage());
//            }
//            return new ResponseEntity<Map<String,String>>(errormap, HttpStatus.BAD_REQUEST);
//        }
//        return  new ResponseEntity<>(authService.signup(registerRequest), HttpStatus.CREATED);
   authService.signup(registerRequest);
   return new ResponseEntity<>("User Registration successful",HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyaccount(@PathVariable("token")  String token){
        authService.verifyaccount(token);
        return new ResponseEntity<>("Account activated successfully",HttpStatus.OK);
    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
      return authService.login(loginRequest);

    }
    @PostMapping("/refreshtoken")
     public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refreshToken(refreshTokenRequest);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logoutanddeleterefreshtoken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteToken(refreshTokenRequest.getRefreshToken());
        return  ResponseEntity.status(HttpStatus.OK).body("Refresh Token successfully deleted");
    }

}
