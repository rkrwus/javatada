package miniGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GradeDodger extends JPanel implements ActionListener, KeyListener{
	private int width=800;
	private int height=700;
	
	public boolean gameOn = false;
	
	MainSystem mainSystem;
	
	private Player player; 
	private List<FGrade> fs; // (f의 높이 너비 좌표값)의 리스트
	private List<AGrade> as; // fs 와 동일
	private Timer gameTimer;   // 게임타이머
	private int timerCounter;  // 카운터
	private long startTime;    // 시간 계산용
	private long endTime;      // ,,
	private int point;         // 게임 내부 포인트 --> clear 기준 정하기용
	private Image fImage;
	private Image aImage;
	
	public GradeDodger(MainSystem mainSystem) {		
		this.mainSystem = mainSystem;
		//this.width = mainSystem.getWidth();
    	//this.height = mainSystem.getHeight();
		player = new Player(width/2-30, height -50, 60, 20, 15); // 60, 20, 7 - 플레이어 너비 높이 속도
		fs = new ArrayList<>();
		as = new ArrayList<>();
		gameTimer = new Timer(10, this); // 10 millisec 딜레이
		timerCounter = 0;
		point = 0;
		gameOn = true;
		
		loadImages();
		
		setBackground(Color.GRAY);
		
		addKeyListener(this);
		setFocusable(true);
		
		gameTimer.start();
		startTime = System.currentTimeMillis();
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Timer getTimer() {
		return this.gameTimer;
	}
	
	public int getScore() {
		long scoreTime = (endTime - startTime) / 1000;
		return (int) scoreTime;
	}
	
	private void spawnF() {
		Random random = new Random();
		if(random.nextDouble() < 0.03) {
			int fWidth = 30;
			int fHeight = 30;
			int fX = random.nextInt(width - fWidth);
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
			int aX = random.nextInt(width - aWidth);
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
				if(point < 90) {
					point +=10;
				}else gameClear();
				break;
			}
		}
	}
	private void collideFs() {
		Rectangle playerBounds = player.getBounds();
		for(FGrade f : fs) {
			if(playerBounds.intersects(f)) {
				fs.remove(f);
				point -= 10;
				break;
			}
		}
	}
	
	private void sendScore() {
		mainSystem.getFourthScore();
	}
	
	private void gameClear() {
		endTime = System.currentTimeMillis();
		gameTimer.stop();
		
		JLabel gameOverLabel = new JLabel("!!!GAME CLEAR!!!");
	    Font font = new Font("Arial", Font.BOLD, 40);
	    gameOverLabel.setFont(font);
	    gameOverLabel.setBounds((width-400)/2, (height-80)/2, 400, 80);
	    add(gameOverLabel);
	    repaint();

	    // timer 사용.
	    Timer delayTimer = new Timer(3000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            clearPanel();
	        }
	    });
	    delayTimer.setRepeats(false); // 한번만 실행.
	    delayTimer.start();
	    
	    System.out.println("clear in " + (endTime - startTime) + " ms");
	    System.out.println(getScore());
	    sendScore();
	    gameOn = false;
	}
	
	private void gameOver() {
	    endTime = System.currentTimeMillis();
	    gameTimer.stop();
	    
	    /*      ---------------- label 출력 안됌으로 인해 사용 x -----------------------     
	    try{
	    	JLabel gameOverLabel = new JLabel("!!!GAME OVER!!!");
	    	Font font = new Font("Arial", Font.BOLD, 40);
	        gameOverLabel.setFont(font);
	        gameOverLabel.setBounds((WIDTH-400)/2, (HEIGHT-80)/2, 400, 80);
	        add(gameOverLabel);
	        repaint();
	        
	    	System.out.println("game over!!");
	    	
	    	Thread.sleep(3000);
	    }catch (InterruptedException e) {
	    	e.printStackTrace();
	    }
	    */
	    
	    JLabel gameOverLabel = new JLabel("!!!GAME OVER!!!");
	    Font font = new Font("Arial", Font.BOLD, 40);
	    gameOverLabel.setFont(font);
	    gameOverLabel.setBounds((width-400)/2, (height-80)/2, 400, 80);
	    add(gameOverLabel);
	    repaint();

	    // Thread.sleep 대신 timer 사용.
	    Timer delayTimer = new Timer(3000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            clearPanel();
	        }
	    });
	    delayTimer.setRepeats(false); // 한번만 실행.
	    delayTimer.start();
	    
	    System.out.println((endTime - startTime) + " ms");
	    System.out.println(getScore());
	    
	    sendScore();
	    
	    gameOn = false;

	    //clearPanel();
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
		timerCounter++;
		
		if(timerCounter %50 == 0) {
			spawnF();
			spawnA();
		}
        update();
    }
	
	@Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT && player.x > 0) {
            player.moveLeft();
        } else if (key == KeyEvent.VK_RIGHT && player.x < width - player.width) {
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
        	 // Print the URLs to the console --> 경로 확인용
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
        g2d.drawString("point : " + point, 30, 50);
    }
    
    private void clearPanel() {   
    	setVisible(false);
        removeAll(); // GradDodger의 모든 컴포넌트 삭제.
 
        revalidate();
        repaint();
        
        mainSystem.operateRankingSystem();
	}
	
}