package hojun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class RankingSystem extends JPanel {
	
	MainSystem mainSystem;
	private JTable rankTable;
	
	public RankingSystem(MainSystem mainSystem) {
		this.mainSystem = mainSystem;
		showRanking();
	}
	
	public RankingSystem(MainSystem mainSystem, int score) {
		this.mainSystem = mainSystem;
		
		User newUser = new User(score);
		getUserData(newUser);
	}

	void getUserData(User newUser) {
		JLabel label = new JLabel("이름 : ");
	    label.setFont(new Font("", Font.PLAIN, 20));
	    JTextField txtName = new JTextField(20);
	    txtName.setPreferredSize(new Dimension(20, 35));
	    JButton confirmBtn = new JButton("확인");
	    confirmBtn.setFont(new Font("", Font.PLAIN, 20));

		add(label);
		add(txtName);
		add(confirmBtn);

		setVisible(true);

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				newUser.setName(name);
				txtName.setText("");

				removeAll();
				revalidate();
				repaint();

				showRanking(newUser);
			}
		});
	}

	void showRanking() {
//		신규 유저가 포함된 정렬 리스트를 반환받아 JTable 생성 후 랭킹 보여주기(포커스를 신규 유저에?)

		ArrayList<User> userList = readRankFile("C:\\Temp\\rankFile.txt");
		rankTable = tabulate(userList);
		JScrollPane sp = new JScrollPane(rankTable);

		JLabel lb = new JLabel("LEADER BOARD");
		lb.setFont(new Font("Mistral", Font.PLAIN, 30));
		lb.setHorizontalAlignment(JLabel.CENTER);
		
		JButton backBtn = new JButton("BACK");
		backBtn.setFont(new Font("Mistral", Font.PLAIN, 20));

		JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // FlowLayout을 사용하여 우측 정렬
		backPanel.add(backBtn);
		
		setLayout(new BorderLayout());
		add(sp, BorderLayout.CENTER);
		add(lb, BorderLayout.NORTH);
		add(backPanel, BorderLayout.SOUTH);

		setSize(500, 500);
		setVisible(true);

		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();

				mainSystem.replay();
			}
		});
	}

	void showRanking(User newUser) {
//		신규 유저가 포함된 정렬 리스트를 반환받아 JTable 생성 후 랭킹 보여주기(포커스를 신규 유저에?)

		ArrayList<User> sortList = sortRanking(newUser);
		rankTable = tabulate(sortList);
		JScrollPane sp = new JScrollPane(rankTable);

		JLabel lb = new JLabel(newUser.getName() + "님의 순위는 " + newUser.getRank() + "위");
		lb.setFont(new Font("", Font.PLAIN, 30));
		lb.setHorizontalAlignment(JLabel.CENTER);

		JButton replayBtn = new JButton("REPLAY");
		replayBtn.setFont(new Font("Mistral", Font.PLAIN, 20));
		
		JPanel replayPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // FlowLayout을 사용하여 우측 정렬
		replayPanel.add(replayBtn);

		setLayout(new BorderLayout());
		add(sp, BorderLayout.CENTER);
		add(lb, BorderLayout.NORTH);
		add(replayPanel, BorderLayout.SOUTH);
		
		setSize(500, 500);
		setVisible(true);

		replayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				revalidate();
				repaint();
				
				mainSystem.replay();
			}
		});
	}
	
	JTable tabulate(ArrayList<User> list) {
//		콜렉션 리스트를 표로 변환
		String[] title = { "Rank", "User", "Score" };
		String[][] data = new String[list.size()][title.length];

		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			data[i][0] = Integer.toString(user.getRank());
			data[i][1] = user.getName();
			data[i][2] = Integer.toString(user.getScore());
		}

		JTable table = new JTable(data, title);
		return table;
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