package ca.casualt.norland.exportformat;

/**
 * This class is just to enforce type safety on level numbers for statistics.
 * 
 * @author Tony
 */
public final class LevelNumber {
    /**
     * The level number.
     */
    private final int levelNumber;

    /**
     * A level number with this number.
     * 
     * @param levelNumberIn
     */
    public LevelNumber(final int levelNumberIn) {
        this.levelNumber = levelNumberIn;
    }

    /**
     * @return the number of the level.
     */
    public int getLevelNumber() {
        return levelNumber;
    }
}
