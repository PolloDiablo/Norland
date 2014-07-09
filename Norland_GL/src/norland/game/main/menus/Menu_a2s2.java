package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_a2s2 implements MenuState  {

	//Switch ID: 8
	private Thing title;
	private Thing level12;
	private Thing level13;
	private Thing level14;
	private Thing next;
	private Thing previous;
	private Thing cancel;

	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a2s2");
		
		title = new Thing(GlMainMenu.bitmapa2s2, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		level12 = new Thing(GlMainMenu.bitmapLevel12, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		level13 = new Thing(GlMainMenu.bitmapLevel13, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		level14 = new Thing(GlMainMenu.bitmapLevel14, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		previous = new Thing(GlMainMenu.bitmapPrevious, 0.25*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		next = new Thing(GlMainMenu.bitmapNext, 0.75*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		level12.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level12.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		level13.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level13.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		level14.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level14.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		previous.setX(0.25*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		previous.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		next.setX(0.75*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		next.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		level12.update();
		level13.update();
		level14.update();
		previous.update();
		next.update();
		cancel.update();
		
		
		

		if(level12.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(12,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 13){
			if(level13.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(13,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 14){
			if(level14.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(14,context);
			}
		}

		if(previous.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 7;	
		}	
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 15){
			if(next.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.USER_MENU_SELECT = 9;	
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
		level12.draw(gl);
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 13){
			level13.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 14){
			level14.draw(gl);
		}
		previous.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 15){
			next.draw(gl);
		}
		cancel.draw(gl);
		
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		level12.initShape(gl, context);
		level13.initShape(gl, context);
		level14.initShape(gl, context);
		previous.initShape(gl, context);
		next.initShape(gl, context);
		cancel.initShape(gl, context);
	}

}
