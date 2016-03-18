package whatever.gamepool;

import java.util.EventListener;
import java.util.EventObject;
import java.util.Objects;

/**
 * Created by Mateusz on 2016-03-18.
 */
public class MoveEvent extends EventObject {
    Directions direction;
    public MoveEvent(GyroRunner source, Directions dir)
    {
        super(source);
        direction = dir;
    }
    public Directions getDirection()
    {
       return direction;
    }
}
