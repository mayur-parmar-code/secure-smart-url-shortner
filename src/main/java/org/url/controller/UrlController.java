package org.url.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.url.DTO.ShortenRequestDTO;
import org.url.config.JwtService;
import org.url.entity.User;
import org.url.repository.UserRepository;
import org.url.service.serviceImpl.UrlRecordService;
import org.url.utils.Utils;

import java.util.Map;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlRecordService urlService;
    private final UserRepository userRepo;
    private final JwtService jwtService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(@Valid @RequestBody ShortenRequestDTO req, HttpServletRequest request) {
        String token = jwtService.extractAuthToken(request);
        if (Utils.isValidStr(token)) {
            User user = userRepo.findByEmail(jwtService.extractUsername(token));
            String shortUrl = urlService.shorten(req, user);
            return ResponseEntity.ok(Map.of("shortUrl", shortUrl));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
    }
}
