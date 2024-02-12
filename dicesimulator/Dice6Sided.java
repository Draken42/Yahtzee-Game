package dicesimulator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import shapeslib.Circle;
import shapeslib.Square;

public class Dice6Sided extends Die {
    public Dice6Sided() throws Exception {
        super(6);
    }
    /*
    public Dice6Sided(Random rng) {
        super(6);
    }
    */
    @Override
    public void draw(Graphics g) { 
        Graphics2D g2 = (Graphics2D) g;
        
        Square sq = new Square(10, 10, 60); // not scallable :^( 
        sq.setLineWidth(3.0f);
        sq.draw(g2);
  
        /*
        * xpos0   xpos1         xpos2        xpos3          xpos4(edge)
        *        .(node1)                    .(node2)
        *        .(node3)       .(node4)     .(node5)
        *        .(node6)                    .(node7)
        *
        */
        // divide the square's width by 4 to make a unit that is one fourth of the squares height/width
        // make nodes that are 1/1, 1/2, 1/3, 2/2, 3/1, 3/2, and 3/3 square unit to set where to put the die's dots  
        
        //x and y coordinates of sq
        int x = sq.getX();
        int y = sq.getY();
        
        //width and height of sq
        int sqWidth = sq.getWidth();
        int sqHeight = sq.getHeight();
        
        int radius = (int) (sq.getWidth() / 2.0 * SCALING_FACTOR);
        
        int unit = sqWidth / 4; // should be 15 because width is 60
        Circle node = new Circle(0, 0, 7);
        node.setFilled(true);
        node.setFillColor(Color.black);
        
        if (getValue() % 2 == 1) { // if odd.. // video 26, 26:00 
            node.setX(10 + unit * 2);
            node.setY(10 + unit * 2);
            node.draw(g2);
            if (getValue() >= 3) {
                node.setX(10 + 3 * unit);
                node.setY(10 + unit);
                node.draw(g2);
                node.setX(10 + unit);
                node.setY(10 + 3 * unit);
                node.draw(g2);
            }
            if (getValue() == 5) {
                node.setX(10 + unit);
                node.setY(10 + unit);
                node.draw(g2);
                node.setX(10 + 3 * unit);
                node.setY(10 + 3 * unit);
                node.draw(g2);
            }
        }
        else { // if even..
            if (getValue() >= 2) {
                node.setX(10 + 3 * unit);
                node.setY(10 + unit);
                node.draw(g2);
                node.setX(10 + unit);
                node.setY(10 + 3 * unit);
                node.draw(g2);
            }
            if (getValue() >= 4) {
                node.setX(10 + unit);
                node.setY(10 + unit);
                node.draw(g2);
                node.setX(10 + 3 * unit);
                node.setY(10 + 3 * unit);
                node.draw(g2);
            }
            if (getValue() == 6) {
                node.setX(10 + unit);
                node.setY(10 + 2 * unit);
                node.draw(g2);
                node.setX(10 + 3 * unit);
                node.setY(10 + 2 * unit);
                node.draw(g2);             
            }
        }                 
    }   
    private final double SCALING_FACTOR = 0.25;      
}
