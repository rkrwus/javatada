package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MainSystem {
	
	private int totalScore = 0;
	public MainSystem() {
		new StartFrame(this);		
	}
	
	int convertTimeToScore(int time) {
//		입력받은 시간이 짧을 수록 높은 점수를 갖도록 환산
//		최대 시간을 10분(=600s)로 잡고 점수는 (최대 시간-소요시간)*가중치로 환산, 소요시간이 10분을 초과하는 경우 0점
		final int MAX_TIME = 600;
		final int WEIGHT = 12345;
		
		int score = (MAX_TIME - time)*WEIGHT;
		return score;
	}
	
//	void startFirstGame() {
//		this.totalScore += convertTimeToScore(new FirstGame(this).getTime());
//	}
//	
//	void startSecondGame() {
//		this.totalScore += convertTimeToScore(new SecondGame(this).getTime());
//	}
//	
//	void startThirdGame() {
//		this.totalScore += convertTimeToScore(new ThirdGame(this).getTime());
//	}
//	
//	void FourthGame() {
//		this.totalScore += new FourthGame(this).getScore();
//	}
	
	void operateRankingSystem() {
//		test-ing
		totalScore += convertTimeToScore(480);	// 8분 소요되었다 가정
		new RankingSystem(this, this.totalScore);
	}
	
	void replay() {
		this.totalScore = 0;
		new StartFrame(this);		
	}

	public static void main(String[] args) {
		new MainSystem();
	}

}

class StartFrame extends JFrame {

	public StartFrame(MainSystem mainSystem) {
		setTitle("Start Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = getContentPane();
		c.setLayout(new BorderLayout());
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
		
		c.add(imgPanel);

		setSize(300, 300);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);

		playBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("play btn pressed");
				c.remove(imgPanel);
				revalidate();
				repaint();
				mainSystem.operateRankingSystem();
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
}

class User {
	private int rank;
	private String name;
	private int score;

//	신규 유저정보 입력할 때
	public User(int score) {
		this.score = score;
	}

//	랭킹 파일에서 유저 정보 불러와 객체 생성할 때
	public User(int rank, String name, int score) {
		this.rank = rank;
		this.name = name;
		this.score = score;
	}

	int getRank() {	return this.rank; }
	void setRank(int rank) { this.rank = rank; }

	String getName() { return this.name; }
	void setName(String name) { this.name = name; }

	int getScore() {return this.score; }
	void setScore(int score) { this.score = score; }

}

class RankingSystem extends JFrame {
	
	MainSystem mainSystem;
	private JTable rankTable;

	public RankingSystem(MainSystem mainSystem, int score) {
		this.mainSystem = mainSystem;
		
		User newUser = new User(score);
		getUserData(newUser);
	}

	void getUserData(User newUser) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("이름: ");
		JTextField txtName = new JTextField(10);
		JButton confirmBtn = new JButton("확인");

		panel.add(label);
		panel.add(txtName);
		panel.add(confirmBtn);

		add(panel);
		setSize(300, 300);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				newUser.setName(name);
				txtName.setText("");

				getContentPane().remove(panel);
				revalidate();
				repaint();

				showRanking(newUser);
			}
		});
	}

	void showRanking(User newUser) {
//		신규 유저가 포함된 정렬 리스트를 반환받아 JTable 생성 후 랭킹 보여주기(포커스를 신규 유저에?)

		ArrayList<User> sortList = sortRanking(newUser);

		String[] title = { "Rank", "User", "Score" };
		String[][] data = new String[sortList.size()][title.length];

		for (int i = 0; i < sortList.size(); i++) {
			User user = sortList.get(i);
			data[i][0] = Integer.toString(user.getRank());
			data[i][1] = user.getName();
			data[i][2] = Integer.toString(user.getScore());
		}

		JLabel lb = new JLabel(newUser.getName() + "님의 순위는 " + newUser.getRank() + "위");
		lb.setHorizontalAlignment(JLabel.CENTER);

		rankTable = new JTable(data, title);
		JScrollPane sp = new JScrollPane(rankTable);
		
		JButton replayBtn = new JButton("다시하기");
		replayBtn.setBorderPainted(false);

		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(sp, BorderLayout.CENTER);
		c.add(lb, BorderLayout.NORTH);
		
		JPanel replayPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // FlowLayout을 사용하여 우측 정렬
		replayPanel.add(replayBtn);
		c.add(replayPanel, BorderLayout.SOUTH);
		
		setSize(300, 300);
		setVisible(true);

		replayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(c);
				revalidate();
				repaint();
				
				mainSystem.replay();
			}
		});
	}

	ArrayList<User> sortRanking(User newUser) {
//		기존의 랭킹 파일을 읽어와 신규 유저 정보에 따라 정렬 및 파일을 갱신하고, 정렬된 리스트 반환
		ArrayList<User> userList = readRankFile("C:\\Temp\\rankFile.txt");
		userList.add(newUser);

//      점수에 따라 내림차순으로 정렬
		Collections.sort(userList, Comparator.comparingInt(User::getScore).reversed());
		setUserRank(userList);

		writeRankFile("C:\\Temp\\rankFile.txt", userList);
		return userList;
	}

	void setUserRank(ArrayList<User> userList) {
		for (int i = 0; i < userList.size(); i++) {
			userList.get(i).setRank(i + 1);
		}
	}

	ArrayList<User> readRankFile(String filePath) {

		ArrayList<User> userList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
//					해당 경로에 파일이 존재하면서 비어있지 않은 경우
	                String[] parts = line.split(", ");
	                int rank = Integer.parseInt(parts[0]);
	                String name = parts[1];
	                int score = Integer.parseInt(parts[2]);

	                userList.add(new User(rank, name, score));
	            }
			}
		} catch (FileNotFoundException e) {
//			해당 경로에 파일이 없는 경우 새로운 파일 생성
			 File newFile = new File(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return userList;
	}

	void writeRankFile(String filePath, ArrayList<User> userList) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
			for (User user : userList) {
//            	순위와 이름, 점수를 ','로 구분해 작성(CSV 형태로)
				writer.println(user.getRank() + ", " + user.getName() + ", " + user.getScore());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
