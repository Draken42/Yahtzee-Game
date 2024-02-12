package shapeslib;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author velasquezda
 */
public class Rectangle extends Shape2D {
    public Rectangle(int x, int y, int width, int height)
    {
      super(x, y, width, height);
    }
    
    public Rectangle()
    {
        super();

    } 
    
    @Override
    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        
    
        java.awt.Rectangle box = new java.awt.Rectangle(getX(), getY(), 
                getWidth(), getHeight());
        
        if (isFilled())
        {
            g2.setColor(getFillColor());
            g2.fill(box);
        }
        g2.draw(box);   
        g2.setColor(getOutlineColor());
        g2.setStroke(new BasicStroke(getLineWidth()));
        
    }
}
