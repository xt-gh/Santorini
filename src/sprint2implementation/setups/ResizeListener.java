package sprint2implementation.setups;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

// Design pattern: Singleton.
// Singleton is used as the design pattern for the ResizeListener class because only one instance is needed for the window to be resized.
public class ResizeListener extends ComponentAdapter {

    private static ResizeListener instance;
    private JFrame windowFrame;
    private JComponent gameBoard;
    private int boardWidth;
    private int boardHeight;

    private ResizeListener(JFrame windowFrame, JComponent gameBoard, int width, int height){
        setWindowFrame(windowFrame);
        setWindowComponent(gameBoard);
        setWidth(width);
        setHeight(height);
    }

    public static ResizeListener getInstance(JFrame windowFrame, JComponent gameBoard, int width, int height) {
        if (instance == null) {
            instance = new ResizeListener(windowFrame, gameBoard, width, height);
        }
        return instance;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        int newWidth = (windowFrame.getWidth() - boardWidth)/2;
        int newHeight = (windowFrame.getHeight() - boardHeight)/2;
        gameBoard.setBounds(newWidth, newHeight, boardWidth, boardHeight);
    }

    private void setHeight(int newHeight) {
        boardHeight = newHeight;
    }

    private void setWidth(int newWidth) {
        boardWidth = newWidth;
    }

    private void setWindowComponent(JComponent newWindowComponent) {
        gameBoard = newWindowComponent;
    }

    private void setWindowFrame(JFrame newWindowFrame) {
        windowFrame = newWindowFrame;
    }
}

