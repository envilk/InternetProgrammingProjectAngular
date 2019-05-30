package es.unex.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.giiis.pi.model.Chollo;


public interface CholloDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	
	/**
	 * Gets a chollo from the DB using id.
	 * 
	 * @param id
	 *            Chollo Identifier.
	 * 
	 * @return Chollo object with that id.
	 */
	public Chollo get(long id);

	/**
	 * Gets all the notes from the database.
	 * 
	 * @return List of all the chollos from the database.
	 */
	public List<Chollo> getAll();
	
	/**
	 * Gets all the chollos from the database that contain a text in the title.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the chollos from the database that contain a text either in the title.
	 */	
	public List<Chollo> getAllBySearchTitle(String search);


	/**
	 * Gets all the chollos from the database that belong to a user.
	 * 
	 * @param idu
	 *            User identifier.
	 * 
	 * @return List of all the chollos from the database that belong to a user
	 */	
	public List<Chollo> getAllByUser(long idu);
	
	
	/**
	 * Gets all the chollos from the database that contain a text in the description.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the chollos from the database that contain a text in the description.
	 */	
	public List<Chollo> getAllBySearchDescription(String search);
	
	/**
	 * Gets all the chollos from the database that contain a text either in the title or in the description.
	 * 
	 * @param search
	 *            Search string .
	 * 
	 * @return List of all the chollos from the database that contain a text either in the title or in the description.
	 */	
	public List<Chollo> getAllBySearchAll(String search);

	/**
	 * Adds a chollo to the database.
	 * 
	 * @param chollo
	 *            Chollo object with the chollo details.
	 * 
	 * @return Chollo identifier or -1 in case the operation failed.
	 */
	
	public long add(Chollo chollo);

	/**
	 * Updates an existing chollo in the database.
	 * 
	 * @param chollo
	 *            Chollo object with the new details of the existing chollo.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	public boolean save(Chollo chollo);

	/**
	 * Deletes a chollo from the database.
	 * 
	 * @param id
	 *            Chollo identifier.
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	public boolean delete(long id);
}
