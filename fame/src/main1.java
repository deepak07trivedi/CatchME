import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

public class main1 extends Application{

	static int block_size=15;

	
	int width=30,height=20;
	
	Field f;
	
	int il=5;
	long then=System.nanoTime();
	@Override
	public void start(Stage ps) {
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
					Alert a=new Alert(AlertType.INFORMATION);
					a.setHeaderText("You Lost");
					a.setContentText("Your Score is :"+f.score);
					Platform.runLater(a::showAndWait);
					a.setOnHidden(e->{
						root.getChildren().clear();
						f = new Field(width, height);
						score.setText("Score:0");
						root.getChildren().addAll(f,score);
					});
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
		
		
		ps.setResizable(false);
		ps.setScene(scene);
		ps.setTitle("Snake Game");
		
		
		ps.show();

	}
	public static void main(String[] args) {
		launch(args);
	}

}

