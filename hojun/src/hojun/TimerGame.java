package hojun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerGame extends JPanel {
    private JPanel startPanel;
    private JPanel gamePanel;
    private JLabel timerLabel;
    private JButton startButton;
    private JButton stopButton;
    private JButton rewindButton;

    private Timer gameTimer;
    private long startTime;
    private double totalPlaytime;
    MainSystem main;

    
    public TimerGame(MainSystem main) {
    	this.main = main;
        gamePanel = new JPanel(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        revalidate();

        ImageIcon gameImageIcon = new ImageIcon("images/GameImage.jpg");
        gameImageIcon = new ImageIcon(gameImageIcon.getImage().getScaledInstance(1280, 720, Image.SCALE_DEFAULT));

        timerLabel = new JLabel("0");
        timerLabel.setFont(new Font("TimesRoman", Font.ITALIC, 200));
        timerLabel.setBounds(750, -250, 1000, 1000);
        gamePanel.add(timerLabel);
        
        JLabel gameImageLabel = new JLabel(gameImageIcon);
        gamePanel.add(gameImageLabel, BorderLayout.CENTER);
        
        rewindButton = new JButton();
		ImageIcon backIcon = new ImageIcon("images/rewind.png");
		rewindButton.setIcon(backIcon);
		rewindButton.setBounds(5, 5, 70, 70);
		gameImageLabel.add(rewindButton);
        
        stopButton = new JButton("8.8초에서 9초 사이에 멈춰라!");
        stopButton.setBounds(740, 350, 300, 100);
        gameImageLabel.add(stopButton);
        
        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	rewind();
            }
        });

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
                main.getFirstScore();
                remove(gamePanel);
                clearPanel();
                
            } else {
            	Object[] options = {"Retry"};
            	int option = JOptionPane.showOptionDialog(
            	        this,
            	        "시간 내에 멈추지 못했습니다!\n재시도 하세요!",
            	        "Retry",
            	        JOptionPane.DEFAULT_OPTION,
            	        JOptionPane.INFORMATION_MESSAGE,
            	        null,
            	        options,
            	        options[0]
            	);

            	if (option == 0) { // 사용자가 "Retry"를 선택한 경우
            	    startTime = System.currentTimeMillis();
            	    gameTimer.start();
                } 
            }
        }
    }

    public int getScore() {
        return (int) totalPlaytime;
    }
    
    private void clearPanel() {   
    	setVisible(false);
        removeAll();
 
        revalidate();
        repaint();
        
        main.playSecondStory();
	}
    
    private void rewind() {
		setVisible(false);
        removeAll();
 
        revalidate();
        repaint();
        
        main.rewind1();
	}
}