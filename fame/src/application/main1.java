package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

public class main1 extends Application{

	static String u_id,u_pass;
	static String u;
	static int block_size=15;
	public static boolean checking=false;
	
	static int width=30;
	static int height=20;
	static Field f;
	static Stage p;
	static String[] a;
	static int il=5;
	static long then=System.nanoTime();
	Thread thread;
	
	
	
	@Override
	public void start(Stage ps) {
		
		ps.setTitle("Login page");
		p=ps;
		FlowPane root0 = new FlowPane(200,50);
		Scene scen = new Scene(root0,400,400);
		root0.setAlignment(Pos.CENTER);
		TextField log=new TextField(),pass=new PasswordField();
		Button submit=new Button("Submit"),register=new Button("Register");
		Label lo=new Label("Enter the details");
		log.setPromptText("Login ID");
		pass.setPromptText("Password");
		lo.setFont(new Font("Arial",15));
		lo.setMinWidth(210);
		log.setMinWidth(210);
		pass.setMinWidth(210);

		submit.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent ae) {
			
			u_id=log.getText();
			u_pass=pass.getText();
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/java", "root", "1234"); 
	            
	            Statement st=con.createStatement();
	            ResultSet rs=st.executeQuery("select pass from results where id="+"\""+main1.u_id+"\";");
	            while(rs.next()) {u=rs.getString(1);}
	            if(u.equals(main1.u_pass)) {checking=true;}
	            else {checking=false;
	            	  start(p);}
	            con.close();
	        
			}
			catch(Exception e) {
				System.out.println("Error !!! "+e);
			}
			
			if(checking==true) {
	    		
				VBox root=new VBox(10);
				root.setPadding(new Insets(10));
					
				f = new Field(width,height);
				f.addSnake(new Snake(il,f));

				Label score= new Label("Score :0");
				score.setFont(Font.font("tahoma",32));
				
				
				AnimationTimer timer=new AnimationTimer() {
					public void handle(long now) {
						if(now-then>1000000000/15) {
						f.update();
						then=now;
						score.setText("Score :"+ f.score);
						
						if(f.isDead()) {
							stop();
							ps.setTitle(u_id+"'s Scoreboard");
							FlowPane root1 = new FlowPane(120,50);
							Scene sce = new Scene(root1,400,400);
							root1.setAlignment(Pos.CENTER);
							Label res=new Label("Your Score is :"+f.score);
							Button save=new Button("Save"),dsave=new Button("Don't Save");
							res.setMinWidth(240);
							save.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent ae) { 
								try {
								Class.forName("com.mysql.cj.jdbc.Driver");
					            Connection con =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/java", "root", "1234"); 
					            
					            Statement st=con.createStatement();
					            st.executeUpdate("update results set score="+"\""+f.score+"\""+"where id="+"\""+main1.u_id+"\";");
					            
					            con.close();
						        
								}
								catch(Exception e) {
									System.out.println("Error !!! "+e);
								}
								Alert a=new Alert(AlertType.INFORMATION);
								a.setHeaderText("Better Luck Next Time");
								a.setContentText(u_id+" your score is recorded.");
								Platform.runLater(a::showAndWait);
								a.setOnHidden(e->{
									root.getChildren().clear();
									f = new Field(width, height);
									score.setText("Score:0");
									root.getChildren().addAll(f,score);
									
								});
							}});
							dsave.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent ae) { 
								Alert a=new Alert(AlertType.INFORMATION);
								a.setHeaderText("Better Luck Next Time");
								a.setContentText(u_id+" your score is not recorded.");
								Platform.runLater(a::showAndWait);
								a.setOnHidden(e->{
									root.getChildren().clear();
									f = new Field(width, height);
									score.setText("Score:0");
									root.getChildren().addAll(f,score);
									
								});
							}});
							root1.getChildren().addAll(res,save,dsave);
							ps.setScene(sce);
							ps.show();
							}
						}
						
					}
					
				};
				timer.start();
				root.getChildren().addAll(f,score);
				
				Scene scene=new Scene(root);
				scene.setOnKeyPressed(e ->{
					if(e.getCode().equals(KeyCode.UP) && f.snake.getDirection() !=Block.DOWN) {
					f.snake.setDirection(Block.UP);
					}
					if(e.getCode().equals(KeyCode.DOWN)&& f.snake.getDirection()!=Block.UP) {
						f.snake.setDirection(Block.DOWN);
					}
					if(e.getCode().equals(KeyCode.LEFT) && f.snake.getDirection()!=Block.RIGHT) {
						f.snake.setDirection(Block.LEFT);
					}
					if(e.getCode().equals(KeyCode.RIGHT) && f.snake.getDirection()!=Block.LEFT) {
						f.snake.setDirection(Block.RIGHT);
					}
				});
				
				
				p.setResizable(false);
				p.setScene(scene);
				p.setTitle("Snake Game");
				
				
				p.show();
				}
			
		}});
		
		
		register.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent ae) { 
			
			ps.setTitle("Welcome new user");
			FlowPane root2 = new FlowPane(160,50);
			Scene sc = new Scene(root2,400,400);
			root2.setAlignment(Pos.CENTER);
			TextField logi=new TextField(),pas=new TextField();
			Button submi=new Button("Submit");
			Label lo=new Label("Enter the details");
			logi.setPromptText("New User ID");
			pas.setPromptText("Password");
			lo.setFont(new Font("Arial",15));
			lo.setMinWidth(210);
			logi.setMinWidth(210);
			pas.setMinWidth(210);
			submi.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent ae) {
				//System.out.println(logi.getText());
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection con =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/java", "root", "1234");		            
		            Statement stat=con.createStatement();
		            //stat.executeUpdate("insert into results values("+"\""+logi.getText()+"\","+pas.getText()+"\","+0+"\");");
		            stat.executeUpdate("INSERT INTO results VALUES("
		            		+"\""+logi.getText()+"\","
		            		+"\""+pas.getText()+"\","
		            		+"\""+0+"\");");
		            con.close();
		            start(p);			        
		            }
					catch(Exception e) {
						lo.setText("Entered user id already exists");
					}
			}});
			ps.setScene(sc);
			root2.getChildren().addAll(lo,logi,pas,submi);
			ps.show();
		}});
		ps.setScene(scen);
		root0.getChildren().addAll(lo,log,pass,register,submit);
		ps.show();}
	public static void main(String[] args) {
		launch(args);
	}
}

