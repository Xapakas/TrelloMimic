package myTrello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class BList
{
	String name;
	HasMembersList<Card> cards;
	Board parentBoard;
	User owner;
	
	public BList(){}
	
	public BList(String name, Board parentBoard)
	{
		this.name = name;
		this.parentBoard = parentBoard;
		cards = new HasMembersList<Card>(); // same warning as Board
		owner = parentBoard.getOwner();
		parentBoard.addList(this);
	}
	
	public boolean moveCard(Card card, int index, User requester)
	{
		if (!requester.equals(owner)) {
			return false;
		}
		int orig_index;
		try {
			orig_index = cards.members.indexOf(card);
		} catch(Exception e) {
			// the card is being moved from a different list
			orig_index = cards.members.size();
		}
		for (int i = orig_index; i > index; i--) {
			// Shift every card between orig_index and index up 1.
			cards.members.add(i, cards.members.get(i-1));
		}
		cards.members.add(index, card);
		return true;
	}

	public boolean addCard(Card card, User requester)
	{
		if (requester.equals(owner)) {
			card.setParentList(this);
			return cards.addMember(card);
		}
		return false;
	}
	
	public boolean removeCard(Card card, User requester)
	{
		if (requester.equals(owner)) {
			card.setParentList(null);
			return cards.removeMember(card);
		}
		return false;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public HasMembersList<Card> getCards()
	{
		return cards;
	}

	public void setCards(HasMembersList<Card> cards)
	{
		// Don't use this method. It's just for XML.
		this.cards = cards;
	}

	public Board getParentBoard()
	{
		return parentBoard;
	}

	public void setParentBoard(Board parentBoard)
	{
		this.parentBoard = parentBoard;
	}
	
	public User getOwner()
	{
		return owner;
	}

	public void setOwner(User owner)
	{
		this.owner = owner;
	}
	
	public void storeToDisk()
	{
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("BList.xml")));
		} catch(FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File "	+ "BList.xml");
		}
		encoder.writeObject(this);
		encoder.close();
	}
	
	public static BList loadFromDisk()
	{
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(
					new FileInputStream("BList.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File BList.xml not found");
		}
		BList blistInstance = (BList) decoder.readObject();
		return blistInstance;
	}

//	@Override
//	public int hashCode()
//	{
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
//		result = prime * result + ((parentBoard == null) ? 0 : parentBoard.hashCode());
//		return result;
//	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BList other = (BList) obj;
		if (cards == null)
		{
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null)
		{
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (parentBoard == null)
		{
			if (other.parentBoard != null)
				return false;
		} else if (!parentBoard.equals(other.parentBoard))
			return false;
		return true;
	}
	
}