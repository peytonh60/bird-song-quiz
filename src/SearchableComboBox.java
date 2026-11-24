import java.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

public class SearchableComboBox {

    public static JComboBox addAnswerBox() {

        // read bird names from answer file into list
        List<> options = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("docs/answers.csv"))) {
            String line;
            while ((br.readLine() = line) != null) {
                try {
                    options.add(line.split(",")[1]);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // creates combo box with names
        JComboBox<String> comboBox = new JComboBox<>(options.toArray(new String[0]));
        comboBox.setEditable(true);

        // adds text field to searchable box
        JTextField editor = new (JTextField) comboBox.getEditor().getEditorComponent();
        editor.setText("");

        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = editor.getText();
                List<String> filtered = new ArrayList<>();

                // filter matching bird names
                for (String option : options) {
                    if (item.toLowerCase().contains(input.toLowerCase())) {
                        filtered.add(item);
                    }
                }

                // updates drop down menu while saving text input
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(filered.toArray(new String[0]));
                comboBox.setModel(model);
                editor.setText(input);

                // shows pop up as long as text matches
                if (!filtered.isEmpty() && !input.isEmpty()) {
                    comboBox.showPopup();
                } else {
                    comboBox.hidePopup();
                }
            }
        });

        return comboBox;
    }
}