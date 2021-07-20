package com.sjzc.kt.config;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Date;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.sjzc.kt.entity.RaiseEntity;

@Component
public class WebSocketEventListener {

	
	@EventListener
    public RaiseEntity handleWebSocketConnectListener(SessionConnectedEvent event) {
		RaiseEntity raiseEntity = new RaiseEntity();
		raiseEntity.setCreateTime(new Date());
        InetAddress localHost;
        try {
            localHost = Inet4Address.getLocalHost();
           // LOGGER.info("Received a new web socket connection from:" + localHost.getHostAddress() + ":" + serverPort);
        } catch (Exception e) {
            //LOGGER.error(e.getMessage(), e);
        }
        return raiseEntity;

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null) {
            //LOGGER.info("User Disconnected : " + username);
        	RaiseEntity raiseEntity = new RaiseEntity();
        	raiseEntity.setType(1);
        	//raiseEntity.setSender(username);
            try {
                //redisTemplate.opsForSet().remove(onlineUsers, username);
                //redisTemplate.convertAndSend(userStatus, JsonUtil.parseObjToJson(chatMessage));
            } catch (Exception e) {
               // LOGGER.error(e.getMessage(), e);
            }

        }
    }
}
