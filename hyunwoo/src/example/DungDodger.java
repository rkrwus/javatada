package example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Dung extends Rectangle {
    public Dung(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}

class Money extends Rectangle{
	public Money(int x, int y, int width, int height) {
		super (x, y, width, height);
	}
}

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

public class DungDodger extends JFrame implements ActionListener, KeyListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Player player;
    private List<Dung> dungs;
    private List<Money> moneyList;
    private Timer timer;
    private int score;

    public DungDodger() {
        setTitle("Dung Dodger");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        player = new Player(WIDTH / 2 - 30, HEIGHT - 50, 60, 20, 5);
        dungs = new ArrayList<>();
        moneyList = new ArrayList<>();
        timer = new Timer(20, this);
        score = 0;

        addKeyListener(this);
        setFocusable(true);

        timer.start();
        spawnDung();
    }

    private void spawnDung() {
        Random random = new Random();
        if(random.nextDouble() < 0.03) {
        	
        	int dungWidth = 30;
        	int dungHeight = 30;
        	int dungX = random.nextInt(WIDTH - dungWidth);
        	int dungY = 0;

        	Dung dung = new Dung(dungX, dungY, dungWidth, dungHeight);
        	dungs.add(dung);
        }
    }
    
    private void spawnMoney() {
    	Random random = new Random();
    	
    	if(random.nextDouble() < 0.02) {
    		int moneyWidth = 20;
    		int moneyHeight = 20;
    		int moneyX = random.nextInt(WIDTH - moneyWidth);
    		int moneyY = 0;
    		
    		Money money = new Money(moneyX, moneyY, moneyWidth, moneyHeight);
    		moneyList.add(money);
    	}
    }
    
    private void moveMoney() {
    	for(Money money : moneyList) {
    		money.y += 5;
    	}
    }

    private void moveDungs() {
        for (Dung dung : dungs) {
            dung.y += 5; // Adjust the speed of falling dungs
        }
    }
    
    private void collectMoney() {
    	Rectangle playerBounds = player.getBounds();
    	
    	for(Money money : moneyList) {
    		if(playerBounds.intersects(money)) {
    			moneyList.remove(money);
    			score += 10;
    			break;
    		}
    	}
    }

    private void checkCollisions() {
        Rectangle playerBounds = player.getBounds();

        for (Dung dung : dungs) {
            if (playerBounds.intersects(dung)) {
                gameOver();
            }
        }
    }

    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over!");
        System.exit(0);
    }

    private void update() {
    	spawnMoney();
        moveDungs();
        moveMoney();
        checkCollisions();
        collectMoney();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        spawnDung();
        update();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT && player.x > 0) {
            player.moveLeft();
        } else if (key == KeyEvent.VK_RIGHT && player.x < WIDTH - player.width) {
            player.moveRight();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        
        Font font = new Font("Arial", Font.BOLD, 20);
        g2d.setFont(font);  // 폰트 설정.
        
        g2d.setColor(Color.BLUE);
        g2d.fillRect(player.x, player.y, player.width, player.height);

        g2d.setColor(Color.RED);
        for (Dung dung : dungs) {
            g2d.fill(dung);
        }
        
        g2d.setColor(Color.yellow);
        for(Money money : moneyList) {
        	g2d.fill(money);
        }
        
        g2d.setColor(Color.green);
        g2d.drawString("Score : " + score, 30, 50);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DungDodger game = new DungDodger();
            game.setVisible(true);
        });
    }
}
