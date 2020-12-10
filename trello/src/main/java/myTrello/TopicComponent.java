package myTrello;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class TopicComponent extends Component
{
	private static final long serialVersionUID = -2320482192956076449L;
	String description;
	static Map<String, String> dictionary;
	
	public TopicComponent() {}; // for XML
	
	public TopicComponent(String description)
	{
		this.description = description;
		dictionary = new TreeMap<String, String>();
		
		dictionary.put("Project", "#0066ff"); // blue
		dictionary.put("Event", "#ff6600"); // orange
		dictionary.put("Problem", "#cc0000"); // red
		dictionary.put("Goal", "#00cc00"); // green
		dictionary.put("Reminder", "#9900ff"); // purple
		dictionary.put("Other", "#ffc61a"); // dark yellow
		
		/* ORDER: Event, Goal, Other, Problem, Project, Reminder.
		 * Why is that the order? Idk, but it shows up in the
		 * same order every time, so mission accomplished. */
	}
	
	@Override
	public String getColor()
	{
		return dictionary.get(description);
	}

	public String[] getDescOptions()
	{	
		/* The reason this is so convoluted is so that the 
		 * dictionary will return everything in order. */
		
		ArrayList<String> descOptions = new ArrayList<String>();
		Set<Entry<String, String>> entrySet = dictionary.entrySet();
		Iterator<Entry<String, String>> i = entrySet.iterator();
		
		while (i.hasNext()) 
		{ 
            Map.Entry<String, String> entry = 
            		(Map.Entry<String, String>) i.next(); 
            String key = (String) entry.getKey(); 
            descOptions.add(key);
        }
		
		return descOptions.toArray(new String[dictionary.size()]);
	}
	
	public static Map<String, String> getDictionary()
	{
		return dictionary;
	}

	public static void setDictionary(Map<String, String> dictionary)
	{
		TopicComponent.dictionary = dictionary;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
