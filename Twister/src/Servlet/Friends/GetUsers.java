package Servlet.Friends;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Services.Friends;

public class GetUsers extends HttpServlet{
	 public GetUsers() {
		 super();
	 }
	 
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( " text / plain " );
		PrintWriter out = response.getWriter ();
		//int id_user1 =Integer.parseInt(request.getParameter("id_user1"));
		String login= request.getParameter("login");
		out.println(Friends.getUsers(login));

	 	
	 }
	 


}
