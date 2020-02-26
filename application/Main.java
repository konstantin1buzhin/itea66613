package application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Main extends Application {
	Stage primaryStage;
	TextField dogName = new TextField();
	TextField dogAge = new TextField();
	TextField ownerName = new TextField();
	TextField checkDog = new TextField();
	DogCache dogCache = new DogCache();
	ByteArrayInputStream bais;
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {

			Label label1 = new Label("Dog`s name:");
			Label label2 = new Label("Dog`s age:");
			Label label3 = new Label("Owner name:");

			Button createDog = new Button("Create Dog");
			Button gc = new Button("GC");
			gc.setMinWidth(76);
			Button buttonGetDog = new Button("get Dog");
			GridPane root = new GridPane();
			root.setAlignment(Pos.TOP_CENTER);
			root.setHgap(10);
			root.setVgap(10);
			root.setPadding(new Insets(30, 30, 30, 30));
			root.add(label1, 0, 0);
			root.add(label2, 0, 1);
			root.add(label3, 0, 2);
			root.add(dogName, 1, 0);
			root.add(dogAge, 1, 1);
			root.add(ownerName, 1, 2);
			root.add(checkDog, 1, 6);
			HBox hBox = new HBox();
			hBox.getChildren().add(createDog);
			hBox.setAlignment(Pos.BASELINE_RIGHT);
			root.add(hBox, 1, 3);
			root.add(gc, 0, 6);
			root.add(buttonGetDog, 0, 7);
			Scene scene = new Scene(root, 360, 360);
			primaryStage.setScene(scene);
			primaryStage.show();

			createDog.setOnAction((event) -> {
//				DBWorker dbWorker = new DBWorker();
//				dbWorker.createDog(dogName.getText(), new Integer(dogAge.getText()), ownerName.getText());
				Owner owner = new Owner(ownerName.getText());
				Dog dog = dogCache.getDog(dogName.getText(), new Integer(dogAge.getText()), owner, checkDog);
				
				FileOutputStream fos = null;
				baos = new ByteArrayOutputStream();
				try {
				    fos = new FileOutputStream(new File("dog.bin")); 
				    

				    byte[] array = dog.toString().getBytes();
				    baos.write(array);
				    baos.writeTo(fos);
				} catch(IOException ioe) {
				    ioe.printStackTrace();
				} finally {
				    try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				
				
				
				

			});
			gc.setOnAction((event) -> {
				System.gc();
				checkDog.setText("Garbage collector");

			});
			buttonGetDog.setOnAction((event) -> {
				byte[] arr;
				try {
					arr = Files.readAllBytes(Paths.get("dog.bin"));
					bais = new ByteArrayInputStream(arr);
					int b =0;
					String masiv = "";
			         while((b = bais.read())!=-1) {
			            char c = (char)b;
			            masiv+=c;
			         }
			         checkDog.setText(masiv);
				} catch (IOException e2) {
					e2.printStackTrace();
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
