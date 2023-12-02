package hojun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimerGame extends JFrame {
    private JPanel centerPanel; // 시간을 감싸는 JPanel
    private JLabel timerLabel;
    private JButton startButton;
    private JButton stopButton;

    private Timer gameTimer;
    private long startTime;
    private double totalPlaytime;

    public TimerGame() {
        setTitle("타이머 게임");
        setSize(400, 300); // 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 전체 컨테이너에 BorderLayout 설정
        setLayout(new BorderLayout());

        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 중앙 정렬된 FlowLayout
        timerLabel = new JLabel("0");
        timerLabel.setFont(new Font("TimesRoman", Font.ITALIC, 50));
        centerPanel.add(timerLabel);

        add(centerPanel, BorderLayout.NORTH); // 중앙 패널을 중앙에 배치

        startButton = new JButton("시작");
        startButton.setPreferredSize(new Dimension(100, 200));
        add(startButton, BorderLayout.WEST); 

        stopButton = new JButton("스탑");
        stopButton.setPreferredSize(new Dimension(100, 200));
        add(stopButton, BorderLayout.EAST);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        });
    }

    private void startGame() {
        timerLabel.setText("0");
        startTime = System.currentTimeMillis();

        gameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                double seconds = elapsedTime / 1000.0; // 밀리초를 초로 변환
                timerLabel.setText(String.format("%.1f", seconds)); // 소수점 한 자리까지 표시
            }
        });

        gameTimer.start();
    }

    private void stopGame() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
            long stopTime = System.currentTimeMillis();
            double elapsedTime = (stopTime - startTime) / 1000.0; // 밀리초를 초로 변환
            totalPlaytime += elapsedTime;

            if (elapsedTime >= 8.8 && elapsedTime <= 9.0) {
                JOptionPane.showMessageDialog(TimerGame.this, "알람 설정에 성공했습니다!\n총 플레이 타임: " + String.format("%.1f", totalPlaytime) + " 초");
            } else {
                JOptionPane.showMessageDialog(TimerGame.this, "시간 내에 멈추지 못했습니다!");
            }
        }
    }

    public static void main(String[] args) {
    	TimerGame timerGame = new TimerGame();
    	timerGame.setVisible(true);

    }
}


