package views;

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
		userNameLabel.setText(mc.getCurrentUser().getName());
		boards = mc.getCurrentUser().getBoardsMemberOf().getMembers();
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
		if (selectedBoardName != null)
		{
			Board selectedBoard = mc.getBoard(selectedBoardName, mc.getCurrentUser());
			mc.showBoardPage(selectedBoard);
		}
    }
	
	@FXML
    void onClickRemoveBoard(ActionEvent event) 
	{
		String selectedBoardName = boardListView.getSelectionModel().getSelectedItem();
		Board selectedBoard = mc.getBoard(selectedBoardName, mc.getCurrentUser());
		mc.getCurrentUser().removeBoardMemberOf(selectedBoard);
		mc.getCurrentUser().removeBoardOwned(selectedBoard);
		mc.removeBoard(selectedBoardName); // remove from server
		mc.setCurrentBoard(null);
		setupScene(); // remake listview
    }
	
	@FXML
    void onClickSubmit(ActionEvent event) {
		String newBoardName = newBoardText.getText();
		if (boardNames.contains(newBoardName))
		{
			mc.showPopupView("Board must have a unique name.");
		}
		else
		{
			Board newBoard = mc.createBoard(newBoardName, mc.getCurrentUser());
			mc.setCurrentBoard(newBoard);
			mc.getCurrentUser().addBoardOwned(newBoard);
			mc.getCurrentUser().addBoardMemberOf(newBoard);
			mc.updateBoard();
			boardNames.add(newBoard.getName());
			boards = mc.getCurrentUser().getBoardsMemberOf().getMembers();
			newBoardText.setText("");
		}
    }
}
