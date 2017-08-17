/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jefferpc.chat.websocket;

import com.jefferpc.chat.model.Message;
import com.jefferpc.chat.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

/**
 *
 * @author Ing. Jefferson Pérez Cervera
 */
@ApplicationScoped
public class SessionHandler {

    private final Set<Session> sessions = new HashSet<>();
    private final Set<User> users = new LinkedHashSet<>();
    private final List<Message> messages = new ArrayList<>();

    /**
     *
     * @param session
     */
    public void addSession(Session session) {
        sessions.add(session);

    }

    /**
     *
     * @param session
     */
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    /**
     *
     * @param user
     */
    public void addUser(User user,Session session) {
        if (validateUser(user)) {
            for (Message message : messages) {
                JsonObject addMessage = createAddMessage(message);
                sendToSession(session, addMessage);
            }
            users.add(user);
            Message message = new Message();
            message.setMessage(user.getUserName() + " has connected!.");
            message.setUser(user);
            JsonObject addMessage = createAddMessage(message);
            sendToAllConnectedSessions(addMessage);
        }

    }

    /**
     *
     * @param user
     */
    public void removeUser(User user) {
        users.remove(user);
    }

    public void addMessage(String text, User user) {
        Message message = new Message();
        message.setMessage(text);
        message.setUser(user);
        messages.add(message);
        JsonObject addMessage = createAddMessage(message);
        sendToAllConnectedSessions(addMessage);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Set<User> getUsers() {
        return users;
    }

    /**
     * send message to all sessions
     *
     * @param message
     */
    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    /**
     * Send message to specific session
     *
     * @param session
     * @param message
     */
    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * create message to send
     *
     * @param user
     * @return
     */
    private JsonObject createAddMessage(Message message) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "addMessage")
                .add("username", message.getUser().getUserName())
                .add("message", message.getMessage())
                .build();
        return addMessage;
    }

    /**
     *
     * @param user
     * @return
     */
    private boolean validateUser(User user) {
        for (User userTemp : users) {
            if(userTemp.getUserName().equals(user.getUserName())){
                return false;
            }
        }
        return true;
    }

}
