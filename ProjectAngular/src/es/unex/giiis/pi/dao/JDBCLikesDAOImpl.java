package es.unex.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.logging.Logger;

import es.unex.giiis.pi.model.Like;

public class JDBCLikesDAOImpl implements LikesDAO{

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCCholloDAOImpl.class.getName());

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean isLike(int idu, int idc) {

		if (conn == null)
			return false;

		boolean is = false;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM likes WHERE idu = "+ idu +" AND idc = "+idc);

			Like like = new Like();

			like.setIdu(rs.getInt("idu"));
			like.setIdc(rs.getInt("idc"));

			is = true;

			logger.info("fetching chollos by text either in the title or in the description: user: "+like.getIdu()+" chollo: "+like.getIdc());				

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return is;
	}

	@Override
	public void add(Like like) {
		if (conn != null){

			Statement stmt;

			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO likes (idu,idc) VALUES("
						+like.getIdu()+","+like.getIdc() +")");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public boolean delete(int idu, int idc) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM likes WHERE idu = "+idu+" AND idc = "+idc);
				logger.info("deleting like where user: "+idu+" and chollo: "+idc);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

}
