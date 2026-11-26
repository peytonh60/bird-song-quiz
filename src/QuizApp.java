import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class QuizApp {

    // number of questions
    private static final int n = 5;

    private String[][] questions;

    // Card Layout manages different parges in the game
    private final CardLayout cards = new CardLayout();

    private int score = 0;

    private final JPanel main = new JPanel(cards);
    private final JLabel resultLabel = new JLabel("Score: " + score + " / " + n);

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
            cards.show(main, "q0");
        });
        startPanel.add(startButton);

        // adds start panel to main panel of cards
        main.add(startPanel, "start");

        play();

        frame.add(main);
        frame.setVisible(true);
    }

    private void play() {

        // generates 5 questions
        questions = generateQuestions();

        // creates one page per question
        for (int i = 0; i < questions.length; i++) {
            main.add(buildQuestionPage(i), "q" + i);
        }

        // creates result page
        JPanel resultPanel = new JPanel(new BorderLayout(10, 10));
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        JButton playAgain = new JButton("Play Again");
        playAgain.addActionListener(e -> {
            cards.show(main, "start");
            play();
        });

        resultPanel.add(resultLabel, BorderLayout.CENTER);
        resultPanel.add(playAgain, BorderLayout.SOUTH);

        // adds result panel to card set
        main.add(resultPanel, "result");
    }
    private JPanel buildQuestionPage(int index) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String answer = questions[index][1];

        // adds correct/incorrect label
        JLabel correctLabel = new JLabel("");

        // adds text drop box
        JComboBox<String> answerBox = SearchableComboBox.addAnswerBox();

        // adds submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setEnabled(true);

        submitButton.addActionListener(e -> {

            String guess = String.valueOf(answerBox.getEditor().getItem()).trim();

            if (guess.equalsIgnoreCase(answer.trim())) {
                score++;
                correctLabel.setText("Correct!");
            } else {
                correctLabel.setText("Incorrect. Answer is " + answer);
            }

            // pauses submit button to show feedback
            submitButton.setEnabled(false);

            // uses swing timer to wait for feedback
            Timer timer = new Timer(1500, ev -> goToNext(index));
            timer.setRepeats(false);
            timer.start();
        });

        // adds questions
        panel.add(AudioPlayer.addAudioBox(questions[index][0]), BorderLayout.NORTH);

        // creates panel for text box and submit button
        JPanel responsePanel = new JPanel();

        responsePanel.add(answerBox);
        responsePanel.add(submitButton);

        panel.add(responsePanel, BorderLayout.CENTER);

        panel.add(correctLabel, BorderLayout.SOUTH);

        return panel;
    }

    // advances to next card
    private void goToNext(int index) {
        int next = index + 1;
        if (next < questions.length) {
            cards.show(main, "q" + next);
        } else {
            resultLabel.setText(score + " / " + questions.length);
            cards.show(main, "result");
        }
    }

    // array of questions where index 0 = audio file path and index 1 = answer
    private String[][] generateQuestions() {
        List<String> songs = new ArrayList<>();
        List<String> names = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("docs/answers.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                songs.add(line.split(",")[0]);
                names.add(line.split(",")[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] questions = new String[songs.size()][2];
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