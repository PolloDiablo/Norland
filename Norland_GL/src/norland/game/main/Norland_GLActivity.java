package norland.game.main;

import norland.game.main.projectiles.Projectile;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

//TODO: hey Jeremy, check this out: http://developer.android.com/resources/tutorials/opengl/opengl-es10.html
//Also, these might help too? http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/graphics/index.html

public class Norland_GLActivity extends Activity implements Callback {
	
	//private static final String TAG = Norland_GLActivity.class.getSimpleName();
	
	/** The OpenGL view */
	private GlRenderer glSurfaceView;

	private AlertDialog pauseDialog;

	private boolean stayPaused;
	private boolean justShown;

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
        
        glSurfaceView = new GlRenderer(this);
        
        // set our renderer to be the main renderer with
        //the current activity context
        //glSurfaceView.setMyRenderer(new GlRenderer(this));
        setContentView(glSurfaceView);
        
        stayPaused=true;
        
        alertHandler = new Handler(this);
        isShowingPauseDialog=false;
    }
	/** Remember to resume the glSurface  */
	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	}

	/** Also pause the glSurface  */
	@Override
	protected void onPause() {
		
		//Get rid of all of the Projectiles
		for(int i=0; i<GlRenderer.projectiles.size();i++){
			GlRenderer.projectiles.remove(i);
			i--;		
		}
		for(int i=0; i<GlRenderer.wakes.size();i++){
			GlRenderer.wakes.remove(i);
			i--;		
		}
		Projectile.clearAllPools();
		
		
		GlRenderer.runningUpgradeScreen=false;		
		GlRenderer.onPauseCalled=true;
		/*if(GlRenderer.mediaPlayer.isPlaying()){
			GlRenderer.mediaPlayer.stop();
		}*/
		GlMainMenu.mediaPlayer.release();
		
		if(isShowingPauseDialog){
			pauseDialog.cancel();
		}
		
		glSurfaceView.onPause();
		super.onPause();
		finish();	
	}	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_MENU && !GlRenderer.runningUpgradeScreen){
	    		
	    		if(!GlRenderer.paused){
		    		GlRenderer.paused=true;
					enterPauseMenu();
	    		}else{
	    			Log.d("onkeydown","not sure why here, but unpausing.");
	    			if(pauseDialog.isShowing()){
	    				pauseDialog.cancel();
	    			}else{
	    				GlRenderer.paused = false;
	    			}
	    		}
	    	return true;
	    }
	    
	   if (keyCode == KeyEvent.KEYCODE_BACK){
	    	if(!GlRenderer.runningUpgradeScreen){
	    		GlRenderer.backToMenu=true;
	    	}else{
	    		GlRenderer.endUpgradeScreen=true;
	    	}
	    	return true;
	    }
   
	    return false;
	}
	
	
	
	
private void enterPauseMenu(){
		Builder builder = new AlertDialog.Builder(this);
		
		builder.setNegativeButton("Cancel", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				stayPaused=false;
				pauseDialog.cancel();
			}
		});
		
		builder.setNeutralButton("Options", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				stayPaused=true;
				pauseDialog.cancel();
				enterOptionsMenu();
			}
		});
		
		builder.setPositiveButton("Exit", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				pauseDialog.cancel();
				GlRenderer.backToMenu=true;
			}
		});
				
		
		
		builder.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface arg0) {
				if(!stayPaused){
					GlRenderer.paused=false;
				}
			}
		});
		
		builder.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(!justShown){
					if (keyCode == KeyEvent.KEYCODE_MENU ){
						stayPaused=false;
						pauseDialog.cancel();
						isShowingPauseDialog=false;
					}
					if (keyCode == KeyEvent.KEYCODE_BACK ){
						stayPaused=false;
						pauseDialog.cancel();
						isShowingPauseDialog=false;
					}
				}else{
					justShown = false;
				}
				return true;
			}
		});
		
	    builder.setMessage("Level " + GlRenderer.getNextLevel() + " - Paused");
	    pauseDialog = builder.show();
	    isShowingPauseDialog=true;
	    justShown = true;
	}
private void enterOptionsMenu(){
		
		GlRenderer.paused=true;
		
		Builder builder = new AlertDialog.Builder(this);
		
		
		builder.setNegativeButton("Cancel", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				pauseDialog.cancel();
				enterPauseMenu();
			}
		});
		builder.setNeutralButton("Sound", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				stayPaused=true;
				pauseDialog.cancel();
				enterSoundMenu();
			}
		});
		
		builder.setPositiveButton("Tips", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				stayPaused=true;
				pauseDialog.cancel();
				enterTipsMenu();
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface arg0) {
				if(!stayPaused){
					GlRenderer.paused=false;
				    isShowingPauseDialog=false;
				}
			}
		});
		
		builder.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(!justShown){
					if (keyCode == KeyEvent.KEYCODE_MENU ){
						stayPaused=false;
						pauseDialog.cancel();
					    isShowingPauseDialog=false;
					}
					if (keyCode == KeyEvent.KEYCODE_BACK ){
						stayPaused=false;
						pauseDialog.cancel();
						isShowingPauseDialog=false;
					}
				}else{
					justShown = false;
				}
				return true;
			}
		});
		
	    builder.setMessage("Options");
	    pauseDialog = builder.show();
	    isShowingPauseDialog=true;
	    justShown = true;
	}
private void enterSoundMenu(){
		
		GlRenderer.paused=true;
		
		Builder builder = new AlertDialog.Builder(this);	
		
		builder.setNegativeButton("Off", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_soundOn, false).commit();
				stayPaused=true;
				pauseDialog.cancel();
				enterOptionsMenu();
			}
		});
		builder.setPositiveButton("On", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_soundOn, true).commit();
				stayPaused=true;
				pauseDialog.cancel();
				enterOptionsMenu();
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface arg0) {
				if(!stayPaused){
					GlRenderer.paused=false;
				    isShowingPauseDialog=false;
				}
			}
		});
		
		builder.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(!justShown){
					if (keyCode == KeyEvent.KEYCODE_MENU ){
						stayPaused=false;
						pauseDialog.cancel();
					    isShowingPauseDialog=false;
					}
					if (keyCode == KeyEvent.KEYCODE_BACK ){
						stayPaused=false;
						pauseDialog.cancel();
						isShowingPauseDialog=false;
					}
				}else{
					justShown = false;
				}
				return true;
			}
		});
		
	    builder.setMessage("Sound");
	    pauseDialog = builder.show();
	    isShowingPauseDialog=true;
	    justShown = true;
	}
private void enterTipsMenu(){
		
		GlRenderer.paused=true;
		
		Builder builder = new AlertDialog.Builder(this);
		
		
		builder.setNegativeButton("Off", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_tipsOn, false).commit();
				stayPaused=true;
				pauseDialog.cancel();
				enterOptionsMenu();
			}
		});
		builder.setPositiveButton("On", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_tipsOn, true).commit();
				stayPaused=true;
				pauseDialog.cancel();
				enterOptionsMenu();
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface arg0) {
				if(!stayPaused){
					GlRenderer.paused=false;
				    isShowingPauseDialog=false;
				}
			}
		});
		
		builder.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if(!justShown){
					if (keyCode == KeyEvent.KEYCODE_MENU ){
						stayPaused=false;
						pauseDialog.cancel();
					    isShowingPauseDialog=false;
					}
					if (keyCode == KeyEvent.KEYCODE_BACK ){
						stayPaused=false;
						pauseDialog.cancel();
						isShowingPauseDialog=false;
					}
				}else{
					justShown = false;
				}
				return true;
			}
		});
		
	    builder.setMessage("Tips");
	    pauseDialog = builder.show();
	    isShowingPauseDialog=true;
	    justShown = true;
	}




//For handling tips
private Handler alertHandler;
private final String ALERTKEY = "alertkey";
private static String current_title;
private static String current_message;
private static boolean isShowingPauseDialog;

public boolean handleMessage(Message msg) {
	
	if(msg.getData().containsKey(ALERTKEY)){
		return true;
	}
	createBooleanDialog();
	return false;
}

private void createBooleanDialog() {
	Builder builder = new AlertDialog.Builder(this);
  
	builder.setPositiveButton("OK", new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			pauseDialog.cancel();
			isShowingPauseDialog=false;
			GlRenderer.paused=false;
		}
	}); 
	
	builder.setOnKeyListener(new OnKeyListener() {
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_MENU ){
				pauseDialog.cancel();
				isShowingPauseDialog=false;
				GlRenderer.paused=false;
			}
			if (keyCode == KeyEvent.KEYCODE_BACK ){
				pauseDialog.cancel();
				isShowingPauseDialog=false;
				GlRenderer.paused=false;
			}
			return true;
		}
	});
	builder.setTitle(current_title);
	builder.setMessage(current_message);
    pauseDialog = builder.show();
    isShowingPauseDialog=true;
   
}

public void displayTip(String title, String message){
	GlRenderer.paused=true;
	current_title=title;
	current_message=message;
	Bundle theBundle = new Bundle();	
	Message theMessage = new Message();
	theMessage.setData(theBundle);
	alertHandler.sendMessage(theMessage);
}

 
}