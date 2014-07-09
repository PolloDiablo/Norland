package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.Thing;

import android.content.Context;

public class Menu_EasterEggs implements MenuState {

	//Switch ID: 14
	private Thing title;
	private Thing rubberDucky;
	private Thing maliciousIcebergs;
	private Thing cancel;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_EasterEggs");
	
		title = new Thing(GlMainMenu.bitmapTitle_eastereggs, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		rubberDucky = new Thing(GlMainMenu.bitmapRubberDuckyMode, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 512, 64);
		maliciousIcebergs = new Thing(GlMainMenu.bitmapEvilIcebergsMode, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 512, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		rubberDucky.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		rubberDucky.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);
		
		maliciousIcebergs.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		maliciousIcebergs.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		cancel.update();
		rubberDucky.update();
		maliciousIcebergs.update();
		
		if(cancel.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT=13;
		}
		
		if(rubberDucky.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.showRubberDuckyAlert();
		}
		if(maliciousIcebergs.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.showEvilIcebergsAlert();
		}
		
		GlMainMenu.clickSelection.setX(10000);
		GlMainMenu.clickSelection.setY(10000);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		rubberDucky.initShape(gl, context);
		maliciousIcebergs.initShape(gl, context);
		cancel.initShape(gl, context);
	}

	public void onDrawFrame(GL10 gl,Context context) {
		title.draw(gl);
		rubberDucky.draw(gl);
		maliciousIcebergs.draw(gl);
		cancel.draw(gl);
	}

}
