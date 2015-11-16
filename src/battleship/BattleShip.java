/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.AI.*;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class BattleShip extends JFrame implements Runnable {
    static final int XBORDER = 20;
    static final int YBORDER = 20;
    static final int YTITLE = 30;
    static final int WINDOW_BORDER = 8;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + 1400;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 2 * YBORDER + 800;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;
    
    final int numRows = 10;
    final int numColumns = 10; 
    final int sideborderlength = 100;
    final int topborderlength = 250;
    final int sideborderlength2 = 50;
    boolean startMenu;
    boolean play;
    AI ai;
    
    boolean placing;
    int placingRow;
    int placingCol;
    int placeStatus;
    
    Ship board1[][];
    Ship board2[][];
    
    int numShips;
    Ship player1[];
    Ship player2[];
    
    boolean turn;
   
    int mouseXPos;
    int mouseYPos;
    
    int winconnect;
    Color borderColor;
    
    static BattleShip frame1;
    public static void main(String[] args) {
        frame1 = new BattleShip();
        frame1.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
    }

    public BattleShip() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    //board1
                    int xpos = e.getX() - getX(0);
                    int ypos = e.getY() - getY(0);
                    if (xpos < 0 || ypos > getHeight2() || xpos > getWidth2() || ypos > getHeight2())
                        return;
                  
                    int ydelta = (getHeight2()-topborderlength)/numRows;
                    int xdelta = (getWidth2()/2)/numColumns;
                    int col;
                    int row;
                    if(ypos >=topborderlength )
                    {
                        if(xpos <= getWidth2()/2 && play)
                        {    
                            col = xpos/xdelta;
                            row = (ypos-topborderlength)/ydelta;
                            if(board1[row][col] != null && board1[row][col].getType() != Ship.Type.Miss)
                            {
                                board1[row][col].shoot();
                                ai.fire(board2);
                            }
                            else
                            {
                                board1[row][col] = new Ship(0,Ship.Type.Miss);
                                ai.fire(board2);
                            }
                        }
                        else if(xpos >= getWidth2()/2 && !play && placing)
                        {
                            col = (xpos-getWidth2()/2)/xdelta;
                            row = (ypos-topborderlength)/ydelta;
                            board2[row][col] = new Ship(0,Ship.Type.AirCraftCarrier);
                            placingRow = row;
                            placingCol = col;
                            
                            placing = false;
                        }
                    }
                    
                }
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        
          mouseXPos = e.getX() - getX(0);
          mouseYPos = e.getY() - getY(0);
        
          repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode() && !placing && !play)
                {
                    if(placeStatus == 0)
                    {
                        if(placeShip(new Ship(0,Ship.Type.AirCraftCarrier,Ship.Direction.Up),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 1)
                    {
                        if(placeShip(new Ship(0,Ship.Type.BattleCarrier,Ship.Direction.Up),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 2)
                    {
                        if(placeShip(new Ship(0,Ship.Type.Floater,Ship.Direction.Up),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 3)
                    {
                        if(placeShip(new Ship(0,Ship.Type.Pontoon,Ship.Direction.Up),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                            play = true;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                }
                if (e.VK_DOWN == e.getKeyCode())
                {
                    
                    if(placeStatus == 0)
                    {
                        if(placeShip(new Ship(0,Ship.Type.AirCraftCarrier,Ship.Direction.Down),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 1)
                    {
                        if(placeShip(new Ship(0,Ship.Type.BattleCarrier,Ship.Direction.Down),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 2)
                    {
                        if(placeShip(new Ship(0,Ship.Type.Floater,Ship.Direction.Down),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 3)
                    {
                        if(placeShip(new Ship(0,Ship.Type.Pontoon,Ship.Direction.Down),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                            play = true;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                }
                if (e.VK_LEFT == e.getKeyCode())
                {
                    
                    if(placeStatus == 0)
                    {
                        if(placeShip(new Ship(0,Ship.Type.AirCraftCarrier,Ship.Direction.Left),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 1)
                    {
                        if(placeShip(new Ship(0,Ship.Type.BattleCarrier,Ship.Direction.Left),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 2)
                    {
                        if(placeShip(new Ship(0,Ship.Type.Floater,Ship.Direction.Left),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 3)
                    {
                        if(placeShip(new Ship(0,Ship.Type.Pontoon,Ship.Direction.Left),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                            play = true;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                }
                if (e.VK_RIGHT == e.getKeyCode())
                {
                    
                    if(placeStatus == 0)
                    {
                        if(placeShip(new Ship(0,Ship.Type.AirCraftCarrier,Ship.Direction.Right),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 1)
                    {
                        if(placeShip(new Ship(0,Ship.Type.BattleCarrier,Ship.Direction.Right),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 2)
                    {
                        if(placeShip(new Ship(0,Ship.Type.Floater,Ship.Direction.Right),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                    else if(placeStatus == 3)
                    {
                        if(placeShip(new Ship(0,Ship.Type.Pontoon,Ship.Direction.Right),
                                placingRow,placingCol,board2))
                        {
                            placing = true;
                            placeStatus++;
                            play = true;
                        }
                        else
                        {
                            board2[placingRow][placingCol] = null;
                            placing = true;
                        }
                    }
                }
                if (e.VK_B == e.getKeyCode())
                {
                  play= !play;
                }
                if (e.VK_V == e.getKeyCode())
                {
                
                }
                if (e.VK_N == e.getKeyCode())
                {
                    
                }
                if (e.VK_ESCAPE == e.getKeyCode())
                {
                    reset();
                }
                repaint();
            }
        });
        init();
        start();
    }




    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

//fill background
        g.setColor(borderColor);

        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.ORANGE);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        g.setColor(Color.black);
//horizontal lines
//        for (int zi=1;zi<numRows;zi++)
//        {
//            g.drawLine(getX(0) ,getY(0)+zi*getHeight2()/numRows ,
//            getX(getWidth2()) ,getY(0)+zi*getHeight2()/numRows );
//        }
////vertical lines
//        for (int zi=1;zi<numColumns;zi++)
//        {
//            g.drawLine(getX(0)+zi*getWidth2()/numColumns ,getY(0) ,
//            getX(0)+zi*getWidth2()/numColumns,getY(getHeight2())  );
//        }
        
        
        
//drawing horizontal vertical lines board1
        int length = 10;
        for (int zi=0;zi<numRows;zi++)
        {
            g.drawLine(getX(0) ,getY(topborderlength)+zi*(getHeight2()-topborderlength)/numRows ,
            getX(getWidth2()/2) ,getY(topborderlength)+zi*(getHeight2()-topborderlength)/numRows );
        }
        for (int zi=0;zi<numColumns+1;zi++)
        {
            g.drawLine(getX(0)+zi*((getWidth2()/2)/numColumns) ,getY(topborderlength) ,
            getX(0)+zi*(getWidth2()/2)/numColumns, getY(getHeight2())  );
        }      
        
        g.setColor(borderColor);
        g.fillRect(getWidth2()/2-(length)+30, getY(topborderlength),length, getHeight2());
        
        //drawing horizontal and vertical lines board2
        g.setColor(Color.blue);
        for (int r=0;r<numRows+1;r++)
        {
            g.drawLine(getX(getWidth2()/2) ,getY(topborderlength)+r*(getHeight2()-topborderlength)/numRows ,
            getX(getWidth2()/2)+getWidth2()/2,getY(topborderlength)+r*(getHeight2()-topborderlength)/numRows );
        }
        for (int c=0;c<numColumns+1;c++)
        {
            g.drawLine(getX((getWidth2()/2)+c*(getWidth2()/2)/numColumns) ,getY(topborderlength) ,
            getX((getWidth2()/2)+c*((getWidth2()/2)/numColumns)), getY(getHeight2())  );
        }
  
        
//draw ships 
        int offset1 = 26;
        g.setColor(Color.orange);
        for(int zrow=0;zrow<numRows;zrow++)
        {
            for(int zcolumn=0;zcolumn<numColumns;zcolumn++)
            {
                {
                    if (board1[zrow][zcolumn] != null)
                    {                   
                        if(board1[zrow][zcolumn].getType() == Ship.Type.Miss)
                        {
                            g.setColor(Color.GREEN);
                            g.fillRect(getX(0)+zcolumn*(getWidth2()/2)/numColumns,
                            getY(topborderlength)+zrow*(getHeight2()-topborderlength)/numRows,
                            (getWidth2()/2)/numColumns,
                            (getHeight2()-topborderlength)/numRows);
                        }  
                        else if(board1[zrow][zcolumn].getHit())
                        {
                            g.setColor(Color.RED);
                            g.fillRect(getX(0)+zcolumn*(getWidth2()/2)/numColumns,
                            getY(topborderlength)+zrow*(getHeight2()-topborderlength)/numRows,
                            (getWidth2()/2)/numColumns,
                            (getHeight2()-topborderlength)/numRows);
                        }
                        else
                        {
                            
                            g.setColor(Color.CYAN);
                            g.fillRect(getX(0)+zcolumn*(getWidth2()/2)/numColumns,
                            getY(topborderlength)+zrow*(getHeight2()-topborderlength)/numRows,
                            (getWidth2()/2)/numColumns,
                            (getHeight2()-topborderlength)/numRows);
                        }
                    }  
                }
                {
                    if (board2[zrow][zcolumn] != null)
                    {                   
                        if(!board2[zrow][zcolumn].getHit())
                        {
                            g.setColor(Color.BLUE);
                                g.fillRect(getX(getWidth2()/2)+zcolumn*(getWidth2()/2)/numColumns,
                                getY(topborderlength)+zrow*(getHeight2()-topborderlength)/numRows,
                                (getWidth2()/2)/numColumns,
                                (getHeight2()-topborderlength)/numRows);
                        }          
                        else if(board2[zrow][zcolumn].getType() == Ship.Type.Miss)
                        {
                            g.setColor(Color.GREEN);
                            g.fillRect(getX(getWidth2()/2)+zcolumn*(getWidth2()/2)/numColumns,
                            getY(topborderlength)+zrow*(getHeight2()-topborderlength)/numRows,
                            (getWidth2()/2)/numColumns,
                            (getHeight2()-topborderlength)/numRows);
                        }     
                        else
                        {
                            g.setColor(Color.RED);
                            g.fillRect(getX(getWidth2()/2)+zcolumn*(getWidth2()/2)/numColumns,
                            getY(topborderlength)+zrow*(getHeight2()-topborderlength)/numRows,
                            (getWidth2()/2)/numColumns,
                            (getHeight2()-topborderlength)/numRows);
                        }
                    }  
                }          
            }
        }
        

        
        gOld.drawImage(image, 0, 0, null);
    }
    public void PlaceOpponentShips(Ship[][] board)
    {
        int randrow = (int)(Math.random()*numRows);
        int randcol = (int)(Math.random()*numColumns);
        while(!placeShip(new Ship(0,Ship.Type.Floater,Ship.Direction.Right),randrow,randcol,board1));
    }
    public boolean placeShip(Ship _ship,int startRow,int startCol,Ship[][] board)
    {
        boolean place = false;
        if(_ship.getDirection() == Ship.Direction.Down
            && startRow+_ship.getSize()<numRows+1)
        {
            for(int index = 0;index<_ship.getSize();index++)
            {
                board[startRow+index][startCol] = new Ship(index,_ship.getType(),_ship.getDirection());
                place = true;
            }
        }
        else if(_ship.getDirection() == Ship.Direction.Up
            && startRow-_ship.getSize()>-2)
        {
            for(int index = 0;index<_ship.getSize();index++)
            {
                board[startRow-index][startCol] = new Ship(index,_ship.getType(),_ship.getDirection());
                place = true;
            }
        }
        else if(_ship.getDirection() == Ship.Direction.Left
            && startCol-_ship.getSize()>=-1)
        {
            for(int index = 0;index<_ship.getSize();index++)
            {
                board[startRow][startCol-index] = new Ship(index,_ship.getType(),_ship.getDirection());
                place = true;
            }
        }
        else if(_ship.getDirection() == Ship.Direction.Right
            && startCol+_ship.getSize()<numColumns+1)
        {
            for(int index = 0;index<_ship.getSize();index++)
            {
                board[startRow][startCol+index] = new Ship(index,_ship.getType(),_ship.getDirection());
                place = true;
            }
        }
        return place;
    }
/////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = .05;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
////////////////////////////////////////////////////////////////////    
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        numShips=5;
        startMenu=true;
        borderColor = Color.BLACK;
        play = false;
        placing = true;
        placeStatus = 0;
        ai = new AI(AI.Difficulty.Medium);
        
        board1=new Ship[numRows][numColumns];
        board2=new Ship[numRows][numColumns];
        for(int zrow=0;zrow<numRows;zrow++)
        {
            for(int zcol=0;zcol<numColumns;zcol++)
            {
                board1[zrow][zcol] = null;
                board2[zrow][zcol] = null;
            }
        }
        player1 = new Ship[numShips];
        player2 = new Ship[numShips];
        
        placeShip(new Ship(0,Ship.Type.AirCraftCarrier,Ship.Direction.Down),0,0,board1);
        placeShip(new Ship(0,Ship.Type.BattleCarrier,Ship.Direction.Right),1,1,board1);
        placeShip(new Ship(0,Ship.Type.StandardShip,Ship.Direction.Down),2,2,board1);
        placeShip(new Ship(0,Ship.Type.Floater,Ship.Direction.Right),3,3,board1);
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
            reset();
        }
        
        for (int zrow = 0;zrow < numRows;zrow++)
        {
            for (int zcolumn = 0;zcolumn < numColumns;zcolumn++)
            {
                
            }
        }
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public int getY(int y) {
        return (y + YBORDER + YTITLE );
    }

    public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    public int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public int getHeight2() {
        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
    }
}
