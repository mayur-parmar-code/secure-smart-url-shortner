package org.url.service.serviceImpl;

import org.springframework.stereotype.Component;
import org.url.entity.UrlRecord;
import org.url.entity.User;
import org.url.service.AccessValidationStrategy;

@Component
public class PublicAccessStrategy implements AccessValidationStrategy {

    @Override
    public void validate(UrlRecord record, User user) {

    }
}
