/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmduquer.groupchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author cmduquer
 */
@ServerEndpoint(value = "/chat/{room}/{nickname}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {

    private final Logger log = Logger.getLogger(getClass().getName());

    @OnOpen
    public void open(final Session session, @PathParam("room") final String room, @PathParam("nickname") final String nickname) {
        log.info("session openend and bound to room: " + room);
        log.info("session openend and bound to nickname: " + nickname);
        session.getUserProperties().put("room", room);
        session.getUserProperties().put("nickname", nickname);
        int totalNickName = 0;
        for (Session s : session.getOpenSessions()) {
                System.out.println(nickname);
                if (s.getUserProperties().get("nickname").equals(nickname)) {
                    totalNickName++;
                }
        }
        if (totalNickName > 1) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "NickName alreay exits"));
            } catch (IOException ex) {
                Logger.getLogger(ChatEndpoint.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    @OnClose
    public void onClose(CloseReason reason) {
        System.out.println("Closing connection");
        System.out.println("Reason" + reason);
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {
        String room = (String) session.getUserProperties().get("room");
        String nickname = (String) session.getUserProperties().get("nickname");
        log.info(nickname);
        try {
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()
                        && room.equals(s.getUserProperties().get("room"))) {
                    s.getBasicRemote().sendObject(chatMessage);
                }
            }
        } catch (IOException | EncodeException e) {
            log.log(Level.WARNING, "onMessage failed", e);
        }
    }
}
