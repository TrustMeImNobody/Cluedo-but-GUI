
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Map extends JPanel {

    public final static int windowWidth = 500;
    public final static int mapHeight = 500;
    public Cluedo game;

    public Map(Cluedo g) {
        this.game = g;
    }


    public Dimension getPreferredSize() {
        return new Dimension(windowWidth, mapHeight);
    }


    /**
     * Draw the board on the panel by iterating through all the cells and setting their colour depending on the contents
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        ArrayList<ArrayList<Cell>> board = game.board.cells;
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                Cell current = board.get(row).get(col);
                if (current.player != null) {
                    Player player = current.player;
                    g.setColor(player.token.color);
                } else if (current.type.equals("XXX")) {
                    g.setColor(Color.black);
                } else if (current.type.equals("   ")) {
                    g.setColor(Color.lightGray);
                } else {
                    g.setColor(Color.black);
                }
                g.fillRect(30 + col * 18, row * 18, 18, 18);
                g.setColor(Color.BLACK);
                g.drawRect(30 + col * 18, row * 18, 18, 18);

            }
        }
        //draw the weapons on the board
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                Cell current = board.get(row).get(col);
                g.setFont(g.getFont().deriveFont(10.0f));
                g.drawString(current.weapon, 30 + col * 18, (row * 18) + 10);
            }
        }

        //draw the room names on the board
        g.setColor(Color.red);
        g.setFont(g.getFont().deriveFont(12.0f));
        g.drawString("Kitchen", 53, 66);
        g.drawString("Ballroom", 228, 90);
        g.drawString("Conservatory", 390, 66);
        g.drawString("Billiard Room", 390, 198);
        g.drawString("Library", 390, 306);
        g.drawString("Study", 390, 432);
        g.drawString("Hall", 223, 414);
        g.drawString("Lounge", 53, 414);
        g.drawString("Dining Room", 53, 230);

    }
}
