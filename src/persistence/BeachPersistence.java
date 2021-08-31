package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Beach;
import model.WaveDirection;
import model.WindDirection;
import util.JdbcUtil;

public class BeachPersistence {
	
	private static BeachPersistence beachPersistence;
	private Beach beach;
	private ArrayList<String> beachesNames;
	private String sql = "";
	private PreparedStatement ps;
	private ResultSet rs;
	private Connection con;
	private boolean success;
	
	public static BeachPersistence getInstance() {
		if(beachPersistence == null) {
			beachPersistence = new BeachPersistence();
		}
		return beachPersistence;
	}
	
	public boolean createBeach(Beach beach) {
		
		success = false;
		sql = "INSERT INTO beach (name, wind, wave) VALUES (?, ?, ?)";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, beach.getName());
			ps.setInt(2, beach.getWindDirection());
			ps.setInt(3, beach.getWaveDirection());
			int i = ps.executeUpdate();
			
			if(i == 1) {
				success = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sql = "";
		JdbcUtil.closeConnection(con);
		return success;
	}
	
	public ArrayList<String> listBeachesNames(int wind, int wave) {
		
		beachesNames = new ArrayList<>();
		
		try {
			
			con = JdbcUtil.getConnetion();
			
			if((wind != WindDirection.ALL_WINDS && wind != WindDirection.NO_WIND) && wave == WaveDirection.ALL_WAVES) {
				sql = "SELECT DISTINCT name FROM beach WHERE wind = ? ORDER BY name";
				ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, wind);

			} else if ((wind == WindDirection.ALL_WINDS || wind == WindDirection.NO_WIND) && wave != WaveDirection.ALL_WAVES) {
				sql = "SELECT DISTINCT name FROM beach WHERE wave = ? ORDER BY name";
				ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, wave);
				
			} else if ((wind == WindDirection.ALL_WINDS || wind == WindDirection.NO_WIND) && wave == WaveDirection.ALL_WAVES) {
				sql = "SELECT DISTINCT name FROM beach ORDER BY name";
				ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			} else {
				sql = "SELECT DISTINCT name FROM beach WHERE wind = ? AND wave = ? ORDER BY name";
				ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, wind);
				ps.setInt(2, wave);
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				beachesNames.add(rs.getString("name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "";
		JdbcUtil.closeConnection(con);
		return beachesNames;
	}
	
	public boolean deleteBeach(int id) {
		success = false;
		sql = "DELETE FROM beach WHERE id = ?";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id);
			int i = ps.executeUpdate();
			
			if(i == 1) {
				success = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sql = "";
		JdbcUtil.closeConnection(con);
		return success;
	}
	
	
	public boolean updateBeach(Beach beach) {
		
		success = false;
		sql = "UPDATE beach SET name = ?, wind = ?, wave = ? WHERE id = ? ";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, beach.getName());
			ps.setInt(2, beach.getWindDirection());
			ps.setInt(3, beach.getWaveDirection());
			ps.setInt(4, beach.getId());
			
			int i = ps.executeUpdate();
			
			if(i == 1) {
				success = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}	

}
