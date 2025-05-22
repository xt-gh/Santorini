package sprint3implementation.timers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerTimer {
    private final int totalSeconds = 30;   // 5 minutes
    private int remainingSeconds;
    private Timer swingTimer;
    private JLabel timerLabel;
    private boolean isRunning = false;
    private TimerListener timerListener;

    public PlayerTimer(JLabel timerLabel, TimerListener listener){
        this.timerLabel = timerLabel;
        this.timerListener = listener;
        this.remainingSeconds = totalSeconds;
        updateLabel();
        swingTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (remainingSeconds > 0) {
                    remainingSeconds--;
                    updateLabel();
                    if (remainingSeconds == 0) {
                        swingTimer.stop();
                        timeOut();
                    }
                }
            }
        });
    }




        private void updateLabel() {
            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;
            timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
        }

        private void timeOut() {
//            JOptionPane.showMessageDialog(null, "Time's up! You loses.");
            if(timerListener != null){
                timerListener.onTimeOut();
            }
        }

        public void start() {
            if (!isRunning && remainingSeconds > 0) {
                swingTimer.start();
                isRunning = true;
            }
        }

        public void pause() {
            swingTimer.stop();
            isRunning = false;
        }

        public void reset(){
            swingTimer.stop();
            remainingSeconds = totalSeconds;
            updateLabel();
            isRunning = false;

            swingTimer.start();
        }

        public int getRemainingSeconds() {
            return remainingSeconds;
        }

        public interface TimerListener{
            void onTimeOut();
        }



}
