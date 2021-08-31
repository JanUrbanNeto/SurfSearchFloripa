package controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateTimeController {
	
	private static DateTimeController dateTimeController;
	private Timestamp dateTime;
	SimpleDateFormat formatterScreen;
	SimpleDateFormat formatterFile;
	
	public DateTimeController() {
		formatterScreen = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		formatterFile = new SimpleDateFormat("_yyyy-MM-dd_HHmmss_");
	}
	
	public static DateTimeController getInstance() {
		if(dateTimeController == null) {
			dateTimeController = new DateTimeController();
		}
		return dateTimeController;
	}
	
	public Timestamp getDateTime() {
		dateTime = new Timestamp(System.currentTimeMillis());
		return dateTime;
	}
	
	public String formatDateTimeScreen(Timestamp dateTime) {
		return formatterScreen.format(dateTime);
	}
	
	public String formatDateTimeFile(Timestamp dateTime) {
		return formatterFile.format(dateTime);
	}

}
