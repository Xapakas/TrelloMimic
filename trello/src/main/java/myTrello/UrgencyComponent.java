package myTrello;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class UrgencyComponent extends Component
{
	private static final long serialVersionUID = 2439481593894084912L;
	String description;
	static Map<String, String> dictionary = new TreeMap<String, String>();
	
	public UrgencyComponent() {}; // for XML
	
	public UrgencyComponent(String description)
	{
		this.description = description;
		
		dictionary.put("very urgent", "#E53000"); // red
		dictionary.put("urgent", "#B74C05"); // darkorangered
		dictionary.put("slightly urgent", "#89690B"); // orangebrown
		dictionary.put("not that urgent", "#5B8511"); // olive
		dictionary.put("not urgent", "#2DA217"); // dark green
		dictionary.put("lol ignore this", "#00BF1D"); // light green
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
		UrgencyComponent.dictionary = dictionary;
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
