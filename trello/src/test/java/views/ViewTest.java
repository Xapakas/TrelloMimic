package views;

import org.testfx.assertions.api.Assertions;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import myTrello.Board;

@ExtendWith(ApplicationExtension.class)
public class ViewTest
{
	MainController mc;
	
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
	
	private void loginInputs(FxRobot robot, String username, String password, String server)
	{
		enterText(robot, username,"#usernameTextField");
		enterText(robot, password,"#passwordTextField");
		enterText(robot, server,"#serverTextField");
		robot.clickOn("#loginButton");
	}
	
	private void verifyPopupMessage(FxRobot robot, String message)
	{
		Assertions.assertThat(robot.lookup("#popupLabel").
				queryAs(Label.class)).hasText(message);
		robot.clickOn("#closeButton");
	}
	
	@SuppressWarnings("unchecked")
	ListView<String> getBoardNames(FxRobot robot)
	{
		return (ListView<String>) robot.lookup("#boardListView").queryAll()
				.iterator().next();
	}
	
	@Test
	public void mainTest(FxRobot robot)
	{
//		loginInputs(robot,"Noah","hunter2","wrongservername");
//		verifyPopupMessage(robot,"Server error: recheck server name and try again.");
//		loginInputs(robot,"Noah","wrongpassword","localhost");
//		verifyPopupMessage(robot,"Invalid username or password.");
//		loginInputs(robot,"wrongusername","hunter2","localhost");
//		verifyPopupMessage(robot,"Invalid username or password.");
//		loginInputs(robot,"Noah","hunter2","localhost"); // correct input. go to user page
//		
//		Assertions.assertThat(robot.lookup("#userNameLabel").
//				queryAs(Label.class)).hasText("Noah");
		
		loginInputs(robot,"Hackerman","[hacker voice] i'm in","localhost");
		
		enterText(robot,"roboticBoard","#newBoardText");
		robot.clickOn("#submitNewBoardButton");
		
		ListView<String> displayedBoardNames = getBoardNames(robot);
		robot.clickOn("#saveButton");
		ArrayList<Board> actualBoards = mc.currentUser.getBoardsMemberOf().getMembers();
		Assertions.assertThat(displayedBoardNames).hasExactlyNumItems(actualBoards.size());
		
		for (Board board : actualBoards)
		{
			Assertions.assertThat(displayedBoardNames).hasListCell(board.getName());
		}
	}
}
