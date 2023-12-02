package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 800;
    private final int DOT_SIZE = 40;
    private final int DELAY = 140;

    private final ArrayList<Case> snakeBody = new ArrayList<>();
    private int dots;
    private final Apple apple = new Apple(0,0);

    private boolean leftDirection = false;
    private boolean rightDirection = false;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean start = true;
    private boolean inGame = true;

    private Timer timer;
    private final Random random = new Random();
    private final SnakeSprites snakeSprites = new SnakeSprites();


    public Board() {
        initBoard();
    }

    private void initBoard(){
        addKeyListener(new TAdapter());
        setBackground(Color.green);
        setFocusable(true);

        initGame();
    }

    private void initGame(){
        dots = 2;

        snakeBody.add(new Case(B_WIDTH / 2, B_HEIGHT / 2));
        snakeBody.add(new Case(B_WIDTH / 2, B_HEIGHT / 2 + DOT_SIZE));

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void locateApple(){
        int x;
        int y;
        do {
            x = random.nextInt(0, 19) * DOT_SIZE;
            y = random.nextInt(0, 19) * DOT_SIZE;
        } while (testOccupedPos(x, y));

        apple.setXY(x, y);

        repaint(apple.getX(), apple.getY(), DOT_SIZE, DOT_SIZE);
    }

    private boolean testOccupedPos(int x, int y){
        for (Case aCase : snakeBody) {
            if (aCase.getX() == x && aCase.getY() == y)
                return true;
        }
        return false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // background
        g2d.drawImage(snakeSprites.getGrassBackground(), 0, 0, this);

        if (inGame){
            // draw Apple
            g2d.drawImage(apple.getImage(), apple.getX(), apple.getY(), this);

            // draw the snake body

            // head
            if (rightDirection)
                g.drawImage(snakeSprites.getHeadRight(), snakeBody.get(0).getX(), snakeBody.get(0).getY(), this);

            else if (leftDirection)
                g.drawImage(snakeSprites.getHeadLeft(), snakeBody.get(0).getX(), snakeBody.get(0).getY(), this);

            else if (downDirection)
                g.drawImage(snakeSprites.getHeadDown(), snakeBody.get(0).getX(), snakeBody.get(0).getY(), this);

            else
                g.drawImage(snakeSprites.getHeadUp(), snakeBody.get(0).getX(), snakeBody.get(0).getY(), this);

            Case last;
            Case beforeLast;
            Case afterLast;

            // body
            for (int z = 1; z < dots - 1; z++) {
                last = snakeBody.get(z);
                beforeLast = snakeBody.get(z - 1);
                afterLast = snakeBody.get(z + 1);

                if (last.getX() == beforeLast.getX() && last.getY() == beforeLast.getY() - DOT_SIZE){
                    if (last.getX() == afterLast.getX() - DOT_SIZE)
                        g.drawImage(snakeSprites.getBodyBottomRight(), last.getX(), last.getY(), this);

                    else if (last.getX() == afterLast.getX() + DOT_SIZE)
                        g.drawImage(snakeSprites.getBodyBottomLeft(), last.getX(), last.getY(), this);

                    else
                        g.drawImage(snakeSprites.getBodyVertical(), last.getX(), last.getY(), this);
                }

                else if (last.getX() == beforeLast.getX() && last.getY() == beforeLast.getY() + DOT_SIZE){
                    if (last.getX() == afterLast.getX() - DOT_SIZE)
                        g.drawImage(snakeSprites.getBodyTopRight(), last.getX(), last.getY(), this);

                    else if (last.getX() == afterLast.getX() + DOT_SIZE)
                        g.drawImage(snakeSprites.getBodyTopLeft(), last.getX(), last.getY(), this);

                    else
                        g.drawImage(snakeSprites.getBodyVertical(), last.getX(), last.getY(), this);
                }

                else if (last.getX() == beforeLast.getX() - DOT_SIZE && last.getY() == beforeLast.getY()){
                    if (last.getY() == afterLast.getY() - DOT_SIZE)
                        g.drawImage(snakeSprites.getBodyBottomRight(), last.getX(), last.getY(), this);

                    else if (last.getY() == afterLast.getY() + DOT_SIZE)
                        g.drawImage(snakeSprites.getBodyTopRight(), last.getX(), last.getY(), this);

                    else
                        g.drawImage(snakeSprites.getBodyHorizontal(), last.getX(), last.getY(), this);
                }

                else {
                    if (last.getY() == afterLast.getY() - DOT_SIZE)
                        g.drawImage(snakeSprites.getBodyBottomLeft(), last.getX(), last.getY(), this);

                    else if (last.getY() == afterLast.getY() + DOT_SIZE)
                        g.drawImage(snakeSprites.getBodyTopLeft(), last.getX(), last.getY(), this);

                    else
                        g.drawImage(snakeSprites.getBodyHorizontal(), last.getX(), last.getY(), this);
                }
            }

            // tail
            last = snakeBody.get(snakeBody.size() - 1);
            beforeLast = snakeBody.get(snakeBody.size() - 2);

            if (last.getX() == beforeLast.getX() && last.getY() == beforeLast.getY() - DOT_SIZE)
                g.drawImage(snakeSprites.getTailUp(), last.getX(), last.getY(), this);

            else if (last.getX() == beforeLast.getX() + DOT_SIZE && last.getY() == beforeLast.getY())
                g.drawImage(snakeSprites.getTailRight(), last.getX(), last.getY(), this);

            else if (last.getX() == beforeLast.getX() - DOT_SIZE && last.getY() == beforeLast.getY())
                g.drawImage(snakeSprites.getTailLeft(), last.getX(), last.getY(), this);

            else
                g.drawImage(snakeSprites.getTailDown(), last.getX(), last.getY(), this);
        }

        else {

            // TODO: GameOver

        }
    }

    public void checkEatApple(){
        if (apple.getX() == snakeBody.get(0).getX() && apple.getY() == snakeBody.get(0).getY()){
            dots ++;
            locateApple();
            snakeBody.add(new Case(apple.getX(), apple.getY()));
        }
    }

    private void checkCollision() {
        for (int z = dots - 1; z > 3; z--) {
            if ((snakeBody.get(0).getX() == snakeBody.get(z).getX()) && (snakeBody.get(0).getY() == snakeBody.get(z).getY())) {
                inGame = false;
                break;
            }
        }

        if (snakeBody.get(0).getY() >= B_HEIGHT) {
            inGame = false;
        }

        if (snakeBody.get(0).getY() < 0) {
            inGame = false;
        }

        if (snakeBody.get(0).getX() >= B_WIDTH) {
            inGame = false;
        }

        if (snakeBody.get(0).getX() < 0) {
            inGame = false;
        }

        if (!inGame) {
            repaint();
            timer.stop();
        }
    }

    public void move(){
        if (!start){
            for (int z = dots - 1; z > 0; z--) {
                snakeBody.get(z).setX(snakeBody.get(z - 1).getX());
                snakeBody.get(z).setY(snakeBody.get(z - 1).getY());
            }
        }

        Case c = snakeBody.get(0);
        if (leftDirection) {
            c.setX(c.getX() - DOT_SIZE);
        }

        if (rightDirection) {
            c.setX(c.getX() + DOT_SIZE);
        }

        if (upDirection) {
            c.setY(c.getY() - DOT_SIZE);
        }

        if (downDirection) {
            c.setY(c.getY() + DOT_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame){
            checkCollision();
            checkEatApple();
            move();

            // Small repaint optimization
            repaint(Math.max(snakeBody.get(0).getX() - DOT_SIZE, 0), Math.max(snakeBody.get(0).getY() - DOT_SIZE, 0), DOT_SIZE * 3, DOT_SIZE * 3);

            if (snakeBody.size() > 1)
                repaint(Math.max(snakeBody.get(snakeBody.size() - 1).getX() - DOT_SIZE, 0), Math.max(snakeBody.get(snakeBody.size() - 1).getY() - DOT_SIZE, 0), DOT_SIZE * 3, DOT_SIZE * 3);
        }
    }


    private class TAdapter extends KeyAdapter {

        private long lastKeyEventTime = 0;
        private static final long KEY_EVENT_DELAY = 80;

        @Override
        public void keyPressed(KeyEvent e) {

            start = false;

            long currentTime = System.currentTimeMillis();

            // Check if enough time has passed since the last key event
            if (currentTime - lastKeyEventTime >= KEY_EVENT_DELAY) {

                int key = e.getKeyCode();

                if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;

                } else if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;

                } else if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                    upDirection = true;
                    leftDirection = false;
                    rightDirection = false;

                } else if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                    downDirection = true;
                    leftDirection = false;
                    rightDirection = false;
                }

                // Update the last key event time
                lastKeyEventTime = currentTime;
            }
        }
    }
}
