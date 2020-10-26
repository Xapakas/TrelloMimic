package myTrello;

import java.util.Collection;

public interface HasMembers<T>
{
	public boolean addMember(T member);
	public boolean removeMember(T member);
	public boolean hasMember(T member);
	public Collection<T> getMembers();
}
