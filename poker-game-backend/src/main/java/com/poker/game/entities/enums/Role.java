package com.poker.game.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_PLAYER;

    @Override
    public String getAuthority() {
        return name();
    }
}
