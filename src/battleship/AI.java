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
    private boolean hunt;
    private int lastRow;
    private int lastColumn;
    
    AI(Difficulty _hardness){
        difficulty = _hardness;
        hunt = true;
    }    
    public void fire(Ship[][] board){
        if(difficulty==Difficulty.easy)
        {
            fireEasy(board);
        }
        else if (difficulty == Difficulty.hard)
        {
            fireHard(board)
        }
    }
    private void fireEasy(Ship[][] board)
    {
        int row;
        int col;
////////////uncomment the below lines when ship is up to date
//        do
//        {           
//            row = (int)(Math.random()*board.length);
//            col = (int)(Math.random()*board[row].length);
//        }
//        while(!(board[row][col] == null || (board[row][col]  != null && board[row][col].getType() != Ship.Type.Miss &&
//              !board[row][col].getHit() ) ) );
//        if(board[row][col] == null)
//        {
//            board[row][col] = new Ship(Ship.Miss);
//        }
//        else
//        {
//            board[row][col].Shot();
//        }
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
            while(!(board[row][col] == null || (board[row][col]  != null && board[row][col].getType() != Ship.Type.Miss &&
                  !board[row][col].getHit())) && 
                  )
        }
    }
}
