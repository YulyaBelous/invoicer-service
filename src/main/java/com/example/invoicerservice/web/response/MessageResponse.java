package com.example.invoicerservice.web.response;

/**
 * The MessageResponse class represents a JSON response containing a message. It is used by the REST API to return a
 * response to the client after a successful request.
 */
public class MessageResponse {

    private String message;

    /**
     * Constructs a new MessageResponse object with the specified message.
     *
     * @param message the message to include in the response
     */
    public MessageResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the message included in the response.
     *
     * @return the message included in the response
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message included in the response.
     *
     * @param message the message to include in the response
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
