package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientRegisterController {

	@FXML
	private TextField textNameClient;

	@FXML
	private Button buttonConnect;

	@FXML
	private Button reEnterButton;

	private void showAlertWithoutHeaderText() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Test Connection");
		alert.setHeaderText(null);
		alert.setContentText("You have entered port/ip wrong!");
		Button reEnter = new Button("Re-enter");
		alert.setGraphic(reEnter);

		alert.showAndWait();

	}

	@FXML
	public void showReEnter(ActionEvent event) {
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
		Stage primaryStage = (Stage) (((Node) event.getSource()).getScene().getWindow());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Client registration");
		primaryStage.show();
		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (portTextField.getText().equals("")) {
					showAlertWithoutHeaderText();
				} else {
					MainClient.ipAddress = ipTextField.getText();
					MainClient.port = new Integer(portTextField.getText());
					Parent root = null;
					try {
						root = FXMLLoader.load(getClass().getResource("/ClientRegister.fxml"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("Client");
					primaryStage.show();

				}
			}
		});
		useLocalHostButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (portTextField.getText().equals("")) {
					showAlertWithoutHeaderText();
				} else {
					MainClient.ipAddress = "127.0.0.1";
					MainClient.port = new Integer(portTextField.getText());
					Parent root = null;
					try {
						root = FXMLLoader.load(getClass().getResource("/ClientRegister.fxml"));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("Client");
					primaryStage.show();

				}
			}
		});
	}

	public ClientRegisterController() {

	}

	Socket s;
	DataInputStream din;
	DataOutputStream dout;
	ObservableList<String> langs = FXCollections.observableArrayList();
	ListView<String> listUsers = new ListView<String>();
	TextArea msgBox;
	String id;
	TextField sendText;
	String clientID = "";
	DataOutputStream dos;
	DataInputStream dis;
//	DBClient dbClient = new DBClient();

	@FXML
	public void showConnect(ActionEvent event) {
		try {
			id = textNameClient.getText();
			s = new Socket(InetAddress.getByName(MainClient.ipAddress), MainClient.port);
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(id);
			String currentLine = new DataInputStream(s.getInputStream()).readUTF();
			if (currentLine.equals("You are already registered...!!!")) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Test Connection");
				alert.setHeaderText(null);
				alert.setContentText("You are already online!");

				alert.showAndWait();
			} else {
				Button selectAll = new Button("selectAll");
				Button sendButton = new Button("Send");
				msgBox = new TextArea();
				sendText = new TextField();
				listUsers = new ListView<String>();
				listUsers.setMinSize(200, 250);
				Label idLabel = new Label("Hello: " + id);
				String iD = "";

				din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());

				listUsers.setItems(langs);
				GridPane root = new GridPane();
				root.setAlignment(Pos.CENTER);
				root.setHgap(10);
				root.setVgap(10);
				root.setPadding(new Insets(30, 30, 30, 30));
				selectAll.setMinSize(80, 40);
				sendText.setMinWidth(450);
				idLabel.setMinWidth(120);
				root.add(listUsers, 2, 1);
				root.add(selectAll, 0, 1);
				root.add(sendButton, 0, 2);
				root.add(msgBox, 1, 1);
				root.add(idLabel, 0, 0);
				HBox hBox = new HBox();
				hBox.getChildren().add(sendText);
				hBox.setAlignment(Pos.BASELINE_RIGHT);
				root.add(hBox, 1, 2);
				Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
				Scene scene = new Scene(root, 800, 400);
				Thread thread = new Thread(new Runnable() {
					String iD = id;

					@Override
					public void run() {
						while (true) {
							try {
								String m = dis.readUTF();

								if (m.contains(":;.,/=")) {
									m = m.substring(6);
									langs.clear();
									StringTokenizer st = new StringTokenizer(m, ",");
									while (st.hasMoreTokens()) {

										String u = st.nextToken();
										if (!iD.equals(u)) {
											langs.add(u);
											MultipleSelectionModel<String> langsSelectionModel = listUsers
													.getSelectionModel();
											langsSelectionModel.selectedItemProperty()
													.addListener(new ChangeListener<String>() {

														@Override
														public void changed(
																ObservableValue<? extends String> observable,
																String oldValue, String newValue) {
															clientID = newValue;

														}
													});

											Platform.runLater(new Runnable() {
												public void run() {
													listUsers.setItems(langs);

												}
											});
										}
									}
								} else {
									msgBox.appendText("" + m + "\n");
								}
							} catch (Exception ex) {
								try {
									String m = "#4344554@@@@@67667@@" + clientID + ": is leaved";
									dos.writeUTF(m);
									s.shutdownInput();
									s.shutdownOutput();
									s.close();
									break;
								} catch (IOException e) {
//									e.printStackTrace();

								}

							}
						}

					}
				});

				thread.setDaemon(true);
				thread.start();

				window.setScene(scene);

				window.show();

				selectAll.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						clientID = "";

					}
				});

				sendButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						try {

							String m = sendText.getText();
							String mm = m;
							String clientNumber = clientID;

							if (!clientID.isEmpty()) {
								m = "#4344554@@@@@67667@@" + clientNumber + ": " + mm;
								dos.writeUTF(m);
								sendText.setText("");

								msgBox.appendText("< YOU send to " + clientNumber + " > " + mm + "\n");
//								dbClient.setHistoryLine(id, "< YOU send to " + clientNumber + " > " + mm);
//								dbClient.getClientHistory(id);
							} else {
								dos.writeUTF(m);
								sendText.setText("");
								msgBox.appendText("< YOU send to All >" + mm + "\n");
//								dbClient.setHistoryLine(id, "< YOU send to All >" + mm);
//								dbClient.getClientHistory(id);
							}
						} catch (Exception ex) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Test Connection");
							alert.setHeaderText(null);
							alert.setContentText("Connection is refused!\nUser does not exist anymore.");
							alert.showAndWait();
						}

					}
				});

			}

		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Test Connection");
			alert.setHeaderText(null);
			alert.setContentText("Connection is refused!\nClient is closed");
			alert.showAndWait();

		}

	}
}
