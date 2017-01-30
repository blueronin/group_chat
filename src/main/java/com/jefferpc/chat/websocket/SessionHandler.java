/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jefferpc.chat.websocket;

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
    private final List<String> messages = new ArrayList<>();

    /**
     *
     * @param session
     */
    public void addSession(Session session) {
        sessions.add(session);
        for (User user : users) {
            JsonObject addMessage = createAddMessage(user);
            sendToSession(session, addMessage);
        }
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
    public void addUser(User user) {
        if (validateUser(user)) {
            users.add(user);
            JsonObject addMessage = createAddMessage(user);
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

    public void addMessage(String message, User user) {
        messages.add(user.getUserName() + ": " + message);
    }

    public List<String> getMessages() {
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
    private JsonObject createAddMessage(User user) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("username", user.getUserName())
                .add("status", user.getStatus())
                .build();
        return addMessage;
    }

    /**
     *
     * @param user
     * @return
     */
    private boolean validateUser(User user) {
        return !users.contains(user);
    }

}
