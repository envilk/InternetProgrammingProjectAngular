package es.unex.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.giiis.pi.model.ChollosCategory;

public class JDBCChollosCategoryDAOImpl implements ChollosCategoryDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCChollosCategoryDAOImpl.class.getName());

	@Override
	public List<ChollosCategory> getAll() {

		if (conn == null) return null;
						
		ArrayList<ChollosCategory> chollosCategoryList = new ArrayList<ChollosCategory>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cholloscategory");
						
			while ( rs.next() ) {
				ChollosCategory chollosCategory = new ChollosCategory();
				chollosCategory.setIdc(rs.getInt("idc"));
				chollosCategory.setIdct(rs.getInt("idct"));
						
				chollosCategoryList.add(chollosCategory);
				logger.info("fetching all chollosCategory: "+chollosCategory.getIdc()+" "+chollosCategory.getIdct());
					
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return chollosCategoryList;
	}

	@Override
	public List<ChollosCategory> getAllByCategory(long idct) {
		
		if (conn == null) return null;
						
		ArrayList<ChollosCategory> chollosCategoryList = new ArrayList<ChollosCategory>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ChollosCategory WHERE idct="+idct);

			while ( rs.next() ) {
				ChollosCategory chollosCategory = new ChollosCategory();
				chollosCategory.setIdc(rs.getInt("idc"));
				chollosCategory.setIdct(rs.getInt("idct"));

				chollosCategoryList.add(chollosCategory);
				logger.info("fetching all chollosCategory by idc: "+chollosCategory.getIdc()+"->"+chollosCategory.getIdct());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return chollosCategoryList;
	}
	
	@Override
	public List<ChollosCategory> getAllByChollo(long idc) {
		
		if (conn == null) return null;
						
		ArrayList<ChollosCategory> chollosCategoryList = new ArrayList<ChollosCategory>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ChollosCategory WHERE idc="+idc);

			while ( rs.next() ) {
				ChollosCategory chollosCategory = new ChollosCategory();
				chollosCategory.setIdc(rs.getInt("idc"));
				chollosCategory.setIdct(rs.getInt("idct"));
							
				chollosCategoryList.add(chollosCategory);
				logger.info("fetching all chollosCategory by idct: "+chollosCategory.getIdct()+"-> "+chollosCategory.getIdc());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return chollosCategoryList;
	}
	
	
	@Override
	public ChollosCategory get(long idc,long idct) {
		if (conn == null) return null;
		
		ChollosCategory cholloCategory = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ChollosCategory WHERE idc="+idc+" AND idct="+idct);			 
			if (!rs.next()) return null;
			cholloCategory= new ChollosCategory();
			cholloCategory.setIdc(rs.getInt("idc"));
			cholloCategory.setIdct(rs.getInt("idct"));

			logger.info("fetching chollosCategory by idc: "+cholloCategory.getIdc()+"  and idct: "+cholloCategory.getIdct());
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return cholloCategory;
	}
	
	

	@Override
	public boolean add(ChollosCategory chollosCategory) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO ChollosCategory (idc,idct) VALUES('"+
									chollosCategory.getIdc()+"','"+
									chollosCategory.getIdct()+"')");
						
				logger.info("creating ChollosCategory:("+chollosCategory.getIdc()+" "+chollosCategory.getIdct());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save(ChollosCategory chollosCategory) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM ChollosCategory WHERE idc="+chollosCategory.getIdc());			 
				if (!rs.next()) 
					return false;
				long idct = rs.getInt("idct");

				stmt.executeUpdate("UPDATE ChollosCategory SET idct="+chollosCategory.getIdct()
				+" WHERE idc = "+chollosCategory.getIdc() + " AND idct = " + idct);
				
				logger.info("updating ChollosCategory:("+chollosCategory.getIdc()+" "+chollosCategory.getIdct());
				
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idc, long idct) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM ChollosCategory WHERE idc ="+idc+" AND idct="+idct);
				logger.info("deleting ChollosCategory: "+idc+" , idct="+idct);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}
	
}
