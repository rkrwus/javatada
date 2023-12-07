package gameforwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.text.DecimalFormat;

public class MiroGame extends JPanel {
	private int width;
	private int height;
	
	private int[] b1 = {530, 380, 370, 400};
	private int[] b2 = {10, 300, 420, 245};
	private int[] b3 = {430, 50, 420, 245};
	private int[] b4 = {850, 50, 420, 245};
	private int[] b5 = {900, 380, 420, 245};
	
    private int playerX = 10;
    private int playerY = 570;
    private final int playerWidth = 55;
    private final int playerHeight = 75;
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
    	
        setLayout(new BorderLayout());

        ImageIcon loadingImageIcon = new ImageIcon("images/loading2.jpg");
        loadingImageIcon = new ImageIcon(loadingImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));

        JLabel loadingLabel = new JLabel(loadingImageIcon);

        JButton startButton = new JButton("시작하기");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        startPanel = new JPanel(new BorderLayout());
        startPanel.add(loadingLabel, BorderLayout.CENTER);
        startPanel.add(startButton, BorderLayout.SOUTH);
        startPanel.add(timerLabel, BorderLayout.NORTH);

        add(startPanel, BorderLayout.CENTER);
    }

    private void startGame() {
        startPanel.setVisible(false);
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

        Rectangle building1Bounds = new Rectangle(b1[0], b1[1], b1[2], b1[3]);
        Rectangle building2Bounds = new Rectangle(b2[0], b2[1], b2[2], b2[3]);
        Rectangle building3Bounds = new Rectangle(b3[0], b3[1], b3[2], b3[3]);
        Rectangle building4Bounds = new Rectangle(b4[0], b4[1], b4[2], b4[3]);
        Rectangle building5Bounds = new Rectangle(b5[0], b5[1], b5[2], b5[3]);

        if (playerBounds.intersects(building1Bounds) ||
                playerBounds.intersects(building2Bounds) ||
                playerBounds.intersects(building3Bounds) ||
                playerBounds.intersects(building4Bounds)) {
            playerX = 10;
            playerY = 570;
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
        removeAll(); // GradDodger의 모든 컴포넌트 삭제.
 
        revalidate();
        repaint();
        
        main.playThirdGame();
	}

 /* public static main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("바뀐 시험장으로 찾아가자!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2560, 1440);

        MiroGame miroGame = new MiroGame();
        frame.add(miroGame);
        frame.setVisible(true);
    } */
}



