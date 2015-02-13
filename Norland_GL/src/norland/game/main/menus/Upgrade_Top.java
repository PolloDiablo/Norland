package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.Tips;
import norland.game.main.UpgradeMain;
import norland.game.main.VisualDynamic;
import norland.game.main.menus.UpgradeMenuItem.UpgradeMenuItemType;
import android.content.Context;

public class Upgrade_Top extends UpgradeSuper{

	//Switch ID: 1
	private UpgradeMenuItem title;
	private UpgradeMenuItem points;
	private VisualDynamic pointNum;
	private UpgradeMenuItem arrows;
	private UpgradeMenuItem cannons;
	private UpgradeMenuItem defense;
	private UpgradeMenuItem health;
	private UpgradeMenuItem utility;
	private UpgradeMenuItem exit;
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Top");
		title = new UpgradeMenuItem(GlRenderer.bitmapTTop, UpgradeMenuItemType.TITLE, 0);
		menuItems.add(title);
		points = new UpgradeMenuItem(GlRenderer.bitmapPoints, UpgradeMenuItemType.POINTS, 1);
		menuItems.add(points);
		pointNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(pointNum);
		arrows = new UpgradeMenuItem(GlRenderer.bitmapBArrows, UpgradeMenuItemType.BUTTON, 2);
		menuItems.add(arrows);
		cannons = new UpgradeMenuItem(GlRenderer.bitmapBCannons, UpgradeMenuItemType.BUTTON, 3);
		menuItems.add(cannons);
		defense = new UpgradeMenuItem(GlRenderer.bitmapBDefense, UpgradeMenuItemType.BUTTON, 4);
		menuItems.add(defense);
		health = new UpgradeMenuItem(GlRenderer.bitmapBHealth, UpgradeMenuItemType.BUTTON, 5);
		menuItems.add(health);
		utility = new UpgradeMenuItem(GlRenderer.bitmapBUtility, UpgradeMenuItemType.BUTTON, 6);
		menuItems.add(utility);
		exit = new UpgradeMenuItem(GlRenderer.bitmapExit, UpgradeMenuItemType.BUTTON, -1);
		menuItems.add(exit);
	
	}

	private static boolean hasShownUpgradeTip =false;
	
	public void update(Thing clickSelection, Context context) {
		super.updateMenuItemPositions();
		
		if(!hasShownUpgradeTip && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) == 4){
    		GlRenderer.showTip(Tips.upgradesTitle, Tips.upgrades);
    		hasShownUpgradeTip=true;
		}
		
		if(arrows.getThing().hasCollided(clickSelection,true)){
			GlRenderer.USER_UPGRADE_SELECT=2;
		}
		if(cannons.getThing().hasCollided(clickSelection,true)){
			GlRenderer.USER_UPGRADE_SELECT=3;
		}
		if(defense.getThing().hasCollided(clickSelection,true)){
			GlRenderer.USER_UPGRADE_SELECT=4;
		}
		if(health.getThing().hasCollided(clickSelection,true)){
			GlRenderer.USER_UPGRADE_SELECT=5;
		}
		if(utility.getThing().hasCollided(clickSelection,true)){
			GlRenderer.USER_UPGRADE_SELECT=6;
		}
		if(exit.getThing().hasCollided(clickSelection,true)){
			GlRenderer.endUpgradeScreen=true;
		}
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

}
