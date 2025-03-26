package prototype.pee_yee_peen;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizeListener extends ComponentAdapter {

    private JFrame windowFrame;
    private JComponent gameBoard;
    private int boardWidth;
    private int boardHeight;

    public ResizeListener(JFrame windowFrame, JComponent gameBoard, int width, int height){
        setWindowFrame(windowFrame);
        setWindowComponent(gameBoard);
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        int newWidth = (windowFrame.getWidth() - boardWidth)/2;
        int newHeight = (windowFrame.getHeight() - boardHeight)/2;
        gameBoard.setBounds(newWidth, newHeight, boardWidth, boardHeight);
    }

    public void setHeight(int newHeight) {
        boardHeight = newHeight;
    }

    public void setWidth(int newWidth) {
        boardWidth = newWidth;
    }

    public void setWindowComponent(JComponent newWindowComponent) {
        gameBoard = newWindowComponent;
    }

    public void setWindowFrame(JFrame newWindowFrame) {
        windowFrame = newWindowFrame;
    }
}

