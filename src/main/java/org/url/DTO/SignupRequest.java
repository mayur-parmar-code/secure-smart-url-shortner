package org.url.DTO;

import lombok.Data;
import org.url.constants.Role;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private Role role;
}

