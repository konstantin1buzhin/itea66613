package homework4;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerExpl implements Initializable{

	@FXML
	private ListView<String> listView;
	
	@FXML
	private Label labelRoot;

	@FXML
	private Label labelWindow;
	
	@FXML
	private TextField currentDir;
	
	@FXML
	private Button buttonNext;
	
	@FXML
	private Button buttonPrevious;
	
	@FXML
	private Button diskC;
	
	@FXML
	private Button diskD;
	
	@FXML
	private Button buttonSearch;
	
	Path path = Paths.get("C:\\");
	
	public void search() throws IOException {
		
		currentDir.setText(path.toString());
		ArrayList<String> myList = new ArrayList<String>();

			try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path.toString()))) {
	            for (Path p : directoryStream) {
	            	if(!p.toFile().isHidden()) {
	                myList.add(p.getFileName().toString());
	            	}
	            }
	        } catch (IOException ex) {
	        ex.printStackTrace();
	    }
		ObservableList<String> langs = FXCollections.observableArrayList(myList);
        listView.setItems(langs);
        MultipleSelectionModel<String> langsSelectionModel = listView.getSelectionModel();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>(){
             

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				labelRoot.setText(newValue);

			}
        });
		
	}
        
     String cutbuffer = "";
     String copybuffer = "";
               
	
	
	@FXML
	public void searchDir(ActionEvent event) throws IOException {
		path = Paths.get(currentDir.getText());
		if(Files.exists(path)) {
		search();
		}
	}
	
	@FXML
	private void showNext(ActionEvent event) throws IOException {
		if(!labelRoot.equals(null)) {
		path = Paths.get(path.toString()+"\\"+labelRoot.getText());
		if(Files.exists(path)&&Files.isRegularFile(path)){
			Desktop desktop = Desktop.getDesktop();
			desktop.open(path.toFile());
			path = Paths.get(path.toString().substring(0, path.toString().lastIndexOf("\\")));
		}else {
			search();
		}
		}
				
		
	}
	
	@FXML
	private void showPrevious(ActionEvent event) throws IOException {
		if(!path.getRoot().equals(path)) {
		path = Paths.get(path.getParent().toString());
		search();
		}
	}

	@FXML
	private void showDiskC(ActionEvent event) throws IOException {
		path = Paths.get("C:\\");
		search();
	}
	
	@FXML
	private void showDiskD(ActionEvent event) throws IOException {
		path = Paths.get("D:\\");
		search();
	}
	String lastPath;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount()==2&&!labelRoot.getText().equals(null)&&event.getButton() == MouseButton.PRIMARY) {
					ActionEvent ev = new ActionEvent();
					try {
						showNext(ev);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if(event.getButton() == MouseButton.SECONDARY) {
//						&&event.getClickCount()==1) {
					
//				
//					        if (evente.getCode().equals(KeyCode.ENTER)) {
					ObservableList<String> langss = FXCollections.observableArrayList("Open", "Rename", "Delete", "Create", "Cut", "Copy", "Paste");
					ListView<String> listSecon = new ListView<String>(langss);
					 MultipleSelectionModel<String> langsssSelectionModel = listSecon.getSelectionModel();
				        langsssSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>(){
				             

							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
								lastPath = newValue;
								

							}
				        });
	                
	                StackPane secLayout = new StackPane();
	                secLayout.getChildren().addAll(listSecon);
	     
	                Scene secScene = new Scene(secLayout, 300, 210);

	                Stage newwWindow = new Stage();
	                newwWindow.setTitle("Menu");
	                newwWindow.setScene(secScene);

	                newwWindow.initModality(Modality.WINDOW_MODAL);
	                newwWindow.show();
	                
	                listSecon.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							if(event.getClickCount()==2&&event.getButton() == MouseButton.PRIMARY) {
								ActionEvent ev = new ActionEvent();
								if(lastPath.equals("Open")) {
									try {
										showNext(ev);
									} catch (IOException e) {
										e.printStackTrace();
									}
									newwWindow.close();
									
								} else if(lastPath.equals("Rename")) {
									  Label secondLabel = new Label("Enter name of this file(directory)");
									  Label errorLabel = new Label("The file or directory(with this name) already exists");
									  
									  secondLabel.setLayoutX(100);
									  secondLabel.setLayoutY(50);
									  secondLabel.setTextAlignment(TextAlignment.CENTER);
									  secondLabel.setAlignment(Pos.CENTER);
									  
									  errorLabel.setLayoutX(40);
									  errorLabel.setLayoutY(90);
									  errorLabel.setTextAlignment(TextAlignment.CENTER);
									  errorLabel.setTextFill(Color.web("#FF0000"));
									  errorLabel.setAlignment(Pos.CENTER);
									  
									  TextField textFi = new TextField();
									  
									  textFi.setLayoutX(100);
									  textFi.setLayoutY(120);
									  textFi.setMinWidth(20);
									  textFi.setMinHeight(20);
									  textFi.setAlignment(Pos.CENTER);
									  
									  Button okey = new Button("Ok");
									  Button cancel = new Button("Cancel");
									  
									  okey.setLayoutX(100);
									  okey.setLayoutY(160);
									  okey.setMinWidth(70);
									  okey.setMinHeight(20);
									  
									  cancel.setLayoutX(200);
									  cancel.setLayoutY(160);
									  cancel.setMinWidth(70);
									  cancel.setMinHeight(20);
									  
									  AnchorPane root = new AnchorPane();
									  if(!labelRoot.getText().equals("")) {
									  textFi.setText(labelRoot.getText());
									  }
									  errorLabel.setVisible(false);
							            root.getChildren().addAll(secondLabel, errorLabel, textFi, okey, cancel);

							            Scene seconScene = new Scene(root, 400, 200);

							            Stage newwwWindow = new Stage();
							            newwwWindow.setTitle("Request");
							            newwwWindow.setScene(seconScene);

							            newwwWindow.initModality(Modality.WINDOW_MODAL);
							            if(!labelRoot.getText().equals("")) {
							            	 newwwWindow.show();
											  }
							           
							            okey.setOnAction(new EventHandler<ActionEvent>() {

											@Override
											public void handle(ActionEvent event) {
												
													path = Paths.get(currentDir.getText());
													try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path.toString()))) {
											            for (Path p : directoryStream) {
											            	if(Files.exists(p)&&textFi.getText().equals(p.getFileName().toString())) {
											            		errorLabel.setVisible(true);
											            	}
											            }
											        } catch (IOException ex) {
											        ex.printStackTrace();
											    }
													if(!labelRoot.getText().equals(null)&&!textFi.getText().equals(null)) {
													path = Paths.get(currentDir.getText()+"\\"+labelRoot.getText());
													Path path2 = Paths.get(currentDir.getText()+"\\"+textFi.getText());
													if(Files.notExists(path2)) {
														 try {
															Files.move(path, path2, StandardCopyOption.REPLACE_EXISTING);
															newwwWindow.close();
															path = Paths.get(currentDir.getText());
															search();
															
														} catch (IOException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													}
												
												
											}
											}
										});

										  cancel.setOnAction(new EventHandler<ActionEvent>() {

												@Override
												public void handle(ActionEvent event) {
													newwwWindow.close();
													path = Paths.get(currentDir.getText());
													try {
														search();
													} catch (IOException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}
											});
									
									newwWindow.close();
									
								} else if(lastPath.equals("Delete")) {
									
									Label secondLabel = new Label("Are you sure that \n you would like to delete this file(directory)");

									  secondLabel.setLayoutX(35);
									  secondLabel.setLayoutY(50);
									  secondLabel.setTextAlignment(TextAlignment.CENTER);
									  secondLabel.setFont(new Font("Arial", 18));
									  secondLabel.setAlignment(Pos.CENTER);

									  Button okey = new Button("Ok");
									  Button cancel = new Button("Cancel");
									  
									  okey.setLayoutX(100);
									  okey.setLayoutY(120);
									  okey.setMinWidth(70);
									  okey.setMinHeight(20);
									  
									  cancel.setLayoutX(200);
									  cancel.setLayoutY(120);
									  cancel.setMinWidth(70);
									  cancel.setMinHeight(20);
									  AnchorPane root = new AnchorPane();
									  
							            root.getChildren().addAll(secondLabel, okey, cancel);
							            
							            
							            Scene seconScene = new Scene(root, 400, 200);

							            Stage neWindow = new Stage();
							            neWindow.setTitle("Confirmation");
							            neWindow.setScene(seconScene);
							            neWindow.initModality(Modality.WINDOW_MODAL);
							            if(!labelRoot.getText().equals("")) {
							            	 neWindow.show();
											  }
							           
							            okey.setOnAction(new EventHandler<ActionEvent>() {

											@Override
											public void handle(ActionEvent event) {
												
													System.out.println(path.toString());
													
													path = Paths.get(currentDir.getText()+"\\"+labelRoot.getText());
													try {
														Files.delete(path);
														neWindow.close();
														path = Paths.get(currentDir.getText());
														search();
													} catch (IOException e) {
														e.printStackTrace();
													}
											}
										});

										  cancel.setOnAction(new EventHandler<ActionEvent>() {

												@Override
												public void handle(ActionEvent event) {
													neWindow.close();
													path = Paths.get(currentDir.getText());
													try {
														search();
													} catch (IOException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													
												}
											});
									
									
									newwWindow.close();
								} else if(lastPath.equals("Create")) {
									
									ObservableList<String> langss = FXCollections.observableArrayList("Folder","Text Document", 
											"Microsoft Word Document");
									ListView<String> listCreate = new ListView<String>(langss);
									 MultipleSelectionModel<String> langSelectionModel = listCreate.getSelectionModel();
								        langSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>(){
								             

											@Override
											public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//												labelWindow.setText(newValue);
												lastPath = newValue;
												

											}
								        });
					                
					                StackPane secoLayout = new StackPane();
					                secoLayout.getChildren().addAll(listCreate);
					     
					                Scene secoScene = new Scene(secoLayout, 300, 90);
					     
					                // New window (Stage)
					                Stage newwwwWindow = new Stage();
					                newwwwWindow.setTitle("Creation");
					                newwwwWindow.setScene(secoScene);
					     
					                // Specifies the modality for new window.
					                newwwwWindow.initModality(Modality.WINDOW_MODAL);
					                
					                
					                listCreate.setOnMouseClicked(new EventHandler<MouseEvent>() {

										@Override
										public void handle(MouseEvent event) {
											newwWindow.close();
											if(event.getClickCount()==2&&event.getButton() == MouseButton.PRIMARY) {
												if(lastPath.equals("Folder")) {
													
													createSomething(lastPath);
													
													newwwwWindow.close();
												} else if(lastPath.equals("Text Document")) {
													
													createSomething(lastPath);
													
													newwwwWindow.close();
												} else if(lastPath.equals("Microsoft Word Document")) {
													
													createSomething(lastPath);
													
													newwwwWindow.close();
												}
											}
										}
					                	
					                });
					                newwwwWindow.show();
									
									
								} else if(lastPath.equals("Cut")) {
									
									 path= Paths.get(currentDir.getText()+"/"+labelRoot.getText());
	                                    copybuffer = path.toString();
	                                    cutbuffer = path.toString();
									
									newwWindow.close();
								} else if(lastPath.equals("Copy")) {
									path= Paths.get(currentDir.getText()+"/"+labelRoot.getText());
                                    
                                    copybuffer = path.toString();
                                    cutbuffer = "";
									
									newwWindow.close();
								} else if(lastPath.equals("Paste")) {
									path= Paths.get(copybuffer);
                                    System.out.println(path.toString());
                                    copybuffer = copybuffer.substring(copybuffer.lastIndexOf("\\"));

                                    Path newdir1;
                                    Path newdir;
                                    if(!copybuffer.equals("")&&cutbuffer.equals("")) {
                                      if(!(labelRoot.getText()==null)) {
                                        newdir1 = Paths.get(currentDir.getText()+"\\"+labelRoot.getText());
                                        newdir = Paths.get(currentDir.getText()+"\\"+labelRoot.getText()+"\\"+copybuffer);
                                              } else {
                                                newdir1 = Paths.get(currentDir.getText()+"\\"+copybuffer);
                                                newdir = Paths.get(currentDir.getText()+"\\"+copybuffer);
                                              }
                                    if(Files.exists(newdir)&&Files.isDirectory(newdir)){

                                      newdir = Paths.get(newdir.toString()+"_copy");
                                    } else if(Files.isDirectory(newdir)&&!Files.exists(newdir)){
                                      try {
                            Files.createDirectory(newdir);
                          } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                          }
                                    } else if(Files.isRegularFile(newdir)&&Files.exists(newdir)){
                                      newdir = Paths.get(newdir.toUri()+"_copy");
                                    } else if(Files.isRegularFile(newdir)&&!Files.exists(newdir)){
                                    }
                                    try { 
                                            Files.walkFileTree(path, new MyFileCopyVisitor(path, newdir)); 
                                                 
                                        } catch (IOException e) { 
                                            e.printStackTrace(); 
                                        }
                                    
                                  } else if(!cutbuffer.equals("")) {
                                    path= Paths.get(cutbuffer);
                                    cutbuffer = cutbuffer.substring(cutbuffer.lastIndexOf("\\"));
                                      if(!(labelRoot.getText()==null)) {
                                    newdir1 = Paths.get(currentDir.getText()+"\\"+labelRoot.getText()+"\\"+cutbuffer);
                                      } else {
                                        newdir1 = Paths.get(currentDir.getText()+"\\"+cutbuffer);
                                      }
                                    try {
                          Files.move(path, newdir1, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                          // TODO Auto-generated catch block
                          e.printStackTrace();
                        }
                                  }
									newwWindow.close();
									path = Paths.get(currentDir.getText());
									try {
										search();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

							} 
						}
				});

				}
				
			}
		});
		
	}
	
	int num = 0;
	
	public void createSomething(String str) {
		Label secondLabel;
		Path newPath;
		
		if(str.equals("Folder")) {
			secondLabel = new Label("Enter name of this directory");
			newPath = Paths.get(currentDir.getText()+"\\"+"new Folder");
			while(Files.exists(newPath)){
				num+=1;
				newPath = Paths.get(currentDir.getText()+"\\"+"new Folder"+num);
			};
		} else if(str.equals("Text Document")){
			secondLabel = new Label("Enter name of this file");
			newPath = Paths.get(currentDir.getText()+"\\"+"new Text Document.txt");
			while(Files.exists(newPath)){
				num+=1;
				newPath = Paths.get(currentDir.getText()+"\\"+"new Text Document"+num+".txt");
			};
		}  else if(str.equals("Microsoft Word Document")){
			secondLabel = new Label("Enter name of this file");
			newPath = Paths.get(currentDir.getText()+"\\"+"new Microsoft Word Document.docx");
			while(Files.exists(newPath)){
				num+=1;
				newPath = Paths.get(currentDir.getText()+"\\"+"new Microsoft Word Document"+num+".docx");
			};
		} else {
			secondLabel = new Label("Enter name of this file or directory");
			newPath = Paths.get(currentDir.getText()+"\\"+"new File");
		}
		
		  Label errorLabel = new Label("The file or directory(with this name) already exists");
		  
		  secondLabel.setLayoutX(100);
		  secondLabel.setLayoutY(50);
		  secondLabel.setTextAlignment(TextAlignment.CENTER);
		  secondLabel.setAlignment(Pos.CENTER);
		  
		  errorLabel.setLayoutX(40);
		  errorLabel.setLayoutY(90);
		  errorLabel.setTextAlignment(TextAlignment.CENTER);
		  errorLabel.setTextFill(Color.web("#FF0000"));
		  errorLabel.setAlignment(Pos.CENTER);
		  
		  TextField textFi = new TextField();
		  
		  textFi.setLayoutX(100);
		  textFi.setLayoutY(120);
		  textFi.setMinWidth(20);
		  textFi.setMinHeight(20);
		  textFi.setAlignment(Pos.CENTER);
		  
		  Button okey = new Button("Ok");
		  Button cancel = new Button("Cancel");
		  
		  okey.setLayoutX(100);
		  okey.setLayoutY(160);
		  okey.setMinWidth(70);
		  okey.setMinHeight(20);
		  
		  cancel.setLayoutX(200);
		  cancel.setLayoutY(160);
		  cancel.setMinWidth(70);
		  cancel.setMinHeight(20);

		  textFi.setText(newPath.getFileName().toString());
		  
		  
		  
		  AnchorPane root = new AnchorPane();
		  errorLabel.setVisible(false);
          root.getChildren().addAll(secondLabel, errorLabel, textFi, okey, cancel);
          
          
          Scene seconScene = new Scene(root, 400, 200);

          // New window (Stage)
          Stage newWindow = new Stage();
          newWindow.setTitle("Creation");
          newWindow.setScene(seconScene);

          // Specifies the modality for new window.
          newWindow.initModality(Modality.WINDOW_MODAL);
          
          newWindow.show();
	
         
          okey.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					
						path = Paths.get(currentDir.getText());
						try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path.toString()))) {
				            for (Path p : directoryStream) {
				            	if(Files.exists(p)&&textFi.getText().equals(p.getFileName().toString())) {
				            		errorLabel.setVisible(true);
				            	}
				            }
				        } catch (IOException ex) {
				        ex.printStackTrace();
				    }
						Path pa;
//						System.out.println(pa);
//						System.out.println(labelRoot.getText()==null);
						
						path = Paths.get(currentDir.getText()+"\\"+textFi.getText());
						pa = Paths.get(currentDir.getText()+"\\"+textFi.getText());
						
						if(Files.isRegularFile(pa)){
							path = Paths.get(currentDir.getText()+"\\"+labelRoot.getText()+"\\"
									+textFi.getText());
							
						}else {
							path = Paths.get(currentDir.getText()+"\\"+textFi.getText());
							System.out.println(path.toString());
						}
						if(!textFi.getText().equals(null)) {
						int arr = path.toString().indexOf('.');
						System.out.println(arr);
						if(arr==-1) {
							System.out.println("first");
							 try {
								Files.createDirectory(path);
								newWindow.close();
								path = Paths.get(currentDir.getText());
								search();
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							System.out.println("second");
							 try {
									Files.createFile(path);
									newWindow.close();
									path = Paths.get(currentDir.getText());
									search();
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
					
					
				}
				}
			});
			  
			  
			  cancel.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						newWindow.close();
						path = Paths.get(currentDir.getText());
						try {
							search();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
}

	


class MyFileCopyVisitor extends SimpleFileVisitor<Path> { 
    Path source, destination; 
 
    public MyFileCopyVisitor(Path s, Path d) { 
        source = s; 
        destination = d; 
    } 
 
    public FileVisitResult visitFile(Path path, 
            BasicFileAttributes fileAttributes) throws IOException, NoSuchFileException { 
        Path newd = destination.resolve(source.relativize(path)); 
        if(!Files.isHidden(path)) {
      
      
        try {  
            Files.copy(path, newd, StandardCopyOption.REPLACE_EXISTING); 
          
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return FileVisitResult.CONTINUE; 
    } return FileVisitResult.CONTINUE; 
    }

 
    public FileVisitResult preVisitDirectory(Path path, 
            BasicFileAttributes fileAttributes) throws IOException, NoSuchFileException { 
        Path newd = destination.resolve(source.relativize(path)); 
        if(!Files.isHidden(path)) {
        try { 
            Files.copy(path, newd, StandardCopyOption.REPLACE_EXISTING); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return FileVisitResult.CONTINUE; 
    }  return FileVisitResult.CONTINUE; 
    }
}




