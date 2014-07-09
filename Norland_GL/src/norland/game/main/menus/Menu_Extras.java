package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Menu_Extras implements MenuState {

	//Switch ID: 13
	private Thing title;
	private Thing easterEggs;
	private Thing highScores;
	private Thing credits;
	private Thing cancel;
	
	public void addStuff() {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_Extras");


		title = new Thing(GlMainMenu.bitmapTitle_extras, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*100, 256, 64);
		easterEggs = new Thing(GlMainMenu.bitmapEasterEggs256, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 512, 64);
		highScores  = new Thing(GlMainMenu.bitmapHighScores, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*250, 512, 64);
		credits = new Thing(GlMainMenu.bitmapCredits, GlMainMenu.WIDTH/2, GlMainMenu.heightScale*450, 256, 64);
		cancel = new Thing(GlMainMenu.bitmapCancel, GlMainMenu.WIDTH/2, GlMainMenu.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
	}

	public void update(Context context) {
		title.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		title.setY(GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		easterEggs.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		easterEggs.setY(GlMainMenu.heightScale*250+GlMainMenu.menuShiftY);
		
		highScores.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		highScores.setY(GlMainMenu.heightScale*350+GlMainMenu.menuShiftY);
		
		credits.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		credits.setY(GlMainMenu.heightScale*450+GlMainMenu.menuShiftY);

		
		cancel.setX(GlMainMenu.WIDTH/2+GlMainMenu.menuShiftX);
		cancel.setY(GlMainMenu.HEIGHT-GlMainMenu.heightScale*100+GlMainMenu.menuShiftY);
		
		easterEggs.update();
		highScores.update();
		credits.update();
		cancel.update();
		
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_hasCompleted, false)){
			if(easterEggs.hasCollided((GlMainMenu.clickSelection),true)){
				GlMainMenu.USER_MENU_SELECT=14;
			}
		}
		
		if(highScores.hasCollided((GlMainMenu.clickSelection),true)){
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://norland.casualt.ca/"));
            context.startActivity(intent);

		}
		
		if( credits.hasCollided((GlMainMenu.clickSelection),true) ){
			GlMainMenu.showCredits(context);
			
		}

		if(cancel.hasCollided((GlMainMenu.clickSelection),true)){
			GlMainMenu.USER_MENU_SELECT=1;
		}
		
		GlMainMenu.clickSelection.setX(10000);
		GlMainMenu.clickSelection.setY(10000);
		
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		easterEggs.initShape(gl, context);
		highScores.initShape(gl, context);
		credits.initShape(gl, context);
		cancel.initShape(gl, context);
	}

	public void onDrawFrame(GL10 gl, Context context) {
		
		title.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_hasCompleted, false)){
			easterEggs.draw(gl);
		}
		highScores.draw(gl);
		credits.draw(gl);
		cancel.draw(gl);
	}

}
