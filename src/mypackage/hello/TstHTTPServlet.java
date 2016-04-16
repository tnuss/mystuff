package mypackage.hello;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test2Servlet
 */
@WebServlet("/TstHTTPServlet")
public class TstHTTPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TstHTTPServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		TestHello varHello = new TestHello();
		
		out.println (
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" +" +
                    "http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "<html> \n" +
                  "<head> \n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; " +
                      "charset=ISO-8859-1\"> \n" +
                    "<title> Testing HTTP Servelet from JSP  </title> \n" +
                  "</head> \n" +
                  "<body> <div align='center'> \n" +
                    "<style= \"font-size=\"12px\" color='black'\"" + "\">" +
                      " ---------- This is returned from TstHTTPServlet ---------" + " <br> " +
                    "\n" + varHello.getResponse() +
                  "</font></body> \n" +
                "</html>" 
              );      
	}

}
