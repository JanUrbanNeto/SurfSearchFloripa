package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.User;
import util.JdbcUtil;

public class UserPersistence {
	
	private static UserPersistence userPersistence;
	private User user;
	private ArrayList<User> users;
	private String sql;
	private PreparedStatement ps;
	private ResultSet rs;
	private Connection con;
	private boolean success;
	
	public static UserPersistence getInstance() {
		if(userPersistence == null) {
			userPersistence = new UserPersistence();
		}
		return userPersistence;
	}
	
	public boolean createUser(User user) {
		
		success = false;
		sql = "INSERT INTO user (name, email, master) VALUES (?, ?, ?)";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setBoolean(3, user.isMaster());
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

	public int verifyUserName(String name) {
		
		int i = 0;
		sql = "SELECT COUNT(name) AS total FROM user WHERE name = ?";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, name);
			
			rs = ps.executeQuery();
			rs.next();
			i = rs.getInt("total");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
	public int verifyUserEmail(String email) {
		
		int i = 0;
		sql = "SELECT COUNT(email) AS total FROM user WHERE email = ?";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			rs.next();
			i = rs.getInt("total");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
	public User selectUserByNameEmail(String name, String email) {
		
		user = new User();
		sql = "SELECT * FROM user WHERE name = ? AND email = ?";
			
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, name);
			ps.setString(2, email);
			
			rs = ps.executeQuery();
			rs.next(); 
			
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setMaster(rs.getBoolean("master"));
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public ArrayList<User> listAllUsers() {
		
		sql = "SELECT * FROM user ORDER BY name";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setMaster(rs.getBoolean("master"));
				
				users.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "";
		JdbcUtil.closeConnection(con);
		return users;
	}
	
	public boolean deleteUser(int id) {
		success = false;
		sql = "DELETE FROM user WHERE id = ?";
		
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
	
	
	public boolean updateUser(User user) {
		
		success = false;
		sql = "UPDATE user SET name = ?, email = ? WHERE id = ? ";
		
		try {
			
			con = JdbcUtil.getConnetion();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			
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
