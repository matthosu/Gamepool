package whatever.gamepool;

import java.util.EventObject;

/**
 * Created by Mateusz on 2016-04-22.
 */
public class RotationEvent  extends EventObject {
    private float directionY;

    public RotationEvent(GyroAngle source, float dirY) {
        super(source);
        directionY = dirY;
    }

    public float getDirectionY() {
        return directionY;
    }
}
