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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import es.unex.giiis.pi.dao.CholloDAO;
import es.unex.giiis.pi.dao.JDBCCholloDAOImpl;
import es.unex.giiis.pi.dao.JDBCLikesDAOImpl;
import es.unex.giiis.pi.dao.LikesDAO;
import es.unex.giiis.pi.model.Chollo;
import es.unex.giiis.pi.model.Like;
import es.unex.giiis.pi.model.User;

@Path("/likes")
public class LikesResource {

	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;


	@POST	  	  
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(@Context HttpServletRequest request, String cholloId) throws Exception {	
		Connection conn = (Connection) sc.getAttribute("dbConn");

		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);	  	 

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if(user != null) {
			LikesDAO likesDao = new JDBCLikesDAOImpl();
			likesDao.setConnection(conn);
			int idu, idc;
			idu = (int) user.getId();
			idc = Integer.parseInt(cholloId);
			logger.info("----------------------- user: "+idu+"   chollo: "+idc);

			Like like = new Like();
			like.setIdu(idu);
			like.setIdc(idc);

			Chollo chollo = cholloDao.get(idc);
			if(likesDao.isLike(idu, idc)){
				logger.info("----------------ARRIVES IF-------------------");
				likesDao.delete(idu, idc);
				chollo.setLikes(chollo.getLikes()-1);
			}
			else {
				logger.info("----------------ARRIVES ELSE-------------------");
				likesDao.add(like);
				chollo.setLikes(chollo.getLikes()+1);
			}

			cholloDao.save(chollo);
		}
		Response res;

		res = Response //return 201 and Location: /likes/newid
				.created(
						uriInfo.getAbsolutePathBuilder()
						.path(cholloId)
						.build())
				.contentLocation(
						uriInfo.getAbsolutePathBuilder()
						.path(cholloId)
						.build())
				.build();
		return res; 
	}
}
