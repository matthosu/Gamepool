package whatever.gamepool;

import whatever.gameFifteen.Menu15Activity;
import whatever.game2048.Menu2048Activity;
import whatever.gameRace.MenuRaceActivity;

public class ItemContainer {

    private final Class[] activityClasses = {Menu2048Activity.class, MenuRaceActivity.class, Menu15Activity.class};
    private final String[][] activityDescriptions = {{"2048", "An easy game, where we have to merge numbers"},
            {"Race", "An action game, where you drive a car and avoid collisions with others"},
            {"Fifteen", "Simple logic puzzle which aim is to put elements in correct order"}};
    private final int[] activityIcons = {R.drawable.item_2048, R.drawable.item_race, R.drawable.item_fifteen};

    private static ItemContainer ourInstance = new ItemContainer();

    public static ItemContainer getInstance() {
        return ourInstance;
    }

    private ItemContainer() {
    }

    public int getCount() {
        return activityClasses.length;
    }

    public Class getActivityClass(int i) {
        return activityClasses[i];
    }

    public String[] getActivityDescription(int i) {
        return activityDescriptions[i];
    }

    public int getActivityIcon(int i) {
        return activityIcons[i];
    }
}
