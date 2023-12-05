package gradDodgeGame;

import javax.swing.*;

//메인 JFrame 생성. 아마 처음부터 JFrame 하나만 가지고 panel들 바꿔가며 게임 진행시키면 될 듯. 
public class Game_main extends JFrame{
	public Game_main() {
		setTitle("javaTada games javaTada games javaTada games");
		setSize(800, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GradeDodger gradeDodger = new GradeDodger();
		add(gradeDodger);
		
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void main(String[] args) {
		new Game_main();
	}
}
