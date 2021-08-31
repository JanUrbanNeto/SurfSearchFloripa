package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import controller.Facade;
import model.Search;
import model.User;
import util.JdbcUtil;

public class SearchPersistence {
	
	private static SearchPersistence searchPersistence;
	private Facade facade;
	private Search search;
	private ArrayList<Search> searches;
	private String sql;
	private PreparedStatement ps;
	private ResultSet rs;
	private Connection con;
	private boolean success;
	
	public static SearchPersistence getInstance() {
		if(searchPersistence == null) {
			searchPersistence = new SearchPersistence();
		}
		return searchPersistence;
	}
	
	public boolean createSearch(Search search) {
		
		success = false;
		sql = "INSERT INTO search (id_user, wind, wave, datetime) VALUES (?, ?, ?, ?)";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, search.getIdUser());
			ps.setInt(2, search.getWindDirection());
			ps.setInt(3, search.getWaveDirection());
			ps.setTimestamp(4, search.getTimestamp());
			int i = ps.executeUpdate();
			
			if(i == 1) {
				success = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sql="";
		JdbcUtil.closeConnection(con);
		return success;
	}
	
	public ArrayList<Search> listSearches(int idUser) {
		
		searches = new ArrayList<>();
		sql = "SELECT * FROM search WHERE id_user = ?";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, idUser);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				search = new Search();
				search.setId(rs.getInt("id"));
				search.setIdUser(rs.getInt("id_user"));
				search.setWindDirection(rs.getInt("wind"));
				search.setWaveDirection(rs.getInt("wave"));
				search.setTimestamp(rs.getTimestamp("datetime"));
				
				searches.add(search);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sql="";
		JdbcUtil.closeConnection(con);
		return searches;
	}
	
}
