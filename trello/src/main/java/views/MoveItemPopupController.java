package views;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import myTrello.BList;
import myTrello.Board;
import myTrello.Card;

public class MoveItemPopupController {
	
	MainController mc;
	Stage stage;
	String type;
	Board board;

    @FXML
    private Label messageLabel1;

    @FXML
    private ListView<String> listView1;

    @FXML
    private Label messageLabel2;

    @FXML
    private ListView<String> listView2;

    @FXML
    private Button swapButton;

    @FXML
    void onClickSwapButton(ActionEvent event) 
    {
    	int index1 = listView1.getSelectionModel().getSelectedIndex();
    	int index2 = listView2.getSelectionModel().getSelectedIndex();
    	
    	if (type.equals("list"))
    	{
    		BList selectedList = board.getLists().getMembers().get(index1);
    		board.removeList(selectedList); // it duplicates if it doesn't do this first.
    		board.moveList(selectedList, index2, board.getOwner());
    		
    		mc.updateBoard();
    		mc.showBoardPage(board);
    	}
    	else if (type.equals("card"))
    	{
    		ArrayList<Card> cards = new ArrayList<Card>();
    		ArrayList<BList> blists = board.getLists().getMembers();
    		ArrayList<Integer> intsPerBlist = new ArrayList<Integer>();
    		int count;
    		
    		for (BList blist : blists)
    		{
    			count = 0;
    			for (Card card : blist.getCards().getMembers())
    			{
    				count++;
    				cards.add(card);
    			}
    			intsPerBlist.add(count);
    		}
    		
    		Card selectedCard = cards.get(index1);
    		
    		int pos = 0;
    		while (index2 >= intsPerBlist.get(pos)) // whiteboarded
    		{
    			index2 -= intsPerBlist.get(pos);
    			pos++;
    		}
    		int pos2 = 0;
    		while (index1 >= intsPerBlist.get(pos2)) // whiteboarded
    		{
    			index1 -= intsPerBlist.get(pos2);
    			pos2++;
    		}
    		
    		BList selectedList = board.getLists().getMembers().get(pos);
    		BList oldList = board.getLists().getMembers().get(pos2);
    		// if you dont remove the card first, it duplicates.
    		oldList.removeCard(selectedCard, oldList.getOwner());
    		selectedList.moveCard(selectedCard, index2, board.getOwner());
    		
    		mc.updateBoard();
    		mc.showBoardPage(board);
    	}
    	
    	stage.close();
    }
    
    public void setupScene(String type, MainController mc, Stage stage)
    {
    	this.mc = mc;
    	this.type = type;
    	this.stage = stage;
    	this.board = mc.currentBoard;
    	
    	messageLabel1.setText("Choose a " + type + "to move.");
    	messageLabel2.setText("Choose a " + type + "to swap it with.");
    	
    	if (type.equals("list"))
    	{
    		ArrayList<BList> blists = board.getLists().getMembers();
    		ObservableList<String> bListNames = FXCollections.observableArrayList();
    		
    		for (BList blist : blists)
    		{
    			bListNames.add(blist.getName());
    		}
    		
    		listView1.setItems(bListNames);
    		listView2.setItems(bListNames);
    	}
    	else if (type.equals("card"))
    	{
    		ArrayList<BList> blists = board.getLists().getMembers();
    		ArrayList<Card> cards = new ArrayList<Card>();
    		
    		for (BList blist : blists)
    		{
    			for (Card card : blist.getCards().getMembers())
    			{
    				cards.add(card);
    			}
    		}
    		ObservableList<String> cardNames = FXCollections.observableArrayList();
    		
    		for (Card card : cards)
    		{
    			cardNames.add(card.getName());
    		}
    		
    		listView1.setItems(cardNames);
    		listView2.setItems(cardNames);
    	}
    }

}
