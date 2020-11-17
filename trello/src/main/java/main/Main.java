package main;

import javafx.application.Application;
import javafx.stage.Stage;
import views.MainController;

public class Main extends Application
{
	MainController mc;
	
	@Override
	public void start(Stage stage) throws Exception
	{
		mc = new MainController(stage);
		mc.showLoginPage();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}