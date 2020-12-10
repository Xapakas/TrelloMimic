package views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DropdownController
{
	CardController parentCont;
	Stage stage;
	String typeOrDesc;
	
	@FXML
    private Label messageLabel;

    @FXML
    private ListView<String> optionsListView;

    @FXML
    private Button submitButton;

    @FXML
    void onClickSubmit(ActionEvent event) 
    {
    	String choice = optionsListView.getSelectionModel().getSelectedItem();
    	
    	if (typeOrDesc.equals("type"))
    	{
    		parentCont.receiveCompType(choice);
    	}
    	else if (typeOrDesc.equals("description"))
    	{
    		parentCont.receiveCompDesc(choice);
    	}
    	
    	stage.close();
    }
    
    public void setupScene(String typeOrDesc, ObservableList<String> options, 
    		CardController parentCont, Stage stage)
    {
    	messageLabel.setText("Choose a component " + typeOrDesc + "!");
    	optionsListView.setItems(options);
    	this.parentCont = parentCont;
    	this.stage = stage;
    	this.typeOrDesc = typeOrDesc;
    }
}
