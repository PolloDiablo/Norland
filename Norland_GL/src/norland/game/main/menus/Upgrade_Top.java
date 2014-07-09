package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.R;
import norland.game.main.Thing;
import norland.game.main.Tips;
import norland.game.main.UpgradeMain;
import norland.game.main.VisualDynamic;
import android.content.Context;

public class Upgrade_Top implements MenuState {

	//Switch ID: 1
	private Thing title;
	private Thing points;
	private VisualDynamic pointNum;
	private Thing arrows;
	private Thing cannons;
	private Thing defense;
	private Thing health;
	private Thing utility;
	private Thing exit;
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Top");
		
		title = new Thing(GlRenderer.bitmapTTop, GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*75, 512, 128);
		points = new Thing(GlRenderer.bitmapPoints, GlRenderer.WIDTH_HALF-GlMainMenu.widthScale*25, GlMainMenu.heightScale*150, 512, 64);
		pointNum = new VisualDynamic(100,100);
		arrows = new Thing(GlRenderer.bitmapBArrows, GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*250, 256, 64);
		cannons = new Thing(GlRenderer.bitmapBCannons, GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*350, 256, 64);
		defense = new Thing(GlRenderer.bitmapBDefense, GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*450, 256, 64);
		health = new Thing(GlRenderer.bitmapBHealth, GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*550, 256, 64);
		utility = new Thing(GlRenderer.bitmapBUtility,GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*650, 256, 64);
		exit = new Thing(GlRenderer.bitmapExit, GlRenderer.WIDTH_HALF, GlRenderer.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
	}

	private static boolean hasShownUpgradeTip =false;
	
	public void update(Context context) {
		
		if(!hasShownUpgradeTip && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) == 4){
    		GlRenderer.showTip(Tips.upgradesTitle, Tips.upgrades);
    		hasShownUpgradeTip=true;
		}
		
		arrows.update();
		cannons.update();
		defense.update();
		health.update();
		utility.update();
		exit.update();
		
		if(arrows.hasCollided((GlRenderer.clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=2;
		}
		if(cannons.hasCollided((GlRenderer.clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=3;
		}
		if(defense.hasCollided((GlRenderer.clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=4;
		}
		if(health.hasCollided((GlRenderer.clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=5;
		}
		if(utility.hasCollided((GlRenderer.clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=6;
		}
		if(exit.hasCollided((GlRenderer.clickSelection),true)){
			GlRenderer.endUpgradeScreen=true;
		}
		
		GlRenderer.clickSelection.setX(10000);
		GlRenderer.clickSelection.setY(10000);
	}

	private int pointsRemaining;
	public void onDrawFrame(GL10 gl, Context context) {
		title.draw(gl);
		points.draw(gl);
		
		pointsRemaining=
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)-
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		pointNum.updateBitmap(gl, context, "" + pointsRemaining);
		pointNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.upperNumOffset, GlMainMenu.heightScale*(150+13));
		
		arrows.draw(gl);
		cannons.draw(gl);
		defense.draw(gl);
		health.draw(gl);
		utility.draw(gl);
		exit.draw(gl);
	}

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		points.initShape(gl, context);
		pointNum.initShape(gl, context);
		pointNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		arrows.initShape(gl, context);
		cannons.initShape(gl, context);
		defense.initShape(gl, context);;
		health.initShape(gl, context);
		utility.initShape(gl, context);
		exit.initShape(gl, context);
	}

}
