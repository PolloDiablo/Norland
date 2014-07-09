package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_a3s2 implements MenuState {

	//Switch ID: 11
	private Thing title;
	private Thing level21;
	private Thing level22;
	private Thing level23;
	private Thing cancel;
	private Thing previous;
	private Thing next;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a3s2");
		
		title = new Thing(GlMainMenu.bitmapa3s2, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		level21 = new Thing(GlMainMenu.bitmapLevel21, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		level22 = new Thing(GlMainMenu.bitmapLevel22, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		level23 = new Thing(GlMainMenu.bitmapLevel23, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		previous = new Thing(GlMainMenu.bitmapPrevious, 0.25*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		next = new Thing(GlMainMenu.bitmapNext, 0.75*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		level21.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level21.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		level22.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level22.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		level23.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level23.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		previous.setX(0.25*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		previous.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		next.setX(0.75*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		next.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		level21.update();
		level22.update();
		level23.update();
		previous.update();
		next.update();
		cancel.update();
		
		
		

		if(level21.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(21,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 22){
			if(level22.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(22,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 23){
			if(level23.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(23,context);
			}
		}

		if(previous.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 10;	
		}	
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 24){
			if(next.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.USER_MENU_SELECT = 12;	
			}	
		}	

		if(cancel.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
		
		GlMainMenu.clickSelection.setX(10000);
		GlMainMenu.clickSelection.setY(10000);
		
	}

	public void onDrawFrame(GL10 gl,Context context) {
		title.draw(gl);
		level21.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 22){
			level22.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 23){
			level23.draw(gl);
		}
		previous.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 24){
			next.draw(gl);
		}
		cancel.draw(gl);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		level21.initShape(gl, context);
		level22.initShape(gl, context);
		level23.initShape(gl, context);
		previous.initShape(gl, context);
		next.initShape(gl, context);
		cancel.initShape(gl, context);	
	}

}
