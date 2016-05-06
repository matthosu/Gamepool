package whatever.gamepool;

import java.util.EventObject;

public class RotationEvent extends EventObject {
    private float directionY;

    public RotationEvent(GyroAngle source, float dirY) {
        super(source);
        directionY = dirY;
    }

    public float getDirectionY() {
        return directionY;
    }
}
