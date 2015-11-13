/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

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
    
    Ship board1[][];
    Ship board2[][];
    
    int numShips;
    Ship player1[];
    Ship player2[];
    
    boolean turn;
   
    int mouseXPos;
    int mouseYPos;
    
    int winconnect;
    
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
                    if (xpos < 0 || ypos > topborderlength || xpos > getWidth2() || ypos > getHeight2())
                        return;
                  
                    int ydelta = (getHeight2()-topborderlength)/numRows;
                    int xdelta = (getWidth2()/2)/numColumns;
                    int col = xpos/xdelta;
                    int row = ypos/ydelta;
                    if(ypos <=topborderlength )
                    {
                        if(xpos <= getWidth2()/2)
                        {    
                            col = xpos/xdelta;
                            row = ypos/ydelta;
                            board1[row][col] = new Ship(5);
                        }
                        else
                        {
                            col = xpos/xdelta;
                            row = ypos/ydelta;
                            board2[row][col] = new Ship(5);
                        }
                    }
                    board1[row][col] = new Ship(5);
                    
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
                if (e.VK_RIGHT == e.getKeyCode())
                {
                    
                }
                if (e.VK_B == e.getKeyCode())
                {
                  
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
        g.setColor(Color.BLUE);

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
        int offset = 20;
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
        
        g.setColor(Color.blue);
        g.fillRect(getWidth2()/2+offset, getY(topborderlength), getWidth2()/1000+offset, getHeight2());
        
        //drawing horizontal and vertical lines board2
        g.setColor(Color.blue);
        for (int r=0;r<numRows+1;r++)
        {
            g.drawLine(getX(getWidth2()/2) ,getY(topborderlength)+r*(getHeight2()-topborderlength)/numRows ,
            getX(getWidth2()+getWidth2()/2),getY(topborderlength)+r*(getHeight2()-topborderlength)/numRows );
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
                if (board1[zrow][zcolumn] != null)
                {                   
                    drawShip(getX(sideborderlength)+zcolumn*getWidth2()/numColumns,
                    getY(topborderlength-25)+zrow*getHeight2()/numRows,
                    (getWidth2()/2)/numColumns,
                    ((getHeight2()*2/3))/numRows);
                }              
            }
        }
        

        
        gOld.drawImage(image, 0, 0, null);
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
public void drawShip(int xleft,int ytop,int xlength,int ywidth)
    {
        g.setColor(Color.yellow);
        g.fillRect(xleft, ytop , xlength , ytop);
    }    
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        numShips=5;
        startMenu=true;
        
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
        board1[1][0] = new Ship(1);
        board1[2][0] = new Ship(1);
        
        
        player1 = new Ship[numShips];
        player2 = new Ship[numShips];
        for(int index=0;index<numShips;index++){
            player1[index]=new Ship(index+1);
            player1[index]=new Ship(index+1);
        }
        
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
