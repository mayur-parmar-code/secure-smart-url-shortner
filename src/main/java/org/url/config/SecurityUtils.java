package org.url.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public static AppUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AppUserDetails) {
            return (AppUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public static Long getCurrentUserId() {
        AppUserDetails currentUser = getCurrentUser();
        if(currentUser != null){
            return currentUser.getUser().getId();
        }
        return null;
    }
}
