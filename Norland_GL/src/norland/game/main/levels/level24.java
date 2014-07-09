package norland.game.main.levels;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.R;

import android.content.Context;
import android.media.MediaPlayer;

public class level24 extends LevelSuper{

	public void addStuff() {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		
	}

	public void initiateShapes(GL10 gl, Context context) {
		// TODO Auto-generated method stub
		
	}

	public void finishLevel(Context context) {
		// TODO Auto-generated method stub
		
	}

	public void startMusic(Context context) {
		// TODO Auto-generated method stub
		GlMainMenu.mediaPlayer = MediaPlayer.create(context, R.raw.norlandmaintitle);
	}

}
