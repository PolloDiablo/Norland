package norland.game.main.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Used to send up information to the server.
 * 
 * @author Tony
 */
public final class StatsBundle {

    private final List<LevelInteraction> loggedInteractions;

    /**
     * The StatsBundle.
     * 
     * @param loggedInteractions
     */
    public StatsBundle(final Stack<LevelInteraction> loggedInteractions) {
        this.loggedInteractions = new ArrayList<LevelInteraction>(loggedInteractions);
    }

    /**
     * @return the loggedInteractions
     */
    public List<LevelInteraction> getLoggedInteractions() {
        return loggedInteractions;
    }
}
