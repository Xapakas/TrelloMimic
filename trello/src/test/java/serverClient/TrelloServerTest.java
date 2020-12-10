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
	BList blist1;
	Card card1;
	BLabel blabel1;
	DayComponent daycomp;

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
			tp.loadUsers();
			noah = tp.authenticateUser("Noah","hunter2");
			assertEquals((noah==null), false);
			evildoer = tp.authenticateUser("Hackerman","wrongpassword");
			assertEquals((evildoer==null), true);
			
			board1 = tp.createBoard("testBoard", noah);
			blist1 = new BList("testList", board1);
			card1 = new Card("testCard", blist1);
			blabel1 = new BLabel("testLabel");
			card1.addLabel(blabel1, card1.getOwner());
			daycomp = new DayComponent("Wednesday");
			card1.addComponent(daycomp, card1.getOwner());
			
			tp.updateBoard(board1, board1, noah);
			
			Board board1copy = tp.getBoard("testBoard", noah);
			assertEquals((board1copy==null),false);
			assertEquals((board1.equals(board1copy)),true); // every attribute is the same
			
			assertEquals((tp.getBoard("fakeBoard", noah)==null),true);
			
			ArrayList<User> users = tp.getUsers();
			tp.saveUsers();
			tp.loadUsers();
			ArrayList<User> users2 = tp.getUsers();
			assertEquals((users.equals(users2)), true);
			
			assertEquals((tp.getBoard("testBoard", noah)==null),false);
			tp.removeBoard("testBoard", noah);
			tp.saveUsers();
			tp.loadUsers();
			assertEquals((tp.getBoard("testBoard", noah)==null),true);
			
			tp.saveUsers();
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
