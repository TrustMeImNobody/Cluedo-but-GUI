import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {
    private JFrame f;
    private JPanel main;


    public static void main(String[] args) {
        GUI thing = new GUI();
        thing.runTheTest();
    }

    private void runTheTest() {
        f = new JFrame();
        f.setResizable(false);
        f.setSize(400, 450);
        starterWindow();
        f.setContentPane(main);
        f.setVisible(true);
    }

    JTextField purpleName,blueName,greenName,redName,whiteName,mustardName;
    ArrayList<JTextField> fields = new ArrayList<>();
    private void starterWindow() {
        main = new JPanel();
        main.setLayout(new BorderLayout());
        JLabel instruct = new JLabel("Select Players");
        main.add(instruct,BorderLayout.PAGE_START);
        JPanel sub = new JPanel();
        sub.setLayout(new GridLayout(6,2));

        JRadioButton purpleRadButton = new JRadioButton("Professor Plum");
        purpleRadButton.addActionListener(e -> {
            if(purpleRadButton.isSelected()){purpleName.setEditable(true);}
            else {purpleName.setEditable(false);}
        });
        sub.add(purpleRadButton);
        purpleName = new JTextField();
        purpleName.setPreferredSize(new Dimension(100,10));
        purpleName.setEditable(false);
        sub.add(purpleName);

        JRadioButton blueRadButton = new JRadioButton("Mrs. Peacock");
        blueRadButton.addActionListener(e -> {
            if(blueRadButton.isSelected()){blueName.setEditable(true);}
            else {blueName.setEditable(false);}
        });
        sub.add(blueRadButton);
        blueName = new JTextField();
        blueName.setPreferredSize(new Dimension(100,20));
        blueName.setEditable(false);
        sub.add(blueName);

        JRadioButton greenRadButton = new JRadioButton("Mr. Green");
        greenRadButton.addActionListener(e -> {
            if(greenRadButton.isSelected()){greenName.setEditable(true);}
            else {greenName.setEditable(false);}
        });
        sub.add(greenRadButton);
        greenName = new JTextField();
        greenName.setPreferredSize(new Dimension(100,10));
        greenName.setEditable(false);
        sub.add(greenName);

        JRadioButton redRadButton = new JRadioButton("Miss Scarlett");
        redRadButton.addActionListener(e -> {
            if(redRadButton.isSelected()){redName.setEditable(true);}
            else {redName.setEditable(false);}
        });
        sub.add(redRadButton);
        redName = new JTextField();
        redName.setPreferredSize(new Dimension(100,20));
        redName.setEditable(false);
        sub.add(redName);

        JRadioButton whiteRadButton = new JRadioButton("Mrs. White");
        whiteRadButton.addActionListener(e -> {
            if(whiteRadButton.isSelected()){whiteName.setEditable(true);}
            else {whiteName.setEditable(false);}
        });
        sub.add(whiteRadButton);
        whiteName = new JTextField();
        whiteName.setPreferredSize(new Dimension(100,20));
        whiteName.setEditable(false);
        sub.add(whiteName);

        JRadioButton mustardRadButton = new JRadioButton("Colonel Mustard");
        mustardRadButton.addActionListener(e -> {
            if(mustardRadButton.isSelected()){mustardName.setEditable(true);}
            else {mustardName.setEditable(false);}
        });
        sub.add(mustardRadButton);
        mustardName = new JTextField();
        mustardName.setPreferredSize(new Dimension(100,20));
        mustardName.setEditable(false);
        sub.add(mustardName);
        JButton done = new JButton("Done");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int count = 0;
                for(JTextField f:fields){
                    if(f.isEditable()){
                        count++;
                        if(f.getText().equals("")){
                            JOptionPane.showMessageDialog(null,"Please type a name for all active players");
                            return;
                        }
                    }
                }
                if(count<3){
                    JOptionPane.showMessageDialog(null,"You need at least 3 players");
                    return;
                }
                System.out.println("The players are: ");
                if(purpleRadButton.isSelected()){System.out.println(purpleName.getText()+" as Plum");}
                if(blueRadButton.isSelected()){System.out.println("Peacock "+blueName.getText());}
                if(greenRadButton.isSelected()){System.out.println("Green "+greenName.getText());}
                if(whiteRadButton.isSelected()){System.out.println("White "+whiteName.getText());}
                if(redRadButton.isSelected()){System.out.println("Scarlett "+redName.getText());}
                if(mustardRadButton.isSelected()){System.out.println("Mustard "+mustardName.getText());}
            }
        });
        main.add(done,BorderLayout.PAGE_END);
        main.add(sub);

        fields.add(purpleName);
        fields.add(blueName);
        fields.add(greenName);
        fields.add(whiteName);
        fields.add(redName);
        fields.add(mustardName);
    }
}
