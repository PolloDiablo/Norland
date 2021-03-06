package ca.casualt.norland.exportformat;

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

    public StatsBundle() {
        loggedInteractions = new ArrayList<LevelInteraction>();
    }

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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StatsBundle: [loggedInteractions=");
        builder.append(loggedInteractions);
        builder.append("]");
        return builder.toString();
    }
}
