package serverClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import myTrello.*;

public class TrelloServer extends UnicastRemoteObject implements TrelloServerInterface
{
	private static final long serialVersionUID = 3001254035461836704L;
	ArrayList<User> users = User.loadListFromDisk();

	protected TrelloServer() throws RemoteException
	{
		
	}

	public User authenticateUser(String username, String password) throws RemoteException
	{
		for (User user: users)
		{
			if (user.getName().equals(username)) {
				if (user.getPassword().equals(password)) {
					return user;
				}
			}
		}
		return null;
	}

	public Board getBoard(String boardName, User requester) throws RemoteException
	{
		for (Board board: requester.getBoardsMemberOf().getMembers()) {
			if (board.getName().equals(boardName)) {
				return board;
			}
		}
		return null;
	}

	public Board createBoard(String boardName, User requester) throws RemoteException
	{
		Board newBoard = new Board(boardName, requester);
		User.storeListToDisk(users);
		return newBoard;
	}

	public boolean updateBoard(Board oldBoard, Board newBoard, User requester) throws RemoteException
	{
		if (oldBoard.getMembers().getMembers().contains(requester) && newBoard.getMembers().getMembers().contains(requester)) {
			oldBoard.setLists(newBoard.getLists());
			oldBoard.setMembers(newBoard.getMembers());
			oldBoard.setName(newBoard.getName());
			oldBoard.setOwner(newBoard.getOwner());
			User.storeListToDisk(users);
			return true;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		try
		{
			TrelloServer T = new TrelloServer();
			Naming.rebind("TRELLO", T);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

}
