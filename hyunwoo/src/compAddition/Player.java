package compAddition;

import java.awt.*;
//speed 변수 조정 통해 player 속도 조절 가능
class Player extends Rectangle {
    private int speed;

    public Player(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }
}