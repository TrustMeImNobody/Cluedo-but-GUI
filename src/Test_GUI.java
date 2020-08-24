import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//We will probably want to run the program from here and create a cluedo object in main
public class Test_GUI extends JFrame {

    public final static int windowHeight = 800;
    public final static int windowWidth = 600;

    public static void main(String[] args) {
        Test_GUI frame = new Test_GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//update this at some point to open a dialog box
        JMenuBar bar = createMenuBar();
        frame.setJMenuBar(bar);

        JPanel backgroundPanel = new JPanel();

        //backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel,BoxLayout.PAGE_AXIS));
        //GridBagConstraints gbc = new GridBagConstraints();

        JPanel map = createMapPanel();
//        gbc.gridwidth = 3;
//        gbc.gridheight = 2;
//        //gbc.ipady = windowHeight*2/3;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        //gbc.weightx = 0;
//        //gbc.weighty = 0.5;
//        backgroundPanel.add(map,gbc);
        backgroundPanel.add(map);

        JPanel interactPanel = new JPanel();
        interactPanel.setLayout(new BoxLayout(interactPanel,BoxLayout.LINE_AXIS));

        JPanel buttons = createButtonPanel();
//        gbc = new GridBagConstraints();
//        //gbc.ipady = windowHeight/3;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 1;
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        //gbc.weightx = 1;
//        //gbc.weighty = 1;
        interactPanel.add(buttons);//,gbc);

        JPanel text = createTextPanel();
//        gbc = new GridBagConstraints();
//        //gbc.ipady = windowHeight/3;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridwidth = 1;
//        gbc.gridx = 1;
//        gbc.gridy = 2;
//        //gbc.weightx = 1;
//        //gbc.weighty = 1;
        interactPanel.add(text);//,gbc);
        backgroundPanel.add(interactPanel);
        frame.add(backgroundPanel);

        frame.setVisible(true);
        //frame.pack();//Not necessary if we explicitly define the sizes
        frame.setSize(windowWidth,windowHeight);

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
        panel.setSize(windowWidth,windowHeight*2/3);
        Canvas c = new Canvas();
        c.setBackground(Color.green);
        c.setSize(windowWidth,windowHeight*2/3);
        panel.add(c);
        return panel;
    }

    public static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.blue);
        panel.setSize(windowWidth/2,windowHeight/3);
        panel.setLayout(new GridLayout(2,2));
        panel.add(new JPanel());
        panel.add(new JPanel());
        JPanel dpad = createDPad();
        dpad.setSize(windowWidth/4,windowHeight/6);
        dpad.setBackground(Color.black);
        panel.add(dpad);
        return panel;
    }

    public static JPanel createTextPanel() {
        JPanel panel = new JPanel();
        panel.setSize(windowWidth/2,windowHeight/3);
        panel.setBackground(Color.red);
        panel.setLayout(new GridLayout(2,2));
        return panel;
    }

    public static JPanel createDPad(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        JLabel empty = new JLabel("___");
        empty.setSize(30,30);
        JLabel up = new JLabel("_Up__");
        up.setSize(30,30);
        up.setHorizontalTextPosition(JLabel.CENTER);
        JLabel left = new JLabel("Left_");
        left.setSize(30,30);
        JLabel right = new JLabel("Right");
        right.setSize(30,30);
        JLabel down = new JLabel("Down_");
        down.setSize(30,30);

        panel.add(new JLabel("____"));
        panel.add(up);
        panel.add(new JLabel("____"));
        panel.add(left);
        panel.add(new JLabel("____"));
        panel.add(right);
        panel.add(new JLabel("____"));
        panel.add(down);
        panel.add(new JLabel("____"));

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {}
            @Override
            public void mousePressed(MouseEvent mouseEvent) {}
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                    int x = mouseEvent.getX();
                    int y = mouseEvent.getY();
                    System.out.println("x:"+x+" y:"+y);
                    if(y<25&&65<x&&x<105){System.out.println("DPAD UP");}
                    if(x<30&&30<y&&y<45){System.out.println("DPAD LEFT");}
                    if(130<x&&x<160&&30<y&&y<45){System.out.println("DPAD RIGHT");}
                    if(y>55&&65<x&&x<105){System.out.println("DPAD DOWN");}
                    }
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}
            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        });

        return panel;
    }

}


