package gameforwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerStory extends JPanel{
	MainSystem main;
	JButton startButton;
	JButton rewindButton;
	private int width;
	private int height;
	
	public TimerStory(MainSystem main) {
		this.main = main;
		this.width = main.getWidth();
    	this.height = main.getHeight();
    	
    	setLayout(new BorderLayout());
    	ImagePanel imgPanel = new ImagePanel(new ImageIcon("images/loading1.jpg").getImage());
		imgPanel.setLayout(null);
    	
		startButton = new JButton("GO");
		startButton.setFont(new Font("Mistral", Font.PLAIN, 50));
		startButton.setForeground(new Color(25, 77, 51));
		startButton.setBackground(new Color(240, 248, 255));
		startButton.setBounds(width - 350, height - 250, 200, 80);
		
		rewindButton = new JButton();
		ImageIcon backIcon = new ImageIcon("images/rewind.png");
		rewindButton.setIcon(backIcon);
		rewindButton.setBounds(5, 5, 70, 70);
		
		imgPanel.add(rewindButton);
		imgPanel.add(startButton);
		add(imgPanel);
		
		rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	rewind();
            }
        });
		
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
        
        main.playFirstGame();
	}
	private void rewind() {
		setVisible(false);
        removeAll(); // GradDodger의 모든 컴포넌트 삭제.
 
        revalidate();
        repaint();
        
        main.rewindToOrigin();
	}
	

}
