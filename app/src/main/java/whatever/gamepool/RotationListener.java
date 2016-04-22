package whatever.gamepool;

import java.util.EventListener;

/**
 * Created by Mateusz on 2016-04-22.
 */
public interface RotationListener extends EventListener {               // listener to gyro sygnals to modify during integration
    void onEvent(RotationEvent e);                                      // as in the event class - variable direction in event have 4 possible values (0 - north, 1 - east, 2 - south, 3 - west)
}                                                                   // possible to add speed of move, but insignificant in that game
