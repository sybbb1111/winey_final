package com.green.winey_final.auth;

import com.green.winey_final.auth.model.AuthDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public ResponseEntity signIn(HttpServletRequest req, HttpServletResponse res, AuthDto authDto) {
        return null;
    }

}
