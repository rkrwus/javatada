package teamproject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MakePuzzle extends JFrame {
    private BufferedImage[][] original = new BufferedImage[3][3];
    private BufferedImage image;
    private JTextField textField;
    private long startTime;
    private long endTime;
    private int time;

    public MakePuzzle() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(2560, 1440);

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        imagePanel.setLayout(new BorderLayout());

        try {
            loadImage("image/exam.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel startButtonPanel = new JPanel(new GridBagLayout());
        startButtonPanel.setOpaque(false);

        JButton startButton = new JButton("시작하기");
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(e -> startPuzzleGame());
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
        getContentPane().removeAll();
        revalidate();

        try {
            loadImage("image/puzzle.png");
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

        JPanel puzzlePanel = createPuzzlePanel();
        add(puzzlePanel, BorderLayout.CENTER);

        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.SOUTH);

        startTime = System.currentTimeMillis();
        revalidate();
    }

    private JPanel createPuzzlePanel() {
        return new JPanel() {
            @Override
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
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        JLabel label = new JLabel("메모의 정답(특수기호, 띄어쓰기 제외):");
        textField = new JTextField(20);
        JButton submitButton = new JButton("정답 제출");

        submitButton.addActionListener(e -> checkAnswerAndCalculateTime());

        inputPanel.add(label);
        inputPanel.add(textField);
        inputPanel.add(submitButton);

        return inputPanel;
    }

    private void checkAnswerAndCalculateTime() {
        endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        int timeInSeconds = (int) (timeTaken / 1000);

        String inputText = textField.getText();
        if (inputText.equals("교수님사랑합니다")) {
            showSuccessMessage(timeInSeconds);
        } else {
            showErrorMessage();
        }
    }

    private void showSuccessMessage(int timeInSeconds) {
        JOptionPane.showMessageDialog(null, "정답입니다! 소요된 시간: " + timeInSeconds + " 초");
    }

    private void showErrorMessage() {
        JOptionPane.showMessageDialog(null, "틀렸습니다. 다시 시도해주세요.");
    }

    private int getscore() {
    	endTime = System.currentTimeMillis();
    	long timeTaken = endTime-startTime;
    	return time = (int)(timeTaken/1000);
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

    public static void main(String[] args) {
    	new MakePuzzle();
    }
}
