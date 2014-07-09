package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_a3s1 implements MenuState  {

	//Switch ID: 10
	private Thing title;
	private Thing level18;
	private Thing level19;
	private Thing level20;
	private Thing cancel;
	private Thing next;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a3s1");
		
		title = new Thing(GlMainMenu.bitmapa3s1, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		level18 = new Thing(GlMainMenu.bitmapLevel18, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		level19 = new Thing(GlMainMenu.bitmapLevel19, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		level20 = new Thing(GlMainMenu.bitmapLevel20, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		next = new Thing(GlMainMenu.bitmapNext, 0.75*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		level18.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level18.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		level19.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level19.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		level20.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level20.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		next.setX(0.75*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		next.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		level18.update();
		level19.update();
		level20.update();
		next.update();
		cancel.update();
		
		
		
		
		if(level18.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(18,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 19){
			if(level19.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(19,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 20){
			if(level20.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(20,context);
			}
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 21){
			if(next.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.USER_MENU_SELECT = 11;	
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
		level18.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 19){
			level19.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 20){
			level20.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 21){
			next.draw(gl);
		}
		cancel.draw(gl);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		level18.initShape(gl, context);
		level19.initShape(gl, context);
		level20.initShape(gl, context);
		next.initShape(gl, context);
		cancel.initShape(gl, context);		
	}

}
