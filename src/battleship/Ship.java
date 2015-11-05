/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;



public class Ship{
    public enum Type{
        AirCraftCarrier,BattleCarrier,StandardShip,Floater,Pontoon,Miss
    }
    public enum Direction{
        Up,Down,Right,Left
    }
    private Type type;
    private boolean hits[];
    private boolean sunk;
    private int size;
    private int spot;
    private Direction direction;
    Ship(int _spot, Type _type,Direction _direction)
    { 
        direction = _direction;
        type = _type;
        sunk = false;
        if(_type == Type.Miss)
            spot = 0;
        else
            spot = _spot;
        if(type == Type.AirCraftCarrier)
            size = 5;
        else if(type == Type.BattleCarrier)
            size = 4;
        else if(type == Type.Floater)
            size = 3;
        else if(type == Type.StandardShip)
            size = 3;
        else if(type == Type.Pontoon)
            size = 2;
        else if(type == Type.Miss)
            size = 1;
        hits = new boolean[size];
    }
    public void draw(int ytop, int xleft, int hieght,int width){
        
    }
    public int getSize(){
        return size;
    }
    public Direction getDirection(){
        return direction;
    }
    public Type getType()
    {
        return(type);
    }
    public int getHits(){
        int numHits=0;
        for(int index=0;index<hits.length;index++)
        {
            if(hits[index] == true)
                numHits++;
        }
        return numHits;
    }
    public boolean getHit(){
        return hits[spot];
    }
    public void shoot(){
        hits[spot]=true;
        if(getHits() == size)
            sunk = true;
    }
    public boolean getSunk(){
        return sunk;
    }
}
