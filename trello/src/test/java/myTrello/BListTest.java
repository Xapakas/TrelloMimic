package myTrello;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BListTest
{
	BList list1;
	BList list2;
	Board board1;
	Board board2;
	User noah;
	User throckmorton;
	Card card1;
	Card card2;
	Card card3;
	ArrayList<Card> cardlistthing;
	boolean success;
	
	@BeforeEach
	void setUp() throws Exception
	{
		noah = new User("noah","hunter2");
		throckmorton = new User("throckmorton","skater4lyfe");
		board1 = new Board("boarding school",noah);
		list1 = new BList("listopher",board1);
		list2 = new BList("listen",board1);
		card1 = new Card("cardy b",list1);
		card2 = new Card("kim cardashian",list1);
		card3 = new Card("cardio",list2);
	}

	@Test
	void testBListName() // spec 7 and spec 11
	{
		assertEquals(list1.getName(),"listopher");
		list1.setName("lolumbus");
		assertEquals(list1.getName(),"lolumbus");
	}
	
	@Test
	void testBListCards() // spec 8
	{
		cardlistthing = list1.getCards().getMembers();
		assertEquals(cardlistthing.get(0),card1);
		assertEquals(cardlistthing.get(1),card2);
		assertEquals(list1.removeCard(card2,throckmorton), false);
		assertEquals(list1.removeCard(card2,noah), true);
		assertEquals(list1.removeCard(card2,noah), false);
		cardlistthing = list1.getCards().getMembers();
		assertEquals(cardlistthing.get(0),card1);
		try {
			cardlistthing.get(1);
		} catch(Exception e) {
			success = true;
		}
		assertEquals(success,true);
		assertEquals(list1.removeCard(card1,noah), true);
	}
	
	@Test
	void testMoveCards() // spec 12
	{
		assertEquals(list1.addCard(card1, list1.getOwner()), true);
		assertEquals(list1.addCard(card2, list1.getOwner()), true);
		assertEquals(list1.moveCard(card2, 0, list1.getOwner()), true);
		cardlistthing = list1.getCards().getMembers();
		assertEquals(cardlistthing.get(0), card2);
		assertEquals(cardlistthing.get(1), card1);
	}
	
	@Test
	void testMoveCardBetweenLists() // spec 16
	{
		assertEquals(list1.addCard(card1, list1.getOwner()), true);
		assertEquals(list1.moveCard(card3, 0, list1.getOwner()), true);
		cardlistthing = list1.getCards().getMembers();
		assertEquals(cardlistthing.get(0), card3);
		assertEquals(cardlistthing.get(1), card1);
	}
	
	@Test
	void testXML()
	{
		list1.storeToDisk();
		BList diskList = BList.loadFromDisk();
		assertEquals(list1.equals(diskList), true);
		assertEquals(list2.equals(diskList), false);
	}
}
