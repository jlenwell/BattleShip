/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

public class AI {
    public enum Difficulty{
        easy,hard
    }
    Difficulty difficulty;
    AI(Difficulty _hardness){
        difficulty = _hardness;
    }    
//    public void fire(Ship[] board){
//        if(difficulty==Difficulty.easy)
//        {
//            fireEasy(board);
//        }
//    }
//    private void fireEasy(Ship[] board)
//    {
//        do
//        {
//            
//        }while()
//    }
}
