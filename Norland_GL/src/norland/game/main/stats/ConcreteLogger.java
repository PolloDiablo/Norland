package norland.game.main.stats;

import java.util.Stack;

import norland.game.main.stats.export.ExportHandler;
import android.content.Context;
import android.util.Log;

/**
 * An implementation of the LoggerInterface.
 * 
 * @author Tony
 */
public class ConcreteLogger implements LoggerInterface {

    /**
     * Logged Interactions.
     */
    private final Stack<LevelInteraction> loggedInteractions = new Stack<LevelInteraction>();

    /**
     * Latest level attempt.
     */
    private LevelAttempt latestAttempt = null;

    /**
     * Default constructor.
     */
    public ConcreteLogger() {
    }

    public synchronized void startNewLevel(final LevelNumber levelNumberIn, final Context context) {
        loggedInteractions.push(new LevelInteraction(context));
    }

    public synchronized void newLevelAttempt() {
        latestAttempt = new LevelAttempt();
    }

    public synchronized void levelKill(final CharacterType characterTypeIn) {
        if (latestAttempt == null) {
            System.err.println("Can't kill character without new attempt.");
        } else {
            latestAttempt.getKills().killedCharacter(characterTypeIn);
        }
    }

    public synchronized void attemptOutcome(final boolean outcome) {
        if (latestAttempt == null) {
            System.err.println("Can't complete level attempt without new attempt.");
        } else {
            latestAttempt.levelComplete(outcome);
            loggedInteractions.peek().getLevelAttempts().add(latestAttempt);
            latestAttempt = null;
        }
    }

    public synchronized void levelInteractionComplete() {
        loggedInteractions.peek().levelInteractionComplete();
    }

    /**
     * Clear the stats.
     */
    private void clear() {
        loggedInteractions.clear();
        latestAttempt = null;
    }

    /**
     * Will attempt a blocking upload of the stats. Context is required to
     * determine network connectivity information. (as stats will only upload
     * over wifi).
     */
    public synchronized void uploadLoggedStats(final Context context) {
        ExportHandler eh = new ExportHandler(context, false);
        StatsBundle toExport = new StatsBundle(loggedInteractions);
        boolean result = eh.attemptUpload(toExport);
        Log.i("ConcreteLogger", "Result of uploadLoggedStats = " + result);
        if (result) {
            clear();
        }
    }
}
