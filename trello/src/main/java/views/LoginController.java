package views;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import myTrello.User;
import serverClient.TrelloServerInterface;

public class LoginController
{
	MainController mc;
	
	public void setMC(MainController mc)
	{
		this.mc = mc;
	}
	
	@FXML
    private TextField usernameTextField;
	
	@FXML
    private PasswordField passwordTextField;
	
	@FXML
    private TextField serverTextField;
	
	@FXML
    private Button loginButton;
	
	@FXML
	private Label popupLabel;
	
	@FXML
	private Button closePopupButton;
	
	TrelloServerInterface ts;
	Registry registry;
	User user;
	boolean connected;
	
	@FXML
    void onClickLogin(ActionEvent event) {
		connected = true;
		String username = usernameTextField.textProperty().get();
		String password = passwordTextField.textProperty().get();
		String serverName = serverTextField.textProperty().get();

		try
		{
			registry = LocateRegistry.getRegistry(serverName);
			ts = (TrelloServerInterface) registry.lookup("TRELLO");
//			mc.setAllUsers(User.loadListFromDisk());
			ts.loadUsers();
			user = ts.authenticateUser(username, password);
			mc.setServer(ts);
		} catch (RemoteException | NotBoundException e)
		{
			e.printStackTrace();
			mc.showPopupView("Server error: recheck server name and try again.");
			clearTextFields();

			connected = false;
		}
		if (connected)
		{
			if (user != null)
			{
				mc.setCurrentUser(user);
				mc.showUserPage();
			}
			else
			{
				mc.showPopupView("Invalid username or password.");
				clearTextFields();
			}
		}
				
    }
	
	private void clearTextFields()
	{
		usernameTextField.setText("");
		passwordTextField.setText("");
		serverTextField.setText("");
	}
}
