package serverClient;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import myTrello.*;

class TrelloServerTest
{
	TrelloServer ts;
	Registry registry;
	ArrayList<User> users;
	User noah;
	User evildoer;
	Board board1;
	Board board2;
	Board board3;
	Board board4;
	Board board5;

	@BeforeEach
	void setUp() throws Exception
	{
		ts = new TrelloServer();
		registry = LocateRegistry.createRegistry(2099);
		registry.rebind("TRELLO", ts);
		users = User.loadListFromDisk();
	}

	@AfterEach
	void tearDown() throws Exception
	{
		registry.unbind("TRELLO");
	}
	
	@Test
	void test()
	{
		TrelloServerInterface tp;
		try
		{
			tp = (TrelloServerInterface) registry.lookup("TRELLO");
			noah = tp.authenticateUser("Noah","hunter2");
			assertEquals((noah==null), false);
			evildoer = tp.authenticateUser("Hackerman","wrongpassword");
			assertEquals((evildoer==null), true);
			
			board1 = tp.getBoard("testBoard", noah);
			assertEquals((board1==null),false);
			board2 = tp.getBoard("fakeBoard", noah);
			assertEquals((board2==null),true);
			
			board3 = tp.createBoard("thirdBoard", noah);
			
			board4 = new Board("clientBoard", noah);
			tp.updateBoard(board3, board4, noah);
			
			board5 = tp.getBoard("clientBoard", noah);
			assertEquals((board5==null),false);
		} 
		catch (RemoteException e)
		{
			e.printStackTrace();
		} 
		catch (NotBoundException e)
		{
			e.printStackTrace();
		}
	}
}
