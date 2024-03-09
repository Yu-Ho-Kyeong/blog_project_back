package com.blog.blogback.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CustomUserDetails implements UserDetails {
    @Id @GeneratedValue
    @Column(name = "userNo", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "userPw", nullable = false)
    private String userPw;

    private String name;

    private String imgPath;

    private String introduction;

    private char enabled;
    
    @Column(name = "role")
    private String role;  

    @Builder
    public CustomUserDetails(Long id, String userId, String userPw, String role,
        String name, String introduction, String imgPath, char enabled) 
    {
        Assert.hasText(userId, "userId must not be empty");
        Assert.hasText(userPw, "userPw must not be empty");

        this.id = id;
        this.userId = userId;
        this.userPw = userPw;
        this.role = role;
        this.name = name;
        this.introduction = introduction;
        this.imgPath = imgPath;
        this.enabled = '1';
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role));
        return authorities;
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

    @Override
    public String getPassword() {
        return this.userPw;
    }

    @Override
    public String getUsername() {
       return this.userId;
    }
}