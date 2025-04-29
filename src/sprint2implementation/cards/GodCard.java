package sprint2implementation.cards;

public abstract class GodCard implements SpecialAbility{
    protected String name;
    protected String description;

    public GodCard(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
