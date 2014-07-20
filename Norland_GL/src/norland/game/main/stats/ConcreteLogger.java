package norland.game.main.stats;

import java.util.Map;
import java.util.Stack;

import norland.game.main.UpgradeMain;
import norland.game.main.stats.export.ExportHandler;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import ca.casualt.norland.exportformat.CharacterType;
import ca.casualt.norland.exportformat.LevelAttempt;
import ca.casualt.norland.exportformat.LevelInteraction;
import ca.casualt.norland.exportformat.LevelNumber;
import ca.casualt.norland.exportformat.StatsBundle;

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
        SharedPreferences sp = UpgradeMain.getStandardSharedPreferences(context);
        Map<String, ?> userSettings = sp.getAll();
        loggedInteractions.push(new LevelInteraction(userSettings));
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
