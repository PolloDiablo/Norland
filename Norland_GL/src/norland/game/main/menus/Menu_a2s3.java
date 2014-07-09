package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_a2s3 implements MenuState  {

	//Switch ID: 9
	private Thing title;
	private Thing level15;
	private Thing level16;
	private Thing level17;
	private Thing cancel;
	private Thing previous;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a2s3");
		
		title = new Thing(GlMainMenu.bitmapa2s3, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		level15 = new Thing(GlMainMenu.bitmapLevel15, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		level16 = new Thing(GlMainMenu.bitmapLevel16, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		level17 = new Thing(GlMainMenu.bitmapLevel17, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		previous = new Thing(GlMainMenu.bitmapPrevious, 0.25*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
			
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		level15.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level15.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		level16.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level16.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		level17.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level17.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		previous.setX(0.25*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		previous.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		level15.update();
		level16.update();
		level17.update();
		previous.update();
		cancel.update();
		
		
		

		if(level15.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(15,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 16){
			if(level16.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(16,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 17){
			if(level17.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(17,context);
			}
		}

		if(previous.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 8;	
		}	

		if(cancel.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
		
		GlMainMenu.clickSelection.setX(10000);
		GlMainMenu.clickSelection.setY(10000);
		
		
	}

	public void onDrawFrame(GL10 gl,Context context) {
		title.draw(gl);
		level15.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 16){
			level16.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 17){
			level17.draw(gl);
		}
		previous.draw(gl);
		cancel.draw(gl);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		level15.initShape(gl, context);
		level16.initShape(gl, context);
		level17.initShape(gl, context);
		previous.initShape(gl, context);
		cancel.initShape(gl, context);
		
	}

}
