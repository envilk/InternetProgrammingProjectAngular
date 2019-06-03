package es.unex.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.unex.giiis.pi.dao.JDBCUserDAOImpl;
import es.unex.giiis.pi.dao.UserDAO;
import es.unex.giiis.pi.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(
		urlPatterns = { "/LoginServlet.do" }
		)
public class LoginServlet extends HttpServlet {	private static final long serialVersionUID = 1L;
private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

/**
 * @see HttpServlet#HttpServlet()
 */
public LoginServlet() {
	super();
	// TODO Auto-generated constructor stub
}

/**
 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	HttpSession session = request.getSession();
	if (session.getAttribute("user")== null) 
		logger.info("------------------------NONE USER LOGGED-----------------------");
	response.sendRedirect("/ProjectAngular");
}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Connection conn = (Connection) getServletContext().getAttribute("dbConn");
	UserDAO userDao = new JDBCUserDAOImpl();
	userDao.setConnection(conn);

	String email = request.getParameter("email");
	String password = request.getParameter("password");		

	User user = userDao.getEmail(email);

	if ((user != null) 
			&& (user.getPassword().equals(password)))
	{
		HttpSession session = request.getSession();
		session.setAttribute("user",user);
	} 
	else {
		logger.info("------------------------BAD CREDENTIALS-----------------------");
	}	
	response.sendRedirect("/ProjectAngular");
}

}

