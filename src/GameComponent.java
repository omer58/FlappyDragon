import java.awt.image.BufferedImage;

/**
 * Created by omer on 26.09.2015.
 */
public interface GameComponent
{
    void update( int shiftX, int shiftY);
    void paint( BufferedImage buf);

}
