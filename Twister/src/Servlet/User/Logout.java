package Servlet.User;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Services.User;

public class Logout extends HttpServlet{
	public Logout() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("json");
		PrintWriter out = response.getWriter();
		String key = request.getParameter("key");
		JSONObject o = new JSONObject();
		User.deconnexion(key);
		out.print(o.toString());
		//marche pas sur le serveur mais en local si

		
	}
}
