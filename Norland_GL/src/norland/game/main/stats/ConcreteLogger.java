package norland.game.main.stats;

import java.util.Stack;

import android.content.Context;

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
    private LevelAttempt latestAttempt;

    public void startNewLevel(final LevelNumber levelNumberIn, final Context context) {
        loggedInteractions.push(new LevelInteraction(context));
    }

    public void newLevelAttempt() {
        latestAttempt = new LevelAttempt();
    }

    public void levelKill(final CharacterType characterTypeIn) {
        if (latestAttempt == null) {
            System.err.println("Can't kill character without new attempt.");
        } else {
            latestAttempt.getKills().killedCharacter(characterTypeIn);
        }
    }

    public void attemptOutcome(final boolean outcome) {
        if (latestAttempt == null) {
            System.err.println("Can't complete level attempt without new attempt.");
        } else {
            latestAttempt.levelComplete(outcome);
            loggedInteractions.peek().getLevelAttempts().add(latestAttempt);
            latestAttempt = null;
        }
    }

    public void levelInteractionComplete() {
        loggedInteractions.peek().levelInteractionComplete();
    }

    /**
     * For now, this implementation doesn't upload anything yet.
     */
    public void uploadLoggedStats() {
        // TODO Auto-generated method stub
    }

}
