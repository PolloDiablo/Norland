package norland.game.main.stats;

import android.content.Context;

/**
 * A Logger must implement this interface.
 * 
 * @author Tony
 */
public interface LoggerInterface {

    /**
     * Log that you are starting a new level.
     * 
     * @param levelNumberIn
     *            the level that you are starting.
     * @param context
     *            the context run in.
     */
    public void startNewLevel(LevelNumber levelNumberIn, Context context);

    /**
     * Log a level attempt.
     */
    public void newLevelAttempt();

    /**
     * Log a level kill.
     */
    public void levelKill(CharacterType characterTypeIn);

    /**
     * Log a level attempt completed.
     * 
     * @param outcome
     *            (success or failure).
     */
    public void attemptOutcome(boolean outcome);

    /**
     * Complete a level interaction.
     */
    public void levelInteractionComplete();

    /**
     * Upload all logged level interactions.
     */
    public void uploadLoggedStats();

}
