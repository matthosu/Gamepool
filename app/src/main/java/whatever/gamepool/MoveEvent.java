package whatever.gamepool;

import java.util.EventObject;

public class MoveEvent extends EventObject {
    private GyroRunner.Directions direction;

    public MoveEvent(GyroRunner source, GyroRunner.Directions dir) {
        super(source);
        direction = dir;
    }

    public GyroRunner.Directions getDirection() {
        return direction;
    }
}
