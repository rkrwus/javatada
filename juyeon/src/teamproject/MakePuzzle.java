package teamproject;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MakePuzzle extends JFrame {
    private BufferedImage[][] original = new BufferedImage[3][3]; // 원본 이미지 조각 저장
    private BufferedImage image;
    private JTextField textField;

    public MakePuzzle() {
        setTitle("Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080, 720); 

        // 이미지 로드 부분을 loadImage 메서드 대신 직접 처리
        try {
            image = ImageIO.read(new File("image/puzzle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        int width = image.getWidth() / 3;
        int height = image.getHeight() / 3;
        BufferedImage[][] temp = new BufferedImage[3][3]; 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp[i][j] = image.getSubimage(j * width, i * height, width, height); 
            }
        }

        int[] randomNumbers = generateRandomArray();
        int count = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                original[i][j] = temp[randomNumbers[count] / 3][randomNumbers[count] % 3];
                count++;
            }
        }

        JPanel puzzlePanel = new JPanel() { 
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = image.getWidth() / 3;
                int height = image.getHeight() / 3;
                int x = (getWidth() - width * 3) / 2;
                int y = (getHeight() - height * 3) / 2;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        g.drawImage(original[i][j], x, y, width, height, this);
                        x = x + width;
                    }
                    x = (getWidth() - width * 3) / 2;
                    y += height;
                }
            }
        };
        add(puzzlePanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();

        JLabel label = new JLabel("메모의 정답(특수기호 제외):");
        textField = new JTextField(20);
        JButton submitButton = new JButton("정답 제출");

        submitButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                if (inputText.equals("교수님 사랑합니다")) {
                    JOptionPane.showMessageDialog(null, "정답입니다!");
                } else {
                    JOptionPane.showMessageDialog(null, "틀렸습니다. 다시 시도해주세요.");
                }
            }
        });

        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(submitButton);
        add(inputPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private int[] generateRandomArray() {//이미지 조각을 섞는 
        int[] randomNumbers = new int[9];
        boolean[] used = new boolean[9];
        int count = 0;
        while (count < 9) {
            int randomNumber = (int) (Math.random() * 9);
            if (!used[randomNumber]) {
                randomNumbers[count] = randomNumber;
                used[randomNumber] = true;
                count++;
            }
        }
        return randomNumbers;
    }
    public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               // new MakePuzzle();
            }
        });
    }
}
