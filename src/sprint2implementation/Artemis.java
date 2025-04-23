package sprint2implementation;

public class Artemis extends GodCard{
    public Artemis(){
        super("Artemis", "Allows player to build twice in a turn");
    }

    @Override
    public boolean canBuildTwice(){
        return true;
    }
}
