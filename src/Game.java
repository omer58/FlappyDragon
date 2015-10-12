import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Created by omer on 25.09.2015.
 */

class GameBoard extends JComponent implements KeyListener, ActionListener
{
    public static final int BOARD_DIM = 1000;
    private final int DELAY = 20;
    private final int KEY_DELAY = 40;
    private final int MAX_SHIFT = 8;
    private final int BLOCK_SHIFT = -5;
    private int dragonNodeCount = 2;
    private Dragon dragon;
    private Blocks blocks;
    private Food food;
    private int nodeDim = 10;
    private int x = BOARD_DIM / 2 - 100;
    private int y = BOARD_DIM / 2;
    private final int SHIFT_AMOUNT = nodeDim;
    private int shiftX = 0;
    private int shiftY = 0;
    private boolean prayExist = false;

    private boolean upIsDown = false;
    private boolean downIsDown = false;

    private Timer keyTimer;
    private Timer gameTimer;

    public GameBoard()
    {
        dragon = new Dragon( x, y, nodeDim, dragonNodeCount);

        blocks = new Blocks();
        blocks.createBlockDown(200, 10);
        blocks.autoCreate();

        food = new Food();
        food.createFood(BOARD_DIM / 2, 50);

        keyTimer = new Timer(KEY_DELAY, keyListener);

    }

    public void startGame()
    {
        gameTimer = new Timer( DELAY, this);
        gameTimer.start();

    }

    @Override
    public void paintComponent( Graphics g)
    {
        super.paintComponent(g);


        BufferedImage buf = new BufferedImage( GameBoard.BOARD_DIM, GameBoard.BOARD_DIM, BufferedImage.TYPE_INT_RGB);
        dragon.paint(buf);
        food.paint( buf);
        blocks.paint( buf);

        ((Graphics2D)g).drawImage( buf, null, 0, 0);
    }

    ActionListener keyListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println( "keylistener called");

            if( downIsDown)
                shiftY++;
            else if( upIsDown)
                shiftY--;

            if( shiftY > MAX_SHIFT)
                shiftY = MAX_SHIFT;
            else if( shiftY < -1 * MAX_SHIFT)
                shiftY = -1 * MAX_SHIFT;
        }
    };

    @Override
    public void keyTyped(KeyEvent e)
    {
        System.out.println( "key typed");
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        System.out.println("key pressed");
        if( e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            downIsDown = true;
            upIsDown = false;
        }
        else if( e.getKeyCode() == KeyEvent.VK_UP)
        {
            upIsDown = true;
            downIsDown = false;
        }

        keyTimer.start();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        System.out.println( "key released");
        upIsDown = false;
        downIsDown = false;

        keyTimer.stop();
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        dragon.update(shiftX, shiftY);
        blocks.update(BLOCK_SHIFT, 0);
        food.update(BLOCK_SHIFT, 0);

        if( blocks.isColliding( dragon.getHeadX() + nodeDim, dragon.getHeadY())
                || blocks.isColliding( dragon.getHeadX()+ nodeDim, dragon.getHeadY() + nodeDim))
            gameTimer.stop();

        if( food.isColliding( dragon.getHeadX(), dragon.getHeadY()))
        {
            dragon.addNode();
            food.setEaten( true);
        }



        repaint();
    }
}


class GameFrame extends JFrame
{
    public GameFrame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(GameBoard.BOARD_DIM, GameBoard.BOARD_DIM));
        GameBoard game = new GameBoard();
        add( game);
        setVisible( true);
        addKeyListener( game);

        game.startGame();
    }

}



public class Game
{
    public static void main( String args[])
    {
        GameFrame frame = new GameFrame();

    }
}