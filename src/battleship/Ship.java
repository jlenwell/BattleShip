/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

enum Type{
    AirCraftCarrier,BattleCarrier,StandardShip,Floater,Pontoon,Hit,Miss
}
public class Ship {
    private int size;
    Ship(int _size)
    {
        size = _size;
    }
    public void draw(int ytop, int xleft, int hieght,int width){
        
    }
    public int getsize(){
        return size;
    }
}
