/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;



public class Ship {
    enum Type{
    AirCraftCarrier,BattleCarrier,StandardShip,Floater,Pontoon,Miss
    }
    private Type type;
    private boolean hits[];
    private int size;
    Ship(int _size, Type _type)
    { 
        type = _type;
        size = _size;
        hits = new boolean[size];
    }
    public void draw(int ytop, int xleft, int hieght,int width){
        
    }
    public int getsize(){
        return size;
    }
    public Type getType()
    {
        return(type);
    }
    public boolean getHits(){
        return hits[size];
    }
}
