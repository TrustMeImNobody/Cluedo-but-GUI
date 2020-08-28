import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

class Accuse_Dialog extends JDialog {
    String suspect = null;
    String weapon = null;
    String location = null;
    public Cluedo game;

    public static final ArrayList<String> weapons = new ArrayList<String>(Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"));
    public static final ArrayList<String> rooms = new ArrayList<String>(Arrays.asList("Kitchen", "Ballroom", "Conservatory", "Billiard Room", "Library", "Study", "Hall", "Lounge", "Dining Room"));

    public Accuse_Dialog (Cluedo game){
        this.game=game;
    }

    public Accuse_Dialog setContents(ArrayList<String> list, String string) {
        Accuse_Dialog newPanel = new Accuse_Dialog(game);
        newPanel.getContentPane().setLayout(new BoxLayout(newPanel.getContentPane(), BoxLayout.Y_AXIS));

        JLabel title = new JLabel(string);
        newPanel.add(title);

        ButtonGroup button = new ButtonGroup();//you don't actually add this to the panel for some reason
        for (String object : list) {
            JRadioButton rButton = new JRadioButton(object);
            rButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (suspect == null){
                        suspect=rButton.getText();
                        newPanel.setContents(weapons, "Please choose a weapon");
                    }
                    else if(weapon == null){
                        System.out.println("1");
                        weapon=rButton.getText();
                        newPanel.setContents(rooms, "Please choose a room");
                    }
                    else if(location == null){
                        location=rButton.getText();
                        game.accusation(suspect,weapon,location);
                    }
                }
            });
            button.add(rButton);
            newPanel.add(rButton);
        }

        newPanel.setVisible(true);
        newPanel.pack();
        this.setVisible(false);
        return newPanel;
    }
}