package com.example.invoicerservice.security;

import java.util.*;
import java.util.stream.Collectors;

import com.example.invoicerservice.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private boolean activated;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String username, String email, String password, String firstName, String lastName, boolean activated, Collection<? extends GrantedAuthority> authorities) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activated = activated;
        this.authorities = authorities;
    }

    public static CustomUserDetails build(User user) {

        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.isActivated(), authorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isActivated() {
        return activated;
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomUserDetails user = (CustomUserDetails) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
