package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_a1s3 implements MenuState  {

	//Switch ID: 6
	private Thing title;
	private Thing level6;
	private Thing level7;
	private Thing level8;
	private Thing cancel;
	private Thing previous;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a1s3");
		
		title = new Thing(GlMainMenu.bitmapa1s3, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		level6 = new Thing(GlMainMenu.bitmapLevel6, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		level7 = new Thing(GlMainMenu.bitmapLevel7, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		level8 = new Thing(GlMainMenu.bitmapLevel8, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		previous = new Thing(GlMainMenu.bitmapPrevious, 0.25*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
			
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		level6.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level6.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		level7.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level7.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		level8.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level8.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		previous.setX(0.25*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		previous.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		level6.update();
		level7.update();
		level8.update();
		previous.update();
		cancel.update();
		
		
		

		if(level6.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(6,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 7){
			if(level7.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(7,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 8){
			if(level8.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(8,context);
			}
		}

		if(previous.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 5;	
		}	

		if(cancel.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
		
		GlMainMenu.clickSelection.setX(10000);
		GlMainMenu.clickSelection.setY(10000);
		
	}

	public void onDrawFrame(GL10 gl,Context context) {
		title.draw(gl);
		level6.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 7){
			level7.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 8){
			level8.draw(gl);
		}
		previous.draw(gl);
		cancel.draw(gl);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		level6.initShape(gl, context);
		level7.initShape(gl, context);
		level8.initShape(gl, context);
		previous.initShape(gl, context);
		cancel.initShape(gl, context);
	}

}
