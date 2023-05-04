package com.web.webSocket;

import com.alibaba.fastjson.JSONObject;
import com.web.StaticSession;
import com.web.WebApplication;
import com.web.rocketMQ.Questioner;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    private Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionID", session.getId());
        jsonObject.put("question", message.getPayload());
        Questioner questioner = new Questioner();
        questioner.sendQuestion(jsonObject.toJSONString(), 0);
        StaticSession.setMyWebSocketHandler(this);
    }

    public void sendMessage(String id, String msg) throws Exception{
        System.out.println(sessions);
        if(sessions.containsKey(id)){
            WebSocketSession session = sessions.get(id);
            session.sendMessage(new TextMessage(msg));
        }
        else{
            System.out.println("没有找到sessionID");
        }
    }
}
