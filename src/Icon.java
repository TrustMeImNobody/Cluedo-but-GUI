import java.awt.*;

public class Icon {

    String text;
    Color color;

    /**
     * Determines what colour each characters token will display as
     *
     * @param text
     */
    public Icon(String text) {
        switch (text) {
            //Character Icon and start Position Setup
            case "Colonel Mustard":
                this.color = Color.orange;
                this.text = "M";
                break;
            case "Miss Scarlett":
                this.color = Color.red;
                this.text = "S";
                break;
            case "Mrs. White":
                this.color = Color.white;
                this.text = "W";
                break;
            case "Mr. Green":
                this.color = Color.green;
                this.text = "G";
                break;
            case "Mrs. Peacock":
                this.color = Color.blue;
                this.text = "Pe";
                break;
            case "Professor Plum":
                this.color = new Color(200, 0, 200);
                this.text = "Pl";
                break;
        }

    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }
}
