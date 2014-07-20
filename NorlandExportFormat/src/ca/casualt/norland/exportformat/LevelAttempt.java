package ca.casualt.norland.exportformat;

/**
 * A single level attempt log.
 * 
 * @author Tony
 */
public final class LevelAttempt {

    /**
     * Unix time in seconds, of when the level was attempted.
     */
    private final long timeStart;

    /**
     * Unix time in seconds, of when the level attempt ended.
     */
    private long timeEnd = -1;

    /**
     * Whether the attempt was a success or not.
     */
    private boolean success = false;

    /**
     * Tracks the kills on the level.
     */
    private final KillObject kills = new KillObject();

    /**
     * Start the level attempt and create the object.
     * 
     * @param context
     *            used to pull in shared preferences.<br>
     *            TODO: eventually this operation might be wrapped easier in the
     *            upgrade menu class.
     */
    public LevelAttempt() {
        timeStart = getCurrentTimeInSeconds();
    }

    /**
     * Record when the level is complete.
     * 
     * @param successIn
     *            whether or not the level was a success.
     */
    public void levelComplete(final boolean successIn) {
        timeEnd = getCurrentTimeInSeconds();
        setSuccess(successIn);
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
     * @return was the level completed.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            record whether the level was finished or not.
     */
    private void setSuccess(final boolean success) {
        this.success = success;
    }

    /**
     * @return get the kills object.
     */
    public KillObject getKills() {
        return kills;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LevelAttempt: [timeStart=");
        builder.append(timeStart);
        builder.append(", timeEnd=");
        builder.append(timeEnd);
        builder.append(", success=");
        builder.append(success);
        builder.append(", kills=");
        builder.append(kills);
        builder.append("]");
        return builder.toString();
    }
}
