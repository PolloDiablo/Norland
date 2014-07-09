package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_Return implements MenuState {

	//Switch ID: 15
	private Thing title;
	private Thing retry;
	private Thing returnToMenu;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_Return");
		
		title = new Thing(GlMainMenu.bitmapTitle_death, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*125, 512, 128);
		retry = new Thing(GlMainMenu.bitmapRetry, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		returnToMenu = new Thing(GlMainMenu.bitmapExit, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*125+GlMainMenu.menuShiftY);
		
		retry.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		retry.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		returnToMenu.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		returnToMenu.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		retry.update();
		returnToMenu.update();
		
		if(retry.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(GlMainMenu.levelThatTheUserDiedOn,context);
		}
		
		if(returnToMenu.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT=1;
		}
		
		GlMainMenu.clickSelection.setX(10000);
		GlMainMenu.clickSelection.setY(10000);
		
	}

	public void onDrawFrame(GL10 gl, Context context) {
		title.draw(gl);
		retry.draw(gl);
		returnToMenu.draw(gl);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		retry.initShape(gl, context);
		returnToMenu.initShape(gl, context);	
	}

}
