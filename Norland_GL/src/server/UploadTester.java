package server;

import norland.game.main.serverComm.Utilities;

/**
 * For testing the upload server quickly from within java.
 * 
 * @author Main
 */
public class UploadTester {

    /**
     * Run me to test the upload server.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        final long uid = 12345;
        final String uname = "bob";
        Utilities.updateHighScore(uid, uname, 99, 101);
    }
}
