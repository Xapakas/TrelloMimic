package myTrello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class User
{
	String name;
	String password;
	HasMembersList<Board> boards;
	
	public User() {}
	
	public User(String name, String password)
	{
		this.name = name;
		this.password = password;
		boards = new HasMembersList<Board>();
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

	public HasMembersList<Board> getBoards()
	{
		return boards;
	}

	public void setBoards(HasMembersList<Board> boards)
	{
		this.boards = boards;
	}

	public boolean addBoard(Board myBoard)
	{
		return boards.addMember(myBoard);
	}
	
	public boolean removeBoard(Board myBoard)
	{
		myBoard.setOwner(null);
		return boards.removeMember(myBoard);
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
		if (boards == null)
		{
			if (other.boards != null)
				return false;
		} else if (!boards.equals(other.boards))
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
	
}