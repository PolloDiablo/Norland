package norland.game.main.levels;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;


public interface Level {
	
	//TODO say what each function must do
	
	public void addStuff();
	
	/* Update:
	 *  - Caclulate and set angle for compass needle
	 *  - Handle the updating, drawing, but not initiation of projectiles
	 *  - Handle all collisions
	 * 
	 */
	public void update();
	public void handleTips();
	public void onDrawFrame(GL10 gl, Context context);
	public void initiateShapes(GL10 gl, Context context);
	
	//TODO Should start island cache instead of main menu (on some levels)
	//TODO music that runs in the GlRenderer should be stopped in this function
	public void finishLevel(Context context);
	
	public void startMusic(Context context);
}
