package myTrello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
	private static final long serialVersionUID = -3507397639080750095L;
	String name;
	String password;
	HasMembersList<Board> boardsOwned;
	HasMembersList<Board> boardsMemberOf;

	public User() {}
	
	public User(String name, String password)
	{
		this.name = name;
		this.password = password;
		boardsOwned = new HasMembersList<Board>();
		boardsMemberOf = new HasMembersList<Board>();
	}
	
	public boolean login(String username, String entered_password)
	{
		if (this.getPassword() == entered_password && this.getName() == username) {
			// needless to say, this isn't exactly a secure password storage system.
			return true;
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

//	public HasMembersList<Board> getboardsOwned()
//	{
//		return boardsOwned;
//	}
//
//	public void setboardsOwned(HasMembersList<Board> boardsOwned)
//	{
//		this.boardsOwned = boardsOwned;
//	}
	
	public HasMembersList<Board> getBoardsOwned()
	{
		return boardsOwned;
	}

	public void setBoardsOwned(HasMembersList<Board> boardsOwned)
	{
		this.boardsOwned = boardsOwned;
	}

	public HasMembersList<Board> getBoardsMemberOf()
	{
		return boardsMemberOf;
	}

	public void setBoardsMemberOf(HasMembersList<Board> boardsMemberOf)
	{
		this.boardsMemberOf = boardsMemberOf;
	}

	public boolean addBoardOwned(Board myBoard)
	{
		return boardsOwned.addMember(myBoard);
	}
	
	public boolean removeBoardOwned(Board myBoard)
	{
		myBoard.setOwner(null);
		return boardsOwned.removeMember(myBoard);
	}
	
	public boolean addBoardMemberOf(Board myBoard)
	{
		return boardsMemberOf.addMember(myBoard);
	}
	
	public boolean removeBoardMemberOf(Board myBoard)
	{
		return boardsMemberOf.removeMember(myBoard);
	}
		
	public void storeToDisk()
	{
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("User.xml")));
		} catch(FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File "	+ "User.xml");
		}
		encoder.writeObject(this);
		encoder.close();
	}
	
	public static User loadFromDisk()
	{
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(
					new FileInputStream("User.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File User.xml not found");
		}
		User userInstance = (User) decoder.readObject();
		return userInstance;
	}
	
	public static void storeListToDisk(ArrayList<User> users)
	{
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("UserList.xml")));
		} catch(FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File "	+ "UserList.xml");
		}
		encoder.writeObject(users);
		encoder.close();
	}
	
	public static ArrayList<User> loadListFromDisk()
	{
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(
					new FileInputStream("UserList.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File UserList.xml not found");
		}
		@SuppressWarnings("unchecked")
		ArrayList<User> userListInstance = (ArrayList<User>) decoder.readObject();
		return userListInstance;
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
		User other = (User) obj;
		if (boardsMemberOf == null)
		{
			if (other.boardsMemberOf != null)
				return false;
		} else if (!boardsMemberOf.equals(other.boardsMemberOf))
			return false;
		if (boardsOwned == null)
		{
			if (other.boardsOwned != null)
				return false;
		} else if (!boardsOwned.equals(other.boardsOwned))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null)
		{
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

//	@Override
//	public boolean equals(Object obj)
//	{
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		User other = (User) obj;
//		if (boardsOwned == null)
//		{
//			if (other.boardsOwned != null)
//				return false;
//		} else if (!boardsOwned.equals(other.boardsOwned))
//			return false;
//		if (name == null)
//		{
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		if (password == null)
//		{
//			if (other.password != null)
//				return false;
//		} else if (!password.equals(other.password))
//			return false;
//		return true;
//	}
//	
}