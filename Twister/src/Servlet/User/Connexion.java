package Servlet.User;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Connexion() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( " text / plain " );
		PrintWriter out = response.getWriter ();
		
		String log = request.getParameter("login");
		String psw = request.getParameter("password");
		
		out.println((Services.User.connexion(log, psw)).toString());
		
	
	}
	



}
