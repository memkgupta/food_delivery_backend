package com.localbite_express.core.controllers.auth;

import com.localbite_express.core.models.Role;
import com.localbite_express.core.models.Token;
import com.localbite_express.core.models.TokenType;
import com.localbite_express.core.models.User;
import com.localbite_express.core.repositories.TokenRepository;
import com.localbite_express.core.repositories.UserRepository;
import com.localbite_express.core.services.JWTService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    public RegisterResponse register(RegisterRequest request){
      User user = User.builder()
                    .email(request.getEmail())
                    .name(request.getName())
                    .phone(request.getPhone())
              .password(passwordEncoder.encode(request.getPassword()))
              .role(Role.USER)
.build();
        userRepository.save(user);
var jwtToken =jwtService.generateToken(user);
Token token = Token.builder().user(user).tokenType(TokenType.BEARER).expired(false).revoked(false).token(jwtToken).build();
        revokeAllUserToken(user);
tokenRepository.save(token);
return RegisterResponse.builder().token(jwtToken).build();
    }

    private void revokeAllUserToken(User user){
        var validTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if(validTokens.isEmpty()){
            return;
        }
        validTokens.forEach(t->{
            t.setExpired(true);
            t.setRevoked(true);

        });
        tokenRepository.saveAll(validTokens);
    }
    public LoginResponse login(LoginRequest request){

          authenticationManager
                  .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
           User user = userRepository.findByEmail(request.getEmail()).orElseThrow(EntityNotFoundException::new);
        var jwtToken = jwtService.generateToken(user);
        Token token = Token.builder().user(user).tokenType(TokenType.BEARER).expired(false).revoked(false).token(jwtToken).build();
        revokeAllUserToken(user);
        tokenRepository.save(token);
           return LoginResponse.builder().token(jwtToken).message("Login Success").build();

    }
}
