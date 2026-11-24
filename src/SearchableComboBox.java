import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchableComboBox {

    public static JComboBox<String> addAnswerBox() {

        // read bird names from answer file into list
        List<String> options = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader("docs/answers.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
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
        JTextField editor = (JTextField) comboBox.getEditor().getEditorComponent();
        editor.setText("");

        editor.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                String input = editor.getText();
                List<String> filtered = new ArrayList<>();

                // filter matching bird names
                for (String option : options) {
                    if (option.toLowerCase().contains(input.toLowerCase())) {
                        filtered.add(option);
                    }
                }

                // updates drop down menu while saving text input
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(filtered.toArray(new String[0]));
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