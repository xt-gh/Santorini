package sprint2implementation.setups;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

// Design pattern: Singleton.
// Singleton is used as the design pattern for the Window class because only one window instance is needed to set up the game.
public class Window
{
    private static Window instance;
    private JFrame newFrame;
    private Dimension currentScreenSize;

    private Window() {
        // Create a main window for Java GUI
        newFrame = new JFrame();

        // Determine the screen size and set the size of the frame
        currentScreenSize = getCurrentScreenSize();
        newFrame.setSize(currentScreenSize.width, currentScreenSize.height);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // action after the window is close
        newFrame.setTitle("Santorini");

        URL applicationIconPath = Window.class.getResource("/pics/frame_img_icon.jpg");
        ImageIcon applicationIcon = new ImageIcon(applicationIconPath);
        newFrame.setIconImage(applicationIcon.getImage());
    }

    public static Window getInstance() {
        if (instance == null) {
            instance = new Window();
        }
        return instance;
    }

    public JLabel setBgLabel(Dimension currentScreenSize)
    {
        // Scale the background and set the background of the frame
        URL bgImageIconPath = Window.class.getResource("/pics/ocean.jpg");
        Image bgImageIcon = new ImageIcon(bgImageIconPath).getImage();
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
