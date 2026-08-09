package santorini.dto;

import java.util.List;

/**
 * DTO representing a player.
 */
public class PlayerDTO {

    private int playerIndex;
    private String name;
    private String godCardName;
    private String godCardDescription;
    private List<WorkerDTO> workers;
    private boolean hasSkipCard;
    private boolean skipCardUsed;

    public PlayerDTO() {}

    // Getters and Setters

    public int getPlayerIndex() { return playerIndex; }
    public void setPlayerIndex(int playerIndex) { this.playerIndex = playerIndex; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGodCardName() { return godCardName; }
    public void setGodCardName(String godCardName) { this.godCardName = godCardName; }

    public String getGodCardDescription() { return godCardDescription; }
    public void setGodCardDescription(String godCardDescription) { this.godCardDescription = godCardDescription; }

    public List<WorkerDTO> getWorkers() { return workers; }
    public void setWorkers(List<WorkerDTO> workers) { this.workers = workers; }

    public boolean isHasSkipCard() { return hasSkipCard; }
    public void setHasSkipCard(boolean hasSkipCard) { this.hasSkipCard = hasSkipCard; }

    public boolean isSkipCardUsed() { return skipCardUsed; }
    public void setSkipCardUsed(boolean skipCardUsed) { this.skipCardUsed = skipCardUsed; }
}
