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
	ArrayList<User> users;

	protected TrelloServer() throws RemoteException
	{
		
	}
	
	public void loadUsers() throws RemoteException
	{
		users = User.loadListFromDisk();
	}

	public ArrayList<User> getUsers() throws RemoteException
	{
		return users;
	}

	public void setUsers(ArrayList<User> users) throws RemoteException
	{
		this.users = users;
	}
	
	public void saveUsers() throws RemoteException
	{
		System.out.println("TrelloServer is saving users");
		User.storeListToDisk(users);
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

	public Board getBoard(String boardName, User requesterCopy) throws RemoteException
	{
		User requester = authenticateUser(requesterCopy.getName(), 
				requesterCopy.getPassword());
		for (Board board: requester.getBoardsMemberOf().getMembers()) {
			if (board.getName().equals(boardName)) {
				return board;
			}
		}
		return null;
	}

	public Board createBoard(String boardName, User requesterCopy) throws RemoteException
	{
		User requester = authenticateUser(requesterCopy.getName(), 
				requesterCopy.getPassword());
		Board newBoard = new Board(boardName, requester);
		return newBoard;
	}

	public void updateBoard(Board oldBoardCopy, Board newBoard, 
			User requesterCopy) throws RemoteException
	{
		User requester = authenticateUser(requesterCopy.getName(), 
				requesterCopy.getPassword());
		Board oldBoard = getBoard(oldBoardCopy.getName(), requester);
		oldBoard.setLists(newBoard.getLists());
		oldBoard.setMembers(newBoard.getMembers());
		oldBoard.setName(newBoard.getName());
		oldBoard.setOwner(newBoard.getOwner());
	}
	
	public void removeBoard(String boardName, User requesterCopy) throws RemoteException
	{
		User requester = authenticateUser(requesterCopy.getName(), 
				requesterCopy.getPassword());
		Board deadBoard = getBoard(boardName, requesterCopy);
		requester.removeBoardMemberOf(deadBoard);
		requester.removeBoardOwned(deadBoard);
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
