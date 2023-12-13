package hojun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class MiroGame extends JPanel {
	JButton rewindButton;
	private int width;
	private int height;
	
	private int[] b1 = {535, 385, 384, 224};
	private int[] b2 = {70, 355, 384, 224};
	private int[] b3 = {490, 85, 384, 224};
	private int[] b4 = {875, 85, 384, 224};
	private int[] b5 = {920, 385, 384, 224};
	
    private int playerX = 30;
    private int playerY = 590;
    private final int playerWidth = 40;
    private final int playerHeight = 60;
    private Timer gameTimer;
    private long startTime;
    private JPanel startPanel;
    private long elapsedTime;
    private JLabel timerLabel = new JLabel();
    MainSystem main;

    public MiroGame(MainSystem main) {
    	this.main = main;
    	this.width = main.getWidth();
    	this.height = main.getHeight();
    	
    	rewindButton = new JButton();
		ImageIcon backIcon = new ImageIcon("images/rewind.png");
		rewindButton.setIcon(backIcon);
		rewindButton.setBounds(5, 5, 70, 70);
		
		add(rewindButton);
        
		rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	rewind();
            }
        });
		
        startGame();
    }

    private void startGame() {
        setFocusable(true);
        requestFocusInWindow();
        startTime = System.currentTimeMillis();

        gameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                double seconds = elapsedTime / 1000.0;
                timerLabel.setText(String.format("%.1f", seconds));
            }
        });

        gameTimer.start();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int stepSize = 15;

                int newPlayerX = playerX;
                int newPlayerY = playerY;

                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        newPlayerY -= stepSize;
                        break;
                    case KeyEvent.VK_DOWN:
                        newPlayerY += stepSize;
                        break;
                    case KeyEvent.VK_LEFT:
                        newPlayerX -= stepSize;
                        break;
                    case KeyEvent.VK_RIGHT:
                        newPlayerX += stepSize;
                        break;
                }

                if (!checkCollision(newPlayerX, newPlayerY)) {
                    playerX = newPlayerX;
                    playerY = newPlayerY;
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private boolean checkCollision(int x, int y) {
        Rectangle playerBounds = new Rectangle(x, y, playerWidth, playerHeight);

        Rectangle building1Bounds = new Rectangle(b1[0], b1[1], b1[2], b1[3]+1000);
        Rectangle building2Bounds = new Rectangle(-1000, b2[1], 1454, b2[3]);
        Rectangle building3Bounds = new Rectangle(b3[0], b3[1], b3[2], b3[3]);
        Rectangle building4Bounds = new Rectangle(b4[0], b4[1], b4[2], b4[3]);
        Rectangle building5Bounds = new Rectangle(b5[0], b5[1], b5[2], b5[3]);

        if (playerBounds.intersects(building1Bounds) ||
                playerBounds.intersects(building2Bounds) ||
                playerBounds.intersects(building3Bounds) ||
                playerBounds.intersects(building4Bounds)) {
            playerX = 30;
            playerY = 590;
            repaint();
            return true;
        } else if (playerBounds.intersects(building5Bounds)) {
            gameTimer.stop(); // Stop the timer
            elapsedTime = System.currentTimeMillis() - startTime;
            DecimalFormat df = new DecimalFormat("0.00");
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(MiroGame.this);
            JOptionPane.showMessageDialog(parentFrame, "미션 성공했습니다!\n걸린 시간: " + df.format(elapsedTime / 1000.0) + "초");
            main.getSecondScore();
            clearPanel();
            return true;
        }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon Building1 = new ImageIcon("images/대학본부.png");
        ImageIcon Building2 = new ImageIcon("images/창학관.png");
        ImageIcon Building3 = new ImageIcon("images/청운관.png");
        ImageIcon Building4 = new ImageIcon("images/혜성관.png");
        ImageIcon Building5 = new ImageIcon("images/상상관.png");
        ImageIcon playerIcon = new ImageIcon("images/dog.png");

        ImageIcon icon = new ImageIcon("images/map.jpg");
        Image img = icon.getImage();

        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(Building1.getImage(), b1[0], b1[1], b1[2], b1[3], this);
        g.drawImage(Building2.getImage(), b2[0], b2[1], b2[2], b2[3], this);
        g.drawImage(Building3.getImage(), b3[0], b3[1], b3[2], b3[3], this);
        g.drawImage(Building4.getImage(), b4[0], b4[1], b4[2], b4[3], this);
        g.drawImage(Building5.getImage(), b5[0], b5[1], b5[2], b5[3], this);
        g.drawImage(playerIcon.getImage(), playerX, playerY, playerWidth, playerHeight, this);
    }

    public int getScore() {
        return (int)elapsedTime/1000;
    }
    
    private void clearPanel() {   
    	setVisible(false);
        removeAll();
 
        revalidate();
        repaint();
        
        main.playThirdStory();
	}
    private void rewind() {
		setVisible(false);
        removeAll();
 
        revalidate();
        repaint();
        
        main.rewind3();
	}
}