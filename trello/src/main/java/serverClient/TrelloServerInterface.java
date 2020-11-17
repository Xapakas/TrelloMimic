package serverClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import myTrello.*;

public interface TrelloServerInterface extends Remote
{
	public User authenticateUser(String a, String b) throws RemoteException;
	public Board getBoard(String name, User requester) throws RemoteException;
	public Board createBoard(String boardName, User requesterCopy) throws RemoteException;	
	public void updateBoard(Board oldBoardCopy, Board newBoard, User requesterCopy) throws RemoteException;
	public void removeBoard(String boardName, User requesterCopy) throws RemoteException;
	
	public void loadUsers() throws RemoteException;
	public ArrayList<User> getUsers() throws RemoteException;
	public void setUsers(ArrayList<User> users) throws RemoteException;
	public void saveUsers() throws RemoteException;
}
