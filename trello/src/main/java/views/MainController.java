package views;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Main;
import myTrello.Board;
import myTrello.User;
import serverClient.TrelloServerInterface;

public class MainController
{
	Stage stage;
	TrelloServerInterface ts;
	BorderPane mainView;
	User currentUser;
	Board currentBoard;
	ToolbarController toolbar;
	
	public ArrayList<User> getAllUsers()
	{
		try
		{
			return ts.getUsers();
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	boolean isUnsavedData;
	
	public MainController(Stage stage)
	{
		this.stage = stage;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				if (getIsUnsavedData())
				{
					showSavePopupView("You have unsaved data. Would you like "
							+ "to save it before exiting?", "exit");
				}
			}
		});
	}
	
	public void setCurrentUser(User currentUser)
	{
		this.currentUser = currentUser;
	}
	
	public void setCurrentBoard(Board currentBoard)
	{
		this.currentBoard = currentBoard;
	}
	
	public Board getCurrentBoard()
	{
		return currentBoard;
	}
	
	public void setServer(TrelloServerInterface ts)
	{
		this.ts = ts;
	}
	
	public TrelloServerInterface getServer()
	{
		return ts;
	}
	
	public void setIsUnsavedData(boolean isUnsavedData)
	{
		this.isUnsavedData = isUnsavedData;
	}
	
	public boolean getIsUnsavedData()
	{
		return isUnsavedData;
	}
	
	public void showLoginPage()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/LoginPage.fxml"));
		AnchorPane view;
		try
		{
			view = loader.load();
			LoginController cont = loader.getController();
			cont.setMC(this);
			
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.setTitle("Login to our Trello...");
			stage.show();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void showToolbar()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/MainToolbar.fxml"));
		try
		{
			mainView = loader.load();
			ToolbarController cont = loader.getController();
			this.toolbar = cont;
			cont.setMC(this);
			
			Scene s = new Scene(mainView);
			stage.setScene(s);
			stage.show();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void showUserPage()
	{
		showToolbar();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/UserPage.fxml"));
		try
		{
			BorderPane view = loader.load();
			UserController cont = loader.getController();
			cont.setMC(this);
			cont.setupScene();
			
			mainView.setCenter(view);
			stage.setTitle("Showing user: " + currentUser.getName());
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void showBoardPage(Board board)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/BoardPage.fxml"));
		try
		{
			BorderPane view = loader.load();
			BoardController cont = loader.getController();
			cont.setMC(this);
			cont.setBoard(board);
			setCurrentBoard(board);
			cont.setupScene();
			
			mainView.setCenter(view);
			stage.setTitle("Showing board: " + board.getName());
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void showPopupView(String message)
	{
		Stage stage = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/PopupView.fxml"));
		VBox view;
		try
		{
			view = loader.load();
			
			PopupController cont = loader.getController();
			cont.setModel(message,stage);
			
			Scene scene = new Scene(view);
			stage.setScene(scene);
			stage.setTitle(":(");
			stage.show();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
	public void showSavePopupView(String message, String type)
	{
		Stage stage = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/SavePopupView.fxml"));
		VBox view;
		try
		{
			view = loader.load();
			SavePopupController cont = loader.getController();
			cont.setModel(message, type, stage, this);
			
			Scene scene = new Scene(view);
			stage.setScene(scene);
			stage.setTitle("You have unsaved data");
			stage.show();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void showInputPopupView(String message, ControllerInterface parentCont)
	{
		Stage stage = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/InputPopupView.fxml"));
		VBox view;
		try
		{
			view = loader.load();
			InputPopupController cont = loader.getController();
			cont.setModel(message, stage, parentCont);
			
			Scene scene = new Scene(view);
			stage.setScene(scene);
			stage.setTitle("Please enter new data");
			stage.show();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void showMoveItemPopup(String type)
	{
		Stage stage = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../views/MoveItemPopup.fxml"));
		VBox view;
		try
		{
			view = loader.load();
			MoveItemPopupController cont = loader.getController();
			cont.setupScene(type, this, stage);
			
			Scene scene = new Scene(view);
			stage.setScene(scene);
			stage.setTitle("Please enter new data");
			stage.show();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
	
	public void updateBoard()
	{
		try
		{
			ts.updateBoard(currentBoard, currentBoard, 
					currentUser);
			currentBoard = ts.getBoard(currentBoard.getName(), currentUser);
			setIsUnsavedData(true);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
	
	public void removeBoard(String boardName)
	{
		try
		{
			ts.removeBoard(boardName, currentUser);
			setIsUnsavedData(true);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveData()
	{
		System.out.println("mc is saving data");
		try
		{
			ts.saveUsers();
			setIsUnsavedData(false);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
	}
	
	public void goBack()
	{
		if (getCurrentBoard() == null) // on user view
    	{
    		if (getIsUnsavedData())
    		{
    			showSavePopupView("You are about to log out and have unsaved changes. "
    					+ "Would you like to save?", "back");
    		}
    		else
    		{
    			currentUser = null;
    			showLoginPage();
    		}
    	}
    	else // on board view
    	{
    		currentBoard = null;
    		showUserPage();
    	}
	}
	
	public void exit()
	{
		stage.close();
	}
	
}
