package serverClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

import myTrello.*;

public interface TrelloServerInterface extends Remote
{
	public User authenticateUser(String a, String b) throws RemoteException;
	public Board getBoard(String name, User requester) throws RemoteException;
	public Board createBoard(String name, User requester) throws RemoteException;
	public boolean updateBoard(Board oldBoard, Board newBoard, User requester) throws RemoteException;
}
