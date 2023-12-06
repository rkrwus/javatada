package hojun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimerGame extends JFrame {
    private JPanel startPanel;
    private JPanel gamePanel;
    private JLabel timerLabel;
    private JButton startButton;
    private JButton stopButton;

    private Timer gameTimer;
    private long startTime;
    private double totalPlaytime;

    public TimerGame() {
        setTitle("알람을 맞춰라!");
        setSize(2560, 1440);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon loadingImageIcon = new ImageIcon("images/loading1.jpg");
        loadingImageIcon = new ImageIcon(loadingImageIcon.getImage().getScaledInstance(1536, 864, Image.SCALE_DEFAULT));

        JLabel loadingLabel = new JLabel(loadingImageIcon);

        startButton = new JButton("시작하기");
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
    }

    private void startGame() {
    	gamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setContentPane(gamePanel);
        revalidate();

        ImageIcon gameImageIcon = new ImageIcon("images/GameImage.jpg");
        gameImageIcon = new ImageIcon(gameImageIcon.getImage().getScaledInstance(1536, 864, Image.SCALE_DEFAULT));

        JLabel gameImageLabel = new JLabel(gameImageIcon);
        gameImageLabel.setBounds(0, 0, 1280, 720);
        gamePanel.add(gameImageLabel);
        
        timerLabel = new JLabel("0");
        timerLabel.setFont(new Font("TimesRoman", Font.ITALIC, 250));
        timerLabel.setBounds(900, -100, getWidth(), getHeight());
        gameImageLabel.add(timerLabel);
        
        stopButton = new JButton("스탑");
        stopButton.setBounds(900, 450, 300, 100);
        gameImageLabel.add(stopButton);
        
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        });

        
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
    }

    private void stopGame() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
            long stopTime = System.currentTimeMillis();
            double elapsedTime = (stopTime - startTime) / 1000.0;
            totalPlaytime += elapsedTime;

            if (elapsedTime >= 8.8 && elapsedTime <= 9.0) {
                JOptionPane.showMessageDialog(this, "알람 설정에 성공했습니다!\n총 플레이 타임: " + String.format("%.1f", totalPlaytime) + " 초");
                setContentPane(gamePanel);
                revalidate();
            } else {
                int option = JOptionPane.showConfirmDialog(this, "시간 내에 멈추지 못했습니다!\n재시도 하시겠습니까?", "Retry", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    startTime = System.currentTimeMillis();
                    gameTimer.start();
                } else {
                    setContentPane(startPanel);
                    revalidate();
                }
            }
        }
    }

    public int getScore() {
        return (int)totalPlaytime;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TimerGame timerGame = new TimerGame();
            timerGame.setVisible(true);
        });
    }
}