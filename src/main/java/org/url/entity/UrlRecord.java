package org.url.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.url.constants.AccessType;
import org.url.constants.Role;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_records")
@Data
public class UrlRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;

    private String originalUrl;

    @Column(unique = true)
    private String shortCode;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    @Enumerated(EnumType.STRING)
    private Role allowedRole;

    private LocalDateTime expiryTime;

    private LocalDateTime createdAt = LocalDateTime.now();

    private Long clickCount = 0L;

    private Long maxClicks;
}
