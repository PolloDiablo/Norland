package norland.game.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.VideoView;



public class VidMenu_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.video);
        Button b = (Button) findViewById(R.id.donevid_button);
        b.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private static String videoURI;
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("VidMenu","Video Created");
        chooseVideo();
        if(videoURI.matches("none")){
        	onPause();
        	finish();
        	
        }else{
	        VideoView myVideoView = (VideoView) findViewById(R.id.surface_view);
	        myVideoView.setVideoURI(Uri.parse(videoURI));
	        myVideoView.setMediaController(null);
	        myVideoView.requestFocus();
	        myVideoView.setOnPreparedListener(new OnPreparedListener(){
	           public void onPrepared(MediaPlayer m) {
	                 try {
	                	 if(!getBaseContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
	                             Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_soundOn, true)){
	                		 m.setVolume(0f, 0f); 
	                	 }
	           
	                 } catch (Exception e) {
	                	 e.printStackTrace();
	                 }    
	             }
	         });
	         
	        myVideoView.setOnCompletionListener(new OnCompletionListener() {
	            public void onCompletion(MediaPlayer mp) {
	            	finish();
	                /*mp.setOnErrorListener(new OnErrorListener() {
	                    public boolean onError(MediaPlayer mp, int what, int extra) {
	                        Log.e("MPLAY", "what: " + what + ", extra: " + extra);
	                        return false;
	                    }
	                });
	                Log.i("MPLAY", "Media Player Completed?");
	                mp.seekTo(0);
	                mp.setOnSeekCompleteListener(new OnSeekCompleteListener() {
	                    public void onSeekComplete(MediaPlayer mp) {
	                        mp.start();
	                        try {
	                            Thread.sleep(100);
	                        } catch (InterruptedException e) {
	                        }
	                        mp.pause();
	                    }
	                });*/
	                
	            }
	        });
	        
	        myVideoView.start();
        }
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK){
	    	Log.d("VidMenu_Activity","Back button pressed, exiting to main menu.");
	    	launchMainMenu_Activity();
		    super.onPause();
		    return true;
	    }
	    return false;
	}

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    // // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.activity_main, menu);
    // return true;
    // }

    private void launchNorland_GLActivity() {
    	Log.d("VidMenu_Activity","Starting Norland_GlActivity intent");
        startActivity(new Intent(this, Norland_GLActivity.class));
    }
    private void launchMainMenu_Activity() {
    	Log.d("VidMenu_Activity","Starting MainMenu_Activity intent");
        startActivity(new Intent(this, MainMenu_Activity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
    	if(GlRenderer.getNextLevel()!=101){
            launchNorland_GLActivity();
    	}else{
    		launchMainMenu_Activity();
    	}
    }
    
    private void chooseVideo() {
    	//TODO
        switch (GlRenderer.getNextLevel()) {
            case 3:
            	videoURI = "android.resource://norland.game.main/raw/video_a1s1_end";
                break;  
            case 6:
            	videoURI = "android.resource://norland.game.main/raw/video_a1s2_end";
                break;
            case 9:
            	videoURI = "android.resource://norland.game.main/raw/video_a1s3_end";
                break;
            case 10:
            	videoURI = "none";
                break;
            case 11:
            	videoURI = "none";
                break;
            case 12:
            	videoURI = "none";
                break;
            case 13:
            	videoURI = "none";
                break;
            case 14:
            	videoURI = "none";;
                break;
            case 15:
            	videoURI = "none";
                break;
            case 16:
            	videoURI = "none";
                break;
            case 17:
            	videoURI = "none";
                break;
            case 18:
            	videoURI = "none";
                break;
            case 19:
            	videoURI = "none";
                break;
            case 20:
            	videoURI = "none";
                break;
            case 21:
            	videoURI = "none";
                break;
            case 22:
            	videoURI = "none";
                break;
            case 23:
            	videoURI = "none";
                break;
            case 24:
            	videoURI = "none";
                break;
            case 25:
            	videoURI = "none";
                break;
            case 26:
            	videoURI = "none";
                break;
            case 27:
            	videoURI = "none";
                break;
            case 101:
            	//Special Case: Launch the credits
            	videoURI = "android.resource://norland.game.main/raw/video_credits";
                break;
            default:
            	videoURI = "none";
                System.err.println("Video Selection ERROR");
                break;
        }
    }
}
