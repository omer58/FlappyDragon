import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by omer on 27.09.2015.
 */
public class Food implements GameComponent
{
    private int xLoc;
    private int yLoc;
    private int radius;
    private boolean isEaten = false;

    public Food()
    {
        xLoc = -10;
        yLoc = -10;
    }

    public void createFood( int yLoc, int dim)
    {
        xLoc = GameBoard.BOARD_DIM;
        this.yLoc = yLoc;
        this.radius = dim / 2;
    }

    @Override
    public void update(int shiftX, int shiftY)
    {
        xLoc += shiftX;
    }

    @Override
    public void paint(BufferedImage buf)
    {
        if( !isEaten)
        {
            Graphics g = buf.getGraphics();
            g.setColor( Color.cyan);
            g.fillOval( xLoc, yLoc, radius * 2, radius * 2);
        }

    }

    public boolean isColliding( int x, int y)
    {
        if( isEaten)
            return false;

        double dist = Math.sqrt( Math.abs( (xLoc + radius - x)*(xLoc + radius - x) ) +  Math.abs( (yLoc + radius - y)*(yLoc + radius - y) ));
        System.out.println( "distance: " + dist);
        if( dist < radius)
            return true;
        return false;
    }

    public void setEaten( boolean value)
    {
        isEaten = value;
    }
}
