package shapeslib;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 *
 * @author velasquezda
 */
public class IsoTriangle extends Shape2D {
    
    public IsoTriangle(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }
    
    
    @Override
    public void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(getOutlineColor());
        g2.setStroke(new BasicStroke(getLineWidth()));
        
        Point2D.Float bottomLeftPoint, bottomRightPoint, topPoint;
        Line2D.Float leftLine, rightLine, bottomLine;
        
        bottomLeftPoint = 
            new Point2D.Float(this.getX(),this.getY() + getHeight());
        bottomRightPoint = 
            new Point2D.Float(getX()+ getWidth(), getY() + getHeight());
        topPoint = 
            new Point2D.Float(getX()+ (getWidth()/2), getY());
        
        leftLine = new Line2D.Float(topPoint, bottomLeftPoint);
        rightLine = new Line2D.Float(topPoint, bottomRightPoint);
        bottomLine = new Line2D.Float(bottomLeftPoint, bottomRightPoint);
        
        g2.draw(leftLine);
        g2.draw(rightLine);
        g2.draw(bottomLine);
        
    }
    
}