package com.toufiq.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.*;
import javax.websocket.server.ServerEndpointConfig.Configurator;
public class ChatRoomServerConfigurator extends Configurator{

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		sec.getUserProperties().put("username", (String)((HttpSession) request.getHttpSession()).getAttribute("username"));
		
	}

}
