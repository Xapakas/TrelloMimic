package views;

import java.io.IOException;
import java.util.ArrayList;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import main.Main;
import myTrello.BList;
import myTrello.Board;

public class BoardController implements ControllerInterface
{
	MainController mc;
	Board board;
	ArrayList<BList> BLists;
	ArrayList<TitledPane> BListViews;
	int columns;
	int addedLists = 0;
	
	@FXML
    private Label boardNameLabel;
	
	@FXML
	private Button newListButton;
	
	@FXML
    private Button moveListButton;

    @FXML
    private Button moveCardButton;
	
	@FXML GridPane listGridPane;
	
	@FXML
    void onClickMoveCard(ActionEvent event) 
	{
		mc.showMoveItemPopup("card");
    }

    @FXML
    void onClickMoveList(ActionEvent event) 
    {
    	mc.showMoveItemPopup("list");
    }

	@FXML
	void onClickNewList(ActionEvent event) 
	{
		mc.showInputPopupView("Enter the name of your new list!", this);
	}
	
	public void setMC(MainController mc)
	{
		this.mc = mc;
	}
	
	public void setBoard(Board board)
	{
		this.board = board;
	}
	
	public void receiveString(String blistName)
	{
		BList newList = new BList(blistName, mc.getCurrentBoard());
		mc.updateBoard();
		addListView(newList);
	}
	
	public void addListView(BList list)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/ListView.fxml"));
		try
		{
			TitledPane view = loader.load();
			ListController cont = loader.getController();
			cont.setupScene(list, addedLists, mc, this);
			listGridPane.addColumn(columns);
			listGridPane.add(view, columns, 0);
			columns++;
			addedLists++;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setupScene()
	{
		boardNameLabel.setText(mc.getCurrentBoard().getName());
		BLists = mc.getCurrentBoard().getLists().getMembers();
		BListViews = new ArrayList<TitledPane>();
		columns = 0;
	
		for (BList blist : BLists)
		{
			addListView(blist);
			columns++;
		}
	}
	
}