package com.melodymarket.domain.user.model;

import com.melodymarket.application.dto.UserDto;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Setter
@ToString
public class Account implements UserDetails {
    private Long id;
    private String userId;
    private String username;
    private String userPasswd;
    private String nickname;
    private String email;
    private String birthDate;
    private String joinDate;
    private Integer membershipLevel;


    public String getUserId() {
        return userId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userPasswd;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public UserDto convertToUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUserId(this.getUserId());
        userDto.setUsername(this.getUsername());
        userDto.setNickname(this.nickname);
        userDto.setEmail(this.email);
        userDto.setBirthDate(this.birthDate);
        userDto.setMembershipLevel(this.membershipLevel);
        return userDto;
    }
}
