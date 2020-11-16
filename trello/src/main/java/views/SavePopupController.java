package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SavePopupController
{

    @FXML
    private Label messageLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button dontSaveButton;
    
    Stage stage;
    MainController mc;

    @FXML
    void onClickDontSaveButton(ActionEvent event) 
    {
    	stage.close();
    	mc.goBack();
    }

    @FXML
    void onClickSaveButton(ActionEvent event) 
    {
    	mc.saveData();
    	mc.setIsUnsavedData(false);
    	stage.close();
    	mc.goBack();
    }
    
    public void setModel(String message, Stage stage, MainController mc)
    {
    	messageLabel.setText(message);
    	this.stage = stage;
    	this.mc = mc;
    }

}
