
package com.jefferpc.chat.model;

/**
 *
 * @author Ing. Jefferson Pérez Cervera
 */
public class Message {
    private User user;
    private String message;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
