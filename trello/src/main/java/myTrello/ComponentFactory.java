package myTrello;

public class ComponentFactory
{
	public static Component createComponent(String type, String description)
	{
		if (type.equals("Day"))
		{
			return new DayComponent(description);
		}
		else if (type.equals("Urgency"))
		{
			return new UrgencyComponent(description);
		}
		else if (type.equals("Topic"))
		{
			return new TopicComponent(description);
		}
		System.out.println("Error: ComponentFactory reached unreachable return null state");
		return null;
	}
}
