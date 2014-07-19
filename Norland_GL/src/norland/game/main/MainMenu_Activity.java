package norland.game.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class MainMenu_Activity extends Activity implements Callback {

    public static GlMainMenu MMglSurfaceView;

    private AlertDialog pauseDialog;

    // For handling alerts.
    private Handler alertHandler;
    private final String ALERTKEY = "alertkey";

    public enum AlertTypes {
        SOUND, TIPS;
    }

    private Boolean isShowingPauseDialog;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Initiate the Open GL view and
        // create an instance with this activity
        MMglSurfaceView = new GlMainMenu(this);
        // set our renderer to be the main renderer with the current activity
        // context
        // glSurfaceView.setMyRenderer(new GlRenderer(this));
        setContentView(MMglSurfaceView);

        alertHandler = new Handler(this);

        isShowingPauseDialog = false;

        /*
         * String test = getPackageName(); Log.d("TEST",test);
         */
    }

    @Override
    protected void onResume() {
        super.onResume();
        MMglSurfaceView.onResume();
    }
  

    @Override
    protected void onPause() {
        GlMainMenu.onPauseCalled = true;

        GlMainMenu.mediaPlayer.release();

        if (isShowingPauseDialog) {
            pauseDialog.cancel();
        }

        super.onPause();
        MMglSurfaceView.onPause();
        finish();
    }

    public boolean handleMessage(Message msg) {

        if (msg.getData().containsKey(ALERTKEY)) {
            /*
             * AlertTypes ats =
             * AlertTypes.valueOf(msg.getData().getString(ALERTKEY));
             * switch(ats){ case SOUND: createSoundDialog(); break; case TIPS:
             * createTipsDialog(); break; default:
             * System.err.println("Invalid Alert Dialog. "+ats); break; }
             */

            return true;
        }
        createBooleanDialog();
        return false;
    }

    public static final String SHAREDPREFFILE = "norland_menu_preferences";

    private void createBooleanDialog() {
        Builder builder = new AlertDialog.Builder(this);

        builder.setNegativeButton("Off", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getSharedPreferences(SHAREDPREFFILE, MODE_PRIVATE).edit()
                        .putBoolean(current_accessKey, false).commit();
                pauseDialog.cancel();
                isShowingPauseDialog = false;
            }
        });
        builder.setPositiveButton("On", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                getSharedPreferences(SHAREDPREFFILE, MODE_PRIVATE).edit()
                        .putBoolean(current_accessKey, true).commit();
                pauseDialog.cancel();
                isShowingPauseDialog = false;
            }
        });

        builder.setMessage(current_title);
        pauseDialog = builder.show();
        isShowingPauseDialog = true;

    }

    private static String current_title;
    private static String current_accessKey;

    protected void displayAlert(String title, String accessKey) {
        current_title = title;
        current_accessKey = accessKey;
        Bundle theBundle = new Bundle(); // bundles are key->value stores that
                                         // you can toss things into.
        // theBundle.putString(ALERTKEY, alertType.toString());
        Message theMessage = new Message();
        theMessage.setData(theBundle);
        alertHandler.sendMessage(theMessage);
    }

}
