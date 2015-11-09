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
    private Ship[] targets;
    private int numTargets;
    private int targetPositions[][];
    
    AI(Difficulty _hardness){
        difficulty = _hardness;
        hunt = true;
        targets = new Ship[30];
        numTargets=0;
        targetPositions = new int[30][2];
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
        int margin = 500;
////////////uncomment the below lines when ship is up to date
        do
        {           
            row = (int)(Math.random()*board.length);
            col = (int)(Math.random()*board[row].length);
            bugChecker++;
        }
        while(!(board[row][col] == null || (board[row][col]  != null && board[row][col].getType() != Ship.Type.Miss &&
              !board[row][col].getHit() ))  && bugChecker <= margin);
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
        int rand;
        int bugChecker = 0;
        if(hunt)
        {
            if(fireEasy(board))
            {
                targetPositions[numTargets][0] = lastRow+1;
                targetPositions[numTargets][1] = lastColumn;
                targets[numTargets++] = board[lastRow+1][lastColumn];
                targetPositions[numTargets][0] = lastRow-1;
                targetPositions[numTargets][1] = lastColumn;
                targets[numTargets++] = board[lastRow-1][lastColumn];
                targetPositions[numTargets][0] = lastRow;
                targetPositions[numTargets][1] = lastColumn+1;
                targets[numTargets++] = board[lastRow][lastColumn+1];
                targetPositions[numTargets][0] = lastRow;
                targetPositions[numTargets][1] = lastColumn-1;
                targets[numTargets++] = board[lastRow][lastColumn-1];
                
            }
        }
        else
        {
            do
            {
                rand = (int)(Math.random()*numTargets);
                bugChecker++;
            }
            while(!(targets[rand] == null ||
                    (targets[rand] != null && !targets[rand].getHit() && targets[rand].getType() != Ship.Type.Miss) ) &&
                    bugChecker <= 100);
            
            if(bugChecker >= 100)
            {
                hunt = true;
                for(int i =0;i<targets.length;)
                    targets[i] = null;
                for(int i =0;i<targetPositions.length;i++)
                    for(int b =0;b<targetPositions[i].length;b++)
                        targetPositions[i][b] = 0;
                fireMedium(board);
            }
            else if(targets[rand] == null)
            {
                lastRow = targetPositions[rand][0];
                lastColumn = targetPositions[rand][1];
                board[targetPositions[rand][0]][targetPositions[rand][1]] = new Ship(0,Ship.Type.Miss);
            }
            else
            {
                lastRow = targetPositions[rand][0];
                lastColumn = targetPositions[rand][1];
                targets[rand].shoot();
                targetPositions[numTargets][0] = lastRow+1;
                targetPositions[numTargets][1] = lastColumn;
                targets[numTargets++] = board[lastRow+1][lastColumn];
                targetPositions[numTargets][0] = lastRow-1;
                targetPositions[numTargets][1] = lastColumn;
                targets[numTargets++] = board[lastRow-1][lastColumn];
                targetPositions[numTargets][0] = lastRow;
                targetPositions[numTargets][1] = lastColumn+1;
                targets[numTargets++] = board[lastRow][lastColumn+1];
                targetPositions[numTargets][0] = lastRow;
                targetPositions[numTargets][1] = lastColumn-1;
                targets[numTargets++] = board[lastRow][lastColumn-1];
                
                
            }
        }
    }
    private void fireHard(Ship[][] board)
    {
        fireMedium(board);
    }
}
