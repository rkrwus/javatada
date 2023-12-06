package teamproject;

import javax.sound.sampled.*;
import java.io.*;


public class BackgroundMusic {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        String musicFilePath = "image/videoplayback.wav"; // 음악 파일 경로 설정

        // musicFile 만듦
        File musicFile = new File(musicFilePath);

        // 음악 파일을 AudioInputStream으로 읽어들여야 오디오 파일을 읽고, 오디오 데이터를 Clip에 제공
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

        // AudioSystem으로부터 Clip을 만듦
        Clip clip = AudioSystem.getClip();

        // Clip이랑 audioStream 연결
        clip.open(audioStream);

        // 반복 재생 (노래 끊기지 않고 계속 실행)
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        // 음악 재생
        clip.start();

        // 프로그램이 종료될 때까지 재생을 유지하기 위해 대기
        Thread.sleep(clip.getMicrosecondLength() / 1000); // 재생 시간(ms)만큼 대기

        clip.stop(); //일시정지 
        clip.close(); // 아예 정리

        // 사용한 리소스를 정리하기 위해 AudioInputStream을 닫음
        audioStream.close();
    }
}

