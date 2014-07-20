package ca.casualt.norland.shared;

import javax.servlet.ServletRequest;

import com.google.gson.Gson;

/**
 * ServletRequest variable extraction.<br>
 * TODO: Sanitizing?
 * 
 * @author Tony
 */
public final class RequestAccess {
    /**
     * Utility Class.
     */
    private RequestAccess() {
    }

    /**
     * Returns the id, if available. (otherwise empty string).
     * 
     * @param req
     *            to check.
     * @return the id string.
     */
    public static String getID(final ServletRequest req) {
        final String toReturn = getParam(req, RequestKeys.KEY_ID);
        return toReturn;
    }

    /**
     * Returns the token, if available. (otherwise empty string).
     * 
     * @param req
     *            to check.
     * @return the token string.
     */
    public static String getToken(final ServletRequest req) {
        final String toReturn = getParam(req, RequestKeys.KEY_TOKEN);
        return toReturn;
    }

    /**
     * Returns the data, if available. (otherwise empty string).
     * 
     * @param req
     *            to check.
     * @return the token string.
     */
    public static String getData(final ServletRequest req) {
        final String toReturn = getParam(req, RequestKeys.KEY_DATA);
        return toReturn;
    }

    /**
     * Attempt to pull out the json data into the specified class type.
     * 
     * @param req
     *            to check
     * @param classOfT
     *            class to convert the json to. (via gson).
     * @return the data as a real java class.
     */
    public static <T> T getData(final ServletRequest req, Class<T> classOfT) {
        String json = getData(req);
        return new Gson().fromJson(json, classOfT);
    }

    /*
     * Shared.
     */
    /**
     * Returns the parameter, if available. (otherwise empty string).
     * 
     * @param req
     *            to check.
     * @return the parameter string.
     */
    private static String getParam(final ServletRequest req, final String key) {
        final String token = req.getParameter(key);
        if (token == null) {
            return "";
        } else {
            return token;
        }
    }
}
