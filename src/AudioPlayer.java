import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javax.swing.*;

public class AudioPlayer {

    // player object plays mp3 data from an input stream
    private static Player player;

    public static JPanel addAudioBox(String filePath) {
        JPanel audioBox = new JPanel();
        JButton playButton = new JButton("Play song");

        playButton.addActionListener(e -> {
            // if audio is currently playing, stop playing
            if (player != null) {
                player.close();
            }

            // runs audio on a separate thread
            new Thread(() -> {
                try (FileInputStream fis = new FileInputStream("docs/audio_files/" + filePath);
                    BufferedInputStream bis = new BufferedInputStream(fis)) {
                    player = new Player(bis);
                    player.play();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        audioBox.add(playButton);
        return audioBox;
    }
}