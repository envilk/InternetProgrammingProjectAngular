package es.unex.giiis.pi.dao;

import java.sql.Connection;

import es.unex.giiis.pi.model.Like;

public interface LikesDAO {

	public void setConnection(Connection conn);
	
	public boolean isLike(int idu, int idc);
	
	public void add(Like like);

	public boolean delete(int idu, int idc);
}
