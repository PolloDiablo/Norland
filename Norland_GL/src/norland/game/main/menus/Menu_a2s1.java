package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_a2s1 implements MenuState {

	//Switch ID: 7
	private Thing title;
	private Thing level9;
	private Thing level10;
	private Thing level11;
	private Thing cancel;
	private Thing next;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a2s1");
		
		title = new Thing(GlMainMenu.bitmapa2s1, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		level9 = new Thing(GlMainMenu.bitmapLevel9, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		level10 = new Thing(GlMainMenu.bitmapLevel10, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		level11 = new Thing(GlMainMenu.bitmapLevel11, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		next = new Thing(GlMainMenu.bitmapNext, 0.75*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		level9.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level9.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		level10.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level10.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		level11.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level11.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		next.setX(0.75*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		next.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		level9.update();
		level10.update();
		level11.update();
		next.update();
		cancel.update();
		
		
		
		
		if(level9.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(9,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 10){
			if(level10.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(10,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 11){
			if(level11.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(11,context);
			}
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 12){
			if(next.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.USER_MENU_SELECT = 8;	
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
		level9.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 10){
			level10.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 11){
			level11.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 12){
			next.draw(gl);
		}
		cancel.draw(gl);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		level9.initShape(gl, context);
		level10.initShape(gl, context);
		level11.initShape(gl, context);
		next.initShape(gl, context);
		cancel.initShape(gl, context);	
		
		
	}

}
