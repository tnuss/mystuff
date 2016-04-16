package com.orcasdev.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HomePageDispatcher
 */
@WebServlet("/HomePageDispatcher")
public class HomePageDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePageDispatcher() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		return null;
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		return null; 
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	 String txtInput1 = request.getParameter("txtInput1");
	 HttpSession session = request.getSession(true); //Will create first time
	 //session.setAttribute("username", username);
	 //session.setAttribute("password", password);
	 try {
	   response.setContentType("text/html");
	   PrintWriter writer = response.getWriter();
	   writer.println("<html><head><title>Doing Input 1</title></head><body>");
	   writer.println("Thank you, " + txtInput1 +
	                  ". You have entered data into the system.");
	   //String newURL = response.encodeURL("GetSession");
		//writer.println("Click <a href=\"" + newURL + "\">here</a> for another servlet");
	   writer.println("</body></html>");
	   writer.close();
	 } catch (Exception e) {
	   e.printStackTrace();
	 }
	}

}
