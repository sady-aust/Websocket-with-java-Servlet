package com.toufiq.websocket;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UsernameServlet
 */
@WebServlet("/UsernameServlet")
public class UsernameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		HttpSession httpSession = request.getSession(true);
		String username = request.getParameter("username");
		httpSession.setAttribute("username", username);
		
		if(username != null) {
			printWriter.println("<html>");
			printWriter.println("<body>");
			printWriter.println("<mark>Username :"+username+"</mark><br>");
			printWriter.println("<textarea id=\"messagesTestArea\" readonly=\"readonly\" rows=\"10\" cols = \"45\"></textarea><br/><br/>");
			printWriter.println("<input type = \"text\" id = \"messageText\" size= \"50\"/>");
			printWriter.println("<input type = \"button\" value = \"Send\" onclick= \"sendMessage();\"/>");
			printWriter.println("<script>");
			printWriter.println("var websocket = new WebSocket(\"ws://localhost:8085/WebSocektProject/ChatroomServerEndpoint\");");
			printWriter.println("websocket.onmessage = function processMessage(message){");
			printWriter.println("var jsonData = JSON.parse(message.data);");
			printWriter.println("if(jsonData.message != null) messagesTestArea.value += jsonData.message + \"\\n\";");
			printWriter.println("console.log(jsonData)");
			printWriter.println("}");
			printWriter.println("function sendMessage(){");
			printWriter.println("	websocket.send(messageText.value);");
			printWriter.println("	messageText.value = \"\";");
			printWriter.println("}");
			printWriter.println("</script>");
			printWriter.println("</body>");
			printWriter.println("</html>");
			
		}
		
	}

}
