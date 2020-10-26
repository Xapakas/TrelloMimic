package myTrello;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest
{
	Board A;
	Board B;
	User noah;
	User throckmorton;
	BList l;
	BList m;
	HasMembersSet<User> h;
	HashSet<User> hashy;
	
	HasMembersList<BList> blister;
	ArrayList<BList> listopher;
	
	@BeforeEach
	void setUp() throws Exception
	{
		noah = new User("noah","hunter2");
		throckmorton = new User("throckmorton","skating4lyfe");
		A = new Board("testBoard", noah);
		B = new Board("otherBoard", throckmorton);
	}

	@Test
	void testNameAndOwner() // spec 3 and spec 11
	{
		assertEquals(A.getName(), "testBoard");
		assertEquals(A.getOwner(), noah);
		A.setName("testBoardButCooler");
		assertEquals(A.getName(), "testBoardButCooler");
	}
	
	@Test
	void testBoardUsers() // specs 4 and 5
	{
		assertEquals(A.addMember(throckmorton, noah), true);
		h = A.getMembers();
		hashy = h.getMembers();
		assertEquals(hashy.contains(noah), true);
		assertEquals(hashy.contains(throckmorton), true);
		
		assertEquals(A.removeMember(noah, throckmorton), false);
		assertEquals(A.removeMember(throckmorton, noah), true);
		h = A.getMembers();
		hashy = h.getMembers();
		assertEquals(hashy.contains(noah), true);
		assertEquals(hashy.contains(throckmorton), false);
	}
	
	@Test
	void testBoardLists() // spec 6
	{
		l = new BList("listy the list man",A);
		m = new BList("ryan",B);
		blister = A.getLists();
		listopher = blister.getMembers();
		assertEquals(listopher.contains(l), true);
		assertEquals(listopher.contains(m), false);
		
		assertEquals(A.removeList(l), true);
		assertEquals(A.removeList(l), false);
		
		blister = A.getLists();
		listopher = blister.getMembers();
		assertEquals(listopher.contains(l), false);
	}
	
	@Test
	void testMoveLists() // spec 14
	{
		assertEquals(A.addList(l), true);
		assertEquals(A.addList(m), true);
		assertEquals(A.moveList(m, 0, A.getOwner()), true);
		listopher = A.getLists().getMembers();
		assertEquals(listopher.get(0), m);
		assertEquals(listopher.get(1), l);
	}
	
	@Test
	void testXML()
	{
		A.storeToDisk();
		Board diskBoard = Board.loadFromDisk();
		assertEquals(A.equals(diskBoard), true);
		assertEquals(B.equals(diskBoard), false);
	}
	
	@Test
	void testHasMembersSetXML()
	{
		A.addMember(throckmorton, noah);
		h = A.getMembers();
		h.storeToDisk();
		@SuppressWarnings("rawtypes")
		HasMembersSet diskHaskMembersSet = HasMembersSet.loadFromDisk();
		assertEquals(h.equals(diskHaskMembersSet), true);
	}
	
	@Test
	void testHasMembersListXML()
	{
		l = new BList("listy the list man",A);
		m = new BList("ryan",A);
		blister = A.getLists();
		blister.storeToDisk();
		@SuppressWarnings("rawtypes")
		HasMembersList diskBlister = HasMembersList.loadFromDisk();
		assertEquals(blister.equals(diskBlister), true);
	}
}
