package com.example.invoicerservice.web.dto;

import com.example.invoicerservice.domain.Authority;
import com.example.invoicerservice.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The UserDto class represents a data transfer object containing information about a user. It is used by the user
 * controller to send user data to the client.
 */
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean activated;
    private Set<String> authorities;

    /**
     * Constructs a new UserDto object with default values for all fields.
     */
    public UserDto() {}

    /**
     * Constructs a new UserDto object with the specified user data.
     *
     * @param user the user object to extract data from
     */
    public UserDto(User user) {

        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.activated = user.isActivated();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
    }

    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id the user ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user username.
     *
     * @return the user username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user username.
     *
     * @param username the user username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user email.
     *
     * @return the user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user email.
     *
     * @param email the user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user password.
     *
     * @return the user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user password.
     *
     * @param password the user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user first name.
     *
     * @return the user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user first name.
     *
     * @param firstName the user first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the user last name.
     *
     * @return the user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user last name.
     *
     * @param lastName the user last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns whether the user is activated.
     *
     * @return true if the user is activated, false otherwise
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Sets whether the user is activated.
     *
     * @param activated true if the user is activated, false otherwise
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Returns the user authorities.
     *
     * @return the user authorities
     */
    public Set<String> getAuthorities() {
        return authorities;
    }

    /**
     * Sets the user authorities.
     *
     * @param authorities the user authorities
     */
    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}