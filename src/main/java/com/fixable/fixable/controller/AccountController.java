package com.fixable.fixable.controller;

import com.fixable.fixable.dto.JwtResponse;
import com.fixable.fixable.dto.LoginRequest;
import com.fixable.fixable.dto.RegisterRequest;
import com.fixable.fixable.entity.User;
import com.fixable.fixable.repository.UserRepository;
import com.fixable.fixable.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AccountController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        User user = new User(request.getUsername(), request.getPassword());

        Optional<User> userOpt = repo.findByUsername(request.getUsername());

        if(userOpt.isPresent()) {
            return "Usuário já cadastrado! Realize o login!";
        }

        repo.save(user);

        return "Usuário registrado!";
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Optional<User> userOpt = repo.findByUsername(request.getUsername());

        if(userOpt.isPresent() && userOpt.get().getPassword().equals(request.getPassword())) {
            String token = jwtUtil.generateToken(userOpt.get().getUsername());

            Cookie cookie = new Cookie("Session", token);
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setMaxAge(24 * 60 * 60);

            response.addCookie(cookie);

            return new JwtResponse(token);
        }

        throw new RuntimeException("Credenciais inválidas!");
    }
}
