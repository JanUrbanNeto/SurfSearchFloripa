package controller;

import java.util.ArrayList;

import persistence.BeachPersistence;

public class BeachController {
	
	private static BeachController beachController;
	private BeachPersistence beachPersistence;
	
	public BeachController() {
		beachPersistence = BeachPersistence.getInstance();
	}
	
	public static BeachController getInstance() {
		if(beachController == null) {
			beachController = new BeachController();
		}
		return beachController;
	}
	
	public ArrayList<String> listBeachesNames(int wind, int wave) {
		ArrayList<String> beachesNames = beachPersistence.listBeachesNames(wind, wave);
		return beachesNames;
	}

}
