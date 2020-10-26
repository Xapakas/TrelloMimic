package myTrello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class HasMembersList<T> implements HasMembers<T>
{
	ArrayList<T> members;

	public HasMembersList()
	{
		members = new ArrayList<T>();
	}

	public boolean addMember(T member)
	{
		return members.add(member);
	}

	public boolean removeMember(T member)
	{
		return members.remove(member);
	}

	public boolean hasMember(T member)
	{
		if (members.indexOf(member) == -1) {
			return false;
		}
		return true;
	}

	public ArrayList<T> getMembers()
	{
		return members;
	}
	
	public void storeToDisk()
	{
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("HasMembersList.xml")));
		} catch(FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File "	+ "HasMembersList.xml");
		}
		encoder.writeObject(this);
		encoder.close();
	}
	
	public static HasMembersList<?> loadFromDisk()
	{
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(
					new FileInputStream("HasMembersList.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File HasMembersList.xml not found");
		}
		HasMembersList<?> hasMembersListInstance = (HasMembersList<?>) decoder.readObject();
		return hasMembersListInstance;
	}

//	@Override
//	public int hashCode()
//	{
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((members == null) ? 0 : members.hashCode());
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
		@SuppressWarnings("unchecked")
		HasMembersList<T> other = (HasMembersList<T>) obj;
		if (members == null)
		{
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		return true;
	}
	
}
