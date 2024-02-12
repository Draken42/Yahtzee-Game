/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shapeslib;

/**
 *
 * @author velasquezda
 */
public class Circle extends Ellipse{

    public Circle(int x, int y, int radius) {
        super(x - radius, y - radius, radius * 2, radius * 2);
        this.radius = radius;
    }

    public Circle() {
        super();
        radius = 0;
    }
    
    @Override
    public void setX(int x)
    {
        super.setX(x - radius);
    }
    
    @Override
    public void setY(int y)
    {
        super.setY(y - radius);
    }
    
    @Override
    public int getX()
    {
        return super.getX() + radius;
    }
    
    @Override
    public int getY()
    {
        return super.getY() + radius;
    }
    private int radius;
}
