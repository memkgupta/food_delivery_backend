package com.localbite_express.core.controllers.auth;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
@PostMapping("/signup")
    public ResponseEntity<RegisterResponse> signup(@RequestBody RegisterRequest request){

return ResponseEntity.ok(authenticationService.register(request));
}
@PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
    ResponseEntity<LoginResponse> res;
    try{
        return ResponseEntity.ok(authenticationService.login(request));
    }
    catch (Exception e){
        return ResponseEntity.badRequest().body( LoginResponse.builder().message(e.getMessage()).build());
    }

}

}
