package whatever.gamepool;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Objects;

/**
 * Created by Mateusz on 2016-03-18.
 */
public class MoveEvent extends EventObject {
    int direction; // A variable to get know which direction movement is (0 - north, 1 - east, 2 - south, 3 - west)
    public MoveEvent(GyroRunner source, int dir)
    {
        super(source);
        direction = dir;
    }
    public int getDirection()
    {
       return direction;
    }
}
