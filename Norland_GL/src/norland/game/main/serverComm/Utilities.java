package norland.game.main.serverComm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Common methods for interacting with the score/logging/stats server.
 * 
 * @author Main
 */
public class Utilities {

	/**
	 * update the high score with the appropriate kills values.
	 * 
	 * @param uid
	 *            the user id.
	 * @param uName
	 *            the user name.
	 * @param dragonKillCount
	 *            the number of dragons killed.
	 * @param bergKillCount
	 *            the number of bergs killed.
	 */
	@Deprecated
	public static void updateHighScore(final long uid, final String uname,
			final int dragonKillCount, final int bergKillCount) {
		// http://docs.oracle.com/javase/tutorial/networking/urls/connecting.html
		try {

			URL myURL = new URL(
					"http://norland.casualt.ca/?secret=a823iun342jksd89unsdfji3289&uid="
							+ uid + "&uname=" + uname + "&dragons="
							+ dragonKillCount + "&bergs=" + bergKillCount);
			System.err
					.println("HighScore: http://norland.casualt.ca/?secret=a823iun342jksd89unsdfji3289&uid="
							+ uid
							+ "&uname="
							+ uname
							+ "&dragons="
							+ dragonKillCount + "&bergs=" + bergKillCount);
			URLConnection myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					myURLConnection.getInputStream()));
			while (reader.readLine() != null) {
				// don't care...just read it all in.
			}

		}
		/*
		 * catch (MalformedURLException e) { // new URL() failed // ... } catch
		 * (IOException e) { // openConnection() failed // ... }
		 */
		catch (Exception e) {
			System.err.println("Error updating highscore.");
			e.printStackTrace();
		}

	}
}
