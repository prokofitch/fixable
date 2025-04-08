package com.fixable.fixable.controller;


import com.fixable.fixable.dto.ChangePasswordRequest;
import com.fixable.fixable.entity.User;
import com.fixable.fixable.repository.UserRepository;
import com.fixable.fixable.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/change-password")
    public String changePassword(
            @CookieValue("Session") String sessionToken,
            @RequestBody ChangePasswordRequest request
            ) {

        if (sessionToken == null || sessionToken.trim().isEmpty()) {
            return "Token de sessão inválido";
        }

        String username = jwtUtil.extractUsername(sessionToken);

        Optional<User> userOpt = repo.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(request.getNewPassword());
            repo.save(user);

            return "Senha alterada com sucesso!";
        }

        return "Usuário não encontrado";
    }
}
