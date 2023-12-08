package gameforwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class PuzzleStory extends JPanel{
	MainSystem main;
	JButton startButton;
	private int width;
	private int height;
	
	public PuzzleStory(MainSystem main) {
		this.main = main;
		this.width = main.getWidth();
		this.height = main.getHeight();
		
		setLayout(new BorderLayout());
		ImagePanel imgPanel = new ImagePanel(new ImageIcon("images/exam.png").getImage());
		imgPanel.setLayout(null);
		
		startButton = new JButton("GO!!");
		startButton.setFont(new Font("Mistral", Font.PLAIN, 50));
		startButton.setForeground(new Color(25, 77, 51));
		startButton.setBackground(new Color(240, 248, 255));
		startButton.setBounds(width/2 -100, height/2-45, 200, 70);
		
		imgPanel.add(startButton);
		add(imgPanel);
		
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	startGame();
            }
        });
	}
	private void startGame() {
		setVisible(false);
        removeAll(); // GradDodger의 모든 컴포넌트 삭제.
 
        revalidate();
        repaint();
        
        main.playThirdGame();
	}

}
