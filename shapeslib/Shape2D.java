package shapeslib;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;

/**
 *
 * @author velasquezda
 */
public abstract class Shape2D {
    public Shape2D(int x, int y, int width, int height,
                   Color fillColor, Color outlineColor,
                   boolean filled, float lineWidth)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.outlineColor = outlineColor;
        this.lineWidth = lineWidth;
        this.myStroke = new BasicStroke(lineWidth);
        this.filled = filled;
    }
    
    public Shape2D(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.fillColor = Color.white;
        this.outlineColor = Color.black;
        this.lineWidth = 1.0F;
        this.myStroke = new BasicStroke(lineWidth);
        this.filled = false;
    }
    public Shape2D()
    {
        x = 0;
        y = 0;
        width =0;
        height = 0;
        fillColor = Color.white;
        filled = false;
        outlineColor = Color.black;
        lineWidth = 1.0F;
        myStroke = new BasicStroke(lineWidth);
    }
    
    public abstract void draw(Graphics g);
   
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * @return the outlineColor
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * @param outlineColor the outlineColor to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    /**
     * @return the fillColor
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @param fillColor the fillColor to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the filled
     */
    public boolean isFilled() {
        return filled;
    }

    /**
     * @param filled the filled to set
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /**
     * @return the line width
     */
    public float getLineWidth() {
        return lineWidth;
    }

    /**
     * @param lineWidth the myBasicStroke to set
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        this.myStroke = new BasicStroke(lineWidth);
    }
    
    private int x, y, width, height;
    private Color outlineColor, fillColor;
    private boolean filled;
    private float lineWidth;
    private BasicStroke myStroke;
}