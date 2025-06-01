package sprint3implementation.setups;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


/**
 * Singleton class responsible for setting up and managing the main game window.
 * Design Pattern: Singleton - only one instance of the Window class is needed to control the game window.
 *
 * @author Yee Peen
 * Modified by: Tiffany
 */
public class Window {

    /**
     * The single instance of the Window class
     */
    private static Window instance;

    /**
     * The main JFrame used as the game window.
     */
    private JFrame newFrame;

    /**
     * The dimensions of the user's screen.
     */
    private Dimension currentScreenSize;

    /**
     * Private constructor that initializes the main window with appropriate size, title, and icon.
     */
    private Window() {
        // Create a main window for Java GUI
        newFrame = new JFrame();

        // Determine the screen size and set the size of the frame
        currentScreenSize = getCurrentScreenSize();
        newFrame.setSize(currentScreenSize.width, currentScreenSize.height);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // action after the window is close
        newFrame.setTitle("Santorini Game");

        URL applicationIconPath = Window.class.getResource("/pics/frame_img_icon.jpg");
        ImageIcon applicationIcon = new ImageIcon(applicationIconPath);
        newFrame.setIconImage(applicationIcon.getImage());
    }

    /**
     * Returns the singleton instance of the Window class, creating it if necessary.
     *
     * @return the single Window instance
     */
    public static Window getInstance() {
        if (instance == null) {
            instance = new Window();
        }
        return instance;
    }

    /**
     * Creates and returns a JLabel with the scaled background image set to the screen size.
     *
     * @param currentScreenSize the size to scale the background image to
     * @return a JLabel containing the scaled background image
     */
    public JLabel setBgLabel(Dimension currentScreenSize)
    {
        // Scale the background and set the background of the frame
        URL bgImageIconPath = Window.class.getResource("/pics/background.jpg");
        Image bgImageIcon = new ImageIcon(bgImageIconPath).getImage();
        Image scaledBgImage = bgImageIcon.getScaledInstance(currentScreenSize.width, currentScreenSize.height, Image.SCALE_SMOOTH);
        ImageIcon scaledBgImageIcon = new ImageIcon(scaledBgImage);
        JLabel bgLabel = new JLabel(scaledBgImageIcon);
        return bgLabel;
    }

    /**
     * Gets the current screen size of the user's display.
     *
     * @return the screen size as a Dimension object
     */
    public Dimension getCurrentScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Gets the main JFrame representing the game window.
     *
     * @return the game window frame
     */
    public JFrame getNewFrame() {
        return newFrame;
    }
}
