/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;
import java.util.ArrayList;

public class AI {
    public enum Difficulty{
        Easy,Medium,Hard
    }
    
    Difficulty difficulty;
    private boolean hunt;
    private int lastRow;
    private int lastColumn;
    private ArrayList<Ship> targets;
    
    AI(Difficulty _hardness){
        difficulty = Difficulty.Easy;
        hunt = true;
        targets = new ArrayList<Ship>();
    }
    public void setDifficulty(Difficulty _difficulty)
    {
        difficulty = _difficulty;
    }
    public Difficulty getDifficulty()
    {
        return difficulty;
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
    private boolean fireEasy(Ship[][] board)
    {
        boolean hit = false;
        int row;
        int col;
        int bugChecker = 0;
////////////uncomment the below lines when ship is up to date
        do
        {           
            row = (int)(Math.random()*board.length);
            col = (int)(Math.random()*board[row].length);
            bugChecker++;
        }
        while(!(board[row][col] == null || (board[row][col]  != null && board[row][col].getType() != Ship.Type.Miss &&
              !board[row][col].getHit() ))  || bugChecker <= 100);
        lastRow = row;
        lastColumn = col;
        if(board[row][col] == null)
        {
            board[row][col] = new Ship(0,Ship.Type.Miss);
        }
        else if( board[row][col].getType() != Ship.Type.Miss && !board[row][col].getHit())
        {
            board[row][col].shoot();
            hunt = false;
            hit = true;
        }
        return hit;
    }
    private void fireMedium(Ship[][] board)
    {
        fireEasy(board);
    }
    private void fireHard(Ship[][] board)
    {
        int row;
        int col;
        int bugChecker = 0;
        if(hunt)
        {
            if(fireEasy(board))
            {
                if(!targets.contains(board[lastRow+1][lastColumn]))
                {
                    targets.add(board[lastRow+1][lastColumn]);
                }
                if(!targets.contains(board[lastRow-1][lastColumn]))
                {
                    targets.add(board[lastRow-1][lastColumn]);
                }
                if(!targets.contains(board[lastRow][lastColumn+1]))
                {
                    targets.add(board[lastRow][lastColumn+1]);
                }
                if(!targets.contains(board[lastRow][lastColumn-1]))
                {
                    targets.add(board[lastRow][lastColumn-1]);
                }
            }
        }
        else
        {
            do
            {
                row = (int)(Math.random()*targets.size());
                bugChecker++;
            }
            while(!(targets.get(row) == null ||
                    (targets.get(row) != null && !targets.get(row).getHit() && targets.get(row).getType() != Ship.Type.Miss) ) ||
                    bugChecker <= 100);
            
            if(bugChecker >= 100)
            {
                hunt = true;
                if(fireEasy(board))
                {
                    targets = new ArrayList<Ship>();
                    if(!targets.contains(board[lastRow+1][lastColumn]))
                    {
                        targets.add(board[lastRow+1][lastColumn]);
                    }
                    if(!targets.contains(board[lastRow-1][lastColumn]))
                    {
                        targets.add(board[lastRow-1][lastColumn]);
                    }
                    if(!targets.contains(board[lastRow][lastColumn+1]))
                    {
                        targets.add(board[lastRow][lastColumn+1]);
                    }
                    if(!targets.contains(board[lastRow][lastColumn-1]))
                    {
                        targets.add(board[lastRow][lastColumn-1]);
                    }
                }
            }
            else if(targets.get(row) == null)
            {
//                targets.get(row) = new Ship(0,Ship.Type.Miss);
            }
            else
            {
                targets.get(row).shoot();
            }
        }
    }
}
