package homework4;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException{
		
			Parent root = FXMLLoader.load(getClass().getResource("FileExplorer.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("File Explorer");
			primaryStage.show();
			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
