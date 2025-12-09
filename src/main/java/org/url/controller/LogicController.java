package org.url.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/logic")
public class LogicController {

    @GetMapping("/compress")
    public ResponseEntity<?> compress(@RequestParam() String input) {

        if (input == null || input.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "input is required");
        }

        StringBuilder compressedString = new StringBuilder();
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            count++;

            // If it's the last character or the next character is different
            if (i + 1 >= input.length() || input.charAt(i) != input.charAt(i + 1)) {
                compressedString.append(input.charAt(i));
                compressedString.append(count);
                count = 0; // Reset count for the next character sequence
            }
        }

        return ResponseEntity.ok(Map.of("result", compressedString.toString()));
    }
}
