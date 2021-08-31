package application;

import java.io.IOException;

import controller.Facade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.User;

public class SampleController {
	
	private Facade facade;
	private FXMLLoader fxmlLoader;
	private User user;
	
	@FXML
	private Pane centerPane;
	
	@FXML
	private TextField textFieldName;
	
	@FXML
	private TextField textFieldEmail;
	
	@FXML
	private Label warningLabel;
	
	@FXML
	private Button buttonOK;
	
	@FXML
	private ImageView imageViewIcon1;
	
	@FXML
	private ImageView imageViewIcon2;
	
	@FXML
	public void initialize() {
		 facade = Facade.getInstance();
		 imageViewIcon2.setImage(new Image("images/icon2.png"));
		 imageViewIcon2.setVisible(true);
		 imageViewIcon1.setImage(new Image("images/icon1.png"));
		 imageViewIcon1.setVisible(true);
	} 
	
	public void logIn(ActionEvent ev) {
		
		if(textFieldName.getText().equals("") || textFieldEmail.getText().equals("")) {
			warningLabel.setText("Fill out forms!!!");
		} else if (!textFieldName.getText().equals("") && !textFieldEmail.getText().equals("")) {	
			if(!(textFieldEmail.getText().contains(".com") || textFieldEmail.getText().contains(".edu")) || !textFieldEmail.getText().contains("@") || textFieldEmail.getText().equals("@.com") || textFieldEmail.getText().equals("@.edu")) {
				warningLabel.setText("Invalid e-mail!");
			} else {
				if(facade.verifyUserEmail(textFieldEmail.getText()) > 0 && facade.verifyUserName(textFieldName.getText()) > 0) {
					user = facade.returnUser(textFieldName.getText(), textFieldEmail.getText());
					load("SecundaryScreen.fxml");
				} else if (facade.verifyUserEmail(textFieldEmail.getText()) > 0 && facade.verifyUserName(textFieldName.getText()) == 0) {	
					warningLabel.setText("Wrong user name...");
				} else if (facade.verifyUserEmail(textFieldEmail.getText()) == 0 && facade.verifyUserName(textFieldName.getText()) > 0) {	
					warningLabel.setText("Wrong user e-mail...");	
				} else {
					user = new User(textFieldName.getText(), textFieldEmail.getText(), false);
					boolean success = facade.createUser(user);
					if(success) {
						load("SecundaryScreen.fxml");
					} else {
						warningLabel.setText("Something wrong with database!!!");
					}
				}
			}
		}
	}
	
	public void load(String fxml) {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
			centerPane.getChildren().add(fxmlLoader.load());
			SecundaryScreenController controller = fxmlLoader.getController();
			controller.onLoad(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public User getUser() {
		return user;
	}
	
	
}
