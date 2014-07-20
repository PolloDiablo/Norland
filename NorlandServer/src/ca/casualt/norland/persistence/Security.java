package ca.casualt.norland.persistence;

/**
 * Used for security checks.
 * 
 * @author Tony
 */
public final class Security {

    private static final Security THEINSTANCE = new Security();

    /**
     * Singleton.
     */
    private Security() {
    }

    /**
     * @return the singleton.
     */
    public static Security getTheInstance() {
        return THEINSTANCE;
    }

    // TODO: Add in multiple id-token support.

    /**
     * Currently only supported id.
     */
    private static final String SINGLEID = "norland";

    /**
     * Currently only supported
     */
    private static final String SINGLETOKEN = "lolwhat?!";

    /**
     * Check to see if the given id and token are authorized to write stats.
     * 
     * @param id
     *            the id to check.
     * @param token
     *            the token to check.
     * @return true if authorized.
     */
    public boolean isAuthorizedToWriteStats(final String id, final String token) {
        return SINGLEID.equals(id) && SINGLETOKEN.equals(token);
    }
}
