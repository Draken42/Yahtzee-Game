package dicesimulator;


public class GameModel {
    public static final int BONUS = 35;
    public static final int NUM_UPPER_SCORE_CATS = 6;
    public static final int NUM_LOWER_SCORE_CATS = 7;
    public static final int MAX_NUM_TURNS = NUM_UPPER_SCORE_CATS + NUM_LOWER_SCORE_CATS;
    
    public GameModel() {
        upperScoreCategories = new int[NUM_UPPER_SCORE_CATS + 1];
        lowerScoreCategories = new int[NUM_LOWER_SCORE_CATS];
        
        usedUpperScoreCategories = new boolean[NUM_UPPER_SCORE_CATS + 1];
        usedLowerScoreCategories = new boolean[NUM_LOWER_SCORE_CATS];
        
        currentTurnNum = 1;
    }
    
    public boolean is3ofaKind(Dice myDice) {
        int freqTable[];
        freqTable = myDice.buildFreqTable();
        
        boolean found3ofaKind = false;

        int i;
        //loop through the frequency table 
        for (i = 0; i <= myDice.getNumSides(); i++) {
            if (freqTable[i] >= 3) { //if the frequency table index i has a frequency equal to or greater than 3
                found3ofaKind = true;
            }
        }
        return found3ofaKind;
    }
    
    public boolean is4ofaKind(Dice myDice) {
        int freqTable[];
        freqTable = myDice.buildFreqTable();
        boolean found4ofaKind = false;

        int i;
        //loop through the frequency table 
        for (i = 0; i <= myDice.getNumSides(); i++) {
            if (freqTable[i] >= 4) { //if the frequency table index i has a frequency equal to or greater than 3
                found4ofaKind = true;
            }
        }
        return found4ofaKind;
    }
    
    public boolean isFullHouse(Dice myDice) {
        boolean foundThree = false;
        boolean foundTwo = false;
        
        int[] freqTable;
        freqTable = myDice.buildFreqTable();
        for (int i = 1; i <= myDice.getNumSides(); i++) {
            if (freqTable[i] == 3) {
                foundThree = true;
            }
            if (freqTable[i] == 2) {
                foundTwo = true;
            }
        }
        return foundThree && foundTwo;
    }
    
    public boolean isSmallStraight(Dice myDice) {
        boolean returnValue;
        int[] freqTable;
        freqTable = myDice.buildFreqTable();
        for (int i = 1; i <= myDice.getNumSides(); i++) {
            if (freqTable[i] > 1) {
                freqTable[i] = 1; // if there are more than 1 frequency of a number, set it to 1 
            }
        }
        if (freqTable[3] == 1 && freqTable[4] == 1) {
            if(freqTable[1] == 1 && freqTable[2] == 1) {
                returnValue = true;
            }
            else if(freqTable[2] == 1 && freqTable[5] == 1) {
                returnValue = true;
            }
            else returnValue = freqTable[5] == 1 && freqTable[6] == 1;
        } else {
            returnValue = false;
        }
        
        
        return returnValue;
    }
    
    public boolean isLargeStraight(Dice myDice) {
        int[] freqTable;
        freqTable = myDice.buildFreqTable();

        return freqTable[2] == 1 && freqTable[3] == 1 && freqTable[4] == 1 && freqTable[5] == 1;
    }

    //sets the index of the Lower Score Categories passed in to the score passed in
    public void setLowerScoreCat(int index, int score) {
        lowerScoreCategories[index] = score;
    }
    
    //sets the index of the Upper Score Categories passed in to the score passed in
    public void setUpperScoreCat(int index, int score) {
        upperScoreCategories[index] = score;
    }    
    
    //sets the index of the Lower Score Categories passed in to the boolean value passed in
    public void setUsedLowerScoreCat(int index, boolean used) {
        usedLowerScoreCategories[index] = used;
    }
    
    //sets the index of the Upper Score Categories passed in to the boolean value passed in
    public void setUsedUpperScoreCat(int index, boolean used) {
        usedUpperScoreCategories[index] = used;
    }
    
    public boolean getUsedUpperScoringCatState(int i) {
        return usedUpperScoreCategories[i];
    }
    
    public boolean getUsedLowerScoringCatState(int i) {
        return usedLowerScoreCategories[i];
    }

    public int getSumUpperScores() {
        return sumUpperScores;
    }

    public int getSumLowerScores() {
        return sumLowerScores;
    }

    public int getBonus() {
        return bonus;
    }

    public int getGrandTotal() {
        return grandTotal;
    }
    
    public void resetTurn() {
        currentTurnNum = 1;
    }
    
    public int addEmUp(Dice myDice) {
        int i;
        int sum = 0;
        
        for (i = 0; i < myDice.getNumDice(); i++) {
            sum += myDice.getDieValue(i);
        }
        return sum;
    }
    
    public int getCurrentTurnNum() {
        return currentTurnNum;
    }
    
    public void nextTurn() {
        currentTurnNum++;
    }
    
    public void clearAllUpperScoringCats() {
        for(int i = 1; i <= NUM_UPPER_SCORE_CATS; i++) {
            upperScoreCategories[i] = 0;
        }
    }
    
    public void clearAllLowerScoringCats() {
        for(int i = 0; i < NUM_LOWER_SCORE_CATS; i++) {
            lowerScoreCategories[i] = 0;
        }
    }
    
    public void clearUsedScoringCats() {
        int i;
        
        for(i = 1; i <= NUM_UPPER_SCORE_CATS; i++) {
            usedUpperScoreCategories[i] = false; // loop through the usedUpperScoreCategories array and set all the elements to false
        }
        
        for(i = 0; i < NUM_LOWER_SCORE_CATS; i++) {
            usedLowerScoreCategories[i] = false; // loop through the usedLowerScoreCategories array and set all the elements to false
        }
    }
    
    public boolean isOfAKind(Dice myDice, int kind) {
        int[] freqTable = myDice.buildFreqTable();
        
        boolean foundOfAKind = false;
        
        for (int i = 1; i <= myDice.getNumSides(); i++) {
            if (freqTable[i] >= kind) {
                foundOfAKind = true;
            }
        }
        
        return foundOfAKind;
    }    
    
    
    public void updateTotals() {
        bonus = 0;
        sumUpperScores = 0;
        sumLowerScores = 0;
        grandTotal = 0;
        
        int i;
        for (i = 1; i <= NUM_UPPER_SCORE_CATS; i++) {
            sumUpperScores += upperScoreCategories[i];// loop thru the amount of upper score categories there are and find the sum 
        }
        
        if (sumUpperScores > 63) { // if you're elligable to get a bonus
            bonus = 35;
        }
        sumUpperScores += bonus; // add the bonus to upper score sum 
        
        for (i = 0; i < NUM_LOWER_SCORE_CATS; i++) {
            sumLowerScores += lowerScoreCategories[i];// loop thru the amount of lower score categories there are and find the sum 
        }
        
        grandTotal = sumUpperScores + sumLowerScores; // add upper and lower scores to find the grand total 
    }
    
    
    
    private int[] upperScoreCategories;
    private int[] lowerScoreCategories;
    private boolean[] usedUpperScoreCategories;
    private boolean[] usedLowerScoreCategories;
    private int currentTurnNum;
    private int sumUpperScores;
    private int sumLowerScores;    
    private int bonus;
    private int grandTotal;
    
}
