package sprint2implementation;

public class Demeter extends GodCard{
    public Demeter(){
        super("Demeter", "Allows player to move twice in a turn");
    }

    @Override
    public boolean canMoveTwice(){
        return true;
    }
}
