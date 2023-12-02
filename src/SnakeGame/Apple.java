package SnakeGame;

import javax.swing.*;
import java.awt.*;

public class Apple {
    private int x;
    private int y;
    private Image image;

    public Apple(int x, int y){
        this.x = x;
        this.y = y;
        initApple();
    }

    public void initApple(){
        image = new ImageIcon("src/Resources/appleSprite.png").getImage();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Image getImage() {
        return image;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
