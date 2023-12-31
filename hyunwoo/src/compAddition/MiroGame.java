package compAddition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.text.DecimalFormat;

public class MiroGame extends JPanel {
    private int playerX = 10;
    private int playerY = 720;
    private final int playerWidth = 55;
    private final int playerHeight = 75;
    private Timer gameTimer;
    private long startTime;
    private JPanel startPanel;
    private long elapsedTime;
    private JLabel timerLabel = new JLabel();
    MainSystem main;

    public MiroGame(MainSystem main) {
        setLayout(new BorderLayout());

        ImageIcon loadingImageIcon = new ImageIcon("/loading2.jpg");
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

        Rectangle building1Bounds = new Rectangle(610, 480, 480, 500);
        Rectangle building2Bounds = new Rectangle(10, 425, 480,280);
        Rectangle building3Bounds = new Rectangle(520, 90, 480, 280);
        Rectangle building4Bounds = new Rectangle(1000, 90, 480, 280);
        Rectangle building5Bounds = new Rectangle(1090, 480, 480, 280);

        if (playerBounds.intersects(building1Bounds) ||
                playerBounds.intersects(building2Bounds) ||
                playerBounds.intersects(building3Bounds) ||
                playerBounds.intersects(building4Bounds)) {
            playerX = 10;
            playerY = 720;
            repaint();
            return true;
        } else if (playerBounds.intersects(building5Bounds)) {
            gameTimer.stop(); // Stop the timer
            elapsedTime = System.currentTimeMillis() - startTime;
            DecimalFormat df = new DecimalFormat("0.00");
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(MiroGame.this);
            JOptionPane.showMessageDialog(parentFrame, "미션 성공했습니다!\n걸린 시간: " + df.format(elapsedTime / 1000.0) + "초");
            main.getSecondScore();
            return true;
        }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon Building1 = new ImageIcon("/대학본부.png");
        ImageIcon Building2 = new ImageIcon("/창학관.png");
        ImageIcon Building3 = new ImageIcon("/청운관.png");
        ImageIcon Building4 = new ImageIcon("/혜성관.png");
        ImageIcon Building5 = new ImageIcon("/상상관.png");
        ImageIcon playerIcon = new ImageIcon("/dog.png");

        ImageIcon icon = new ImageIcon("images/map.jpg");
        Image img = icon.getImage();

        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(Building1.getImage(), 610, 480, 480, 280, this);
        g.drawImage(Building2.getImage(), 10, 425, 480, 280, this);
        g.drawImage(Building3.getImage(), 520, 90, 480, 280, this);
        g.drawImage(Building4.getImage(), 1000, 90, 480, 280, this);
        g.drawImage(Building5.getImage(), 1090, 480, 480, 280, this);
        g.drawImage(playerIcon.getImage(), playerX, playerY, playerWidth, playerHeight, this);
    }

    public int getScore() {
        return (int) elapsedTime;
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



