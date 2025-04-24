package sprint2implementation;

public class Artemis extends GodCard{
    public Artemis(){
        super("Artemis", "can move twice in a turn");
    }

    @Override
    public boolean canMoveTwice(){
        return true;
    }
}
