import javax.swing.*;
import java.awt.*;

public class Restart_UI extends JDialog {
    private JDialog f;
    private boolean reset = false;
    public boolean restart(){
        f = new JDialog(this,"Confirm Restart",true);
        f.setSize(300,150);
        f.setResizable(false);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        main.add(new JLabel("Do you want to restart?"),BorderLayout.PAGE_START);
        JPanel sub = new JPanel();
        sub.setLayout(new GridLayout(3,2));
        JButton yes = new JButton("Yes");
        yes.addActionListener(e -> {
            reset = true;
            f.setVisible(false);
        });
        JButton no = new JButton("No");
        no.addActionListener(e -> {
            reset = false;
            f.setVisible(false);
        });
        sub.add(new JLabel(""));
        sub.add(new JLabel(""));
        sub.add(yes);
        sub.add(no);
        sub.add(new JLabel(""));
        sub.add(new JLabel(""));
        main.add(sub);

        f.setContentPane(main);
        f.setVisible(true);

        return reset;
    }
}
