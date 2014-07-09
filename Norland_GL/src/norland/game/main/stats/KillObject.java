package norland.game.main.stats;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Contains information about enemy kills, during a level attempt.
 * 
 * @author Main
 */
public final class KillObject {
    /**
     * Hashmap of all the kill counts.
     */
    private final EnumMap<CharacterType, Integer> killCounts = new EnumMap<CharacterType, Integer>(
            CharacterType.class);

    /**
     * Default constructor.
     */
    public KillObject() {
    }

    /**
     * Record that the specified character was killed.
     * 
     * @param ct
     *            the character type.
     */
    public void killedCharacter(final CharacterType ct) {
        if (ct == null) {
            return;
        }
        Integer count = getKillCounts().get(ct);
        if (count == null) {
            count = 0;
        }
        count++;
        killCounts.put(ct, count);
    }

    /**
     * @return Get all the kill counts. (Collection is unmodifiable).
     */
    public Map<CharacterType, Integer> getKillCounts() {
        return Collections.unmodifiableMap(killCounts);
    }

    /**
     * Returns the kill count. -1 if not valid input.
     * 
     * @param ct
     *            the character type.
     * @return the appropriate kill count.
     */
    public int getKillCount(final CharacterType ct) {
        if (ct == null) {
            return -1;
        }
        Integer toReturn = killCounts.get(ct);
        if (toReturn == null) {
            toReturn = 0;
        }
        return toReturn;
    }

}
