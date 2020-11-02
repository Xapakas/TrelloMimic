package myTrello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

public class Board implements Serializable
{
	private static final long serialVersionUID = -7916724492874775343L;
	String name;
	User owner;
	HasMembersSet<User> members;
	HasMembersList<BList> lists;
	
	public Board() {}
	
	public Board(String name, User owner) 
	{
		this.name = name;
		this.owner = owner;
		this.members = new HasMembersSet<User>();
		this.lists = new HasMembersList<BList>();
		owner.addBoardOwned(this);
		addMember(owner,owner);
	}
	
	public boolean addMember(User addedUser, User requester)
	{
		if (requester == this.owner) {
			return (addedUser.addBoardMemberOf(this)
					&& members.addMember(addedUser));
		}
		return false;
	}
	
	public boolean removeMember(User removedUser, User requester)
	{
		if (requester == this.owner) {
			return (removedUser.removeBoardMemberOf(this) 
					&& members.removeMember(removedUser));
		}
		return false;
	}
	
	public boolean addList(BList addedList)
	{
		return lists.addMember(addedList);
	}
	
	public boolean removeList(BList removedList)
	{
		return lists.removeMember(removedList);
	}
	
	public boolean moveList(BList list, int index, User requester)
	{
		if (!requester.equals(owner)) {
			return false;
		}
		int orig_index = lists.members.indexOf(list);
		for (int i = orig_index; i > index; i--) {
			// Shift every list between orig_index and index up 1.
			lists.members.add(i, lists.members.get(i-1));
		}
		lists.members.add(index, list);
		return true;
	} 

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public User getOwner()
	{
		return owner;
	}

	public void setOwner(User owner)
	{
		this.owner = owner;
	}

	public HasMembersSet<User> getMembers()
	{
		return members;
	}

	public void setMembers(HasMembersSet<User> members)
	{
		this.members = members;
	}

	public HasMembersList<BList> getLists()
	{
		return lists;
	}

	public void setLists(HasMembersList<BList> lists)
	{
		this.lists = lists;
	}
	
	/* XML Stuff */
	
	public void storeToDisk()
	{
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("Board.xml")));
		} catch(FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File "	+ "Board.xml");
		}
		encoder.writeObject(this);
		encoder.close();
	}
	
	public static Board loadFromDisk()
	{
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(
					new FileInputStream("Board.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File Board.xml not found");
		}
		Board boardInstance = (Board) decoder.readObject();
		return boardInstance;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (lists == null)
		{
			if (other.lists != null)
				return false;
		} else if (!lists.equals(other.lists))
			return false;
		if (members == null)
		{
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
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
		} else if (!owner.getName().equals(other.owner.getName()))
			return false;
		return true;
	}
	
}
