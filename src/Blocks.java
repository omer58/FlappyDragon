import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by omer on 26.09.2015.
 */
public class Blocks implements GameComponent
{
    private ArrayList<Block> blocks;
    private boolean auto = false;
    private final int BLOCK_HEIGHT_BASE = 100;
    private final int BLOCK_HEIGHT_MAX = 300;

    public Blocks()
    {
        blocks = new ArrayList<Block>();
    }

    public void createBlockDown( int height, int width)
    {
        blocks.add( new Block( GameBoard.BOARD_DIM, GameBoard.BOARD_DIM - height, width, height));
    }

    public void createBlockUp( int height, int width)
    {
        blocks.add( new Block( GameBoard.BOARD_DIM, -10, width, height));

    }


    private boolean change = false;
    @Override
    public void update(int shiftX, int shiftY)
    {


        if(blocks.isEmpty())
            return;

        for( Block b:blocks)
        {
            b.setX(b.getX() + shiftX);

            // removes the block and instantly adds a new one.
            if( b.getX() < 0 - b.getWidth())
            {
                blocks.remove( b);
                int rand =(int) (Math.random() * BLOCK_HEIGHT_MAX + 1);

                if(auto)
                {
                    //if( change)
                        createBlockDown( BLOCK_HEIGHT_BASE + rand, 30);
                    //else
                        //createBlockUp( BLOCK_HEIGHT_BASE + rand, 30);
                }

                change = !change;

            }

        }

    }

    @Override
    public void paint(BufferedImage buf)
    {
        Graphics g = buf.getGraphics();
        g.setColor( Color.green);

        for( Block b:blocks)
            g.fillRoundRect( b.getX(), b.getY(), b.getWidth(), b.getHeight() + 10, 5, 5);
    }

    public void autoCreate()
    {
        auto = true;
    }

    public boolean isColliding( int x, int y)
    {
        for( Block b:blocks)
            // if the block is a down block.
            if( b.getX() < x && x - b.getX() < b.getWidth() && b.getY() < y)
                return true;

        return false;

    }
}
