package com.example.invoicerservice.web.response;

import java.util.List;

/**
 * The JwtResponse class represents a JSON response containing a JWT access token and user details. It is used by the
 * authentication controller to return a response to the client after a successful authentication request.
 */
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean activated;
    private List<String> authorities;

    /**
     * Constructs a new JwtResponse object with the specified access token, user ID, username, email, authorities,
     * first name, last name, and activation status.
     *
     * @param accessToken the JWT access token
     * @param id the user ID
     * @param username the username
     * @param email the email address
     * @param authorities the user's authorities
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param activated the user's activation status
     */
    public JwtResponse(String accessToken, Long id, String username, String email, List<String> authorities, String firstName, String lastName, boolean activated) {

        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.authorities = authorities;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activated = activated;
    }

    /**
     * Returns the JWT access token.
     *
     * @return the JWT access token
     */
    public String getAccessToken() {
        return token;
    }

    /**
     * Sets the JWT access token.
     *
     * @param accessToken the JWT access token
     */
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    /**
     * Returns the token type.
     *
     * @return the token type
     */
    public String getTokenType() {
        return type;
    }

    /**
     * Sets the token type.
     *
     * @param tokenType the token type
     */
    public void setTokenType(String tokenType) {
        this.type = tokenType;
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
     * Returns the email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address.
     *
     * @param email the email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's authorities.
     *
     * @return the user's authorities
     */
    public List<String> getAuthorities() {
        return authorities;
    }

    /**
     * Sets the user's authorities.
     *
     * @param authorities the user's authorities
     */
    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    /**
     * Returns the user's first name.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the user's last name.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     *
     * @param lastName the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the user's activation status.
     *
     * @return the user's activation status
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Sets the user's activation status.
     *
     * @param activated the user's activation status
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}