package hojun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.text.DecimalFormat;

public class MiroGame extends JPanel {
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

    public MiroGame(MainSystem main){
    	this.main = main;
        setLayout(new BorderLayout());

        ImageIcon loadingImageIcon = new ImageIcon("images/loading2.jpg");
        loadingImageIcon = new ImageIcon(loadingImageIcon.getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT));

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

        Rectangle building1Bounds = new Rectangle(535, 385, 384, 1000);
        Rectangle building2Bounds = new Rectangle(70, 355, 384, 224);
        Rectangle building3Bounds = new Rectangle(490, 0, 384, 309);
        Rectangle building4Bounds = new Rectangle(875, 85, 384, 224);
        Rectangle building5Bounds = new Rectangle(920, 385, 384, 224);

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
            main.playThirdGame();
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
        g.drawImage(Building1.getImage(), 535, 385, 384, 224, this);
        g.drawImage(Building2.getImage(), 70, 355, 384, 224, this);
        g.drawImage(Building3.getImage(), 490, 85, 384, 224, this);
        g.drawImage(Building4.getImage(), 875, 85, 384, 224, this);
        g.drawImage(Building5.getImage(), 920, 385, 384, 224, this);
        g.drawImage(playerIcon.getImage(), playerX, playerY, playerWidth, playerHeight, this);
    }

    public int getScore() {
        return (int)elapsedTime;
    }

    /* public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("바뀐 시험장으로 찾아가자!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        MiroGame miroGame = new MiroGame();
        frame.add(miroGame);
        frame.setVisible(true);
    } */
}



