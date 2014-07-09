package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.Thing;
import android.content.Context;
import android.util.Log;



public class Menu_Options implements MenuState {

	//Switch ID: 3
	private Thing title;
	private Thing sound;
	private Thing tips;
	private Thing network;
	private Thing cancel;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_Options");

		title = new Thing(GlMainMenu.bitmapTitle_options, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 256, 64);
		sound = new Thing(GlMainMenu.bitmapSound, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		tips = new Thing(GlMainMenu.bitmapTips, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		network = new Thing(GlMainMenu.bitmapNetwork, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
	}

	public void update(Context context) {
		
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		sound.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		sound.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		tips.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		tips.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		network.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		network.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		sound.update();
		tips.update();
		network.update();
		cancel.update();
		
		
		
		if(sound.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.showSoundAlert();
		}
		
		if(tips.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.showTipsAlert();
		}
		
		if(network.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.showNetworkAlert();
		}

		if(cancel.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 1;	
		}	
		
		GlMainMenu.clickSelection.setX(10000);
		GlMainMenu.clickSelection.setY(10000);
		
	}

	public void initiateShapes(GL10 gl, Context context) {
		
		title.initShape(gl, context);
		sound.initShape(gl, context);
		tips.initShape(gl, context);
		network.initShape(gl, context);
		cancel.initShape(gl, context);	
		
	}

	public void onDrawFrame(GL10 gl,Context context) {
		title.draw(gl);
		sound.draw(gl);
		tips.draw(gl);
		network.draw(gl);
		cancel.draw(gl);
		
	}

}
