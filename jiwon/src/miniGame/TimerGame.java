package miniGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerGame extends JPanel {
	private int width;
	private int height;
	
    private JPanel startPanel;
    private JPanel gamePanel;
    private JLabel timerLabel;
    private JButton startButton;
    private JButton stopButton;

    private Timer gameTimer;
    private long startTime;
    private double totalPlaytime;
    MainSystem main;

    public TimerGame(MainSystem main) {
    	this.main = main;
    	this.width = main.getWidth();
    	this.height = main.getHeight();
    	
        setLayout(new BorderLayout());

        ImageIcon loadingImageIcon = new ImageIcon("images/loading1.jpg");
        loadingImageIcon = new ImageIcon(loadingImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));

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

        add(startPanel, BorderLayout.CENTER);
    }

    private void startGame() {
    	remove(startPanel);
        gamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        revalidate();

        ImageIcon gameImageIcon = new ImageIcon("images/GameImage.jpg");
        gameImageIcon = new ImageIcon(gameImageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));

        JLabel gameImageLabel = new JLabel(gameImageIcon);
        gameImageLabel.setBounds(0, 0, width, height);
        gamePanel.add(gameImageLabel);

        timerLabel = new JLabel("0");
        timerLabel.setFont(new Font("TimesRoman", Font.ITALIC, 250));
        timerLabel.setBounds((width*6)/11, -(height/8), getWidth(), getHeight());
        gameImageLabel.add(timerLabel);

        stopButton = new JButton("스탑");
        stopButton.setBounds(730, height/2, 300, 100);
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
                main.getFirstScore();
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
        removeAll(); // GradDodger의 모든 컴포넌트 삭제.
 
        revalidate();
        repaint();
        
        main.playSecondGame();
	}

    /* public static void main(String[] args) {
        JFrame frame = new JFrame("알람을 맞춰라!");
        frame.setSize(2560, 1440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TimerGame timerGame = new TimerGame();
        frame.add(timerGame);
        frame.setVisible(true);
    } */
}