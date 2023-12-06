package jiwon123;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MainSystem extends JFrame {

	private int totalScore = 0;

	public MainSystem() {
		setTitle("Start Game");
		setSize(2560, 1440);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		playPanel();

		setVisible(true);
//		setLocationRelativeTo(null);
	}

	void play() {
		
	}

	void replay() {
		this.totalScore = 0;
		playPanel();
	}
	
	void operateRankingSystem() {
		RankingSystem rs = new RankingSystem(this, this.totalScore);
		add(rs);
		
//		totalScore += convertTimeToScore(480); // 8분 소요되었다 가정
//		new RankingSystem(this, this.totalScore);
	}

	int convertTimeToScore(int time) {
//	입력받은 시간이 짧을 수록 높은 점수를 갖도록 환산
//	최대 시간을 10분(=600s)로 잡고 점수는 (최대 시간-소요시간)*가중치로 환산, 소요시간이 10분을 초과하는 경우 0점
		final int MAX_TIME = 600;
		final int WEIGHT = 12345;

		int score = (MAX_TIME - time) * WEIGHT;
		return score;
	}

	void playPanel() {

		setLayout(new BorderLayout());
		ImagePanel imgPanel = new ImagePanel(new ImageIcon("C:\\Temp\\backimg.jpg").getImage());
		imgPanel.setLayout(null);

		JLabel playlb = new JLabel("시작 버튼을 눌러주세요!");
		playlb.setBounds(50, 110, 200, 40);
		playlb.setHorizontalAlignment(JLabel.CENTER);

		JButton playBtn = new JButton("시작");
		playBtn.setBounds(110, 150, 80, 40);
		playBtn.setBorderPainted(false);

		imgPanel.add(playlb);
		imgPanel.add(playBtn);
		imgPanel.setSize(500, 500);

		add(imgPanel);

		playBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(imgPanel);
				revalidate();
				repaint();
				
				operateRankingSystem();
			}
		});
	}

	class ImagePanel extends JPanel {
		private Image img;

		public ImagePanel(Image img) {
			this.img = img;
			setSize(new Dimension(img.getWidth(null), img.getHeight(null)));
			setLayout(null);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (img != null) {
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			}
		}
	}

	public static void main(String[] args) {
		new MainSystem();
	}

}
