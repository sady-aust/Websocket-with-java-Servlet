package com.toufiq.websocket;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ChatroomServerEndpoint",configurator = ChatRoomServerConfigurator.class)
public class ChatroomServerEndpoint {
	static Set<Session> chatRoomUsers = new HashSet<Session>();
	
	@OnOpen
	public void handleOpen(EndpointConfig endpointConfig,Session userSession) {
		userSession.getUserProperties().put("username", endpointConfig.getUserProperties().get("username"));
		
		chatRoomUsers.add(userSession);
	}
	
	@OnClose
	public void handleClose(Session userSession) {
		System.out.println("Close");
		chatRoomUsers.remove(userSession);
	}
	
	@OnError
	public void handleError(Throwable t) {
	}
	
	@OnMessage
	public void handleMessage(String message, Session userSession) {
		System.out.println("USer MEssage ");
		System.out.println(message);
		String username = (String) userSession.getUserProperties().get("username");
		System.out.println(chatRoomUsers.size());
		if(username !=null) {
			chatRoomUsers.stream().forEach(x ->{
				try {
					x.getBasicRemote().sendText(buildJsonData(username, message));
					System.out.println("Sent");
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			});
		}

	}
	
	private String buildJsonData(String username,String message) {
		JsonObject jsonObject = Json.createObjectBuilder().add("message", username+": "+message).build();
		StringWriter stringWriter = new StringWriter();
		try {
			JsonWriter jsonWriter = Json.createWriter(stringWriter);
			jsonWriter.writeObject(jsonObject);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return stringWriter.toString();
	}
}	
