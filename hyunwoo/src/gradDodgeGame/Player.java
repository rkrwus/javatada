package gradDodgeGame;

import java.awt.*;

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