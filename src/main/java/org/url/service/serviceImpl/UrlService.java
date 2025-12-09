package org.url.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.url.DTO.ShortenRequest;
import org.url.entity.UrlRecord;
import org.url.entity.User;
import org.url.repository.UrlRecordRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRecordRepository urlRepo;

    public String shorten(ShortenRequest req, User creator) {

        UrlRecord record = new UrlRecord();
        record.setOwnerId(creator.getId());
        record.setOriginalUrl(req.getOriginalUrl());
        record.setAccessType(req.getAccessType());
        record.setAllowedRole(req.getAllowedRole());
        record.setMaxClicks(req.getMaxClicks());

        if (req.getExpiryInMinutes() != null) {
            record.setExpiryTime(LocalDateTime.now().plusMinutes(req.getExpiryInMinutes()));
        }

        String code = Utils.randomAlphanumeric(6);
        record.setShortCode(code);

        urlRepo.save(record);

        return "http://localhost:8080/r/" + code;
    }
}
