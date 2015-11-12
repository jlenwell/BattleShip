/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author 373000836
 */
public class Ship {
    private int size;
    Ship(int _size)
    {
        size = _size;
    }
    public int getsize(){
        return size;
    }
    public static void drawShip(Graphics2D g,int xleft,int xtop,int yleft,int ytop)
    {
        g.setColor(Color.yellow);
        g.fillRect(xleft, xtop , yleft , ytop);
    }
}
