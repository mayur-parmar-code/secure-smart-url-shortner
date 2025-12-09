package org.url.service;

import org.url.entity.UrlRecord;
import org.url.entity.User;

public interface AccessValidationStrategy {
    void validate(UrlRecord record, User user);
}
