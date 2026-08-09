package santorini.dto;

/**
 * Request body for resolving a pending choice (god card ability or skip card).
 */
public class ChoiceRequest {

    private boolean accepted;

    public ChoiceRequest() {}

    public ChoiceRequest(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }
}
