package com.atendestartup.authentication.DTO;

import com.atendestartup.authentication.Enum.UserRole;

public record RegisterDTO(String id, String login, String password, UserRole role) {

}
