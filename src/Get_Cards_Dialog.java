import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

class Get_Cards_Dialog extends JDialog {
    String suspect = "";
    String weapon = "";
    String location = "";
    public Cluedo game;

    public static final ArrayList<String> weapons = new ArrayList<String>(Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"));
    public static final ArrayList<String> rooms = new ArrayList<String>(Arrays.asList("Kitchen", "Ballroom", "Conservatory", "Billiard Room", "Library", "Study", "Hall", "Lounge", "Dining Room"));

    public Get_Cards_Dialog(Cluedo game) {
        this.game = game;
    }

    public void setContents(ArrayList<String> list, String string, boolean accuse) {//maybe try updating the content panel? or making a new constructor to pass stuff along
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        Get_Cards_Dialog dialog = this;
        JLabel title = new JLabel(string);
        newPanel.add(title);

        ButtonGroup button = new ButtonGroup();//you don't actually add this to the panel for some reason
        for (String object : list) {
            JRadioButton rButton = new JRadioButton(object);
            rButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (suspect.equals("")) {
                        suspect = rButton.getText();
                        dialog.setContents(weapons, "Please choose a weapon", accuse);
                    } else if (weapon.equals("")) {
                        weapon = rButton.getText();
                        if (accuse) {
                            dialog.setContents(rooms, "Please choose a room", accuse);
                        } else {
                            game.suggestion(suspect, weapon);
                        }
                    } else if (location.equals("")) {
                        location = rButton.getText();
                        dialog.setVisible(false);
                        game.accusation(suspect, weapon, location);
                    }
                }
            });
            button.add(rButton);
            newPanel.add(rButton);
        }

        newPanel.setVisible(true);
        this.setContentPane(newPanel);
        this.setVisible(true);
        this.pack();

        //this.setVisible(false);
    }
}