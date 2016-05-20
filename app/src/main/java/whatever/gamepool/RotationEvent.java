package whatever.gamepool;

import java.util.EventObject;

public class RotationEvent extends EventObject {
    private float directionY;
    private boolean isSpeededUp;

    public RotationEvent(GyroAngle source, float dirY, boolean speed) {
        super(source);
        directionY = dirY;
        isSpeededUp = speed;
    }

    public float getDirectionY() {
        return directionY;
    }
}
