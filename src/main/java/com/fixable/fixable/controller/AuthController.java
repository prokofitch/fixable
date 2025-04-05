package com.fixable.fixable.controller;


import com.fixable.fixable.dto.ChangePasswordRequest;
import com.fixable.fixable.entity.User;
import com.fixable.fixable.repository.UserRepository;
import com.fixable.fixable.security.JwtUtil;
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
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ChangePasswordRequest request
            ) {
        String token = authHeader.replace("Bearer", "").trim();
        String username = jwtUtil.extractUsername(token);

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
