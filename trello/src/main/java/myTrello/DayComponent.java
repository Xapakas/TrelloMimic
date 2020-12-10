package myTrello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class DayComponent extends Component
{
	private static final long serialVersionUID = 4175701029166810124L;
	String description;
	static ArrayList<String> dayOptions;
	String[] colorScale;
	
	public DayComponent() {}; // for XML
	
	public DayComponent(String description)
	{
		this.description = description;
		dayOptions = new ArrayList<>(Arrays.asList("Sunday", 
				"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));
		
		colorScale = new String[] {"#E52F00", "#DE7000", "#D8AD00", "#BDD200", "#7BCB00", 
				"#3DC500", "#02BF00"}; // HSV gradient scale from red to green
	}
	
	@Override
	public String getColor()
	{
		Calendar calendar = Calendar.getInstance();
		// number ranges from 1 (Sunday) to 7 (Saturday) 
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		int proximity = Math.abs((day - 1) - dayOptions.indexOf(description));
		
		return colorScale[proximity];
	}

	public String[] getDescOptions()
	{
		return dayOptions.toArray(new String[dayOptions.size()]);
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String[] getColorScale()
	{
		return colorScale;
	}

	public void setColorScale(String[] colorScale)
	{
		this.colorScale = colorScale;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public static ArrayList<String> getDayoptions()
	{
		return dayOptions;
	}

	public static void setDayOptions(ArrayList<String> dayOptions)
	{
		DayComponent.dayOptions = dayOptions;
	}
	
}
