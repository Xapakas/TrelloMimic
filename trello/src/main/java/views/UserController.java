package views;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import myTrello.Board;

public class UserController
{
	@FXML
    private Label userNameLabel;

    @FXML
    private ListView<String> boardListView;
    
    @FXML
    private Button goToBoardButton;

    @FXML
    private TextField newBoardText;

    @FXML
    private Button submitNewBoardButton;
    
    @FXML
    private Button removeBoardButton;
    
    ArrayList<Board> boards;
	ObservableList<String> boardNames;
	MainController mc;
    
	public void setupScene()
	{
		userNameLabel.setText(mc.currentUser.getName());
		boards = mc.currentUser.getBoardsMemberOf().getMembers(); // not server side
		boardNames = FXCollections.observableArrayList();
		
		for (Board board : boards)
		{
			boardNames.add(board.getName());
		}
		
		boardListView.setItems(boardNames);
	}
	
	public void setMC(MainController mc)
	{
		this.mc = mc;
	}
	
	@FXML
    void onClickGoToBoard(ActionEvent event) {
		String selectedBoardName = boardListView.getSelectionModel().getSelectedItem();
		try
		{
			Board selectedBoard = mc.ts.getBoard(selectedBoardName, 
					mc.currentUser);
			mc.showBoardPage(selectedBoard);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
//		int index = boardNames.indexOf(selectedBoardName);
//		if (index != -1)
//		{
//			Board selectedBoard = boards.get(index);
//			mc.showBoardPage(selectedBoard);
//		}
    }
	
	@FXML
    void onClickRemoveBoard(ActionEvent event) 
	{
		String selectedBoardName = boardListView.getSelectionModel().getSelectedItem();
		Board selectedBoard;
		try // remove from client
		{
			selectedBoard = mc.ts.getBoard(selectedBoardName, 
					mc.currentUser);
			mc.currentUser.removeBoardMemberOf(selectedBoard);
			mc.currentUser.removeBoardOwned(selectedBoard);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
		mc.removeBoard(selectedBoardName); // remove from server
		mc.setCurrentBoard(null);
		setupScene(); // remake listview
    }
	
	@FXML
    void onClickSubmit(ActionEvent event) {
		String newBoardName = newBoardText.getText();
//		System.out.println("hello");
		try
		{
			Board newBoard = mc.ts.createBoard(newBoardName, mc.currentUser);
			mc.setCurrentBoard(newBoard);
			mc.currentUser.addBoardOwned(newBoard);
			mc.currentUser.addBoardMemberOf(newBoard);
			mc.updateBoard();
			boardNames.add(newBoard.getName());
			boards = mc.currentUser.getBoardsMemberOf().getMembers();
			newBoardText.setText("");
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
    }
}
