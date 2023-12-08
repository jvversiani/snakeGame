package SnakeGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MuteButton extends JButton implements ActionListener {
    private boolean sound = true;
    private String img = "Sound.png";
    private final Board board;

    public MuteButton(Board board){
        this.board = board;

        setIcon(new ImageIcon("src/Resources/" + img));
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setBounds(750, 20, 40, 40);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sound = !sound;
        img = sound ? "Sound.png" : "muteSound.png";
        setIcon(new ImageIcon("src/Resources/" + img));

        board.requestFocusInWindow();
    }

    public boolean isSound(){
        return sound;
    }
}
