package com.learning.security.service;

import com.learning.security.model.Role;
import com.learning.security.model.Token;
import com.learning.security.model.TokenType;
import com.learning.security.model.User;
import com.learning.security.payload.request.AuthenticationRequest;
import com.learning.security.payload.request.RegisterRequest;
import com.learning.security.payload.response.AuthenticationResponse;
import com.learning.security.repository.TokenRepository;
import com.learning.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var userSaved = userRepository.save(user);
        var jwtToken = jwtService.generatedToken(user);
        saveUserToken(userSaved, jwtToken);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse authentication(AuthenticationRequest request) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                  request.getPassword()
          )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email Not Found"));
        var jwtToken = jwtService.generatedToken(user);
        revokeAllUserToken(user);
        saveUserToken(user, jwtToken);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserToken(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty()) return;
        for (Token validUserToken : validUserTokens) {
            validUserToken.setExpired(true);
            validUserToken.setRevoked(true);
        }
        tokenRepository.saveAll(validUserTokens);
    }
}
