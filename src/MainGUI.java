import java.swing.*;

public class MainGUI {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            QuizApp app = new QuizApp();
        });

    }
}