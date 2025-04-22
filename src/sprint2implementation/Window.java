package sprint2implementation;

import javax.swing.*;
import java.awt.*;

public class Window
{
    private JFrame newFrame;
    private Dimension currentScreenSize;

    public Window() {
        // Create a main window for Java GUI
        newFrame = new JFrame();

        // Determine the screen size and set the size of the frame
        currentScreenSize = getCurrentScreenSize();
        newFrame.setSize(currentScreenSize.width, currentScreenSize.height);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // action after the window is close
        newFrame.setTitle("Santorini");

        ImageIcon applicationIcon = new ImageIcon("src/sprint2implementation/pics/frame_img_icon.jpg");
        newFrame.setIconImage(applicationIcon.getImage());
    }

    public JLabel setBgLabel(Dimension currentScreenSize)
    {
        // Scale the background and set the background of the frame
        Image bgImageIcon = new ImageIcon("src/sprint2implementation/pics/ocean.jpg").getImage();
        Image scaledBgImage = bgImageIcon.getScaledInstance(currentScreenSize.width, currentScreenSize.height, Image.SCALE_SMOOTH);
        ImageIcon scaledBgImageIcon = new ImageIcon(scaledBgImage);
        JLabel bgLabel = new JLabel(scaledBgImageIcon);
        return bgLabel;
    }

    public Dimension getCurrentScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public JFrame getNewFrame() {
        return newFrame;
    }
}
