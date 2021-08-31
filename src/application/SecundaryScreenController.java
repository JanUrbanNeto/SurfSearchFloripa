package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.DateTimeController;
import controller.Facade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Search;
import model.User;

public class SecundaryScreenController implements Initializable {
	
	private Facade facade;
	private SampleController sampleController;
	private User user;
	private Search search;
	private FXMLLoader fxmlLoader;
	private ObservableList<String> choiceWaveList;
	private ObservableList<String> choiceWindList;
	private ObservableList<String> surfSpotsResult;
	private Timestamp dateTime;
	private String dateTimeFormated;
	
	@FXML
	private SplitPane splitPane;
	
	@FXML
	private AnchorPane anchorPaneLeft;
	
	@FXML
	private AnchorPane anchorPaneRight;
	
	@FXML
	private Label labelUserName;
	
	@FXML
	private Label labelUserEmail;
	
	@FXML
	private Label labelResultTimeDate;
	
	@FXML
	private Label labelTest;
	
	@FXML
	private Label labelConfirm;
	
	@FXML
	private ChoiceBox<String> choiceBoxWave;
	
	@FXML
	private ChoiceBox<String> choiceBoxWind;
	
	@FXML
	private Button buttonExit;
	
	@FXML
	private Button buttonSearch;
	
	@FXML
	private Button buttonSaveSearch;
	
	@FXML
	private Button buttonReport;
	
	@FXML
	private ListView<String> listViewResults;
	
	@FXML
	private ImageView imageViewSelectedResult;
	
	@FXML
	private ImageView imageViewIcon;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		facade = Facade.getInstance();
		
		imageViewIcon.setImage(new Image("images/icon.png"));
		imageViewIcon.setVisible(true);
		
		choiceWaveList = FXCollections.observableArrayList(
				"0 - No Waves",
				"1 - North East Waves", 
				"2 - South Waves", 
				"3 - South East Waves", 
				"4 - East Waves", 
				"5 - All Waves"
				
		);
		
		choiceBoxWave.setItems(choiceWaveList);
		
		choiceWindList = FXCollections.observableArrayList(
				"0 - No Wind",
				"1 - North Wind",
				"2 - North East Wind",
				"3 - North West Wind",
				"4 - South Wind",
				"5 - South East Wind",
				"6 - South West Wind",
				"7 - West Wind",
				"8 - East Wind",
				"9 - All Winds"
				
		);
		
		choiceBoxWind.setItems(choiceWindList);
	}
	
	public void onLoad(User u) {
		user = u;
		labelUserName.setText(user.getName());
		labelUserEmail.setText(user.getEmail());
	
	}
	
	public void noWaves() {
		listViewResults.getItems().setAll("NO SURF CONDITIONS BRO...", "GO CODE!!!!");
		imageViewSelectedResult.setImage(new Image("images/Code.jpg"));
		imageViewSelectedResult.setVisible(true);
	}
	
	public void searchSurfSpots() {
		imageViewSelectedResult.setVisible(false);
		labelTest.setText("");
		labelConfirm.setText("");
		listViewResults.getItems().setAll("");
		
		dateTime = facade.getDateTime();
		dateTimeFormated = facade.formatDateTimeScreen(dateTime);
		labelResultTimeDate.setText(dateTimeFormated);
		
		int wind = choiceBoxWind.getSelectionModel().getSelectedIndex();
		int wave = choiceBoxWave.getSelectionModel().getSelectedIndex();
		
		if(wave == 0) {
			noWaves();
		} else {
			surfSpotsResult = FXCollections.observableArrayList(facade.listBeachesNames(wind, wave));
			if(surfSpotsResult.isEmpty()) {
				noWaves();
			} else {
				listViewResults.getItems().setAll(surfSpotsResult);
			}	
		}
		
	}
	
	public void resultClicked() {
		imageViewSelectedResult.setVisible(true);
		String result = listViewResults.getSelectionModel().getSelectedItem();
		String resultNormalized = stringNormalizer(result);
		imageViewSelectedResult.setImage(new Image("images/" + resultNormalized + ".jpg"));
		labelTest.setText(result);
	}
	
	
	public void screenExit() {
		
	    try {
	    	fxmlLoader = new FXMLLoader(getClass().getResource("Sample.fxml"));
		    Stage stage = new Stage();
			stage.setScene(new Scene((Parent) fxmlLoader.load()));
			sampleController = fxmlLoader.getController();
		    sampleController.initialize();
		    stage.show();
		    buttonExit.getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveSearch() {
		search = new Search();
		search.setIdUser(user.getId());
		search.setWindDirection(choiceBoxWind.getSelectionModel().getSelectedIndex());
		search.setWaveDirection(choiceBoxWave.getSelectionModel().getSelectedIndex());
		search.setTimestamp(dateTime);
		boolean success = facade.createSearch(search);
		if(success) {
			labelConfirm.setText("Search saved!");
		} else {
			labelConfirm.setText("Search NOT saved!");
		}
		
	}
	
	public void createPdfReport() {
		dateTime = facade.getDateTime();
		dateTimeFormated = facade.formatDateTimeFile(dateTime);
		String fileName = "REPORT" + dateTimeFormated + user.getName();
		ArrayList<Search> list = facade.listSearches(user.getId());
		int i = facade.createPdfReport(fileName, list, user);
		if(i == 1) {
			labelConfirm.setText("Report created!");
		} else {
			labelConfirm.setText("Report fail!");
		}
	}
	
	public String stringNormalizer(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
}
