import javax.swing.*;
import java.awt.*;

public class GUI {
    private JFrame f;
    private JPanel main;
    private JButton button1;
    private JTextField textField1;


    public static void main(String[] args) {
        GUI thing = new GUI();
        thing.runTheTest();
    }

    private void runTheTest() {
        f = new JFrame();
        f.setResizable(false);
        f.setSize(400, 450);
        setupUI();
        f.setContentPane(main);
        f.setVisible(true);
    }

    private void setupUI() {
        main = new JPanel();
        main.setLayout(new BorderLayout());
        JLabel instruct = new JLabel("Select Players");
        main.add(instruct,BorderLayout.PAGE_START);
        JPanel sub = new JPanel();
        sub.setLayout(new GridLayout(6,2));
        JRadioButton purpleRadButton = new JRadioButton("Professor Plum");
        sub.add(purpleRadButton);
        JTextField purpleName = new JTextField();
        purpleName.setPreferredSize(new Dimension(100,10));
        sub.add(purpleName);
        JRadioButton blueRadButton = new JRadioButton("Mrs Peacock");
        sub.add(blueRadButton);
        JTextField blueName = new JTextField();
        blueName.setPreferredSize(new Dimension(100,20));
        sub.add(blueName);
        JRadioButton greenRadButton = new JRadioButton("Rev Green");
        sub.add(greenRadButton);
        JTextField greenName = new JTextField();
        greenName.setPreferredSize(new Dimension(100,10));
        sub.add(greenName);
        JRadioButton redRadButton = new JRadioButton("Scarlett");
        sub.add(redRadButton);
        JTextField redName = new JTextField();
        redName.setPreferredSize(new Dimension(100,20));
        sub.add(redName);
        JRadioButton whiteRadButton = new JRadioButton("White");
        sub.add(whiteRadButton);
        JTextField whiteName = new JTextField();
        whiteName.setPreferredSize(new Dimension(100,20));
        sub.add(whiteName);
        JRadioButton mustardRadButton = new JRadioButton("Mustard");
        sub.add(mustardRadButton);
        JTextField mustardName = new JTextField();
        mustardName.setPreferredSize(new Dimension(100,20));
        sub.add(mustardName);
        main.add(sub);

    }
}
