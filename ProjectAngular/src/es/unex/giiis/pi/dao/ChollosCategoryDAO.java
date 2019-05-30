package es.unex.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.unex.giiis.pi.model.ChollosCategory;


public interface ChollosCategoryDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets all the chollos and the categories related to them from the database.
	 * 
	 * @return List of all the chollos and the categories related to them from the database.
	 */
	
	public List<ChollosCategory> getAll();

	/**
	 *Gets all the ChollosCategory that are related to a category.
	 * 
	 * @param idct
	 *            Category identifier
	 * 
	 * @return List of all the ChollosCategory related to a category.
	 */
	public List<ChollosCategory> getAllByCategory(long idct);
	
	/**
	 * Gets all the ChollosCategory that contains an specific chollo.
	 * 
	 * @param idc
	 *            Chollo Identifier
	 * 
	 * @return List of all the ChollosCategory that contains an specific chollo
	 */
	public List<ChollosCategory> getAllByChollo(long idc);

	/**
	 * Gets a ChollosCategory from the DB using idc and idct.
	 * 
	 * @param idc 
	 *            Chollo identifier.
	 *            
	 * @param idct
	 *            Category Identifier
	 * 
	 * @return ChollosCategory with that idc and idct.
	 */
	
	public ChollosCategory get(long idc,long idct);

	/**
	 * Adds an chollosCategory to the database.
	 * 
	 * @param chollosCategory
	 *            ChollosCategory object with the details of the relation between the chollo and the category.
	 * 
	 * @return Chollo identifier or -1 in case the operation failed.
	 */
	
	public boolean add(ChollosCategory chollosCategory);

	/**
	 * Updates an existing chollosCategory in the database.
	 * 
	 * @param chollosCategory
	 *            ChollosCategory object with the new details of the existing relation between the chollo and the category. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean save(ChollosCategory chollosCategory);

	/**
	 * Deletes an chollosCategory from the database.
	 * 
	 * @param idc
	 *            Chollo identifier.
	 *            
	 * @param idct
	 *            Category Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long idc, long idct);
}