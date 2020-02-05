package homework2;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Controller {
	
	@FXML
	private ImageView image;
	
	@FXML
	private Label phraseFirst;
	
	@FXML
	private Label phraseSecond;
	
	@FXML
	private Label phraseThird;
	
	@FXML
	private RadioButton English;
	
	@FXML
	private RadioButton Ukrainian;
	
	@FXML
	private RadioButton French;
	
	@FXML
	private Button save;
	
	@FXML
	private Button load;
	
	ToggleGroup group = new ToggleGroup();
	
	String language;
	String country;
	@FXML
	public void showEngVersion(){
		English.setToggleGroup(group);
		language = "en";
		country = "EN";
		Locale locale = new Locale(language, country);
		ResourceBundle bundle = ResourceBundle.getBundle("Message", locale);
		phraseFirst.setText(bundle.getString("phr1"));
		phraseSecond.setText(bundle.getString("phr2"));
		phraseThird.setText(bundle.getString("phr3"));
	}
	
	@FXML
	public void showNenkyVersion(){
		Ukrainian.setToggleGroup(group);
		language = "ukr";
		country = "UKR";
		Locale locale = new Locale(language, country);
		ResourceBundle bundle = ResourceBundle.getBundle("Message", locale);
		phraseFirst.setText(bundle.getString("phr1"));
		phraseSecond.setText(bundle.getString("phr2"));
		phraseThird.setText(bundle.getString("phr3"));
	}
	
	@FXML
	public void showFraVersion(){
		French.setToggleGroup(group);
		language = "fr";
		country = "FR";
		Locale locale = new Locale(language, country);
		ResourceBundle bundle = ResourceBundle.getBundle("Message", locale);
		phraseFirst.setText(bundle.getString("phr1"));
		phraseSecond.setText(bundle.getString("phr2"));
		phraseThird.setText(bundle.getString("phr3"));
	}
	
	@FXML
	public void savePhrases(ActionEvent event) {
		try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("info.bin"));) {
		      dataOutputStream.writeUTF(phraseFirst.getText());
		      dataOutputStream.writeUTF(phraseSecond.getText());
		      dataOutputStream.writeUTF(phraseThird.getText());
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	
	@FXML
	public void loadPhrases(ActionEvent event) {
		phraseFirst.setText("1");
		phraseSecond.setText("2");
		phraseThird.setText("3");
		 try (DataInputStream dis = new DataInputStream(new FileInputStream("info.bin"));) {
			 phraseFirst.setText(dis.readUTF());
			 phraseSecond.setText(dis.readUTF());
				phraseThird.setText(dis.readUTF());
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	
}
