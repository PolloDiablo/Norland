package norland.game.main;

import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class GetInformation {

    /**
     * Get the userid based on the subsriber imsi.
     * 
     * @return
     */
    public static long getUID(final Context context) {
        long uid;
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            uid = Long.parseLong(tm.getSubscriberId()); // imsi's are hashed
                                                        // server side for
                                                        // privacy's sake.
            // TODO: client-side hashing?
        } catch (Exception e) {
            uid = 0; // a temporary solution
        }
        return uid;
    }

    /**
     * Get the username from their google account for now.<br>
     * TODO: We get the user name as their google account for now...can setup
     * user-specific later.
     * 
     * @param context
     * @return
     */
    public static String getUserName(final Context context) {
        String uname;
        try {
            AccountManager accounts = AccountManager.get(context);
            uname = accounts.getAccounts()[0].name.split("@")[0].replace('&', '_')
                    .replace('?', '_'); // TODO: be more thorough with making it
                                        // websafe.
                                        // <-- java might have a good function
                                        // for doing
                                        // it in one call.
            Log.i("HighScore", "username: " + uname);
            /*
             * for(Account a:accounts.getAccounts()){
             * Log.i("HighScore","Account name: "+a.name); }
             */

        } catch (Exception e) {
            uname = "bob";
            Log.e("HighScore", "Error getting username.");
            e.printStackTrace();
        }
        return uname;
    }
}
