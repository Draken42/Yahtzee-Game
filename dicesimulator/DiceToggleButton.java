package dicesimulator;

import javax.swing.JToggleButton;
import java.awt.Graphics;

public class DiceToggleButton extends JToggleButton {
    public DiceToggleButton(Die d) {
        myDie = d;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        myDie.draw(g);
    }
    
    private final Die myDie;
}
