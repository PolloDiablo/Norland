package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_a1s2 implements MenuState {

	//Switch ID: 5
	private Thing title;
	private Thing level3;
	private Thing level4;
	private Thing level5;
	private Thing cancel;
	private Thing previous;
	private Thing next;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a1s2");
		
		title = new Thing(GlMainMenu.bitmapa1s2, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		level3 = new Thing(GlMainMenu.bitmapLevel3, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		level4 = new Thing(GlMainMenu.bitmapLevel4, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		level5 = new Thing(GlMainMenu.bitmapLevel5, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		previous = new Thing(GlMainMenu.bitmapPrevious, 0.25*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		next = new Thing(GlMainMenu.bitmapNext, 0.75*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		level3.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level3.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		level4.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level4.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		level5.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level5.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		previous.setX(0.25*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		previous.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		next.setX(0.75*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		next.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		level3.update();
		level4.update();
		level5.update();
		previous.update();
		next.update();
		cancel.update();
		
		
		

		if(level3.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(3,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 4){
			if(level4.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(4,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 5){
			if(level5.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(5,context);
			}
		}

		if(previous.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 4;	
		}	
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 6){
			if(next.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.USER_MENU_SELECT = 6;	
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
		level3.draw(gl);
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 4){
			level4.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 5){
			level5.draw(gl);
		}
		previous.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 6){
			next.draw(gl);
		}
		cancel.draw(gl);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		level3.initShape(gl, context);
		level4.initShape(gl, context);
		level5.initShape(gl, context);
		previous.initShape(gl, context);
		next.initShape(gl, context);
		cancel.initShape(gl, context);
	}

}
