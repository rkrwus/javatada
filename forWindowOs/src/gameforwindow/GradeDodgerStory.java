package gameforwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GradeDodgerStory extends JPanel{
	MainSystem mainSystem;
	JButton startButton;
	JButton rewindButton;
	private int width;
	private int height;
	
	public GradeDodgerStory(MainSystem main) {
		this.mainSystem = main;
		this.width = main.getWidth();
    	this.height = main.getHeight();
    	
    	setLayout(new BorderLayout());
		ImagePanel imgPanel = new ImagePanel(new ImageIcon("images/gdStory.png").getImage());
		imgPanel.setLayout(null);
		
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
		
		startButton = new JButton("GO!!");
		startButton.setFont(new Font("Mistral", Font.PLAIN, 50));
		startButton.setForeground(new Color(25, 77, 51));
		startButton.setBackground(new Color(240, 248, 255));
		startButton.setBounds(width/2 -100, height/2 - 10, 200, 70);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        imgPanel.add(rewindButton);
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
        
		mainSystem.playFourthGame();
	}
	private void rewind() {
		setVisible(false);
        removeAll(); // GradDodger의 모든 컴포넌트 삭제.
 
        revalidate();
        repaint();
        
        mainSystem.rewind6();
	}

}
