package org.url.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.url.service.RedirectService;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectService service;

    @GetMapping("/r/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code, HttpServletRequest request) {
        String url = service.handleRedirect(code, request);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", url)
                .build();
    }
}
