package Servlet.Message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

public class GetMessageUser extends HttpServlet {
	public GetMessageUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( " text / plain " );
		PrintWriter out = response.getWriter ();
		
		String login = request.getParameter("login");
		//int idU=Integer.parseInt(idUser);
		
		
			try {
				out.println(Services.Message.getMessageUser(login));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}

}
