package whatever.gamepool;

import java.util.EventObject;

public class MoveEvent extends EventObject {
    private Directions direction;

    public MoveEvent(GyroRunner source, Directions dir) {
        super(source);
        direction = dir;
    }

    public Directions getDirection() {
        return direction;
    }
}
