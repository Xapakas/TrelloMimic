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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
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
				queryAs(Label.class)).hasText(message); // listview
		robot.clickOn("#closeButton");
	}
	
	@SuppressWarnings("unchecked")
	ListView<String> getBoardNames(FxRobot robot)
	{
		return (ListView<String>) robot.lookup("#boardListView").queryAll()
				.iterator().next();
	}
	
	private void compareBoardLists(FxRobot robot)
	{
		ListView<String> displayedBoardNames = getBoardNames(robot);
		ArrayList<Board> actualBoards = mc.currentUser.getBoardsMemberOf().getMembers();
		Assertions.assertThat(displayedBoardNames).hasExactlyNumItems(actualBoards.size());
		
		for (Board board : actualBoards)
		{
			Assertions.assertThat(displayedBoardNames).hasListCell(board.getName());
		}
	}
	
	private void boardInput(FxRobot robot, String buttonID, String name, 
			String target)
	{
		robot.clickOn(buttonID);
		enterText(robot, name, "#inputTextField");
		robot.clickOn("#submitButton");
		Assertions.assertThat(robot.lookup(target).
				queryAs(Label.class)).hasText(name);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void mainTest(FxRobot robot)
	{
//		loginInputs(robot,"Noah","hunter2","wrongservername");
//		verifyPopupMessage(robot,"Server error: recheck server name and try again.");
//		loginInputs(robot,"Noah","wrongpassword","localhost");
//		verifyPopupMessage(robot,"Invalid username or password.");
//		loginInputs(robot,"wrongusername","hunter2","localhost");
//		verifyPopupMessage(robot,"Invalid username or password.");
		loginInputs(robot,"Hackerman","[hacker voice] i'm in","localhost");
		
		Assertions.assertThat(robot.lookup("#userNameLabel").
				queryAs(Label.class)).hasText("Hackerman");
		robot.clickOn("#backButton");
		
		loginInputs(robot,"Noah","hunter2","localhost"); // correct input. go to user page
		Assertions.assertThat(robot.lookup("#userNameLabel").
				queryAs(Label.class)).hasText("Noah");
		
		compareBoardLists(robot);
		
		enterText(robot,"roboticBoard","#newBoardText");
		robot.clickOn("#submitNewBoardButton");
		
		robot.clickOn("#saveButton");
		compareBoardLists(robot);
		
		enterText(robot,"badBoard","#newBoardText");
		robot.clickOn("#submitNewBoardButton");
		
		
		int lastIndex = mc.currentUser.getBoardsMemberOf().getMembers().size() - 1;
		robot.lookup("#boardListView").queryAs(ListView.class).
			getSelectionModel().select(lastIndex);
		robot.clickOn("#removeBoardButton");
		robot.clickOn("#saveButton");
		compareBoardLists(robot);
		
		lastIndex = mc.currentUser.getBoardsMemberOf().getMembers().size() - 1;
		robot.lookup("#boardListView").queryAs(ListView.class).
			getSelectionModel().select(lastIndex);
		robot.clickOn("#goToBoardButton");
		Assertions.assertThat(robot.lookup("#boardNameLabel").
				queryAs(Label.class)).hasText("roboticBoard");
		
		boardInput(robot,"#newListButton","roboList","#listNameLabel");
		
		boardInput(robot,"#addNewCardButton","roboCard","#cardNameLabel");
		
		robot.clickOn("#cardAccordion");
		
		boardInput(robot,"#addLabelButton","roboLabel","#blabelLabel1");
		boardInput(robot,"#addComponentButton","roboComponent","#componentLabel1");
		boardInput(robot,"#addMemberButton","Noah","#memberLabel1");
		boardInput(robot,"#addComponentButton","otherComponent","#componentLabel2");
		boardInput(robot,"#addMemberButton","Hackerman","#memberLabel2");
		boardInput(robot,"#addLabelButton","otherLabel","#blabelLabel2");
		
		robot.clickOn("#backButton");
		robot.clickOn("#backButton");
		Assertions.assertThat(robot.lookup("#messageLabel").
				queryAs(Label.class)).hasText("You are about to log out and have unsaved changes. Would you like to save?");
		robot.clickOn("#saveButton");
		
		loginInputs(robot,"Noah","hunter2","localhost"); // correct input. go to user page
		Assertions.assertThat(robot.lookup("#userNameLabel").
				queryAs(Label.class)).hasText("Noah");
		
		robot.lookup("#boardListView").queryAs(ListView.class).
		getSelectionModel().select(lastIndex);
		robot.clickOn("#goToBoardButton");
		Assertions.assertThat(robot.lookup("#boardNameLabel").
			queryAs(Label.class)).hasText("roboticBoard");
				
		Assertions.assertThat(robot.lookup("#blabelLabel1").
				queryAs(Label.class)).hasText("roboLabel");
		Assertions.assertThat(robot.lookup("#blabelLabel2").
				queryAs(Label.class)).hasText("otherLabel");
		Assertions.assertThat(robot.lookup("#componentLabel1").
				queryAs(Label.class)).hasText("roboComponent");
		Assertions.assertThat(robot.lookup("#componentLabel2").
				queryAs(Label.class)).hasText("otherComponent");
		Assertions.assertThat(robot.lookup("#memberLabel1").
				queryAs(Label.class)).hasText("Noah");
		Assertions.assertThat(robot.lookup("#memberLabel2").
				queryAs(Label.class)).hasText("Hackerman");
	}
}
