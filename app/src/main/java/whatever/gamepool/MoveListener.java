package whatever.gamepool;

import java.util.EventListener;

public interface MoveListener extends EventListener {               // listener to gyro sygnals to modify during integration
    void onEvent(MoveEvent e);                                      // as in the event class - variable direction in event have 4 possible values (0 - north, 1 - east, 2 - south, 3 - west)
}                                                                   // possible to add speed of move, but insignificant in that game
