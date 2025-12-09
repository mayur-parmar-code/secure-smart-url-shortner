package org.url.DTO;

import lombok.Data;
import org.url.constants.AccessType;
import org.url.constants.Role;

@Data
public class ShortenRequest {
    private String originalUrl;
    private AccessType accessType;
    private Role allowedRole;
    private Long expiryInMinutes;
    private Long maxClicks;
}
