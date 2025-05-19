package sprint3implementation.setups;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * A singleton listener that resizes the game board component
 * within the manin window frame when the frame is resizes.
 * Design Patter: Singleton. Only one instance of this listener is needed for the application window.
 */
public class ResizeListener extends ComponentAdapter {

    /**
     * The single instance of the ResizeListener.
     */
    private static ResizeListener instance;

    /**
     * The main window frame of the application.
     */
    private JFrame windowFrame;

    /**
     * The game board component that needs to be repositioned on resize.
     */
    private JComponent gameBoard;

    /**
     * The original width of the game board.
     */
    private int boardWidth;

    /**
     * The original height of the game board.
     */
    private int boardHeight;


    /**
     * Private constructor to initialize the ResizeListener
     * with required component and dimension.
     *
     * @param windowFrame the main application window
     * @param gameBoard the component to be resized and repositioned
     * @param width the original width of the game board
     * @param height the original height of the game board
     */
    private ResizeListener(JFrame windowFrame, JComponent gameBoard, int width, int height){
        setWindowFrame(windowFrame);
        setWindowComponent(gameBoard);
        setWidth(width);
        setHeight(height);
    }

    /**
     * Returns the singleton instance of ResizeListener, creating it if necessary.

     * @param windowFrame the main application window
     * @param gameBoard the component to be resized and repositioned
     * @param width the original width of the game board
     * @param height the original height of the game board
     *
     * @return the singleton instance of ResizeListener
     */
    public static ResizeListener getInstance(JFrame windowFrame, JComponent gameBoard, int width, int height) {
        if (instance == null) {
            instance = new ResizeListener(windowFrame, gameBoard, width, height);
        }
        return instance;
    }


    /**
     * Automatically called when the window is resized.
     * Recalculates the position of the game board to remain centered.
     *
     * @param e the resize event
     */
    @Override
    public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        int newWidth = (windowFrame.getWidth() - boardWidth)/2;
        int newHeight = (windowFrame.getHeight() - boardHeight)/2;
        gameBoard.setBounds(newWidth, newHeight, boardWidth, boardHeight);
    }

    /**
     * Sets the new height of the game board.
     *
     * @param newHeight the new height in pixels
     */
    public void setHeight(int newHeight) {
        boardHeight = newHeight;
    }

    /**
     * Sets the new width of the game board.
     *
     * @param newWidth the new width in pixels
     */
    public void setWidth(int newWidth) {
        boardWidth = newWidth;
    }

    /**
     * Sets the game board component to be resized and repositioned.
     *
     * @param newWindowComponent the new game board component
     */
    public void setWindowComponent(JComponent newWindowComponent) {
        gameBoard = newWindowComponent;
    }

    public void setWindowFrame(JFrame newWindowFrame) {
        windowFrame = newWindowFrame;
    }
}

