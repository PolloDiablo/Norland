package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;


public class Menu_a3s3 implements MenuState  {

	//Switch ID: 12
	private Thing title;
	private Thing level24;
	private Thing level25;
	private Thing level26;
	private Thing level27;
	private Thing cancel;
	private Thing previous;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a3s3");
		
		title = new Thing(GlMainMenu.bitmapa3s3, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		level24 = new Thing(GlMainMenu.bitmapLevel24, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		level25 = new Thing(GlMainMenu.bitmapLevel25, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		level26 = new Thing(GlMainMenu.bitmapLevel26, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		level27 = new Thing(GlMainMenu.bitmapLevel27, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		previous = new Thing(GlMainMenu.bitmapPrevious, 0.25*GlMainMenu.WIDTH, GlMainMenu.HEIGHT-GlMainMenu.heightScale*200, 128, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
			
		
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		level24.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level24.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);

		level25.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level25.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		level26.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level26.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		level27.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		level27.setY(GlMainMenu.heightScale*550+GlMainMenu.menuShiftY);
		
		previous.setX(0.25*GlMainMenu.WIDTH+GlMainMenu.menuShiftX);
		previous.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*200+GlMainMenu.menuShiftY);
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		title.update();
		level24.update();
		level25.update();
		level26.update();
		level27.update();
		previous.update();
		cancel.update();
		
		
		

		if(level24.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(24,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 25){
			if(level25.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(25,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 26){
			if(level26.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(26,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 27){
			if(level27.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.enterGame(27,context);
			}
		}

		if(previous.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 11;	
		}	

		if(cancel.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
		
		GlMainMenu.clickSelection.setX(10000);
		GlMainMenu.clickSelection.setY(10000);
		
	}

	public void onDrawFrame(GL10 gl,Context context) {
		title.draw(gl);
		level24.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 25){
			level25.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 26){
			level26.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 27){
			level27.draw(gl);
		}
		previous.draw(gl);
		cancel.draw(gl);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		level24.initShape(gl, context);
		level25.initShape(gl, context);
		level26.initShape(gl, context);
		level27.initShape(gl, context);
		previous.initShape(gl, context);
		cancel.initShape(gl, context);
	}

}
