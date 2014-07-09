package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_a1s1 implements MenuState {
	
	//Switch ID: 4
	private Thing title;
	private Thing level1;
	private Thing level2;
	private Thing cancel;
	private Thing next;

	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a1s1");
		
		title = new Thing(GlMainMenu.bitmapa1s1, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		level1 = new Thing(GlMainMenu.bitmapLevel1, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		level2 = new Thing(GlMainMenu.bitmapLevel2, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		next = new Thing(GlMainMenu.bitmapNext, 0.75*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		level1.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level1.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		level2.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level2.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		next.setX(0.75*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		next.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		level1.update();
		level2.update();
		next.update();
		cancel.update();
		
		
		
		
		if(level1.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(1,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 2){
			if(level2.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(2,context);
			}
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 3){
			if(next.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.USER_MENU_SELECT = 5;	
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
		level1.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 2){
			level2.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 3){
			next.draw(gl);
		}
		cancel.draw(gl);
		
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		level1.initShape(gl, context);
		level2.initShape(gl, context);
		next.initShape(gl, context);
		cancel.initShape(gl, context);	
		
	}

}
