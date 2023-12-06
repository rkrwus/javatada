package teamproject;
/*

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MakePuzzle extends JFrame {
    private BufferedImage[][] original = new BufferedImage[3][3]; // 3x3 배열의 BufferedImage를 저장하는 변수
    private BufferedImage image; // 이미지를 저장하는 변수
    private JTextField textField; // 텍스트 입력을 위한 JTextField
    private long startTime; // 게임 시작 시간을 저장하는 변수
    private long endTime; // 게임 종료 시간을 저장하는 변수
    private int timeInSeconds; // 게임 진행 시간(초)을 저장하는 변수

    // MakePuzzle 클래스의 생성자
    public MakePuzzle() {
        setTitle("Puzzle"); // 프레임의 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 윈도우를 닫을 때 프로그램 종료 설정
        setSize(2560, 1440); // 프레임의 크기 설정

        JPanel imagePanel = new JPanel() { // 이미지를 표시하는 패널 생성
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        imagePanel.setLayout(new BorderLayout()); // 이미지 패널의 레이아웃 설정

        try {
            loadImage("image/exam.png"); // exam.png 이미지를 로드하여 image 변수에 저장
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel startButtonPanel = new JPanel(new GridBagLayout()); // 시작 버튼을 담는 패널 생성
        startButtonPanel.setOpaque(false); // 패널을 투명하게 설정

        JButton startButton = new JButton("시작하기"); // "시작하기" 버튼 생성
        startButton.setPreferredSize(new Dimension(200, 50)); // 버튼의 크기 설정
        startButton.addActionListener(new ActionListener() { // 버튼에 ActionListener 추가
            public void actionPerformed(ActionEvent e) {
                startPuzzleGame(); // 시작 버튼 클릭 시 startPuzzleGame 메소드 호출
            }
        });
        startButtonPanel.add(startButton); // 시작 버튼을 패널에 추가

        imagePanel.add(startButtonPanel, BorderLayout.CENTER); // 시작 버튼 패널을 이미지 패널의 가운데에 추가

        add(imagePanel); // 이미지 패널을 프레임에 추가
        setVisible(true); // 프레임을 보이도록 설정
    }

    // 이미지를 로드하는 메소드
    private void loadImage(String imagePath) throws IOException {
        image = ImageIO.read(new File(imagePath)); // imagePath 경로의 이미지를 읽어와 image 변수에 저장
    }

    // 이미지를 3x3 조각으로 자르는 메소드
    private BufferedImage[][] createSubImages(BufferedImage img) {
        int width = img.getWidth() / 3; // 이미지의 가로 길이를 3등분한 값
        int height = img.getHeight() / 3; // 이미지의 세로 길이를 3등분한 값
        BufferedImage[][] temp = new BufferedImage[3][3]; // 3x3 크기의 BufferedImage 배열 생성

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // 원본 이미지를 3x3 조각으로 나누어 temp 배열에 저장
                temp[i][j] = img.getSubimage(j * width, i * height, width, height);
            }
        }

        return temp; // 자른 조각들을 담고 있는 배열 반환
    }

    // 퍼즐 게임 시작 메소드
    private void startPuzzleGame() {
        getContentPane().removeAll(); // 모든 컴포넌트 제거
        revalidate(); // 컴포넌트 다시 그리기

        try {
            loadImage("image/puzzle.png"); // puzzle.png 이미지를 로드하여 image 변수에 저장
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage[][] temp = createSubImages(image); // 이미지를 3x3 조각으로 자른다
        int[] randomNumbers = generateRandomArray(); // 이미지 조각의 순서를 랜덤하게 배열
        int count = 0;

        // 이미지 조각들을 랜덤한 순서로 original 배열에 저장
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                original[i][j] = temp[randomNumbers[count] / 3][randomNumbers[count] % 3];
                count++;
            }
        }

        JPanel puzzlePanel = new JPanel() { // 퍼즐을 보여주는 패널 생성
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = image.getWidth() / 3; // 이미지의 가로 길이를 3등분한 값
                int height = image.getHeight() / 3; // 이미지의 세로 길이를 3등분한 값
                int x = (getWidth() - width * 3) / 2; // 이미지가 그려질 x 좌표
                int y = (getHeight() - height * 3) / 2; // 이미지가 그려질 y 좌표

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        g.drawImage(original[i][j], x, y, width, height, this); // 조각을 그림
                        x = x + width; // x 좌표 증가
                    }
                    x = (getWidth() - width * 3) / 2; // 다음 행으로 이동하기 위해 x 좌표 초기화
                    y += height; // y 좌표 증가
                }
            }
        };
        add(puzzlePanel, BorderLayout.CENTER); // 퍼즐 패널을 중앙에 추가

        JPanel inputPanel = new JPanel(); // 정답 입력을 위한 패널 생성
        JLabel label = new JLabel("메모의 정답(특수기호, 띄어쓰기 제외):"); // 라벨 생성
        textField = new JTextField(20); // 텍스트 입력 필드 생성
        JButton submitButton = new JButton("정답 제출"); // "정답 제출" 버튼 생성

        submitButton.addActionListener(new ActionListener() { // 버튼에 ActionListener 추가
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText(); // 입력된 텍스트 가져오기
                if (inputText.equals("교수님사랑합니다")) {
                    checkAnswerAndCalculateTime(); // 정답 확인 및 시간 측정 메소드 호출
                    JOptionPane.showMessageDialog(null, "정답입니다람쥐."); 
                } else {
                    JOptionPane.showMessageDialog(null, "틀렸습니다. 다시 시도해주세요."); // 오답 메시지 출력
                }
            }
        });

        inputPanel.add(label); // 라벨 추가
        inputPanel.add(textField); // 텍스트 입력 필드 추가
        inputPanel.add(submitButton); // "정답 제출" 버튼 추가
        add(inputPanel, BorderLayout.NORTH); // 입력 패널을 아래쪽에 추가

        startTime = System.currentTimeMillis(); // 게임 시작 시간 기록

        revalidate(); // 변경된 UI를 업데이트
    }

    // 정답 확인 및 시간 측정 메소드
    private void checkAnswerAndCalculateTime() {
        endTime = System.currentTimeMillis(); // 게임 종료 시간 기록

        long timeTaken = endTime - startTime; // 게임 소요 시간 계산

        timeInSeconds = (int) (timeTaken / 1000); // 밀리초를 초로 변환하여 저장
    }

    // 게임 시간(초)을 반환하는 메소드
    private int getScore() {
        return timeInSeconds; // 게임 시간(초) 반환
    }

    // 0부터 8까지의 숫자를 랜덤하게 배열하는 메소드
    private int[] generateRandomArray() {
        int[] randomNumbers = new int[9]; // 길이가 9인 정수 배열 생성
        boolean[] used = new boolean[9]; // 인덱스 사용 여부를 저장하는 boolean 배열
        int count = 0;

        while (count < 9) {
            int randomNumber = (int) (Math.random() * 9); // 0부터 8 사이의 랜덤한 숫자 생성
            if (!used[randomNumber]) {
                randomNumbers[count] = randomNumber; // 사용되지 않은 숫자를 배열에 저장
                used[randomNumber] = true; // 해당 인덱스를 사용했음을 표시
                count++;
            }
        }

        return randomNumbers; // 랜덤한 숫자 배열 반환
    }

    // 프로그램 실행을 위한 main 메소드
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MakePuzzle(); // MakePuzzle 객체 생성하여 UI 시작
            }
        });
    }
}

*/
