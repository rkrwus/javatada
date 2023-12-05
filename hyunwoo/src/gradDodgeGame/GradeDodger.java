package gradDodgeGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GradeDodger extends JPanel implements ActionListener, KeyListener{
	private static final int WIDTH = 800; // 패널 폭
	private static final int HEIGHT = 600; // 패널 높이
	
	private Player player; 
	private List<FGrade> fs; // (f의 높이 너비 좌표값)의 리스트
	private List<AGrade> as; // fs 와 동일
	private Timer spawner;   // f랑 a 스폰시키는 타이머
	private Timer timer;     // 진짜 타이머
	private int score;
	private Image fImage;
	private Image aImage;
	
	public GradeDodger() {		
		player = new Player(WIDTH/2-30, HEIGHT -50, 60, 20, 5);
		fs = new ArrayList<>();
		as = new ArrayList<>();
		spawner = new Timer(20, this); // 20 millisec 딜레이
		score = 0;
		
		loadImages();
		
		addKeyListener(this);
		setFocusable(true);
		
		spawner.start();
	}
	
	public int getWidth() {
		return this.WIDTH;
	}
	
	public int getHeight() {
		return this.HEIGHT;
	}
	
	private void spawnF() {
		Random random = new Random();
		if(random.nextDouble() < 0.03) {
			int fWidth = 30;
			int fHeight = 30;
			int fX = random.nextInt(WIDTH - fWidth);
			int fY = 0;
			
			FGrade f = new FGrade(fX, fY, fWidth, fHeight);
			fs.add(f);
		}
	}
	private void spawnA() {
		Random random = new Random();
		
		if(random.nextDouble() < 0.02) {
			int aWidth = 30;
			int aHeight = 30; 
			int aX = random.nextInt(WIDTH - aWidth);
			int aY = 0;
			
			AGrade a = new AGrade(aX, aY, aWidth, aHeight);
			as.add(a);
		}
	}
	private void moveFs() {
		for(FGrade f: fs) {
			f.y += 5;
		}
	}
	private void moveAs() {
		for(AGrade a : as) {
			a.y += 5;
		}
	}
	private void collectAs() {
		Rectangle playerBounds = player.getBounds();
		for(AGrade a : as) {
			if(playerBounds.intersects(a)) {
				as.remove(a);
				if(score < 100)
				score +=10;
				break;
			}
		}
	}
	private void collideFs() {
		Rectangle playerBounds = player.getBounds();
		for(FGrade f : fs) {
			if(playerBounds.intersects(f)) {
				gameOver();
			}
		}
	}
	
	private void gameOver() { //////////////////////////////////////// 미완성!!!!!!!!!!!!!!!!!!!!!!!!
	    spawner.stop();
	    
	    try{
	    	JLabel gameOver = new JLabel("!!!GAME OVER!!!");
	    	Font font = new Font("Arial", Font.BOLD, 40);
	        gameOver.setFont(font);
	        add(gameOver);
	    	System.out.println("game over!!");
	    	Thread.sleep(3000);
	    }catch (InterruptedException e) {
	    	e.printStackTrace();
	    }

	    clearPanel();
	}
	
	private void update() {
		spawnF();
    	spawnA();
        moveFs();
        moveAs();
        collideFs();
        collectAs();
        repaint();
    }
	
	@Override
    public void actionPerformed(ActionEvent e) {
        spawnF();
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
    
    private void loadImages() {
        try {
        	 // Print the URLs to the console
            System.out.println("f.png URL: " + getClass().getResource("/f.png"));
            System.out.println("a.png URL: " + getClass().getResource("/a.png"));
        	
            fImage = ImageIO.read(getClass().getResource("/f.png"));
            aImage = ImageIO.read(getClass().getResource("/a.png"));
        } catch (Exception e) {
            e.printStackTrace(); // print error message
        }
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
        for (FGrade f : fs) {
        	g2d.drawImage(fImage, f.x, f.y, f.width, f.height, this);
        }
        
        g2d.setColor(Color.yellow);
        for(AGrade a : as) {
        	 g2d.drawImage(aImage, a.x, a.y, a.width, a.height, this);
        }
        
        g2d.setColor(Color.green);
        g2d.drawString("Score : " + score, 30, 50);
    }
    
    private void clearPanel() {   
	    Container parentContainer = getParent();
	    parentContainer.remove(this);
	    
	    parentContainer.revalidate();
	    parentContainer.repaint();
	}
	
}
