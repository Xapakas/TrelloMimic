package myTrello;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest
{
	User A;
	User B;
	Board board1;
	HasMembersList<Board> testboards;
	ArrayList<Board> arrayboards;
	boolean success;
	
	@BeforeEach
	void setUp() throws Exception
	{
		A = new User("Noah","hunter2");
		B = new User("Hackerman","[hacker voice] i'm in");
		testboards = new HasMembersList<Board>();
		board1 = new Board("testBoard", A);
	}

	@Test
	void testLogin() // spec 1
	{
		assertEquals(A.login("Noah", "hunter2"), true);
		assertEquals(A.login("Noah", "hunter3"), false);
		assertEquals(A.login("Naoh", "hunter2"), false);
		assertEquals(A.login("Noah", "[hacker voice] i'm in"), false);
	}
	
	@Test
	void testBoardOwnership() // spec 2
	{
		testboards = A.getBoards();
		arrayboards = testboards.getMembers();
		assertEquals(arrayboards.get(0),board1);
		assertEquals(A.removeBoard(board1), true);
		testboards = A.getBoards();
		arrayboards = testboards.getMembers();
		try {
			arrayboards.get(0);
		} catch(Exception e) {
			success = true;
		}
		assertEquals(success,true);
	}
	
	@Test
	void testXML()
	{
		A.storeToDisk();
		User diskA = User.loadFromDisk();
		assertEquals(A.equals(diskA), true);
		assertEquals(B.equals(diskA), false);
	}
}
