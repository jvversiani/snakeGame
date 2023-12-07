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
    private final int Y_BOUNDS = 3 * DOT_SIZE;
    private final Rectangle BOUNDS = new Rectangle(DOT_SIZE, Y_BOUNDS, B_WIDTH, B_HEIGHT + DOT_SIZE);

    private ArrayList<Case> snakeBody;
    private Case head;
    
    private int dots;
    private final Apple apple = new Apple(0,0);

    private int direction = -1;
    private final int[] listDirections = new int[]{DOT_SIZE, -DOT_SIZE, -DOT_SIZE, DOT_SIZE};

    private boolean inGame;

    private final int DELAY = 140;
    private Timer timer;
    private final Random random = new Random();
    private final SnakeSprites snakeSprites = new SnakeSprites();

    private final Font font = new Font("TimesRoman", Font.PLAIN, 20);
    private int record = 0;

    public Board() {
        initBoard();
    }

    private void initBoard(){
        addKeyListener(new TAdapter());
        setBackground(new Color(39, 166, 33, 255));
        setFocusable(true);

        initGame();
    }

    private void initGame(){
        dots = 2;

        snakeBody = new ArrayList<>();
        inGame = true;
        direction = -1;

        snakeBody.add(new Case( DOT_SIZE + B_WIDTH / 2, DOT_SIZE + B_HEIGHT / 2));
        snakeBody.add(new Case(DOT_SIZE + B_WIDTH / 2, DOT_SIZE + B_HEIGHT / 2 + DOT_SIZE));

        head = snakeBody.get(0);
        
        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void locateApple(){
        int x;
        int y;
        do {
            x = random.nextInt(0, 19) * DOT_SIZE + DOT_SIZE;
            y = random.nextInt(0, 19) * DOT_SIZE + Y_BOUNDS;
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
        g2d.setColor(new Color(31, 129, 26));
        g2d.fillRect(0, 0, 895, DOT_SIZE * 2);

        // Actual score
        g2d.drawImage(snakeSprites.getApple(), DOT_SIZE, DOT_SIZE / 2, this);
        g2d.setColor(Color.white);
        g2d.setFont(font);
        g2d.drawString("" + (dots - 2), (int) (DOT_SIZE * 2.5), (int) (DOT_SIZE * 1.2));

        // highest score
        if (record > 0){
            g2d.drawImage(snakeSprites.getTrophy(), (int) (DOT_SIZE * 3.6), DOT_SIZE / 2, this);
            g2d.drawString("" + (record), DOT_SIZE * 5, (int) (DOT_SIZE * 1.2));
        }

        // grass
        g2d.drawImage(snakeSprites.getGrassBackground(), DOT_SIZE, DOT_SIZE * 3, this);

        if (inGame){
            // draw Apple
            g2d.drawImage(snakeSprites.getApple(), apple.getX(), apple.getY(), this);

            // draw the snake body

            // head
            switch (direction){
                case 0:{
                    g2d.drawImage(snakeSprites.getHeadRight(), head.getX(), head.getY(), this);
                }
                case 1:{
                    g2d.drawImage(snakeSprites.getHeadLeft(), head.getX(), head.getY(), this);
                }
                default: {
                    g2d.drawImage(snakeSprites.getHeadUp(), head.getX(), head.getY(), this);
                }
                case 3:{
                    g2d.drawImage(snakeSprites.getHeadDown(), head.getX(), head.getY(), this);
                }
            }

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
                        g2d.drawImage(snakeSprites.getBodyBottomRight(), last.getX(), last.getY(), this);

                    else if (last.getX() == afterLast.getX() + DOT_SIZE)
                        g2d.drawImage(snakeSprites.getBodyBottomLeft(), last.getX(), last.getY(), this);

                    else
                        g2d.drawImage(snakeSprites.getBodyVertical(), last.getX(), last.getY(), this);
                }

                else if (last.getX() == beforeLast.getX() && last.getY() == beforeLast.getY() + DOT_SIZE){
                    if (last.getX() == afterLast.getX() - DOT_SIZE)
                        g2d.drawImage(snakeSprites.getBodyTopRight(), last.getX(), last.getY(), this);

                    else if (last.getX() == afterLast.getX() + DOT_SIZE)
                        g2d.drawImage(snakeSprites.getBodyTopLeft(), last.getX(), last.getY(), this);

                    else
                        g2d.drawImage(snakeSprites.getBodyVertical(), last.getX(), last.getY(), this);
                }

                else if (last.getX() == beforeLast.getX() - DOT_SIZE && last.getY() == beforeLast.getY()){
                    if (last.getY() == afterLast.getY() - DOT_SIZE)
                        g2d.drawImage(snakeSprites.getBodyBottomRight(), last.getX(), last.getY(), this);

                    else if (last.getY() == afterLast.getY() + DOT_SIZE)
                        g2d.drawImage(snakeSprites.getBodyTopRight(), last.getX(), last.getY(), this);

                    else
                        g2d.drawImage(snakeSprites.getBodyHorizontal(), last.getX(), last.getY(), this);
                }

                else {
                    if (last.getY() == afterLast.getY() - DOT_SIZE)
                        g2d.drawImage(snakeSprites.getBodyBottomLeft(), last.getX(), last.getY(), this);

                    else if (last.getY() == afterLast.getY() + DOT_SIZE)
                        g2d.drawImage(snakeSprites.getBodyTopLeft(), last.getX(), last.getY(), this);

                    else
                        g2d.drawImage(snakeSprites.getBodyHorizontal(), last.getX(), last.getY(), this);
                }
            }

            // tail
            last = snakeBody.get(dots - 1);
            beforeLast = snakeBody.get(dots - 2);

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

            repaint();
            record = Math.max(dots - 2, record);
            initGame();
            repaint(DOT_SIZE * 5 - 10,  DOT_SIZE - 10, DOT_SIZE, DOT_SIZE);

        }
    }

    public void checkEatApple(){
        if (apple.getX() == head.getX() && apple.getY() == head.getY()){
            dots ++;
            locateApple();

            repaint(DOT_SIZE * 2 - 10,  DOT_SIZE - 10, DOT_SIZE, DOT_SIZE);

            snakeBody.add(new Case(apple.getX(), apple.getY()));
        }
    }

    private void checkCollision() {
        int head_x = head.getX();
        int head_y = head.getY();

        for (int z = dots - 1; z > 3; z--) {
            if ((head_x == snakeBody.get(z).getX()) && (head_y == snakeBody.get(z).getY())) {
                inGame = false;
                break;
            }
        }

        if (!BOUNDS.contains(head_x, head_y)){
            inGame = false;
            timer.stop();
        }
    }

    public void move(){
        if (direction >= 0) {
            for (int z = dots - 1; z > 0; z--) {
                snakeBody.get(z).setX(snakeBody.get(z - 1).getX());
                snakeBody.get(z).setY(snakeBody.get(z - 1).getY());
            }
            
            if (direction < 2) {
                head.setX(head.getX() + listDirections[direction]);

            }
            else
                head.setY(head.getY() + listDirections[direction]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame){
            checkCollision();
            checkEatApple();
            move();

            // Small repaint optimization
            repaint(Math.max(head.getX() - DOT_SIZE, 0), Math.max(head.getY() - DOT_SIZE, 0), DOT_SIZE * 3, DOT_SIZE * 3);
            repaint(Math.max(snakeBody.get(dots - 1).getX() - DOT_SIZE, 0), Math.max(snakeBody.get(dots - 1).getY() - DOT_SIZE, 0), DOT_SIZE * 3, DOT_SIZE * 3);
        }
    }


    private class TAdapter extends KeyAdapter {

        private long lastKeyEventTime = 0;
        private static final long KEY_EVENT_DELAY = 0;

        @Override
        public void keyPressed(KeyEvent e) {

            long currentTime = System.currentTimeMillis();

            // Check if enough time has passed since the last key event
            if (currentTime - lastKeyEventTime >= KEY_EVENT_DELAY) {

                int key = e.getKeyCode();

                if ((key == KeyEvent.VK_LEFT) && (direction != 0)) {
                    direction = 1;

                } else if ((key == KeyEvent.VK_RIGHT) && (direction != 1)) {
                    direction = 0;

                } else if ((key == KeyEvent.VK_UP) && (direction != 3)) {
                    direction = 2;

                } else if ((key == KeyEvent.VK_DOWN) && (direction != 2)) {
                    direction = 3;
                }

                // Update the last key event time
                lastKeyEventTime = currentTime;
            }
        }
    }
}
