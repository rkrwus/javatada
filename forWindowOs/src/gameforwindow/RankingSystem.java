package gameforwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

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
		setSize(500, 500);
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
		
		setSize(500, 500);
		setVisible(true);

		replayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(c);
				dispose();
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