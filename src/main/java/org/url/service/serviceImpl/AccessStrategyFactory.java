package org.url.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.url.constants.AccessType;
import org.url.service.AccessValidationService;

@Component
@RequiredArgsConstructor
public class AccessStrategyFactory {

    private final PublicAccessService pub;
    private final PrivateAccessService pri;
    private final RoleBasedAccessService role;

    public AccessValidationService getStrategy(AccessType type) {
        return switch (type) {
            case PUBLIC -> pub;
            case PRIVATE -> pri;
            case ROLE_BASED -> role;
        };
    }
}
