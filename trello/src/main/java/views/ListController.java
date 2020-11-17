package views;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import main.Main;
import myTrello.BList;
import myTrello.Card;

public class ListController implements ControllerInterface
{
	MainController mc;
	BList blist;
	GridPane parentGrid;
	ArrayList<Card> cards;

    @FXML
    private Accordion cardAccordion;

    @FXML
    private Button addNewCardButton;

    @FXML
    private Label listNameLabel;

    @FXML
    void onClickAddNewCard(ActionEvent event) 
    {
    	mc.showInputPopupView("Enter the name of your new card!", this);
    }
    
    public void addCardView(Card card)
    {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/CardView.fxml"));
		try
		{
			TitledPane view = loader.load();
			CardController cont = loader.getController();
			cont.setupScene(card, mc, parentGrid);
			
	    	cardAccordion.getPanes().add(view);

		} catch (IOException e)
		{
			e.printStackTrace();
		}
    }
    
    public void setupScene(BList blist, MainController mc, BoardController bc)
    {
    	this.blist = blist;
    	this.mc = mc;
    	parentGrid = bc.listGridPane;
    	listNameLabel.setText(blist.getName());
    	
    	cards = blist.getCards().getMembers();
    	for (Card card : cards)
    	{
    		addCardView(card);
    	}
    	// accordion.getPanes().add(pane1);
    }

	public void receiveString(String cardName)
	{
		Card newCard = new Card(cardName, blist);
		blist.addCard(newCard, mc.currentUser);
		mc.updateBoard();
		addCardView(newCard);
	}

}
