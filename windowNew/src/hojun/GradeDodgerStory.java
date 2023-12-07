package hojun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;

public class GradeDodgerStory extends JPanel{
	MainSystem mainSystem;
	JButton startButton;
	
	public GradeDodgerStory(MainSystem main) {
		this.mainSystem = main;
		setLayout(new BorderLayout());
		
		ImageIcon storyImage = new ImageIcon("/gdStory.png");
		storyImage = new ImageIcon(storyImage.getImage().getScaledInstance(1536, 864, Image.SCALE_DEFAULT));
		System.out.println("gdStory.png URL: " + getClass().getResource("/gdStory.png"));
		JLabel storylabel = new JLabel(storyImage);

		JButton startButton = new JButton("시작하기");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        
        startButton.setPreferredSize(new Dimension(150, 50));
		
		this.add(storylabel,BorderLayout.CENTER);
		this.add(startButton, BorderLayout.NORTH);
	}
	
	private void startGame() {
		setVisible(false);
        removeAll(); // GradDodger의 모든 컴포넌트 삭제.
 
        revalidate();
        repaint();
        
		mainSystem.playFourthGame();
	}
	

}
