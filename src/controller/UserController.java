package controller;

import model.User;
import persistence.UserPersistence;

public class UserController {
	
	private static UserController userController;
	private UserPersistence userPersistence;
	private User user;

	public UserController() {
		userPersistence = UserPersistence.getInstance();
	}
	
	public static UserController getInstance() {
		if(userController == null) {
			userController = new UserController();
		}
		return userController;
	}
	
	public int verifyEmail(String email) {
		int i = userPersistence.verifyUserEmail(email);
		return i;
	}
	
	public int verifyName(String name) {
		int i = userPersistence.verifyUserName(name);
		return i;
	}
	
	public User returnUser(String name, String email) {
		user = userPersistence.selectUserByNameEmail(name, email);
		return user;
	}
	
	public boolean createUser(User user) {
		boolean success = userPersistence.createUser(user);
		return success;
	}
	
}
