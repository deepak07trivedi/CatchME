package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Food extends Rectangle{
	int posX,posY;
	
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public Food(int x,int y) {
		super(main1.block_size,main1.block_size);
		posX=x;
		posY=y;

		setTranslateX(posX * main1.block_size);
		setTranslateY(posY * main1.block_size);
		
		setFill(Color.LIGHTGREEN);
		setStroke(Color.GREEN);
	}
}


