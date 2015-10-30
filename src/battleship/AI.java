/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

public static class AI {
    public enum Difficulty{
        easy,hard
    }
    Difficulty difficulty;
    boolean hunt;
    AI(Difficulty _hardness){
        difficulty = _hardness;
        hunt = true;
    }    
    public void fire(Ship[][] board){
        if(difficulty==Difficulty.easy)
        {
            fireEasy(board);
        }
    }
    private void fireEasy(Ship[][] board)
    {
        int row;
        int col;
//        uncomment the below lines when ship is up to date
        do
        {           
            row = (int)(Math.random()*board.length);
            col = (int)(Math.random()*board[row].length);
        }
        while(board[row][col].getType() == Ship.Type.Miss &&
              !board[row][col].getHit());
    }
    private void fireHard(Ship[][] board)
    {
        int row;
        int col;
        if(hunt)
        {
            fireEasy(board);
        }
        else
        {
            do
            {
                row = (int)(Math.random()*board.length);
                col = (int)(Math.random()*board[row].length);
            }
            while(board[row][col].getType() != Ship.Type.Miss && 
                  !board[row][col].getHit() &&
                  )
        }
    }
}
