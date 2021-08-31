package controller;

import java.util.ArrayList;

import model.Search;
import persistence.SearchPersistence;

public class SearchController {
	
	private static SearchController searchController;
	private SearchPersistence searchPersistence;
	
	public SearchController() {
		searchPersistence = SearchPersistence.getInstance();
	}
	
	public static SearchController getInstance() {
		if(searchController == null) {
			searchController = new SearchController();
		}
		return searchController;
	}
	
	public boolean createSearch(Search search) {
		boolean success = searchPersistence.createSearch(search);
		return success;
	}
	
	public ArrayList<Search> listSearches(int idUser) {
		ArrayList<Search> searches = searchPersistence.listSearches(idUser);
		return searches;
	}

}
