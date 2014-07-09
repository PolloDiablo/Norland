package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

//Need one of these for every screen in the menu
public interface MenuState {
	
	//Initialize all the things
	public void addStuff();
	
	//Update and check for collisions
	public void update(Context context);
	
	//Call draw for all the things
	public void onDrawFrame(GL10 gl, Context context);
	
	//Call init for all the things
	public void initiateShapes(GL10 gl, Context context);
}
