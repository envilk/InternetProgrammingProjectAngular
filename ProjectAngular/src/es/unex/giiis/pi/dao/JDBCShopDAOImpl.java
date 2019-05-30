package es.unex.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.giiis.pi.model.Shop;


public class JDBCShopDAOImpl implements ShopDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCShopDAOImpl.class.getName());
	
	@Override
	public Shop get(long id) {
		if (conn == null) return null;
		
		Shop shop = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Shops WHERE id ="+id);			 
			if (!rs.next()) return null; 
			shop = new Shop();	 
			shop.setId(rs.getInt("id"));
			shop.setName(rs.getString("name"));
			shop.setUrl(rs.getString("url"));
			
			logger.info("fetching Shop by id: "+id+" -> "+shop.getId()+" "+shop.getName()+" "+shop.getUrl());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shop;
	}

	@Override
	public Shop get(String name) {
		if (conn == null) return null;
		
		Shop shop = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Shops WHERE name ='"+name+"'");			 
			if (!rs.next()) return null; 
			shop = new Shop();	 
			shop.setId(rs.getInt("id"));
			shop.setName(rs.getString("name"));
			shop.setUrl(rs.getString("url"));
			
			logger.info("fetching Shop by id: " + shop.getId()+" "+shop.getName()+" "+shop.getUrl());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shop;
	}
	
	
	public List<Shop> getAll() {

		if (conn == null) return null;
		
		ArrayList<Shop> shops = new ArrayList<Shop>();
		try {
			Statement stmt;
			ResultSet rs;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Shops");
			while ( rs.next() ) {
				Shop shop = new Shop();
				shop.setId(rs.getInt("id"));
				shop.setName(rs.getString("name"));
				shop.setUrl(rs.getString("url"));
				
				shops.add(shop);
				logger.info("fetching shops: "+shop.getId()+" "+shop.getName()+" "+shop.getUrl());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shops;
	}
	
	public List<Shop> getAllBySearchName(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Shop> shops = new ArrayList<Shop>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Shops WHERE UPPER(name) LIKE '%" + search + "%'");

			while (rs.next()) {
				Shop shop = new Shop();
				
				shop.setId(rs.getInt("id"));
				shop.setName(rs.getString("name"));
				shop.setUrl(rs.getString("url"));
				
				shops.add(shop);
				
				logger.info("fetching shops by text in the name: "+search+": "+shop.getId()+" "+shop.getName()+" "+shop.getUrl());
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return shops;
	}
	

	@Override
	public long add(Shop shop) {
		long id=-1;
		long lastid=-1;
		if (conn != null){

			Statement stmt;
			
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='shops'");			 
				if (!rs.next()) return -1; 
				lastid=rs.getInt("seq");
								
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Shops (name,url) VALUES('"
									+shop.getName()+"','"+shop.getUrl()+"')");
				
								
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='shops'");			 
				if (!rs.next()) return -1; 
				id=rs.getInt("seq");
				if (id<=lastid) return -1;
											
				logger.info("CREATING Shop("+id+"): "+shop.getName()+" "+shop.getUrl());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return id;
	}

	@Override
	public boolean save(Shop shop) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				stmt.executeUpdate("UPDATE Shops SET name='"+shop.getName()
				+"', url='"+shop.getUrl()
				+"' WHERE id = "+shop.getId());
				
				logger.info("updating Shop: "+shop.getId()+" "+shop.getName()+" "+shop.getUrl());
				
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long id) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Shops WHERE id ="+id);
				
				logger.info("deleting Shop: "+id);
				
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	
}
