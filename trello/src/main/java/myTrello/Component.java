package myTrello;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

public abstract class Component implements Serializable
{
	private static final long serialVersionUID = -6837121773639431385L;
	String description;
	int maxCapacity;
	
	public Component() {}
	
	public Component(String description, int maxCapacity)
	{
		this.description = description;
		this.maxCapacity = maxCapacity;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getMaxCapacity()
	{
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity)
	{
		this.maxCapacity = maxCapacity;
	}
	
	abstract public String getColor();
		
	public void storeToDisk()
	{
		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(
					new FileOutputStream("Component.xml")));
		} catch(FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File "	+ "Component.xml");
		}
		encoder.writeObject(this);
		encoder.close();
	}
	
	public static Component loadFromDisk()
	{
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(
					new FileInputStream("Component.xml")));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File Component.xml not found");
		}
		Component componentInstance = (Component) decoder.readObject();
		return componentInstance;
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
		Component other = (Component) obj;
		if (description == null)
		{
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (maxCapacity != other.maxCapacity)
			return false;
		return true;
	}
}