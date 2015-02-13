package norland.game.main.menus;

import java.util.HashSet;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.Thing;
import android.content.Context;

//Need one of these for every screen in the menu
public abstract class MenuSuper {
	
	public MenuSuper(){
		buttons = new HashSet<MenuItem>(); //TODO is this a good type of set to use? We are just adding and iterating
	}
	
	protected Set<MenuItem> buttons;

	/** Create each of the buttons.*/
	public abstract void addStuff(Context context);
	
	/** Check for collisions between all buttons and the clicker and do the resulting logics.
	 * IMPORTANT: call super.updateButtonPositions first*/
	public abstract void update(Thing clickSelection, Context context);
	
	/**Update the position of each button based on camera location, also update the hitbox of the button.*/
	protected void updateButtonPositions(){
		for(MenuItem b : buttons){
			b.update();
		}
	}
	

	
	/**Call draw for all the Things (buttons in the menu)*/
	public void onDrawFrame(GL10 gl, Context context) {
		for(MenuItem b : buttons){
			b.draw(gl);
		}
	}
	
	/**Call init for all the Things (buttons in the menu)*/
	public void initiateShapes(GL10 gl, Context context) {
		for(MenuItem b : buttons){
			b.initShape(gl, context);
		}
	}
}
