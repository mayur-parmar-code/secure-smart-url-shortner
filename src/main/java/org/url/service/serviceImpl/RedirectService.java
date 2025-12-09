package org.url.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.url.config.JwtService;
import org.url.entity.UrlRecord;
import org.url.entity.User;
import org.url.repository.UrlRecordRepository;
import org.url.repository.UserRepository;
import org.url.service.AccessValidationStrategy;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RedirectService {

    private final UrlRecordRepository repo;
    private final UserRepository userRepo;
    private final AccessStrategyFactory strategyFactory;
    private final JwtService jwtService;

    public String handleRedirect(String code, String token) {

        UrlRecord record = repo.findByShortCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (record.getExpiryTime() != null &&
                LocalDateTime.now().isAfter(record.getExpiryTime()))
            throw new ResponseStatusException(HttpStatus.GONE, "URL expired");

        if (record.getMaxClicks() != null &&
                record.getClickCount() >= record.getMaxClicks())
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Max clicks reached");

        User user = null;
        if (token != null && token.startsWith("Bearer ")) {
            user = userRepo.findByEmail(jwtService.extractUsername(token));
        }

        AccessValidationStrategy strategy =
                strategyFactory.getStrategy(record.getAccessType());
        strategy.validate(record, user);

        record.setClickCount(record.getClickCount() + 1);
        repo.save(record);

        return record.getOriginalUrl();
    }
}
