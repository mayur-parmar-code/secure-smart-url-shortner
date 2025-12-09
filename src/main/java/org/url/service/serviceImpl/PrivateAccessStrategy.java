package org.url.service.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.url.entity.UrlRecord;
import org.url.entity.User;
import org.url.service.AccessValidationStrategy;

@Component
public class PrivateAccessStrategy implements AccessValidationStrategy {

    @Override
    public void validate(UrlRecord record, User user) {
        if (user == null || !record.getOwnerId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not the owner");
    }
}
