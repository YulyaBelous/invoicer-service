package com.example.invoicerservice.web.dto;

/**
 * The LoginDto class represents a data transfer object containing login credentials. It is used by the authentication
 * controller to receive login requests from the client.
 */
public class LoginDto {

    private String username;
    private String password;

    /**
     * Constructs a new LoginDto object with the specified username and password.
     *
     * @param username the username
     * @param password the password
     */
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
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
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
