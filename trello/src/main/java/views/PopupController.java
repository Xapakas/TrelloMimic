package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopupController
{
	Stage stage;
	
	@FXML
	Label popupLabel;
	
	@FXML
    private Button closeButton;

    @FXML
    void onClickCloseButton(ActionEvent event) {
    	stage.close();
    }
	
	public void setModel(String popupString, Stage stage)
	{
		popupLabel.setText(popupString);
		this.stage = stage;
	}
}
