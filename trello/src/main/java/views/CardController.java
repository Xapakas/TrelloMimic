package views;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import myTrello.Card;
import myTrello.Component;
import myTrello.User;
import myTrello.BLabel;

public class CardController implements ControllerInterface
{
	MainController mc;
	GridPane parentGrid;
	HashSet<BLabel> blabels;
	ArrayList<Component> components;
	ArrayList<User> members;
	FlowPane selectedFlowPane;
	Card card;
	int addedLabels = 0;
	int addedComponents = 0;
	int addedMembers = 0;

    @FXML
    private FlowPane labelsFlowPane;

    @FXML
    private FlowPane componentsFlowPane;

    @FXML
    private FlowPane membersFlowPane;

    @FXML
    private Button addLabelButton;

    @FXML
    private Button addComponentButton;

    @FXML
    private Button addMemberButton;

    @FXML
    private Label cardNameLabel;

    @FXML
    void onClickAddComponent(ActionEvent event) 
    {
    	selectedFlowPane = componentsFlowPane;
    	mc.showInputPopupView("Enter the name of your new component!", this);
    }

    @FXML
    void onClickAddLabel(ActionEvent event) 
    {
    	selectedFlowPane = labelsFlowPane;
    	mc.showInputPopupView("Enter the name of your new label!", this);
    }

    @FXML
    void onClickAddMember(ActionEvent event) 
    {
    	selectedFlowPane = membersFlowPane;
    	mc.showInputPopupView("Enter the name of your new card member!", this);
    }
    
    public void receiveString(String message)
	{
    	if (selectedFlowPane == componentsFlowPane)
    	{
    		Component newComponent = new Component(message, 5); // max capacity... what? 0_o
    		card.addComponent(newComponent, card.getOwner());
    		mc.updateBoard();
			addComponent(newComponent);
    		selectedFlowPane = null;
    	}
    	else if (selectedFlowPane == labelsFlowPane)
    	{
    		BLabel newBLabel = new BLabel(message);
    		card.addLabel(newBLabel, card.getOwner());
    		mc.updateBoard();
    		addBLabel(newBLabel);
    	}
    	else if (selectedFlowPane == membersFlowPane)
    	{
    		ArrayList<User> users = mc.getAllUsers();
			for (User user : users)
			{
				if (user.getName().equals(message))
				{
					card.addUser(user, card.getOwner());
					mc.updateBoard();
					addMember(user);
				}
			}
    	}
	}
    
    public void addBLabel(BLabel blabel)
    {
    	addedLabels++;
    	Label newLabel = new Label(blabel.getText());
    	newLabel.setId("blabelLabel" + addedLabels);
		labelsFlowPane.getChildren().add(newLabel);
    }
    
    public void addComponent(Component component)
    {
    	addedComponents++;
    	Label newLabel = new Label(component.getDescription());
    	newLabel.setId("componentLabel" + addedComponents);
    	componentsFlowPane.getChildren().add(newLabel);
    }
    
    public void addMember(User user)
    {
    	addedMembers++;
    	Label newLabel = new Label(user.getName());
    	newLabel.setId("memberLabel" + addedMembers);
    	membersFlowPane.getChildren().add(newLabel);
    }
    
    public void setupScene(Card card, int index, MainController mc, GridPane parentGrid)
    {
    	this.mc = mc;
    	this.parentGrid = parentGrid;
    	this.card = card;
    	
    	cardNameLabel.setText(card.getName());
    	cardNameLabel.setId("cardNameLabel" + index);
    	
    	blabels = card.getLabels().getMembers();
    	components = card.getComponents().getMembers();
    	members = card.getUsers().getMembers();
    	
    	for (BLabel blabel : blabels)
    	{
    		addBLabel(blabel);
    	}
    	
    	for (Component component : components)
    	{
    		addComponent(component);
    	}
    	
    	for (User member : members)
    	{
    		addMember(member);
    	}
    }
    
}