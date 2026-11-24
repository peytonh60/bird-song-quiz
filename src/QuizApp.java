import javax.swing.*;
import java.awt.*;

public class QuizApp {

    // number of questions
    private static final int n = 5;
    // creates a new array to represent questions correct (1 = correct, 0 = incorrect)
    private static final int[] correct = new int[n];

    // Card Layout manages different parges in the game
    private final CardLayout cards = new CardLayout();

    private final JPanel main = new JPanel(cards);
    private final JLabel resultLabel = new JLabel("- / 5");

    private int totalScore = 0;

    public QuizApp() {
        JFrame frame = new JFrame("Bird Song Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        // builds start page
        JPanel startPanel = new JPanel(new BorderLayout());
        JButton startButton = new JButton("Start Quiz");
        startButton.addActionListener(e -> {
            score = 0;
            cards.show(main, "start");
        });
        startPanel.add(startButton);

        // adds start panel to main panel of cards
        main.add(startPanel, "q0");

        // generates 5 questions
        String[][] questions = generateQuestions();

        // creates one page per question
        for (int i = 0; i < questions.length; i++) {
            main.add(buildQuestionPage(i), "q" + i);
        }

        // creates result page
        JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel resultLabel = new JLabel("Results " + score + " / " + n);
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        JButton playAgain = new JButton("Play Again");
        playAgain.addActionListener(e -> {
           cards.show(main, "start");
        });

        // adds result panel to card set
        main.add(resultPanel, "result");

        frame.add(main);
        frame.setVisible(true);
    }

    private JPanel buildQuestionPage(int index) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String answer = questions[index][1];

        // adds audio box
        panel.add(AudioPlayer.addAudioBox(questions[index][0]));

        // adds text drop box


        return null;
    }

    // array of questions where index 0 = audio file path and index 1 = answer
    private String[][] generateQuestions() {
        List<String> songs = new ArrayList<>();
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("answers.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                songs.add(line.split(",")[0]);
                names.add(line.split(",")[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] questions = new String[songs.size()][names.size()];
        for (int i = 0; i < questions.length; i++) {
            questions[i][0] = songs.get(i);
            questions[i][1] = names.get(i);
        }

        String[][] myRound = new String[n][2];
        for (int i = 0; i < n; i++) {
            int index = (int) (Math.random() * questions.length);
            myRound[i][0] = questions[index][0];
            myRound[i][1] = questions[index][1];
        }

        return myRound;
    }

}