package shapeslib;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Ellipse extends Shape2D{
    public Ellipse(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }
    
    public Ellipse()
    {
        super();
    }
    
    @Override
    public void draw(Graphics g)
    {
       Graphics2D g2;
       
       g2 = (Graphics2D) g;
       
       Ellipse2D.Double anEllipse;
       
      
       
        anEllipse = 
         new Ellipse2D.Double(super.getX(), super.getY(), super.getWidth(), 
                 super.getHeight());
       
        if (isFilled())
        {
            g2.setColor(getFillColor());
            g2.fill(anEllipse);
        }
       g2.setColor(getOutlineColor());
       g2.setStroke(new BasicStroke(getLineWidth()));

       g2.draw(anEllipse);    
      
    }
}
