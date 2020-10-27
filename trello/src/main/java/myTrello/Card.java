package myTrello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Card
{

	String name;
	BList parentList;
	HasMembersSet<Label> labels;
	HasMembersList<User> users;
	HasMembersList<Component> components;
	User owner;
	
	public Card() {}
	
	public Card(String name, BList parentList)
	{
		this.name = name;
		this.parentList = parentList;
		labels = new HasMembersSet<Label>();
		users = new HasMembersList<User>();
		components = new HasMembersList<Component>();
		owner = parentList.getParentBoard().getOwner();
		parentList.addCard(this, owner);
	}
	
	public boolean switchList(BList newList, int index, User requester)
	{
		if (!requester.equals(newList.getOwner())) {
			return false;
		}
		parentList.removeCard(this, requester);
		newList.addCard(this, requester);
		newList.moveCard(this, index, requester);
		return true;
	}
	
	public boolean addLabel(Label newLabel, User requester)
	{
		if (requester.equals(owner)) {
			return labels.addMember(newLabel);
		}
		return false;
	}
	
	public boolean removeLabel(Label label, User requester)
	{
		if (requester.equals(owner)) {
			return labels.removeMember(label);
		}
		return false;
	}
	
	public boolean addUser(User member, User requester)
	{
		if (requester.equals(owner)) {
			return users.addMember(member);
		}
		return false;
	}
	
	public boolean removeUser(User member, User requester)
	{
		if (requester.equals(owner)) {
			return users.removeMember(member);
		}
		return false;
	}
	
	public boolean addComponent(Component component, User requester)
	{
		if (requester.equals(owner)) {
			return components.addMember(component);
		}
		return false;
	}
	
	public boolean removeComponent(Component component, User requester)
	{
		if (requester.equals(owner)) {
			return components.removeMember(component);
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

	public BList getParentList()
	{
		return parentList;
	}

	public void setParentList(BList parentList)
	{
		this.parentList = parentList;
	}

	public HasMembersSet<Label> getLabels()
	{
		return labels;
	}

	public void setLabels(HasMembersSet<Label> labels)
	{
		this.labels = labels;
	}

	public HasMembersList<User> getUsers()
	{
		return users;
	}

	public void setUsers(HasMembersList<User> users)
	{
		this.users = users;
	}

	public HasMembersList<Component> getComponents()
	{
		return components;
	}

	public void setComponents(HasMembersList<Component> components)
	{
		this.components = components;
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
					new FileOutputStream("Card.xml")));
		} catch(FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File "	+ "Card.xml");
		}
		encoder.writeObject(this);
		encoder.close();
	}
	
	public static Card loadFromDisk()
	{
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(
					new FileInputStream("Card.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File Card.xml not found");
		}
		Card cardInstance = (Card) decoder.readObject();
		return cardInstance;
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
		Card other = (Card) obj;
		if (components == null)
		{
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (labels == null)
		{
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
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
		} else if (!(owner.getName().equals(other.owner.getName())))
			return false;
		if (parentList == null)
		{
			if (other.parentList != null)
				return false;
		} else if (!(parentList.getName().equals(other.parentList.getName()))) {
			return false;
		}
		if (users == null)
		{
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
	
}