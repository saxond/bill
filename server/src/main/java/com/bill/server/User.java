package com.bill.server;

import com.google.api.services.oauth2.model.Userinfo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String email;
    private String familyName;
    private String givenName;
    private String name;
    private String id;
    private String locale;
    private String picture;
    private boolean verifiedEmail;
    private boolean enabled;

    public static User from(Userinfo userinfo) {
        var user = new User();
        user.enabled = false;
        user.email = userinfo.getEmail();
        user.verifiedEmail = userinfo.isVerifiedEmail();
        user.id = userinfo.getId();
        user.locale = userinfo.getLocale();
        user.familyName = userinfo.getFamilyName();
        user.givenName = userinfo.getGivenName();
        user.name = userinfo.getName();
        user.picture = userinfo.getPicture();
        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isVerifiedEmail() {
        return verifiedEmail;
    }

    public void setVerifiedEmail(boolean verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public SafeUser safe() {
        return new SafeUser(name, enabled);
    }

    public Authentication asAuthentication() {
        return new AuthenticatedUser() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return User.this;
            }

            @Override
            public boolean isAuthenticated() {
                return enabled;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            }
        };
    }

    public record SafeUser(String name, boolean valid) {
    }
}
