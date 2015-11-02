/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

public static class AI {
    public enum Difficulty{
        Easy,Medium,Hard
    }
    
    Difficulty difficulty;
    private boolean hunt;
    private int lastRow;
    private int lastColumn;
    
    AI(Difficulty _hardness){
        difficulty = Difficulty.Easy;
        hunt = true;
    }
    public void changeDifficultyTo(Difficulty _difficulty)
    {
        difficulty = _difficulty;
    }
    public void fire(Ship[][] board){
        if(difficulty==Difficulty.Easy)
        {
            fireEasy(board);
        }
        else if (difficulty == Difficulty.Medium)
        {
            fireMedium(board);
        }
        else if (difficulty == Difficulty.Hard)
        {
            fireHard(board);
        }
    }
    private void fireEasy(Ship[][] board)
    {
        int row;
        int col;
        int bugChecker = 0;
////////////uncomment the below lines when ship is up to date
//        do
//        {           
//            row = (int)(Math.random()*board.length);
//            col = (int)(Math.random()*board[row].length);
//            bugChecker++;
//        }
//        while(!(board[row][col] == null || (board[row][col]  != null && board[row][col].getType() != Ship.Type.Miss &&
//              !board[row][col].getHit() ))  || bugChecker >= 100);
//        lastRow = row;
//        lastColumn = col;
//        if(board[row][col] == null)
//        {
//            board[row][col] = new Ship(Ship.Miss);
//        }
//        else if( board[row][col].getType() != Ship.Type.Miss && !board[row][col].getHit)
//        {
//            board[row][col].Shot();
//            hunt = false;
//        }
    }
    private void fireMedium(Ship[][] board)
    {
        fireEasy();
    }
    private void fireHard(Ship[][] board)
    {
        int row;
        int col;
        int bugChecker = 0;
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
                bugChecker++;
            }
            while(!(board[row][col] == null || (board[row][col]  != null && board[row][col].getType() != Ship.Type.Miss &&
                  !board[row][col].getHit())) && 
                  (col>= lastColumn-1 && col<=lastColumn+1 && row>= lastRow-1 && row<=lastRow+1) ||
                  bugChecker >= 100)
        }
    }
}
