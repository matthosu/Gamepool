package whatever.gamepool;

import java.util.EventListener;

/**
 * Created by Mateusz on 2016-04-22.
 */
public interface EndGameListener extends EventListener {
    void onEvent(EndGameEvent e);
}
