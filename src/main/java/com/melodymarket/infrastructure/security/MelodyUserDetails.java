package com.melodymarket.infrastructure.security;

import com.melodymarket.domain.user.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MelodyUserDetails implements UserDetails {

    private Long id;
    private String loginId;
    private String username;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public MelodyUserDetails(Account account, Collection<? extends GrantedAuthority> authorities) {
        this.username = account.getUsername();
        this.id = account.getId();
        this.loginId = account.getLoginId();
        this.password = account.getUserPasswd();
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getLoginId() {
        return loginId;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
