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
    String type;

    @FXML
    void onClickDontSaveButton(ActionEvent event) 
    {
    	stage.close();
    	leave();
    }

    @FXML
    void onClickSaveButton(ActionEvent event) 
    {
    	mc.saveData();
    	stage.close();
    	leave();
    }
    
    private void leave()
    {
    	if (type.equals("back"))
    	{
    		mc.goBack();
    	}
    	else if (type.equals("exit"))
    	{
    		mc.exit();
    	}
    }
    
    public void setModel(String message, String type, Stage stage, MainController mc)
    {
    	messageLabel.setText(message);
    	this.type = type;
    	this.stage = stage;
    	this.mc = mc;
    }

}
