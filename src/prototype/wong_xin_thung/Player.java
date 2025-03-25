package prototype.wong_xin_thung;


import java.awt.*;

public class Player {
    private String name;
    private String colourText;
    private Color color;

    public Player(String name, String colourText, Color color){
        this.name = name;
        this.colourText = colourText;
        this.color = color;
    }

    public Color getColor() {  // Add this method
        return color;
    }

    public String getName(){
        return name;
    }

    public String getColourText() {
        return colourText;
    }
}
