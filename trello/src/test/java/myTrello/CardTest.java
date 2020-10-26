package myTrello;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardTest
{
	BList list1;
	BList list2;
	Board board1;
	Board board2;
	User noah;
	User throckmorton;
	Card card1;
	Card card2;
	Label label1;
	Label label2;
	Component component1;
	Component component2;
	ArrayList<User> userList;
	ArrayList<Component> componentList;
	HashSet<Label> labelSet;

	boolean success;

	@BeforeEach
	void setUp() throws Exception
	{
		noah = new User("noah","hunter2");
		throckmorton = new User("throckmorton","skatingislife");
		board1 = new Board("boarderline personality disorder", noah);
		list1 = new BList("listopher",board1);
		card1 = new Card("cardy b",list1);
		label1 = new Label("libel");
		label2 = new Label("label of lontents");
		component1 = new Component("calm pown it", 5);
		component2 = new Component("comp own it", 3);
	}

	@Test
	void testCardName() // spec 8 and spec 11
	{
		assertEquals(card1.getName(),"cardy b");
		card1.setName("cardy c");
		assertEquals(card1.getName(),"cardy c");
	}
	
	@Test
	void testCardLabels() // spec 9 part 1
	{
		assertEquals(card1.addLabel(label1,card1.getOwner()), true);
		assertEquals(card1.addLabel(label2,card1.getOwner()), true);
		labelSet = card1.getLabels().getMembers();
		assertEquals(labelSet.contains(label1), true);
		assertEquals(labelSet.contains(label2), true);
		assertEquals(card1.removeLabel(label2, card1.getOwner()), true);
		assertEquals(card1.removeLabel(label2, card1.getOwner()), false);
		labelSet = card1.getLabels().getMembers();
		assertEquals(labelSet.contains(label1), true);
		assertEquals(labelSet.contains(label2), false);
	}
	
	@Test
	void testCardMembers() // spec 9 part 2 and spec 11
	{
		assertEquals(card1.addUser(noah, card1.getOwner()), true);
		assertEquals(card1.addUser(throckmorton, card1.getOwner()), true);
		userList = card1.getUsers().getMembers();
		assertEquals(userList.get(0), noah);
		assertEquals(userList.get(1), throckmorton);
		assertEquals(card1.removeUser(noah, throckmorton), false);
		assertEquals(card1.removeUser(throckmorton, noah), true);
		assertEquals(card1.removeUser(throckmorton, noah), false);
		userList = card1.getUsers().getMembers();
		assertEquals(userList.get(0), noah);
		try {
			userList.get(1);
		} catch(Exception e) {
			success = true;
		}
		assertEquals(success,true);
		success = false;
	}
	
	@Test
	void testCardComponents() // spec 10 and spec 17
	{
		assertEquals(card1.addComponent(component1, card1.getOwner()), true);
		assertEquals(card1.addComponent(component2, card1.getOwner()), true);
		componentList = card1.getComponents().getMembers();
		assertEquals(componentList.get(0),component1);
		assertEquals(componentList.get(1),component2);
		assertEquals(card1.removeComponent(component2, card1.getOwner()), true);
		assertEquals(card1.removeComponent(component2, card1.getOwner()), false);
		componentList = card1.getComponents().getMembers();
		assertEquals(componentList.get(0),component1);
		try {
			componentList.get(1);
		} catch(Exception e) {
			success = true;
		}
		assertEquals(success,true);
		success = false;
	}
	
	@Test
	void testXML()
	{
		card1.storeToDisk();
		Card diskCard = Card.loadFromDisk();
		assertEquals(card1.equals(diskCard), true);
		assertEquals(card2.equals(diskCard), false);
	}
	
	@Test
	void testLabelXML()
	{
		label1.storeToDisk();
		Label diskLabel = Label.loadFromDisk();
		assertEquals(label1.equals(diskLabel), true);
		assertEquals(label2.equals(diskLabel), false);
	}
	
	@Test
	void testComponentXML()
	{
		component1.storeToDisk();
		Component diskComponent = Component.loadFromDisk();
		assertEquals(component1.equals(diskComponent), true);
		assertEquals(component2.equals(diskComponent), false);
	}
}
