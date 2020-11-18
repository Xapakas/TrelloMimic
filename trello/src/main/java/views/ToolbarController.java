package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ToolbarController 
{
	MainController mc;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;
    
    public void setMC(MainController mc)
    {
    	this.mc = mc;
    }

    @FXML
    void onClickBack(ActionEvent event) 
    {
    	mc.goBack();
    }

    @FXML
    void onClickSave(ActionEvent event) 
    {
    	mc.saveData();
    }

}
