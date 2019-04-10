package Servlet.Friends;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Services.Friends;

public class RemoveFriend extends HttpServlet {
	public RemoveFriend() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		String key = request.getParameter("key");
		int id_friend =Integer.parseInt(request.getParameter("id_friend"));
		
		out.println(Friends.RemoveFriend(key, id_friend));
	}

}
