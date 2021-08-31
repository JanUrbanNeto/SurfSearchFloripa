package controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import model.Search;
import model.User;

public class Facade {
	
	private static Facade facade;
	private UserController userController;
	private BeachController beachController;
	private DateTimeController dateTimeController;
	private SearchController searchController;
	private PdfFileController pdfFileController;
	private User user;
	
	public static Facade getInstance() {
		if(facade == null) {
			facade = new Facade();
		}
		return facade;
	}
	
	public Facade() {
		userController = UserController.getInstance();
		beachController = BeachController.getInstance();
		dateTimeController = DateTimeController.getInstance();
		searchController = SearchController.getInstance();
		pdfFileController = PdfFileController.getInstance();		
	}
	
	public int verifyUserEmail(String email) {
		int i = userController.verifyEmail(email);
		return i;
	}
	
	public int verifyUserName(String name) {
		int i = userController.verifyName(name);
		return i;
	}
	
	public User returnUser(String name, String email) {
		user = userController.returnUser(name, email);
		return user;
	}
	
	public boolean createUser(User user) {
		boolean success = userController.createUser(user);
		return success;
	}
	
	public ArrayList<String> listBeachesNames(int wind, int wave) {
		ArrayList<String> beachesNames = beachController.listBeachesNames(wind, wave);
		return beachesNames;
	}
	
	public Timestamp getDateTime() {
		Timestamp dateTime = dateTimeController.getDateTime();
		return dateTime;
	}
	
	public String formatDateTimeScreen(Timestamp dateTime) {
		String dateTimeFormated = dateTimeController.formatDateTimeScreen(dateTime);
		return dateTimeFormated;
	}
	
	public String formatDateTimeFile(Timestamp dateTime) {
		String dateTimeFormated = dateTimeController.formatDateTimeFile(dateTime);
		return dateTimeFormated;
	}
	
	public boolean createSearch(Search search) {
		boolean success = searchController.createSearch(search);
		return success;
	}
	
	public int createPdfReport(String fileName, ArrayList<Search> list, User user) {
		int i = pdfFileController.createPdfReport(fileName, list, user);
		return i;
	}
	
	public ArrayList<Search> listSearches(int idUser) {
		ArrayList<Search> searches = searchController.listSearches(idUser);
		return searches;
	}

}
