package sprint3implementation.timers;

/**
 * TimerListener interface defines a contract for classes that
 * want to be notified when a timer runs out.
 *
 * Implementers of this interface should provide
 * behavior for the {@code timeOut()} method,
 * which is called when the timer expires.
 *
 * @author Xin Thung
 */
public interface TimerListener {

    /**
     * This method is called when the timer reaches zero or times out.
     * Implementers should define what actions to take upon timeout.
     */
    void timeOut();

}
