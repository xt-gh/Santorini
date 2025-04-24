package sprint2implementation.cards;

public class Demeter extends GodCard {
    public Demeter(){
        super("Demeter", "can build twice in a turn");
    }

    @Override
    public boolean canBuildTwice(){
        return true;
    }
}
