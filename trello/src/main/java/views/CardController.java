package views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import myTrello.Card;
import myTrello.Component;
import myTrello.ComponentFactory;
import myTrello.DayComponent;
import myTrello.TopicComponent;
import myTrello.UrgencyComponent;
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
	String currentComponentType;
	String currentComponentDescription;
	Card card; 
	Map <String, String[]> compDictionary;
	
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
    	ObservableList<String> options = FXCollections.observableArrayList();
    	options.addAll(new String[]{"Day", "Urgency", "Topic"});
    	mc.showDropdownPopup("type", options, this);
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
////    		Component newComponent = new Component(message, 5); // max capacity... what? 0_o
//    		card.addComponent(newComponent, card.getOwner());
//    		mc.updateBoard();
//			addComponent(newComponent);
//    		selectedFlowPane = null;
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

    	Paint.valueOf(component.getColor());
    	newLabel.setTextFill(Paint.valueOf(component.getColor()));
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

	public void receiveCompType(String choice)
	{
		currentComponentType = choice;
		ObservableList<String> options = FXCollections.observableArrayList();

		if (choice.equals("Day"))
		{
			options.addAll(new DayComponent(null).getDescOptions());
		}
		else if (choice.equals("Urgency"))
		{
			options.addAll(new UrgencyComponent(null).getDescOptions());
		}
		else if (choice.equals("Topic"))
		{
			options.addAll(new TopicComponent(null).getDescOptions());
		}
		
		mc.showDropdownPopup("description", options, this);
	}

	public void receiveCompDesc(String choice)
	{
		currentComponentDescription = choice;
		Component component = ComponentFactory.createComponent(currentComponentType, 
				currentComponentDescription);
		card.addComponent(component, card.getOwner());
		mc.updateBoard();
		addComponent(component);
		selectedFlowPane = null;
	}
    
}