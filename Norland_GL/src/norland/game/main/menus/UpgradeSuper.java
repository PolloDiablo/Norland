package norland.game.main.menus;

import java.util.HashSet;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.Thing;
import norland.game.main.VisualDynamic;
import android.content.Context;

public abstract class UpgradeSuper{

	protected Set<UpgradeMenuItem> menuItems;
	protected Set<VisualDynamic> visuals;

	protected static final int upgradeVisualDynamicSize = 100;
	
	public UpgradeSuper(){
		menuItems = new HashSet<UpgradeMenuItem>(); //TODO is this a good type of set to use? We are just adding and iterating
		visuals = new HashSet<VisualDynamic>(); //TODO is this a good type of set to use? We are just adding and iterating
	}
	
	/** Create each of the buttons.*/
	public abstract void addStuff();
	
	/** Check for collisions between all buttons and the clicker and do the resulting logics.
	 * IMPORTANT: call super.updateButtonPositions first*/
	public abstract void update(Thing clickSelection, Context context);
	
	/**Update the position of each button based on camera location, also update the hitbox of the button.*/
	protected void updateMenuItemPositions(){
		for(UpgradeMenuItem i : menuItems){
			i.update();
		}
	}
	
	/**Upgrade menus must draw their Visuals*/
	// TODO can this be generalized at all?
	public abstract void onDrawFrame(GL10 gl, Context context);
	
	/**Call init + does bitmaps for all the Things AND Visuals (buttons in the menu)*/
	public void initiateShapes(GL10 gl, Context context) {
		for(UpgradeMenuItem i : menuItems){
			i.initShape(gl, context);
		}
		for(VisualDynamic v : visuals){
			v.initShape(gl, context);
		}
	}
	
}
