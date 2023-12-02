package SnakeGame;

import javax.swing.*;
import java.awt.*;

public class SnakeSprites {
    private final Image headUp;
    private final Image headDown;
    private final Image headLeft;
    private final Image headRight;
    private final Image tailUp;
    private final Image tailDown;
    private final Image tailRight;
    private final Image tailLeft;
    private final Image bodyHorizontal;
    private final Image bodyVertical;
    private final Image bodyBottomRight;
    private final Image bodyBottomLeft;
    private final Image bodyTopRight;
    private final Image bodyTopLeft;
    private final Image grassBackground;

    public SnakeSprites(){
        headUp = new ImageIcon("src/Resources/head_up.png").getImage();
        headDown = new ImageIcon("src/Resources/head_down.png").getImage();
        headLeft = new ImageIcon("src/Resources/head_left.png").getImage();
        headRight = new ImageIcon("src/Resources/head_right.png").getImage();
        tailDown = new ImageIcon("src/Resources/tail_down.png").getImage();
        tailUp = new ImageIcon("src/Resources/tail_up.png").getImage();
        tailRight = new ImageIcon("src/Resources/tail_right.png").getImage();
        tailLeft = new ImageIcon("src/Resources/tail_left.png").getImage();
        bodyVertical = new ImageIcon("src/Resources/body_vertical.png").getImage();
        bodyHorizontal = new ImageIcon("src/Resources/body_horizontal.png").getImage();
        bodyBottomRight = new ImageIcon("src/Resources/body_bottomright.png").getImage();
        bodyBottomLeft = new ImageIcon("src/Resources/body_bottomleft.png").getImage();
        bodyTopRight = new ImageIcon("src/Resources/body_topright.png").getImage();
        bodyTopLeft = new ImageIcon("src/Resources/body_topleft.png").getImage();
        grassBackground = new ImageIcon("src/Resources/grassBackground.jpg").getImage();
    }

    public Image getGrassBackground() {
        return grassBackground;
    }

    public Image getBodyBottomLeft() {
        return bodyBottomLeft;
    }

    public Image getBodyBottomRight() {
        return bodyBottomRight;
    }

    public Image getBodyTopLeft() {
        return bodyTopLeft;
    }

    public Image getBodyTopRight() {
        return bodyTopRight;
    }

    public Image getBodyHorizontal() {
        return bodyHorizontal;
    }

    public Image getBodyVertical() {
        return bodyVertical;
    }

    public Image getTailDown() {
        return tailDown;
    }

    public Image getTailLeft() {
        return tailLeft;
    }

    public Image getTailRight() {
        return tailRight;
    }

    public Image getTailUp() {
        return tailUp;
    }

    public Image getHeadDown() {
        return headDown;
    }

    public Image getHeadLeft() {
        return headLeft;
    }

    public Image getHeadRight() {
        return headRight;
    }

    public Image getHeadUp() {
        return headUp;
    }
}

