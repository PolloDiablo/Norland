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
     * Default constructor.
     */
    public LevelNumber() {
        levelNumber = 0;
    }

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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LevelNumber: [levelNumber=");
        builder.append(levelNumber);
        builder.append("]");
        return builder.toString();
    }
}
