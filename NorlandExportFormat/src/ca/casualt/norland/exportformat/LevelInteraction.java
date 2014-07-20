package ca.casualt.norland.exportformat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Level interaction data tracking.
 * 
 * @author Tony
 */
public class LevelInteraction {
    /**
     * The level attempts during this interaction with the level.
     */
    private final List<LevelAttempt> levelAttempts = new ArrayList<LevelAttempt>();

    /**
     * Unix time in seconds, of when the level was attempted.
     */
    private final long timeStart;

    /**
     * Unix time in seconds, of when the level attempt ended.
     */
    private long timeEnd = -1;

    /**
     * The current user settings.
     */
    private final Map<String, ?> userSettings;

    @SuppressWarnings({
            "unchecked", "rawtypes"
    })
    public LevelInteraction() {
        this.timeStart = 0;
        this.userSettings = new HashMap();
    }

    /**
     * @param userSettings
     */
    public LevelInteraction(Map<String, ?> userSettings) {
        this.timeStart = getCurrentTimeInSeconds();
        this.userSettings = userSettings;
    }

    // /**
    // * Grabs the current user settings from memory.
    // */
    // private void getCurrentUserSettings(final Context context) {
    // SharedPreferences sp = UpgradeMain.getStandardSharedPreferences(context);
    // userSettings = sp.getAll();
    // }

    /**
     * Finished the level interaction.
     */
    public void levelInteractionComplete() {
        timeEnd = getCurrentTimeInSeconds();
    }

    /**
     * @return the current system time in seconds.
     */
    private long getCurrentTimeInSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * @return the time the attempt was started in unix time, in seconds.
     */
    public long getTimeStart() {
        return timeStart;
    }

    /**
     * @return the time the attempt ended in unix time, in seconds.
     */
    public long getTimeEnd() {
        return timeEnd;
    }

    /**
     * @return unmodifiable map of user settings that reflects them at the time
     *         of creation of this level interaction.
     */
    public Map<String, ?> getUserSettings() {
        return Collections.unmodifiableMap(userSettings);
    }

    /**
     * The attempted levels.
     * 
     * @return
     */
    public List<LevelAttempt> getLevelAttempts() {
        return levelAttempts;
    }
}
