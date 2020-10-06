package com.game.interface2;

import java.awt.*;

public class MapGenerator {
    public int[][] mapBr;
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int row,int col){
        mapBr = new int[row][col];
        for (int i = 0; i < mapBr.length; i++) {
            for (int j = 0; j < mapBr[0].length; j++) {
                mapBr[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;

    }

    public void draw(Graphics2D graphics2D) {
        for (int i = 0; i < mapBr.length; i++) {
            for (int j = 0; j < mapBr[0].length; j++) {
                if (mapBr[i][j] > 0) {
                    graphics2D.setColor(Color.WHITE);
                    graphics2D.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    graphics2D.setStroke(new BasicStroke(3));
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col){
        mapBr[row][col] = value;
    }
}
