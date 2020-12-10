package myTrello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.HashSet;

public class HasMembersSet<T> implements HasMembers<T>, Serializable
{
	private static final long serialVersionUID = -7201526687353969157L;
	HashSet<T> members;

	public HasMembersSet()
	{
		members = new HashSet<T>();
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
		return members.contains(member);
	}

	public HashSet<T> getMembers()
	{
		return members;
	}
	
	public void setMembers(HashSet<T> members)
	{
		this.members = members;
	}
	
	public void storeToDisk()
	{
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("HasMembersSet.xml")));
		} catch(FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File "	+ "HasMembersSet.xml");
		}
		encoder.writeObject(this);
		encoder.close();
	}
	
	@SuppressWarnings("rawtypes")
	public static HasMembersSet loadFromDisk()
	{
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(
					new FileInputStream("HasMembersSet.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File HasMembersSet.xml not found");
		}
		HasMembersSet hasMembersSetInstance = (HasMembersSet) decoder.readObject();
		return hasMembersSetInstance;
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
		@SuppressWarnings("unchecked")
		HasMembersSet<T> other = (HasMembersSet<T>) obj;
		if (members == null)
		{
			if (other.members != null)
				return false;
		}
		else if (!(members.size() == other.members.size()))
		for (T member:members) {
			boolean success = false;
			for (T otherMember:other.members) {
				if (member.equals(otherMember)) {
					success = true;
				}
			}
			if (!success) {
				return false;
			}
		}
		return true;
	}
}