package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputPopupController
{
	ControllerInterface parentCont;
	Stage stage;
	
    @FXML
    private Label messageLabel;

    @FXML
    private TextField inputTextField;

    @FXML
    private Button submitButton;

    @FXML
    void onClickSubmit(ActionEvent event) 
    {
    	String inputText = inputTextField.getText();
    	parentCont.receiveString(inputText);
    	stage.close();
    }
    
    public void setModel(String message, Stage stage, ControllerInterface parentCont)
    {
    	this.parentCont = parentCont;
    	this.stage = stage;
    	messageLabel.setText(message);
    }

}
