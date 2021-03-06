/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.AI;

import battleship.*;

public class AI {
    public enum Difficulty{
        Easy,Medium,Hard
    }
    
    Difficulty difficulty;
    private boolean hunt;
    private int lastRow;
    private int lastColumn;
    private Ship[] targets;
    private int numPossibleTargets;
    private int numTargets;
    private int[][] targetPositions;
    private boolean[] activeTargets;
    
    public AI(Difficulty _hardness){
        numPossibleTargets = 200;
        difficulty = _hardness;
        hunt = true;
        targets = new Ship[numPossibleTargets];
        numTargets=0;
        targetPositions = new int[numPossibleTargets][2];
        activeTargets = new boolean[numPossibleTargets];
        for(int index=0;index<activeTargets.length;index++)
            activeTargets[index] = false;
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
                if(lastRow != board.length)
                {
                    if(board[lastRow+1][lastColumn] != null)
                        activeTargets[numTargets] = !board[lastRow+1][lastColumn].getHit();
                    else
                        activeTargets[numTargets] = true;
                    targetPositions[numTargets][0] = lastRow+1;
                    targetPositions[numTargets][1] = lastColumn;
                    targets[numTargets++] = board[lastRow+1][lastColumn];
                }
                if(lastRow != 0)
                {
                    if(board[lastRow-1][lastColumn] != null)
                        activeTargets[numTargets] = !board[lastRow-1][lastColumn].getHit();
                    else
                        activeTargets[numTargets] = true;
                    targetPositions[numTargets][0] = lastRow-1;
                    targetPositions[numTargets][1] = lastColumn;
                    targets[numTargets++] = board[lastRow-1][lastColumn];
                }
                if(lastColumn != board[lastRow].length)
                {
                    if(board[lastRow][lastColumn+1] != null)
                        activeTargets[numTargets] = !board[lastRow][lastColumn+1].getHit();
                    else
                        activeTargets[numTargets] = true;
                    targetPositions[numTargets][0] = lastRow;
                    targetPositions[numTargets][1] = lastColumn+1;
                    targets[numTargets++] = board[lastRow][lastColumn+1];
                }
                if(lastColumn != 0)
                {
                    if(board[lastRow][lastColumn-1] != null)
                        activeTargets[numTargets] = !board[lastRow][lastColumn-1].getHit();
                    else 
                        activeTargets[numTargets] = true;
                    targetPositions[numTargets][0] = lastRow;
                    targetPositions[numTargets][1] = lastColumn-1;
                    targets[numTargets++] = board[lastRow][lastColumn-1];
                }
            }
        }
        else
        {
            boolean anytargets = false;
            for(int index=0;index<activeTargets.length;index++)
            {
                if(activeTargets[index])
                    anytargets = true;
            }
            
            do
            {
                rand = (int)(Math.random()*numTargets);
                bugChecker++;
            }
            while((!(targets[rand] == null || 
                  (targets[rand] != null && targets[rand].getType() != Ship.Type.Miss && targets[rand].getType() != Ship.Type.Miss ))
                    || !activeTargets[rand] || (targets[rand] != null && targets[rand].getHit()))
                    && bugChecker<=100);
            if(bugChecker >= 100 || !anytargets)
            {
                hunt = true;
                for(int i =0;i<targets.length;i++)
                    targets[i] = null;
                for(int i =0;i<targetPositions.length;i++)
                    for(int b =0;b<targetPositions[i].length;b++)
                        targetPositions[i][b] = 0;
                for(int index=0;index<activeTargets.length;index++)
                    activeTargets[index] = false;
                fireMedium(board);
            }
            else if(targets[rand] == null)
            {
                activeTargets[rand] = false;
                lastRow = targetPositions[rand][0];
                lastColumn = targetPositions[rand][1];
                board[targetPositions[rand][0]][targetPositions[rand][1]] = new Ship(0,Ship.Type.Miss);
            }
            else
            {
                activeTargets[rand] = false;
                lastRow = targetPositions[rand][0];
                lastColumn = targetPositions[rand][1];
                targets[rand].shoot();
                if(lastRow != board.length)
                {
                    if(board[lastRow+1][lastColumn] != null)
                        activeTargets[numTargets] = !board[lastRow+1][lastColumn].getHit();
                    else
                        activeTargets[numTargets] = true;
                    targetPositions[numTargets][0] = lastRow+1;
                    targetPositions[numTargets][1] = lastColumn;
                    targets[numTargets++] = board[lastRow+1][lastColumn];
                }
                if(lastRow != 0)
                {
                    if(board[lastRow-1][lastColumn] != null)
                        activeTargets[numTargets] = !board[lastRow-1][lastColumn].getHit();
                    else
                        activeTargets[numTargets] = true;
                    targetPositions[numTargets][0] = lastRow-1;
                    targetPositions[numTargets][1] = lastColumn;
                    targets[numTargets++] = board[lastRow-1][lastColumn];
                }
                if(lastColumn != board[lastRow].length)
                {
                    if(board[lastRow][lastColumn+1] != null)
                        activeTargets[numTargets] = !board[lastRow][lastColumn+1].getHit();
                    else
                        activeTargets[numTargets] = true;
                    targetPositions[numTargets][0] = lastRow;
                    targetPositions[numTargets][1] = lastColumn+1;
                    targets[numTargets++] = board[lastRow][lastColumn+1];
                }
                if(lastColumn != 0)
                {
                    if(board[lastRow][lastColumn-1] != null)
                        activeTargets[numTargets] = !board[lastRow][lastColumn-1].getHit();
                    else 
                        activeTargets[numTargets] = true;
                    targetPositions[numTargets][0] = lastRow;
                    targetPositions[numTargets][1] = lastColumn-1;
                    targets[numTargets++] = board[lastRow][lastColumn-1];
                }
            }
        }
    }
    private void fireHard(Ship[][] board)
    {
        fireMedium(board);
    }
}
