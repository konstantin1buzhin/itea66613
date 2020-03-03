package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainServer extends Application {
	static String ipAddress;
	static int port;
	Stage primaryStage;

	public void setFxmlLoader() {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/MyServer.fxml"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("My Server");
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				try {
					MyServerController.ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		setMainScene();

	}

	private void setMainScene() {
		Button okButton = new Button("OK");
		Button useLocalHostButton = new Button("USE LOCALHOST");
		TextField ipTextField = new TextField();
		TextField portTextField = new TextField();
		Label ipLabel = new Label("Enter your ip:");
		Label portLabel = new Label("Enter your port:");
		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(30, 30, 30, 30));
		okButton.setMinWidth(130);
		root.add(okButton, 0, 3);
		root.add(ipTextField, 1, 0);
		root.add(portTextField, 1, 1);
		root.add(ipLabel, 0, 0);
		root.add(portLabel, 0, 1);
		HBox hBox = new HBox();
		hBox.getChildren().add(useLocalHostButton);
		hBox.setAlignment(Pos.BASELINE_RIGHT);
		root.add(hBox, 1, 3);
		Scene scene = new Scene(root, 600, 300);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Server registration");
		primaryStage.show();
		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (portTextField.getText().equals("")) {
					showAlertWithoutHeaderText();
				} else {
					ipAddress = ipTextField.getText();
					port = new Integer(portTextField.getText());
					setFxmlLoader();
				}
			}
		});
		useLocalHostButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (portTextField.getText().equals("")) {
					showAlertWithoutHeaderText();
				} else {
					ipAddress = "127.0.0.1";
					port = new Integer(portTextField.getText());
					setFxmlLoader();
				}
			}
		});
	}

	private void showAlertWithoutHeaderText() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Test Connection");
		alert.setHeaderText(null);
		alert.setContentText("You have entered port wrong!");
		Button reEnter = new Button("Re-enter");
		alert.setGraphic(reEnter);

		alert.showAndWait();

	}

	public static void main(String[] args) {

		launch(args);
	}
}
