package Servlet.User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Services.User;

public class CreateUser extends HttpServlet {
	public CreateUser() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		String log = request.getParameter("login");
		String psw = request.getParameter("password");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String mail = request.getParameter("mail");
		
		//JSONObject o = new JSONObject();
		JSONObject jo = User.createUser(log, psw, nom, prenom, mail);
		//marche pas sur le serveur mais en local si
		out.println(jo.toString());

	}

}
