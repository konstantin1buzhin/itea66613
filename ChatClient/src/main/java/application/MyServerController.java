package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MyServerController {

	@FXML
	private Label eStatus = new Label("Server");

	@FXML
	private Label serverLabel = new Label("");

	static ServerSocket ss;
	HashMap clientColl = new HashMap();

	final int port = 3150;

	public MyServerController() {
		try {
			ss = new ServerSocket(MainServer.port, 0, InetAddress.getByName(MainServer.ipAddress));
			System.out.println("server started");

			new ClientAccept().start();
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Test Connection");
			alert.setHeaderText(null);
			alert.setContentText("Connection is refused!\nServer is closed");

			alert.showAndWait();
		}
	}

	DBClient dbClient = new DBClient();
	DataOutputStream dos;

	class ClientAccept extends Thread {

		public void run() {
			while (true) {
				try {
					Socket s = ss.accept();
					String setLine = new DataInputStream(s.getInputStream()).readUTF();
					if (!dbClient.getUser(setLine).equals(setLine)) {
						System.out.println("creating...");
						dbClient.createClient(setLine);
					} else {
						dbClient.getUser(setLine);
						System.out.println("getting...");
					}
					if (clientColl.containsKey(setLine)) {
						dos = new DataOutputStream(s.getOutputStream());
						dos.writeUTF("You are already registered...!!!");

					} else {
						clientColl.put(setLine, s);
						msgBox.appendText(setLine + " Joined ! \n");
						DataOutputStream dos = new DataOutputStream(s.getOutputStream());
						dos.writeUTF("");

						new MsgRead(s, setLine).start();
						new PrepareClientList().start();
					}

				} catch (Exception ex) {
					if (ss.isClosed()) {
						break;
					}
				}
			}
		}
	}

	class MsgRead extends Thread {
		Socket s;
		String ID;

		public MsgRead(Socket s, String ID) {
			this.s = s;
			this.ID = ID;
		}

		public void run() {
			while (!clientColl.isEmpty()) {
				try {
					String setLine = new DataInputStream(s.getInputStream()).readUTF();
					if (setLine.equals("mkoihgteazdovgyhujb096785542AXTY")) {
						clientColl.remove(ID);
						msgBox.appendText(ID + ": removed! \n");
						new PrepareClientList().start();

						Set k = clientColl.keySet();
						Iterator itr = k.iterator();
						while (itr.hasNext()) {
							String key = (String) itr.next();
							if (!key.equalsIgnoreCase(ID)) {
								// ("< " + s + " to " + key + " > " + setLine)
								try {
									new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream())
											.writeUTF("< " + s + " to " + key + " > " + setLine);
								} catch (Exception ex) {
									clientColl.remove(key);
									msgBox.appendText(key + ": removed!");
									new PrepareClientList().start();
								}
							}
						}
					} else if (setLine.contains("#4344554@@@@@67667@@")) {
						setLine = setLine.substring(20);
						StringTokenizer st = new StringTokenizer(setLine, ":");
						String id = st.nextToken();
						setLine = st.nextToken();
						try {
							// ("< " + ID + " to " + id + " > " + setLine)
							new DataOutputStream(((Socket) clientColl.get(id)).getOutputStream())
									.writeUTF("< " + ID + " to " + id + " > " + setLine);
						} catch (Exception ex) {

							clientColl.remove(id);
							msgBox.appendText(id + ": removed!");
							new PrepareClientList().start();

						}
					} else {
						Set kSet = clientColl.keySet();
						Iterator itra = kSet.iterator();
						while (itra.hasNext()) {
							String key2 = (String) itra.next();
							if (!key2.equalsIgnoreCase(ID)) {
								try {
									new DataOutputStream(((Socket) clientColl.get(key2)).getOutputStream())
											.writeUTF("< " + ID + " to All > " + setLine);

								} catch (Exception ex) {

									clientColl.remove(key2);
									msgBox.appendText(key2 + ": removed!");
									new PrepareClientList().start();

								}

							}
						}
					}
				} catch (Exception ex) {
					System.out.println("Connection reset");
					clientColl.remove(ID);
					msgBox.appendText(ID + ": removed! \n");
					break;
				}
			}

		}

	}

	class PrepareClientList extends Thread {

		@Override
		public void run() {
			try {
				String ids = "";
				Set k = clientColl.keySet();
				Iterator itr = k.iterator();
				while (itr.hasNext()) {
					String key = (String) itr.next();
					ids += key + ",";
				}
				if (ids.length() != 0) {
					ids = ids.substring(0, ids.length() - 1);
				}
				itr = k.iterator();
				while (itr.hasNext()) {
					String key = (String) itr.next();
					try {
						new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream()).writeUTF(":;.,/=" + ids);
					} catch (Exception ex) {
						clientColl.remove(key);
						msgBox.appendText(key + ": removed!");

					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@FXML
	private TextArea msgBox;

}
