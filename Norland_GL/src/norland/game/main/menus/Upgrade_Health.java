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

public class Upgrade_Health extends UpgradeSuper {

	//Switch ID: 5
	private UpgradeMenuItem title;
	private UpgradeMenuItem points;
	private VisualDynamic pointNum;
	
	private UpgradeMenuItem max;
	private VisualDynamic maxNum;
	private UpgradeMenuItem MPlus;
	private UpgradeMenuItem MMinus;
	
	private UpgradeMenuItem regen;
	private VisualDynamic regenNum;
	private UpgradeMenuItem RPlus;
	private UpgradeMenuItem RMinus;

	private UpgradeMenuItem uber;
	private VisualDynamic uberNum;
	private UpgradeMenuItem UPlus;
	private UpgradeMenuItem UMinus;
	
	private UpgradeMenuItem back;
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Health");
		title = new UpgradeMenuItem(GlRenderer.bitmapTHealth, UpgradeMenuItemType.TITLE, 0);
		menuItems.add(title);
		points = new UpgradeMenuItem(GlRenderer.bitmapPoints, UpgradeMenuItemType.POINTS, 1);
		menuItems.add(points);
		pointNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(pointNum);
		
		max = new UpgradeMenuItem(GlRenderer.bitmapHealth, UpgradeMenuItemType.TEXT, 2);
		menuItems.add(max);
		maxNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(maxNum);
		MPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 2);
		menuItems.add(MPlus);
		MMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 2);
		menuItems.add(MMinus);
		
		regen = new UpgradeMenuItem(GlRenderer.bitmapRegen, UpgradeMenuItemType.TEXT, 3);
		menuItems.add(regen);
		regenNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(regenNum);
		RPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 3);
		menuItems.add(RPlus);
		RMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 3);
		menuItems.add(RMinus);
		
		uber = new UpgradeMenuItem(GlRenderer.bitmapUber, UpgradeMenuItemType.TEXT, 4);
		menuItems.add(uber);
		uberNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(uberNum);
		UPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 4);
		menuItems.add(UPlus);
		UMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 4);
		menuItems.add(UMinus);
		
		back = new UpgradeMenuItem(GlRenderer.bitmapBack, UpgradeMenuItemType.BOTTOM, -1);
		menuItems.add(back);	
	}

	private int newAmount;
	private int upgradesTotal;
	private int upgradesSpent;
	private boolean canUber;
	public void update(Thing clickSelection, Context context) {
		super.updateMenuItemPositions();
		
		MPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHealth, 1);
		RPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHealthRegen, 1);

		upgradesTotal = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0);
		upgradesSpent = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		
		canUber=false;
		if(MPoints==5 && RPoints==3){
			canUber=true;
		}
		

		if(MPlus.getThing().hasCollided(clickSelection,true) && MPoints<5 && upgradesTotal-upgradesSpent>0){
			newAmount=MPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHealth, newAmount).commit();
			upgradesSpent++;
		}else if(MMinus.getThing().hasCollided(clickSelection,true) && MPoints>1){
			newAmount=MPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHealth, newAmount).commit();
			upgradesSpent--;
			if(canUber & context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHealth, false).commit();
				upgradesSpent--;
			}
		}
		
		if(RPlus.getThing().hasCollided(clickSelection,true) && RPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=RPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHealthRegen, newAmount).commit();
			upgradesSpent++;
		}else if(RMinus.getThing().hasCollided(clickSelection,true) && RPoints>1){
			newAmount=RPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHealthRegen, newAmount).commit();
			upgradesSpent--;
			if(canUber & context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHealth, false).commit();
				upgradesSpent--;
			}
		}
		
		if(MPoints==5 && RPoints==3){
			if(UPlus.getThing().hasCollided(clickSelection,true) && upgradesTotal-upgradesSpent>0 
					&& !context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHealth, true).commit();
				upgradesSpent++;
			}else if(UMinus.getThing().hasCollided(clickSelection,true) && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHealth, false).commit();
				upgradesSpent--;
			}
		}else{
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHealth, false).commit();
		}
		
		if(max.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.shipHealthTitle, Tips.shipHealth);
		}
		if(regen.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.shipHRegenTitle, Tips.shipHRegen);
		}
		if(uber.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.healthUberTitle, Tips.healthUber);
		}
				
		if(back.getThing().hasCollided((clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=1;
		}

		context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesSpent, upgradesSpent).commit();
	}

	private int pointsRemaining;
	private int MPoints;
	private int RPoints;
	private int UPoints;
	public void onDrawFrame(GL10 gl, Context context) {
		title.draw(gl);
		points.draw(gl);
		
		pointsRemaining=
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)-
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		pointNum.updateBitmap(gl, context, "" + pointsRemaining);
		pointNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.upperNumOffset, GlMainMenu.heightScale*(150+13));
		
		max.draw(gl);
		MPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHealth, 1);
		if(MPoints<5)MPlus.draw(gl);
		if(MPoints>1)MMinus.draw(gl);
		MPoints = MPoints -1;
		maxNum.updateBitmap(gl, context, MPoints + "/4");
		maxNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(250+13));


		regen.draw(gl);
		RPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHealthRegen, 1);
		if(RPoints<3)RPlus.draw(gl);
		if(RPoints>1)RMinus.draw(gl);
		RPoints=RPoints-1;
		regenNum.updateBitmap(gl, context, RPoints + "/2");
		regenNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(350+13));

		
		uber.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)){
			UPoints=1;
		}else{
			UPoints=0;
		}
		uberNum.updateBitmap(gl, context, UPoints + "/1");
		uberNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(450+13));
		if(MPoints+1==5 && RPoints+1==3){
			if(!context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)){
				UPlus.draw(gl);
			}else{
				UMinus.draw(gl);
			}
		}
		
		back.draw(gl);
	}
	
}
