package ca.casualt.norland.shared;

/**
 * The keys that can be present in requests.
 * 
 * @author Tony
 */
final class RequestKeys {
    /**
     * Utility Class.
     */
    private RequestKeys() {
    }

    /**
     * The identification of the user.
     */
    static final String KEY_ID = "id";

    /**
     * The security token.
     */
    static final String KEY_TOKEN = "token";

    /**
     * The passed data (json).
     */
    static final String KEY_DATA = "data";
}
