import java.awt.*;

public class Icon {

    int xPos;
    int yPos;
    int width = 15;
    int height = 15;
    String text;
    Color color;

    public Icon(String text){
        switch(text){
            //Character Icon and start Position Setup
            case "Colonel Mustard":
                this.xPos = 15;
                this.yPos = 309; //324
                this.color = Color.orange;
                this.text = "M";
                break;
            case "Miss Scarlett":
                this.xPos = 149; //195
                this.yPos = 435; //452
                this.color = Color.red;
                this.text = "S";
                break;
            case "Mrs. White":
                this.xPos = 186; //241
                this.yPos = 10; //16
                this.color = Color.white;
                this.text = "W";
                break;
            case "Mr. Green":
                this.xPos = 280; //357
                this.yPos = 10; //16
                this.color = Color.green;
                this.text = "G";
                break;
            case "Mrs. Peacock":
                this.xPos = 446; //566
                this.yPos = 115; //124
                this.color = Color.blue;
                this.text = "Pe";
                break;
            case "Professor Plum":
                this.xPos = 446; //566
                this.yPos = 345; //360
                this.color = Color.PINK;
                this.text = "Pl";
                break;
            //
            //Weapon Icon setup and start position
            //
            case "Candlestick":
                //this.xPos = 32;
                //this.yPos = 324;
                this.color = Color.gray;
                this.text = "C";
                break;
            case "Dagger":
                //this.xPos = 195;
                //this.yPos = 452;
                this.color = Color.gray;
                this.text = "D";
                break;
            case "Lead Pipe":
                //this.xPos = 241;
                //this.yPos = 16;
                this.color = Color.gray;
                this.text = "L";
                break;
            case "Revolver":
                //this.xPos = 357;
                //this.yPos = 16;
                this.color = Color.gray;
                this.text = "Re";
                break;
            case "Rope":
                //this.xPos = 566;
                //this.yPos = 124;
                this.color = Color.gray;
                this.text = "Ro";
                break;
            case "Spanner":
                //this.xPos = 566;
                //this.yPos = 360;
                this.color = Color.GRAY;
                this.text = "Sp";
                break;
        }

    }

    public void move(int dx, int dy){
        this.xPos += dx;
        this.yPos += dy;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }
}
