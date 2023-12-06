package teamproject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MakePuzzle extends JFrame {
    public BufferedImage[][] original = new BufferedImage[3][3]; // 원본 이미지 조각 저장
    public BufferedImage image;

    public MakePuzzle() {
        setTitle("시험지 퍼즐");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
        image = loadImage("image/puzzle.png"); // 배경화면 불러오기
        PuzzleMaking(); // 사진 분리해서 퍼즐 만들기
    }

    public BufferedImage loadImage(String imagePath) {
        BufferedImage loadedImage = null;
        try {
            loadedImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedImage;
    }

    public void PuzzleMaking() {
        int width = image.getWidth() / 3;
        int height = image.getHeight() / 3;
        BufferedImage[][] temp = new BufferedImage[3][3]; // 임시 배열 생성
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp[i][j] = image.getSubimage(j * width, i * height, width, height); // 이미지 조각 만들기
            }
        }

        int[] randomNumbers = generateRandomArray();
        int count = 0;

        // 랜덤 배열을 기반으로 이미지 조각을 섞음
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                original[i][j] = temp[randomNumbers[count] / 3][randomNumbers[count] % 3];
                count++;
            }
        }
    }

    // 0부터 8까지의 랜덤한 배열 생성
    public int[] generateRandomArray() {
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
                MakePuzzle puzzle = new MakePuzzle();
                puzzle.setVisible(true);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = image.getWidth() / 3;
        int height = image.getHeight() / 3;
        int x = 0, y = 0;

        // 조각난 이미지를 윈도우에 그림
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                g.drawImage(original[i][j], x, y, width, height, this);
                x += width;
            }
            x = 0;
            y += height;
        }
    }
}
