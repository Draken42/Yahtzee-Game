package dicesimulator;

public class DiceSimulator {
    public final static int STRENGTH = 0;
    public final static int INTELLIGENCE = 1;
    public final static int CONSTITUTION = 2;
    public final static int CHARISMA = 3;
    public final static int DEXTERITY = 4;
    public final static int WISDOM = 5;
    
    public final static int NUM_ATTRIBUTES = 6;
     
    private static String translateAttribute(int et) {
        String returnValue;
            
        switch(et) {
            case STRENGTH:
                returnValue = "Strength";
                break;
            case INTELLIGENCE: 
                returnValue = "Intelligence";
                break;
            case CONSTITUTION: 
                returnValue = "Constitution";
                break;
            case CHARISMA: 
                returnValue = "Charisma";
                break;
            case DEXTERITY: 
                returnValue = "Dexterity";
                break;
            case WISDOM: 
                returnValue = "Wisdom";
                break;
            default:
                returnValue = "You inputed an invalid attribute, BOZO!";
                break;
        }
        return returnValue;      
    }
    
    public static void main(String[] args) {
        // random
    }
}
