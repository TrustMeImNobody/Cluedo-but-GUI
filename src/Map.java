
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

public class Map extends JPanel {

    public final static int windowWidth = 485;
    public final static int mapHeight = 458;
    public Cluedo game;

    public Map(Cluedo g) throws IOException {
        this.game = g;
    }


    public Dimension getPreferredSize() {
        return new Dimension(windowWidth, mapHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image board = null;
        try {
            board = ImageIO.read(new File("src/gameboard.PNG"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel boardLabel = new JLabel(new ImageIcon(board));
        g.drawImage(board, 0,0, windowWidth, mapHeight, null);

        for(Icon t: game.tokens){
            paintToken(g, t);
        }
    }

    public void paintToken(Graphics g, Icon t){
        g.setColor(t.color);
        g.fillRect(t.xPos,t.yPos,t.width,t.height);
        g.setColor(Color.BLACK);
        g.drawRect(t.xPos,t.yPos,t.width,t.height);
        g.drawString(t.text, t.xPos + t.width/4, (int) (t.yPos + t.height*0.75));
    }
}
