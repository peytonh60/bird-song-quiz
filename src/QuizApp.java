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
        JPanel panel = new JPanel(new BorderLayout());


        return null;
    }

    // array of questions where index 0 = audio file path and index 1 = answer
    private String[][] generateQuestions() {
        return null;
    }

}