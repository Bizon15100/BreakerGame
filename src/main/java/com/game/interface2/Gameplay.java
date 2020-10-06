package com.game.interface2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    Graphics graphics;

    private int totalBricks = 21;
    private Timer time;
    private int delay = 15;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballYdir = -1;
    private int ballXdir = -2;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this);
        time.start();
    }

    public void paint(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) graphics);

        graphics.setColor(Color.YELLOW);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("serif",Font.BOLD, 25));
        graphics.drawString("" +score,590,30);

        graphics.setColor(Color.GREEN);
        graphics.fillRect(playerX,550,100,8);

        graphics.setColor(Color.YELLOW);
        graphics.fillOval(ballposX,ballposY,20,20);


        if (totalBricks <= 0){
            message(graphics, "You Won! Score: ");
        }

        if (ballposY >570){
            message(graphics, "Game over! Score: ");
        }


        graphics.dispose();
    }

    private void message(Graphics graphics, String text) {
        play = false;
        ballXdir = 0;
        ballYdir = 0;
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("serif",Font.BOLD, 30));
        graphics.drawString( text + " " +score,190,300);

        graphics.setFont(new Font("serif",Font.BOLD, 20));
        graphics.drawString("Press Enter to restart ",230,350);
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        time.start();
        if (play){
            ballposX += ballXdir;
            ballposY += ballYdir;
            for (int i = 0; i < map.mapBr.length; i++) {
                for (int j = 0; j < map.mapBr[0].length; j++) {
                    if (map.mapBr[i][j]>0){
                        int brickX = j * map.brickWidth +80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);


                        if (ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;

                            if (ballposX +19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                        }
                    }
                }
            }

            if (new Rectangle(ballposX,ballposY,20,20)
                    .intersects(new Rectangle(playerX,550,100,8))){
                ballYdir = -ballYdir;
            }


            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX >= 600){
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT){
            if (playerX < 10){
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);
            }
        }
    }



    private void moveLeft() {
        play = true;
        playerX -= 20;
    }

    private void moveRight() {
        play = true;
        playerX += 20;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
