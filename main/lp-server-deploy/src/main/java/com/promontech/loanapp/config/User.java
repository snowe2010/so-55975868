package com.promontech.loanapp.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import org.hibernate.usertype.UserType;
import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({ "password" })
public class User extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 7189732424186053938L;

    private String tenantId;
    private String organizationId;
    private String userId;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private UserType userType;
    private String suffix;
    private String titleDescription;
    private WorkforceAuthorityBitSet authorityBitSet;
    private ExternalAuthorityBitSet externalAuthorityBitSet;

    @JsonCreator
    public User(@NotNull @JsonProperty("tenantId") String tenantId,
                @JsonProperty("organizationId") String organizationId,
                @NotNull @JsonProperty("userId") String userId,
                @NotNull @JsonProperty("username") String username,
                @JsonProperty("email") String email,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("middleName") String middleName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("userType") UserType userType,
                @JsonProperty("suffix") String suffix,
                @JsonProperty("titleDescription") String titleDescription,
                @JsonProperty("enabled") boolean enabled,
                @JsonProperty("accountNonExpired") boolean accountNonExpired,
                @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired,
                @JsonProperty("accountNonLocked") boolean accountNonLocked,
                @JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities,
                @JsonProperty("authority") WorkforceAuthorityBitSet authorityBitSet,
                @JsonProperty("externalAuthority") ExternalAuthorityBitSet externalAuthorityBitSet) {

        super(username,
              "",
              enabled,
              accountNonExpired,
              credentialsNonExpired,
              accountNonLocked,
              parseAuthorities(authorities, authorityBitSet, externalAuthorityBitSet));

        this.tenantId = tenantId;
        this.organizationId = organizationId;
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userType = userType;
        this.suffix = suffix;
        this.titleDescription = titleDescription;
        this.authorityBitSet = parseAuthorityBitSet(authorities, authorityBitSet);
        this.externalAuthorityBitSet = parseExternalAuthorityBitSet(authorities, externalAuthorityBitSet);
    }

    /**
     * Parses ${@link WorkforceAuthority}s from the provided ${@link WorkforceAuthorityBitSet} and ${@link ExternalAuthorityBitSet}
     */
    public static Collection<? extends GrantedAuthority> parseAuthorities(Collection<? extends GrantedAuthority> originalAuthorities,
                                                                          WorkforceAuthorityBitSet authorityBitSet,
                                                                          ExternalAuthorityBitSet externalAuthorityBitSet) {

        Collection<GrantedAuthority> workforceGrantedAuthorities = new HashSet<>();
        if (authorityBitSet != null) {
            workforceGrantedAuthorities = authorityBitSet.getGrantedAuthorities();
        }
        Collection<GrantedAuthority> externalGrantedAuthorities = new HashSet<>();
        if (externalAuthorityBitSet != null) {
            externalGrantedAuthorities = externalAuthorityBitSet.getGrantedAuthorities();
        }
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.addAll(workforceGrantedAuthorities);
        grantedAuthorities.addAll(externalGrantedAuthorities);

        if (grantedAuthorities.isEmpty() && originalAuthorities != null) {
            return originalAuthorities;
        }
        return grantedAuthorities;
    }

    public static WorkforceAuthorityBitSet parseAuthorityBitSet(Collection<? extends GrantedAuthority> authorities,
                                                                WorkforceAuthorityBitSet authorityBitSet) {
        if (authorityBitSet != null) {
            return authorityBitSet;
        } else if (authorities != null) {
            return new WorkforceAuthorityBitSet(authorities);
        }
        return null;
    }

    public static ExternalAuthorityBitSet parseExternalAuthorityBitSet(Collection<? extends GrantedAuthority> authorities,
                                                                       ExternalAuthorityBitSet externalAuthorityBitSet) {
        if (externalAuthorityBitSet != null) {
            return externalAuthorityBitSet;
        } else if (authorities != null) {
            return new ExternalAuthorityBitSet(authorities);
        }
        return null;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public String getUserId() {
        return this.userId;
    }

    @JsonIgnore
    public Collection<org.springframework.security.core.GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final User user = (User) other;
        return Objects.equals(tenantId, user.tenantId) &&
                Objects.equals(organizationId, user.organizationId) &&
                Objects.equals(userId, user.userId) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(middleName, user.middleName) &&
                Objects.equals(lastName, user.lastName) &&
                userType == user.userType &&
                Objects.equals(suffix, user.suffix) &&
                Objects.equals(titleDescription, user.titleDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                            tenantId,
                            organizationId,
                            userId,
                            email,
                            firstName,
                            middleName,
                            lastName,
                            userType,
                            suffix,
                            titleDescription);
    }

    @Override
    public String toString() {
        return "{" +
                "tenantId=\"" + tenantId + '\"' +
                ", organizationId=\"" + organizationId + '\"' +
                ", userId=\"" + userId + '\"' +
                ", username=\"" + getUsername() + '\"' +
                ", email=\"" + email + '\"' +
                ", firstName=\"" + firstName + '\"' +
                ", middleName=\"" + middleName + '\"' +
                ", lastName=\"" + lastName + '\"' +
                ", userType=\"" + userType + '\"' +
                ", suffix=\"" + suffix + '\"' +
                ", titleDescription=\"" + titleDescription + '\"' +
                ", authority=\"" + authorityBitSet + '\"' +
                ", externalAuthority=\"" + externalAuthorityBitSet + '\"' +
                '}';
    }

}
