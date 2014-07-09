package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;

import android.content.Context;


public class Menu_Play implements MenuState {
	
	//Switch ID: 2
	private Thing title;
	private Thing resume;
	private Thing act1;
	private Thing act2;
	private Thing act3;
	private Thing cancel;

	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_Play");
		
		
		title = new Thing(GlMainMenu.bitmapTitle_campaign, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 512, 64);
		resume= new Thing(GlMainMenu.bitmapResume, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 256, 64);
		act1 = new Thing(GlMainMenu.bitmapAct1, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*350, 256, 64);
		act2 = new Thing(GlMainMenu.bitmapAct2, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		act3 = new Thing(GlMainMenu.bitmapAct3, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*550, 256, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
	}

	public void update(Context context) {
		
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		resume.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		resume.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);
		
		act1.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		act1.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);

		act2.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		act2.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);
		
		act3.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		act3.setY(GlMainMenu.heightScale*550+GlMainMenu.menuShiftY);
		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		resume.update();
		act1.update();
		act2.update();
		act3.update();
		cancel.update();
		
		
		if(resume.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.enterGame(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1),context);
		}
		
		if(act1.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT=4;
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 9){
			if(act2.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.USER_MENU_SELECT=7;
			}
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 18){
			if(act3.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.USER_MENU_SELECT=10;
			}
		}
			
			

		if(cancel.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT=1;
		}
		
		GlMainMenu.clickSelection.setX(10000);
		GlMainMenu.clickSelection.setY(10000);
		
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		resume.initShape(gl, context);
		act1.initShape(gl, context);
		act2.initShape(gl, context);
		act3.initShape(gl, context);
		cancel.initShape(gl, context);
	}

	public void onDrawFrame(GL10 gl,Context context) {
		title.draw(gl);
		resume.draw(gl);
		act1.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 9){
			act2.draw(gl);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 18){
			act3.draw(gl);
		}
		cancel.draw(gl);
	}

}
