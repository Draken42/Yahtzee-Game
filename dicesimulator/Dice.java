package dicesimulator;

public class Dice {
    public Dice() {
        numDice = 0;
        numSides = 0;
        myDice = null;
    }

    public Dice(int numDice, int numSides) throws Exception {
        this.numDice = numDice;
        this.numSides = numSides;
        //an array of class Die 
        this.myDice = new Die[numDice];
        
        for (int i = 0; i < numDice; i++) {
            myDice[i] = new Dice6Sided();
        }
    }

    //rolls the dice 
    public int roll() {
        int i, sum = 0;
        //loop an amount of times depending on the amount of dice and create each individual die
        for (i = 0; i < numDice; i++) {
            sum += myDice[i].roll();
        }
        return sum;
    }

    /* rolls a single die in the array
     *@param dieRoll the die in the array
     */
    public int rollDie(int dieNum) {
        return myDice[dieNum].roll();
    }
    
    /* gets the value of 1 die in the array
     * @param dieNum the individual die in the array
     * @return returns the value of the single die in the array
     */
    public int getDieValue(int dieNum){
        return myDice[dieNum].getValue();
    }
    
    public Die getDie(int dieNumber) {
        return myDice[dieNumber];
    }

    //adds all of the dice up 
    public int addEmUp() {
        int i, sum = 0;
        //loop an amount of times depending on the amount of dice and create each individual die
        for (i = 0; i < numDice; i++) {
            sum += getDieValue(i);
        }
        return sum;
    }
    
    public int[] buildFreqTable() {
        int i;
        int[] freqTable = new int[getNumSides() + 1];
        
        // Get the value of each die in the set of dice
        // Use the value to index the frequency table array and
        // increment the count for that roll in the table 
        for (i = 0; i < numDice; i++) {
            freqTable[getDieValue(i)]++;
        }
        return freqTable;
    }
    
    // check each die in the array and if it's equal to the value that you passed in, add it to the sum. Used for upperscore scoring. 
    public int addOfLikeValue(int value) {
        int sum = 0;
        int dieValue;
        
        for (int i = 0; i < numDice; i++) {
            dieValue = getDieValue(i);
            if (dieValue == value) {
                sum += dieValue;
            }
        }
        return sum;
    }
    
    //GETTERS AND SETTERS
    public int getNumDice() {
        return numDice;
    }

    public void setNumDice(int numDice) {
        this.numDice = numDice;
    }

    public int getNumSides() {
        return numSides;
    }

    public void setNumSides(int numSides) {
        this.numSides = numSides;
    }
    
    //private variables >:D
    private int numDice;
    private int numSides;
    private final Die[] myDice;
}
