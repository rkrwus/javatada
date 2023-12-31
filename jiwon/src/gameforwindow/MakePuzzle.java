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
    
    JButton rewindButton;
   
    public MakePuzzle(MainSystem main) {
       this.main = main;
       setLayout(new BorderLayout());
        
       rewindButton = new JButton();
		ImageIcon backIcon = new ImageIcon("images/rewind.png");
		rewindButton.setIcon(backIcon);
		rewindButton.setBounds(5, 5, 70, 70);
       add(rewindButton);
		
       rewindButton.addActionListener(new ActionListener() {
    	   @Override
    	   public void actionPerformed(ActionEvent e) {
    		   rewind();
            }
        });
		
		add(rewindButton);
        
        startPuzzleGame();
        
        setVisible(true);
    }

    private void loadImage(String imagePath) throws IOException {
        image = ImageIO.read(new File(imagePath));
    }
    
    private BufferedImage resize(BufferedImage img) {
    	BufferedImage resizedImage = new BufferedImage(650, 650, img.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(img, 0, 0, 650, 650, null);
        g2d.dispose();
        return resizedImage;
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
        revalidate();

        try {
            loadImage("images/puzzle.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = resize(image);

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
        inputPanel.setPreferredSize(new Dimension(80,80));
        JLabel label = new JLabel("메모의 정답(특수기호, 띄어쓰기 제외):");
        textField = new JTextField(30);
        textField.setPreferredSize(new Dimension(30, 30));
        label.setFont(new Font("Aial", Font.PLAIN,30));
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
                frame.setSize(1250, 720);
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
    private void rewind() {
		setVisible(false);
        removeAll(); // GradDodger의 모든 컴포넌트 삭제.
 
        revalidate();
        repaint();
        
        main.rewind5();
	}
}