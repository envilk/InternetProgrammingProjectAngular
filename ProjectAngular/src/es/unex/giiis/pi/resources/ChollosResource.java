package es.unex.giiis.pi.resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import es.unex.giiis.pi.dao.JDBCCholloDAOImpl;
import es.unex.giiis.pi.dao.CholloDAO;
import es.unex.giiis.pi.model.Chollo;
import es.unex.giiis.pi.model.User;
import es.unex.giiis.pi.resources.exceptions.CustomBadRequestException;
import es.unex.giiis.pi.resources.exceptions.CustomNotFoundException;

@Path("/chollos")
public class ChollosResource {

	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Chollo> getChollosJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);

		List<Chollo> chollos;
		chollos = cholloDao.getAll();

		return chollos; 
	}

	@GET
	@Path("/chollosRelated/{cholloid: [0-9]+}")  
	@Produces(MediaType.APPLICATION_JSON)
	public List<Chollo> getChollosUserJSON(@Context HttpServletRequest request,
			@PathParam("cholloid") long cholloid) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);

		Chollo chollo = cholloDao.get(cholloid);

		List<List<Chollo>> chollosByTitle = new LinkedList<List<Chollo>>();
		List<List<Chollo>> chollosByDesc = new LinkedList<List<Chollo>>();
		List<Chollo> chollos = new ArrayList<Chollo>();

		//BY TITLE
		
		String[] parts2 = chollo.getTitle().split(" ");
		for (String string : parts2) {
			chollosByTitle.add(cholloDao.getAllBySearchAll(string));
		}
		for (List<Chollo> list : chollosByTitle) {
			chollos.addAll(list);
		}
		
		//BY DESC

		String[] parts = chollo.getDescription().split(" ");
		for (String string : parts) {
			chollosByDesc.add(cholloDao.getAllBySearchAll(string));
		}
		for (List<Chollo> list : chollosByDesc) {
			chollos.addAll(list);
		}
		
		//DUPLICATES

		List<Long> listWithDuplicates = new LinkedList<Long>();
		for (Chollo aux : chollos) {
			listWithDuplicates.add(aux.getId());
		}
		
	    List<Long> listWithoutDuplicates = listWithDuplicates.stream()
	     .distinct()
	     .collect(Collectors.toList());
	   
	    for(int i=0;i<listWithoutDuplicates.size();i++) {
	    	if(chollo.getId() == listWithoutDuplicates.get(i))
	    		listWithoutDuplicates.remove(i);
	    }
	    
	    List<Chollo> chollosRelated = new ArrayList<Chollo>();
	    for (Long long1 : listWithoutDuplicates) {
	    	chollosRelated.add(cholloDao.get(long1));
		}

		return chollosRelated; 
	}


	@GET
	@Path("/orderBylikes/")	  
	@Produces(MediaType.APPLICATION_JSON)
	public List<Chollo> getChollosOrderedByLikesJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);

		List<Chollo> chollos;
		chollos = cholloDao.getAll();
		Collections.sort(chollos, new Comparator<Chollo>() {
			public int compare(Chollo one, Chollo other) {
				Integer likesOne, likesTwo;
				likesOne = one.getLikes();
				likesTwo = other.getLikes(); 
				return likesOne.compareTo(likesTwo);
			}
		});

		return chollos; 
	}

	@GET
	@Path("/showAvailable/")	  
	@Produces(MediaType.APPLICATION_JSON)
	public List<Chollo> getChollosShowAvailableJSON(@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);

		List<Chollo> chollos = new ArrayList<Chollo>();
		List<Chollo> aux = new ArrayList<Chollo>();
		aux = cholloDao.getAll();
		for (Chollo chollo : aux) {
			if(chollo.getSoldout() == 0)//Si no esta vendido
				chollos.add(chollo);
		}

		return chollos; 
	}

	@GET
	@Path("/search/{search: [a-zA-Z0-9_]+}")	  
	@Produces(MediaType.APPLICATION_JSON)
	public List<Chollo> getChollosAllBySearchJSON(@Context HttpServletRequest request, @PathParam("search") String search) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn); 
		List<Chollo> chollos = cholloDao.getAllBySearchAll(search);

		return chollos; 
	}

	@GET
	@Path("/{cholloid: [0-9]+}")	  
	@Produces(MediaType.APPLICATION_JSON)
	public Chollo getCholloJSON(@PathParam("cholloid") long cholloid,
			@Context HttpServletRequest request) {
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);

		Chollo chollo = cholloDao.get(cholloid);

		if (chollo != null) 
			return chollo;
		else throw new CustomNotFoundException("Chollo ("+ cholloid + ") is not found");		   
	}



	@POST	  	  
	@Consumes(MediaType.APPLICATION_JSON)
	public Response post(@Context HttpServletRequest request, Chollo newChollo) throws Exception {	
		Connection conn = (Connection) sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);	  	 

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Response res;
		Map<String, String> messages = new HashMap<String, String>();
		logger.info(newChollo.getImage());
		
		if ((!newChollo.validate(messages)) || user == null)
			throw new CustomBadRequestException("Errors in parameters");
		//save chollo in DB
		newChollo.setIdu(user.getId());
		long id = cholloDao.add(newChollo);

		res = Response //return 201 and Location: /chollos/newid
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


	@PUT
	@Path("/{cholloid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response put(Chollo cholloUpdate,
			@PathParam("cholloid") long cholloid,
			@Context HttpServletRequest request) throws Exception{
		Connection conn = (Connection)sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Response response = null;

		//We check that the chollo exists
		Chollo chollo = cholloDao.get(cholloUpdate.getId());
		if ((chollo != null)
				&&((user.getId() == chollo.getIdu()))){
			if (chollo.getId()!=cholloid) throw new CustomBadRequestException("Error in id");
			else 
			{
				Map<String, String> messages = new HashMap<String, String>();
				if (cholloUpdate.validate(messages)) cholloDao.save(cholloUpdate);						
				else throw new CustomBadRequestException("Errors in parameters");						
			}
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);			

		return response;
	}



	@PUT
	@Path("/soldout/{cholloid: [0-9]+}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putSoldout(
			@Context HttpServletRequest request, 
			@PathParam("cholloid") long cholloid) throws Exception{
		Connection conn = (Connection)sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Response response = null;
		//We check that the chollo exists
		Chollo chollo = cholloDao.get(cholloid);
		if(chollo.getSoldout() == 0)
			chollo.setSoldout(1);
		else
			chollo.setSoldout(0);

		if ((chollo != null)
				&&((user.getId() == chollo.getIdu()))){
			if (chollo.getId()!=cholloid) throw new CustomBadRequestException("Error in id");
			else 
			{
				Map<String, String> messages = new HashMap<String, String>();
				if (chollo.validate(messages)) cholloDao.save(chollo);						
				else throw new CustomBadRequestException("Errors in parameters");						
			}
		}
		else throw new WebApplicationException(Response.Status.NOT_FOUND);			

		return response;
	}




	@DELETE
	@Path("/{cholloid: [0-9]+}")	  
	public Response deleteChollo(@PathParam("cholloid") long cholloid,
			@Context HttpServletRequest request) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CholloDAO cholloDao = new JDBCCholloDAOImpl();
		cholloDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Chollo chollo = cholloDao.get(cholloid);
		if ((chollo != null)
				&&((user.getId() == chollo.getIdu()))){
			cholloDao.delete(cholloid);
			return Response.noContent().build(); //204 no content 
		}
		else throw new CustomBadRequestException("Error in user or id");		

	}

} 
