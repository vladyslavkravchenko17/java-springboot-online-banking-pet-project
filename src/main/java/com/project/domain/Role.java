package com.project.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN"),
    STUFF("STUFF");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public boolean equalsName(String otherName) {
        return authority.equals(otherName);
    }

    public String toString() {
        return this.authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}