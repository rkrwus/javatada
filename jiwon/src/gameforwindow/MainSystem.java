package gameforwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MainSystem extends JFrame {
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;

	TimerGame game1;
	MiroGame game2;
	MakePuzzle game3;
	GradeDodger game4;
	
	TimerStory story1;
	MiroStory story2;
	PuzzleStory story3;
	GradeDodgerStory story4;
	
	private int firstScore = 0;
	private int secondScore = 0;
	private int thirdScore = 0;
	private int fourthScore = 0;
	private int totalScore = 0;

	public MainSystem() {
		setTitle("JavaTada JavaTada JavaTada JavaTada JavaTada");
		setSize(WIDTH, HEIGHT);
		// setResizable(false);
		playPanel();
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	void playFirstStory() {
		story1 = new TimerStory(this);
		setLocationRelativeTo(null);
		getContentPane().removeAll();
		add(story1);
		story1.requestFocus(); 
		setResizable(false);
		setVisible(true);
		System.out.println("playFirstStory __ good");
	}
	
	void playSecondStory() {
		story2 = new MiroStory(this);
		setLocationRelativeTo(null);
		getContentPane().removeAll();
		add(story2);
		story2.requestFocus(); 
		setResizable(false);
		setVisible(true);
		System.out.println("playFirstStory __ good");
	}
	
	void playThirdStory() {
		story3 = new PuzzleStory(this);
		setLocationRelativeTo(null);
		getContentPane().removeAll();
		add(story3);
		story3.requestFocus(); 
		setResizable(false);
		setVisible(true);
		System.out.println("playFirstStory __ good");
	}
	
	void playFourthStory() {
		story4 = new GradeDodgerStory(this);
		setLocationRelativeTo(null);
		getContentPane().removeAll();
		add(story4);
		story4.requestFocus(); 
		setResizable(false);
		setVisible(true);
		System.out.println("playFourthStory __ good");
	}

	void playFirstGame() {
	      System.out.println("playFirstGame");
	      game1 = new TimerGame(this);
	      getContentPane().removeAll();
	      add(game1);
	      game1.requestFocus(); 
	      setVisible(true);
	      System.out.println("playFirstGame __ good");
	   }
	   
	   void playSecondGame() {
	      System.out.println("playSecondGame");
	      game2 = new MiroGame(this);
	      getContentPane().removeAll();
	      add(game2);
	      game2.requestFocus();
	      setVisible(true);
	      System.out.println("playSecondGame __ good");
	   }
	   
	   void playThirdGame() {
	      game3 = new MakePuzzle(this);
	      getContentPane().removeAll();
	      add(game3);
	      game3.requestFocus();
	      setVisible(true);
	      System.out.println("playThirdGame __ good");
	   }
	   
	   void playFourthGame() {
	      game4 = new GradeDodger(this);
	      getContentPane().removeAll();// 기존에 추가된 컴포넌트를 제거
	      add(game4);
	      game4.requestFocus(); 
	      setVisible(true);
	      System.out.println("playFourthGame __ good");
	   }
	
	void getFirstScore() {
		System.out.println("getFirstScore");
		firstScore = game1.getScore();
		System.out.println("getFirstScore __ ok" + firstScore);
	}
	
	void getSecondScore() {
		System.out.println("getScondeScore");
		secondScore = game2.getScore();
		System.out.println("getSecondScore __ ok" + secondScore);
	}
	
	void getThirdScore() {
		System.out.println("getThirdScore");
		thirdScore = game3.getScore();
		System.out.println("getThirdScore __ ok" + thirdScore);
	}
	
	void getFourthScore() {
		System.out.println("getFourthScore");
		fourthScore = game4.getScore();
		System.out.println("getFourthScore __ ok" + fourthScore);
	}
	
	void rewind1() {
		playFirstStory();
	}
	void rewind2() {
		playFirstGame();
	}
	void rewind3() {
		playSecondStory();
	}
	void rewind4() {
		playSecondGame();
	}
	void rewind5() {
		playThirdStory();
	}
	void rewind6() {
		playThirdGame();
	}
	void rewind7() {
		playFourthStory();
	}
	void rewindToOrigin() {
		playPanel();
	}

	void replay() {
		this.totalScore = 0;
		playPanel();
		setVisible(true);
	}

	void operateRankingSystem(boolean addNew) {	
		RankingSystem rs;
		
		if (addNew == true) {
			totalScore = firstScore + secondScore + thirdScore + fourthScore;
			totalScore = convertTimeToScore(totalScore);	
			rs = new RankingSystem(this, totalScore);
		} else {
			rs = new RankingSystem(this);
		}
		
		add(rs);
		setVisible(true);
		setFocusable(true);
	}

	int convertTimeToScore(int time) {
//		입력받은 시간이 짧을 수록 높은 점수를 갖도록 환산
//		최대 시간을 10분(=600s)로 잡고 점수는 (최대 시간-소요시간)*가중치로 환산, 소요시간이 10분을 초과하는 경우 0점
			final int MAX_TIME = 600;
			final int WEIGHT = 12345;
			int score;
			
			System.out.println("time : "+time);
			
			if (time > MAX_TIME || time <= 0) time = MAX_TIME;
			
			score = (MAX_TIME - time) * WEIGHT;
			
			System.out.println("score : "+score);
			return score;
		}

	void playPanel() {
		setLayout(new BorderLayout());
		ImagePanel imgPanel = new ImagePanel(new ImageIcon("images/start.jpg").getImage());
		imgPanel.setLayout(null);

		JButton playBtn = new JButton("PLAY");
		playBtn.setFont(new Font("Mistral", Font.PLAIN, 30));
		playBtn.setForeground(new Color(25, 77, 51));
		playBtn.setBackground(new Color(240, 248, 255));
		playBtn.setBounds(WIDTH/2-185, 330, 100, 60);
//		playBtn.setBorderPainted(false);
		
		JButton rankBtn = new JButton("RANKING");
		rankBtn.setFont(new Font("Mistral", Font.PLAIN, 30));
		rankBtn.setForeground(new Color(25, 77, 51));
		rankBtn.setBackground(new Color(240, 248, 255));
		rankBtn.setBounds(WIDTH/2-85, 330, 150, 60);
//		rankBtn.setBorderPainted(false);
		
		JButton exitBtn = new JButton("EXIT");
		exitBtn.setFont(new Font("Mistral", Font.PLAIN, 30));
		exitBtn.setForeground(new Color(25, 77, 51));
		exitBtn.setBackground(new Color(240, 248, 255));
		exitBtn.setBounds(WIDTH/2+65, 330, 100, 60);
//		exitBtn.setBorderPainted(false);
		
		imgPanel.add(playBtn);
		imgPanel.add(rankBtn);
		imgPanel.add(exitBtn);
		add(imgPanel);

		playBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("play button pressed");
				
				remove(imgPanel);
				revalidate();
				repaint();

				playFirstStory();
			}
		});
		
		rankBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("rank button pressed");
				
				remove(imgPanel);
				revalidate();
				repaint();

				operateRankingSystem(false);
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("exit button pressed");
				System.exit(1);
			}
		});
	}

	public static void main(String[] args) {
		new MainSystem();
		BackgroundMusic backgroundMusic = new BackgroundMusic();
        backgroundMusic.playBackgroundMusic();
	}

}