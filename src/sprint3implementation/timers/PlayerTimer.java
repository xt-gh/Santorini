package sprint3implementation.timers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * PlayerTimer class controls a countdown timer for a player.
 * It shows time on a JLabel and notifies when time is up.
 */
public class PlayerTimer implements TimerListener{
    //    private final int totalSeconds = 5 * 60;   // 5 minutes
    private final int totalSeconds = 20;   // 10 seconds (for testing)
    private int remainingSeconds;
    private Timer timer;
    private JLabel timerLabel;
    private boolean isRunning = false;
    private String playerName;

    public PlayerTimer(JLabel timerLabel, String playerName){
        this.timerLabel = timerLabel;
        this.playerName = playerName;
        this.remainingSeconds = totalSeconds;
        updateLabel();

        //Swing Timer updates every 1 second(1000ms)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingSeconds > 0) {
                    remainingSeconds--;
                    updateLabel();

                    // if time reach 0, stop the timer and trigger timeout
                    if (remainingSeconds == 0) {
                        timer.stop();
                        timeOut();
                    }
                }
            }
        });
    }

    /**
     * Updates the JLabel with the current time in mm:ss format.
     */
    private void updateLabel() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
    }

    /**
     * This method is called when the time runs out.
     * It shows a message and exits the application.
     */
    @Override
    public void timeOut(){
        JOptionPane.showMessageDialog(
                null,
                playerName + " LOSE!! Time's up!",
                "Tournament Result",
                JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }

    /**
     * Starts the countdown if not already running.
     */
    public void start() {
        if (!isRunning && remainingSeconds > 0) {
            timer.start();
            isRunning = true;
        }
    }

    /**
     * Pauses the countdown.
     */
    public void pause() {
        timer.stop();
        isRunning = false;
    }

    /**
     * Resets the countdown to the initial time and starts again.
     */
    public void reset(){
        timer.stop();
        remainingSeconds = totalSeconds;
        updateLabel();
        isRunning = false;
        timer.start();
    }
}
