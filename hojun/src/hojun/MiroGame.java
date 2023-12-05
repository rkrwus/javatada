package hojun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MiroGame extends JFrame {
    private MyPanel panel = new MyPanel();
    private int playerX = 10;
    private int playerY = 720;
    private final int playerWidth = 55;
    private final int playerHeight = 75;
    private Timer timer;
    private long startTime;
    private JPanel startPanel;
    private long elapsedTime;

    public MiroGame() {
        setTitle("바뀐 시험장으로 찾아가자!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon loadingImageIcon = new ImageIcon("images/loading2.jpg");
        loadingImageIcon = new ImageIcon(loadingImageIcon.getImage().getScaledInstance(1536, 864, Image.SCALE_DEFAULT));

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

        setLayout(new BorderLayout());
        add(startPanel, BorderLayout.CENTER);

        setSize(2560, 1440);
        setVisible(true);
    }

    private void startGame() {
        startPanel.setVisible(false);
        add(panel, BorderLayout.CENTER);
        panel.requestFocusInWindow();
        panel.setVisible(true); 
        startTime = System.currentTimeMillis();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                DecimalFormat df = new DecimalFormat("0.00");
                setTitle("바뀐 시험장으로 찾아가자! - 경과 시간: " + df.format(elapsedTime / 1000.0) + "초");
            }
        }, 0, 1000);

        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int stepSize = 10;

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
                    panel.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        panel.setFocusable(true);
    }

    private boolean checkCollision(int x, int y) {
        Rectangle playerBounds = new Rectangle(x, y, playerWidth, playerHeight);

        Rectangle building1Bounds = new Rectangle(610, 480, 480, 280);
        Rectangle building2Bounds = new Rectangle(10, 425, 480, 280);
        Rectangle building3Bounds = new Rectangle(520, 90, 480, 280);
        Rectangle building4Bounds = new Rectangle(1000, 90, 480, 280);
        Rectangle building5Bounds = new Rectangle(1090, 480, 480, 280);

        if (playerBounds.intersects(building1Bounds) ||
            playerBounds.intersects(building2Bounds) ||
            playerBounds.intersects(building3Bounds) ||
            playerBounds.intersects(building4Bounds)) {
            playerX = 10;
            playerY = 720;
            panel.repaint();
            return true;
        } else if (playerBounds.intersects(building5Bounds)) {
            timer.cancel(); // Stop the timer
            elapsedTime = System.currentTimeMillis() - startTime;
            DecimalFormat df = new DecimalFormat("0.00");
            JOptionPane.showMessageDialog(panel, "미션 성공했습니다!\n걸린 시간: " + df.format(elapsedTime / 1000.0) + "초");
            return true;
        }

        return false;
    }

    class MyPanel extends JPanel {
        private ImageIcon Building1 = new ImageIcon("images/대학본부.png");
        private ImageIcon Building2 = new ImageIcon("images/창학관.png");
        private ImageIcon Building3 = new ImageIcon("images/청운관.png");
        private ImageIcon Building4 = new ImageIcon("images/혜성관.png");
        private ImageIcon Building5 = new ImageIcon("images/상상관.png");
        private ImageIcon playerIcon = new ImageIcon("images/dog.png");

        private ImageIcon icon = new ImageIcon("images/map.jpg");
        private Image img = icon.getImage();

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            g.drawImage(Building1.getImage(), 610, 480, 480, 280, this);
            g.drawImage(Building2.getImage(), 10, 425, 480, 280, this);
            g.drawImage(Building3.getImage(), 520, 90, 480, 280, this);
            g.drawImage(Building4.getImage(), 1000, 90, 480, 280, this);
            g.drawImage(Building5.getImage(), 1090, 480, 480, 280, this);
            g.drawImage(playerIcon.getImage(), playerX, playerY, playerWidth, playerHeight, this);
        }
    }
    
    public long getScore() {
    	return elapsedTime;
    }

    public static void main(String[] args) {
        new MiroGame();
    }
}


