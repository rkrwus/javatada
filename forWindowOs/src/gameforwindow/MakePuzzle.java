package gameforwindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MakePuzzle extends JPanel {
    private BufferedImage[][] original = new BufferedImage[3][3];
    private BufferedImage image;
    private JTextField textField;
    private long startTime;
    private long endTime;
    private int timeInSeconds;
    MainSystem main;
   
    public MakePuzzle(MainSystem main) {
       this.main = main;
        setLayout(new BorderLayout());

        JPanel imagePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        imagePanel.setLayout(new BorderLayout());

        try {
            loadImage("images/exam.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel startButtonPanel = new JPanel(new GridBagLayout());
        startButtonPanel.setOpaque(false);

        JButton startButton = new JButton("시작하기");
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startPuzzleGame();
            }
        });
        startButtonPanel.add(startButton);

        imagePanel.add(startButtonPanel, BorderLayout.CENTER);

        add(imagePanel);
        setVisible(true);
    }

    private void loadImage(String imagePath) throws IOException {
        image = ImageIO.read(new File(imagePath));
    }

    private BufferedImage[][] createSubImages(BufferedImage img) {
        int width = img.getWidth() / 3;
        int height = img.getHeight() / 3;
        BufferedImage[][] temp = new BufferedImage[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp[i][j] = img.getSubimage(j * width, i * height, width, height);
            }
        }

        return temp;
    }

    private void startPuzzleGame() {
        removeAll();
        revalidate();

        try {
            loadImage("images/puzzle.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage[][] temp = createSubImages(image);
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
        JLabel label = new JLabel("메모의 정답(특수기호, 띄어쓰기 제외):");
        textField = new JTextField(20);
        JButton submitButton = new JButton("정답 제출");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                if (inputText.equals("김화정교수님사랑합니다")) {
                    checkAnswerAndCalculateTime();
                    JOptionPane.showMessageDialog(null, "정답입니다람쥐.");
                   main.getThirdScore();
                   clearPanel();
                } else {
                    JOptionPane.showMessageDialog(null, "틀렸습니다. 다시 시도해주세요.");
                }
            }
        });

        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(submitButton);
        add(inputPanel, BorderLayout.NORTH);

        startTime = System.currentTimeMillis();

        revalidate();
    }

    private void checkAnswerAndCalculateTime() {
        endTime = System.currentTimeMillis();

        long timeTaken = endTime - startTime;

        timeInSeconds = (int) (timeTaken / 1000);
    }

    public int getScore() {
        return timeInSeconds;
    }

    private int[] generateRandomArray() {
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
    

   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Puzzle");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(2560, 1440);
                frame.add(new MakePuzzle());
                frame.setVisible(true);
            }
        });
    } */
    private void clearPanel() {   
    	setVisible(false);
        removeAll(); // GradDodger의 모든 컴포넌트 삭제.
 
        revalidate();
        repaint();
        
        main.playFourthStory();
	}
}