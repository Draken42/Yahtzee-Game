package dicesimulator;

import java.awt.Graphics;
import java.util.Random;
public abstract class Die {
    /*public Die(Random rng, int numSides) {
        this.numSides = numSides;
        value = 0;
        rand = rng;
    }
    
    public Die(int sides) {
        numSides = sides;
        value = 0;
    }*/ //Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException: Cannot invoke "java.util.Random.nextInt(int)" because "this.rand" is null
    public Die() {
        value = 0;
        numSides = 1;  
    }

    public Die(int numSides) throws Exception {
        if (numSides > 0) {
            this.numSides = numSides;
            value = 0;      
        }
        else {
            throw new Exception("INVALID AMOUNT OF SIDES, BOZO!");  
        }
    }

    public int roll() {
        Random rand = new Random();
        setValue(rand.nextInt(getNumSides()) + 1);
        return value;
    }
    
    public int getNumSides() {
        return numSides;
    }
    
    public void setNumSides(int sides) {
        numSides = sides;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    public abstract void draw(Graphics g);
            
    protected int numSides;
    protected int value;
    private Random rand;
}
