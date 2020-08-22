import javax.swing.*;
import java.awt.*;

//We will probably want to run the program from here and create a cluedo object in main
public class Test_GUI extends JFrame {

    public static void main(String[] args) {
        Test_GUI frame = new Test_GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//update this at some point to open a dialog box
        JMenuBar bar = createMenuBar();
        frame.setJMenuBar(bar);

        JPanel backgroundPanel = new JPanel();

        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(createMapPanel());
        backgroundPanel.add(createTextPanel());
        backgroundPanel.add(createButtonPanel());

        frame.add(backgroundPanel);

        frame.setVisible(true);
        //frame.pack();//Not necessary if we explicitly define the sizes
        frame.setSize(1000,1000);

        //frame.addWindowListener(new WindowAdapter() { //can probably adjust this code to open a dialog when the windo opens
        //				@Override
        //				public void windowOpened(WindowEvent e) {
        //					display.requestFocus();
        //				}
        //			});
    }

    public static JMenuBar createMenuBar(){
        JMenuBar menuBar =  new JMenuBar();
        JMenu menu =new JMenu("Menu");
        JMenuItem i1=new JMenuItem("Item 1");
        JMenuItem i2=new JMenuItem("Item 2");
        JMenuItem i3=new JMenuItem("Item 3");
        menu.add(i1); menu.add(i2); menu.add(i3);
        menuBar.add(menu);
        return menuBar;
    }

    public static JPanel createMapPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setSize(100,50);
        return panel;
    }

    public static JPanel createTextPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.blue);
        panel.setSize(100,50);
        return panel;
    }

    public static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        panel.setSize(100,50);
        return panel;
    }

}
