import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

//We will probably want to run the program from here and create a cluedo object in main
public class Test_GUI extends JFrame {

    public final static int windowHeight = 800;
    public final static int windowWidth = 600;

    public static void main(String[] args) throws IOException {
        Test_GUI frame = new Test_GUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//update this at some point to open a dialog box
        JMenuBar bar = createMenuBar();
        frame.setJMenuBar(bar);

        JPanel backgroundPanel = new JPanel();

        backgroundPanel.setLayout(new BoxLayout(backgroundPanel,BoxLayout.PAGE_AXIS));

        JPanel map = createMapPanel();
        backgroundPanel.add(map);

        JPanel interactPanel = new JPanel();
        interactPanel.setLayout(new BoxLayout(interactPanel,BoxLayout.X_AXIS));

        JPanel controlPanel = createControlPanel();
        interactPanel.add(controlPanel);

        JPanel text = createTextPanel();
        interactPanel.add(text);
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
        JMenu menu =new JMenu("Game");
        JMenuItem i1=new JMenuItem("Restart Game");
        menu.add(i1);
        menuBar.add(menu);
        return menuBar;
    }

     public static JPanel createMapPanel() throws IOException {
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setSize(windowWidth,windowHeight*2/3);
        //Canvas c = new Canvas();
        //Image board = ImageIO.read(new File("gameboard.PNG"));
        //JLabel boardLabel = new JLabel(new ImageIcon(board));
       //c.setBackground(Color.green);
        //c.setSize(windowWidth,windowHeight*2/3);
        panel.add(new Map());
        return panel;
    }

    public static JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.blue);
        panel.setSize(windowWidth/2,windowHeight/3);
      //  panel.setLayout(new GridLayout(2,2));
        JPanel topPanel = createTopPanel();
        panel.add(topPanel);
        panel.add(new JPanel());

        JPanel bottomPanel = createBottomPanel();
        panel.add(bottomPanel);
        return panel;
    }

    public static JPanel createTopPanel() {
        JPanel panel = new JPanel();
        //panel.setSize(windowWidth/4,windowHeight/6);
        return panel;
    }

    public static JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));

        JPanel buttons = createButtonPanel();
        panel.add(buttons);

        JPanel dpad = createDPad();
        //dpad.setSize(windowWidth/8,windowHeight/6);
       // dpad.setBackground(Color.black);
        panel.add(dpad);
        return panel;
    }

    public static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        //panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setLayout(new GridLayout(2,1));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        panel.add(createDicePanel());
        panel.add(createAccusePanel());
        //panel.add(suggest);
        return panel;
    }

    public static JPanel createDicePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.add(new JButton("Roll Dice"));
        JLabel diceOutput = new JLabel("Dice output\n here");
        panel.add(diceOutput);
        return panel;
    }

    public static JPanel createAccusePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.add(new JLabel("     "));
        JButton accuse = new JButton("Accuse");
        accuse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Testing player display update");
                updatePlayerDisplay(createTestPlayer());
            }
        });
        JButton suggest = new JButton("Suggest");
        panel.add(accuse);

        return panel;
    }

    private static ArrayList<JTextPane> displayHand;
    private static JLabel displayPlayer;
    public static JPanel createTextPanel(){
        JPanel panel = new JPanel();
        panel.setSize(windowWidth/2,windowHeight/3);
        //panel.setBackground(Color.red);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        displayPlayer = new JLabel();
        displayPlayer.setText("Player here");
        panel.add(displayPlayer);
        panel.add(createCardPanel());
        return panel;
    }

    public static void updatePlayerDisplay(Player p){
        if(p==null||p.hand==null){throw new Error("Player broke");}
        displayPlayer.setText(p.character);
        for(JTextPane pane:displayHand){
            pane.setText("");
        }
        for(int i=0;i<p.hand.size();i++){
            System.out.println(p.hand.get(i));
            displayHand.get(i).setText(p.hand.get(i).name);
        }
    }

    public static JPanel createCardPanel(){
        displayHand = new ArrayList<>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,3));
        for(int i=0;i<6;i++){
            displayHand.add(new JTextPane());
        }
        for(JTextPane p:displayHand){
            p.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
            panel.add(p);
        }
        return panel;
    }

    public static JPanel createDPad(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        JLabel up = new JLabel("_Up__");
        //up.setSize(30,30);
        //up.setHorizontalTextPosition(JLabel.CENTER);
        JLabel left = new JLabel("Left_");
        //left.setSize(30,30);
        JLabel right = new JLabel("Right");
        //right.setSize(30,30);
        JLabel down = new JLabel("Down_");
        //down.setSize(30,30);

        panel.add(new JLabel("____"));
        panel.add(up);
        panel.add(new JLabel("____"));
        panel.add(left);
        panel.add(new JLabel("_____"));
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
                //todo adjust parameters for buttons once layout is sorted
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
        //panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,5,false));
        return panel;
    }

    public static Player createTestPlayer(){
        Player test = new Player(Cluedo.characters.get(0));
        test.addCardToHand(new Card(Card.Type.WEAPON,"card1"));
        test.addCardToHand(new Card(Card.Type.ROOM,"card2"));
        test.addCardToHand(new Card(Card.Type.CHARACTER,"card3"));
        test.addCardToHand(new Card(Card.Type.WEAPON,"card4"));
        test.addCardToHand(new Card(Card.Type.ROOM,"card5"));
        test.addCardToHand(new Card(Card.Type.CHARACTER,"card6"));
        return test;
    }
}


