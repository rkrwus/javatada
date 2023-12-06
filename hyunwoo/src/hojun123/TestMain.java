package hojun123;

import javax.swing.*;

public class TestMain extends JFrame{
	public TestMain() {
		setTitle("teset");
		setSize(2560, 1440);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TimerGame timergame = new TimerGame();
		add(timergame);
		
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new TestMain();
	}
}
