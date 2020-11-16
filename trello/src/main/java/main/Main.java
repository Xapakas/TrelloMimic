package main;

import javafx.application.Application;
import javafx.stage.Stage;
import views.MainController;

public class Main extends Application
{
	@Override
	public void start(Stage stage) throws Exception
	{
		MainController vt = new MainController(stage);
		vt.showLoginPage();	
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}