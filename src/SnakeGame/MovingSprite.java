package SnakeGame;

import javax.swing.*;
import java.awt.*;

public class MovingSprite extends JFrame {
    public MovingSprite() {
        initUI();
    }

    private void initUI() {

        add(new Board());

        setTitle("Moving sprite");
        setSize(820, 840);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            MovingSprite ex = new MovingSprite();
            ex.setVisible(true);
        });

    }

}
