import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by omer on 25.09.2015.
 */
public class Dragon implements GameComponent

{
    private int headX, headY, nodeDim, nodeCount;

    // should use my own node class instead of Point.
    private ArrayList<Point> dragon;

    public Dragon( int headX, int headY, int nodeDim, int nodeCount)
    {

        this.headX = headX;
        this.headY = headY;
        this.nodeDim = nodeDim;
        this.nodeCount = nodeCount;

        dragon = new ArrayList<Point>();

        for( int i = 0; i < nodeCount; i++)
            dragon.add( new Point( headX - i * nodeDim, headY));
    }

    public void addNode()
    {
        dragon.add( new Point( headX - dragon.size() * nodeDim, headY));
    }

    public void update( int shiftX, int shiftY)
    {
        //shift the dragon
        for( int i = dragon.size() - 1; i > 0; i--)
            dragon.get(i).setLocation( dragon.get( i).getX(), dragon.get( i - 1).getY());

        Point head = dragon.get(0);
        headX = (int)head.getX() + shiftX;
        headY = (int)head.getY() + shiftY;

        dragon.get(0).setLocation( headX, headY);
    }

    public void paint( BufferedImage buf)
    {

        //BufferedImage buffer = new BufferedImage( GameBoard.BOARD_DIM, GameBoard.BOARD_DIM, BufferedImage.TYPE_INT_RGB);
        Graphics bufG = buf.getGraphics();
        bufG.setColor( Color.red);

        for( Point p:dragon)
            bufG.drawRoundRect( (int)p.getX(), (int)p.getY(), nodeDim, nodeDim, 5,5);

        //g.drawImage( buffer, null, 0,0);
    }

    public int getHeadX() {
        return headX;
    }

    public void setHeadX(int headX) {
        this.headX = headX;
    }

    public int getHeadY() {
        return headY;
    }

    public void setHeadY(int headY) {
        this.headY = headY;
    }
}
