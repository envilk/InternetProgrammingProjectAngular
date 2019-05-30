package es.unex.giiis.pi.resources;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import es.unex.giiis.pi.dao.JDBCUserDAOImpl;
import es.unex.giiis.pi.dao.UserDAO;
import es.unex.giiis.pi.model.User;
import es.unex.giiis.pi.resources.exceptions.CustomBadRequestException;



@Path("/users")
public class UsersResource {

	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		return user; 
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putUserJSON(User newUser, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Response response = null;

		//We check that the user exists
		Map<String, String> messages = new HashMap<String, String>();
		if (newUser.validateName(messages) && user != null) {
			userDao.save(newUser);
			session.setAttribute("user", newUser);
		}
		else throw new CustomBadRequestException("Errors in parameters");		

		return response;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postUserJSON(User newUser, @Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
			
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Response res;

		Map<String, String> messages = new HashMap<String, String>();
		long id;
		if ((!newUser.validateName(messages)) || (userDao.getUsername(newUser.getUsername()) != null)
				|| user == null)//An user can have same email but not same username
			throw new CustomBadRequestException("Errors in parameters");
		else {  //save chollo in DB
			id = userDao.add(newUser);
			newUser = userDao.getUsername(newUser.getUsername());
			session.setAttribute("user", newUser);
		}

		res = Response //return 201 and Location: /user/newid
				.created(
						uriInfo.getAbsolutePathBuilder()
						.path(Long.toString(id))
						.build())
				.contentLocation(
						uriInfo.getAbsolutePathBuilder()
						.path(Long.toString(id))
						.build())
				.build();
		return res; 
	}

	@DELETE
	public Response deleteUserJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		User returnUser = userDao.get(user.getId());
		if(returnUser != null && user != null) { 
			userDao.delete(returnUser.getId());
			session.invalidate();
			return Response.noContent().build(); //204 no content
		}
		else throw new CustomBadRequestException("Error in user or id");	
	}
} 
