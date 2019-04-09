package Servlet.Message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddMessage
 */

public class ListMessage extends HttpServlet {
    
    public ListMessage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( " text / plain " );
		PrintWriter out = response.getWriter ();
		
		String key = request.getParameter("key");
		
		out.println(Services.Message.ListMessage(key));
		}


}
