package com.promontech.loanapp.config;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Concrete implementation of {@link GrantedAuthority}. Necessary to facilitate proper serialization and
 * deserialization.
 *
 * @author mkazarian
 */
public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority;

    @JsonCreator
    public GrantedAuthority(@JsonProperty("authority") @NotNull String authority) {
        this.authority = authority;
    }

    @NotNull
    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final GrantedAuthority that = (GrantedAuthority) other;
        return Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authority);
    }

    @Override
    public String toString() {
        return "GrantedAuthority{" +
                "authority='" + authority + '\'' +
                '}';
    }
}
