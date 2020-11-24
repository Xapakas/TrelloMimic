package views;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class SprintFiveTest
{
	MainController mc;
	int compNum = 1;
	
	@Start
	private void start(Stage stage)
	{
		mc = new MainController(stage);
		mc.showLoginPage();
	}
	
	private void enterText(FxRobot robot, String text, String target)
	{
		robot.clickOn(target);
		robot.write(text);
	}
	
	private void testComponent(FxRobot robot, int typeIndex, 
			int descriptionIndex, String expectedText, String expectedColor)
	{
		robot.clickOn("#addComponentButton");
		Assertions.assertThat(robot.lookup("#labelMessageDropdown").
				queryAs(Label.class)).hasText("Choose a component type!");
		robot.lookup("#listViewDropdown").queryAs(ListView.class).
			getSelectionModel().select(typeIndex);
		robot.clickOn("#submitButtonDropdown");
		
		Assertions.assertThat(robot.lookup("#labelMessageDropdown").
				queryAs(Label.class)).hasText("Choose a component description!");
		robot.lookup("#listViewDropdown").queryAs(ListView.class).
			getSelectionModel().select(descriptionIndex);
		robot.clickOn("#submitButtonDropdown");
		
		checkComponent(robot, expectedText, expectedColor);
	}
	
	private void checkComponent(FxRobot robot, String expectedText, 
			String expectedColor)
	{
		Assertions.assertThat(robot.lookup("#componentLabel" + compNum)
				.queryAs(Label.class)).hasText(expectedText);
		Paint color = robot.lookup("#componentLabel" + compNum)
				.queryAs(Label.class).getTextFill();
		Assertions.assertThat(color).hasToString(expectedColor);
		
		compNum++;
	}
	
	@Test
	public void mainTest(FxRobot robot)
	{
		/* Login */
		
		enterText(robot, "Noah", "#usernameTextField");
		enterText(robot, "hunter2", "#passwordTextField");
		enterText(robot, "localhost", "#serverTextField");
		robot.clickOn("#loginButton");
		
		/* Create Board */
		
		enterText(robot,"roboticBoard","#newBoardText");
		robot.clickOn("#submitNewBoardButton");
		
		/* Go To Board */
		
		int lastIndex = mc.currentUser.getBoardsMemberOf().getMembers().size()
				- 1;
		robot.lookup("#boardListView").queryAs(ListView.class).
		getSelectionModel().select(lastIndex);
		robot.clickOn("#goToBoardButton");

		/* Create a List */
		
		robot.clickOn("#newListButton");
		robot.clickOn("#inputTextField");
		enterText(robot,"roboList","#inputTextField");
		robot.clickOn("#submitButton");
		
		/* Create a Card */
		
		robot.clickOn("#addNewCardButton");
		enterText(robot, "exterminate humans", "#inputTextField");
		robot.clickOn("#submitButton");
		
		robot.clickOn("#cardAccordion0"); // click on its accordion
		
		/* Add Components */

		/* It adds "0x" at the beginning and "ff" to the end of any
		 * hex code I used in my project. */
		
		/* test topic components */
		testComponent(robot,2,0,"Event","0xff6600ff");
		testComponent(robot,2,1,"Goal","0x00cc00ff");
		testComponent(robot,2,2,"Other","0xffc61aff");
		testComponent(robot,2,3,"Problem","0xcc0000ff");
		testComponent(robot,2,4,"Project","0x0066ffff");
		testComponent(robot,2,5,"Reminder","0x9900ffff");
		
		/* test urgency components */
		testComponent(robot,1,0,"lol ignore this","0x00bf1dff");
		testComponent(robot,1,1,"not that urgent","0x5b8511ff");
		testComponent(robot,1,2,"not urgent","0x2da217ff");
		testComponent(robot,1,3,"slightly urgent","0x89690bff");
		testComponent(robot,1,4,"urgent","0xb74c05ff");
		testComponent(robot,1,5,"very urgent","0xe53000ff");
		
		/* test day components */
		String[] colorScale = new String[] {"0xe52f00ff", "0xde7000ff", "0xd8ad00ff", 
				"0xbdd200ff", "0x7bcb00ff", "0x3dc500ff", "0x02bf00"};
		String[] daysOfWeek = new String[] {"Sunday", "Monday", "Tuesday",
				"Wednesday", "Thursday", "Friday", "Saturday"};
		
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		/* let the user see that the test has the right day of the week,
		 * since it's using the same method as that which it is testing */
		System.out.println("The day today is " + daysOfWeek[day - 1]);
		
		for (int i = 0; i < daysOfWeek.length; i++)
		{
			testComponent(robot,0,i, daysOfWeek[i], 
					colorScale[Math.abs(day - (i + 1))]);
		}
		
		/* Now, make sure all of the data is saved to the server properly */
		
		robot.clickOn("#saveButton");
		robot.clickOn("#backButton");
		robot.clickOn("#backButton");
		
		enterText(robot, "Noah", "#usernameTextField"); // login
		enterText(robot, "hunter2", "#passwordTextField");
		enterText(robot, "localhost", "#serverTextField");
		robot.clickOn("#loginButton");
		
		robot.lookup("#boardListView").queryAs(ListView.class). // go to board
			getSelectionModel().select(lastIndex);
		robot.clickOn("#goToBoardButton");
		
		compNum = 1;
		robot.clickOn("#cardAccordion0");
		
		checkComponent(robot,"Event","0xff6600ff");
		checkComponent(robot,"Goal","0x00cc00ff");
		checkComponent(robot,"Other","0xffc61aff");
		checkComponent(robot,"Problem","0xcc0000ff");
		checkComponent(robot,"Project","0x0066ffff");
		checkComponent(robot,"Reminder","0x9900ffff");
		
		checkComponent(robot,"lol ignore this","0x00bf1dff");
		checkComponent(robot,"not that urgent","0x5b8511ff");
		checkComponent(robot,"not urgent","0x2da217ff");
		checkComponent(robot,"slightly urgent","0x89690bff");
		checkComponent(robot,"urgent","0xb74c05ff");
		checkComponent(robot,"very urgent","0xe53000ff");
		
		for (int i = 0; i < daysOfWeek.length; i++)
		{
			checkComponent(robot, daysOfWeek[i], colorScale[Math.abs(day - (i + 1))]);
		}
		
		/* Allow repeated tests by deleting the board 
		 * (since duplicate names are not allowed).
		 * If an assertion fails before this, you'll
		 * have to delete the board manually. */
		
		robot.clickOn("#backButton");
		robot.lookup("#boardListView").queryAs(ListView.class).
			getSelectionModel().select(lastIndex);
		robot.clickOn("#removeBoardButton");
		robot.clickOn("#saveButton");
	}
}
