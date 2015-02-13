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

public class Upgrade_Utility extends UpgradeSuper{

	//Switch ID: 6
	private UpgradeMenuItem title;
	private UpgradeMenuItem points;
	private VisualDynamic pointNum;
	
	private UpgradeMenuItem speed;
	private VisualDynamic speedNum;
	private UpgradeMenuItem SPlus;
	private UpgradeMenuItem SMinus;
	
	private UpgradeMenuItem turningRadius;
	private VisualDynamic turningRadiusNum;
	private UpgradeMenuItem TRPlus;
	private UpgradeMenuItem TRMinus;

	private UpgradeMenuItem uber;
	private VisualDynamic uberNum;
	private UpgradeMenuItem UPlus;
	private UpgradeMenuItem UMinus;
	
	private UpgradeMenuItem back;
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Utility");
		title = new UpgradeMenuItem(GlRenderer.bitmapTUtility, UpgradeMenuItemType.TITLE, 0);
		menuItems.add(title);
		points = new UpgradeMenuItem(GlRenderer.bitmapPoints, UpgradeMenuItemType.POINTS, 1);
		menuItems.add(points);
		pointNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(pointNum);
		
		speed = new UpgradeMenuItem(GlRenderer.bitmapSpeed, UpgradeMenuItemType.TEXT, 2);
		menuItems.add(speed);
		speedNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(speedNum);
		SPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 2);
		menuItems.add(SPlus);
		SMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 2);
		menuItems.add(SMinus);
		
		turningRadius = new UpgradeMenuItem(GlRenderer.bitmapTurning, UpgradeMenuItemType.TEXT, 3);
		menuItems.add(turningRadius);
		turningRadiusNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(turningRadiusNum);
		TRPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 3);
		menuItems.add(TRPlus);
		TRMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 3);
		menuItems.add(TRMinus);
		
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
		
		SPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipSpeed, 1);
		TRPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipTurningRadius, 1);

		upgradesTotal = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0);
		upgradesSpent = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		
		canUber=false;
		if(SPoints==3 && TRPoints==4){
			canUber=true;
		}
		

		if(SPlus.getThing().hasCollided(clickSelection,true) && SPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=SPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipSpeed, newAmount).commit();
			upgradesSpent++;
		}else if(SMinus.getThing().hasCollided(clickSelection,true) && SPoints>1){
			newAmount=SPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipSpeed, newAmount).commit();
			upgradesSpent--;
			if(canUber & context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberUtility, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberUtility, false).commit();
				upgradesSpent--;
			}
		}
		
		if(TRPlus.getThing().hasCollided(clickSelection,true) && TRPoints<4 && upgradesTotal-upgradesSpent>0){
			newAmount=TRPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipTurningRadius, newAmount).commit();
			upgradesSpent++;
		}else if(TRMinus.getThing().hasCollided(clickSelection,true) && TRPoints>1){
			newAmount=TRPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipTurningRadius, newAmount).commit();
			upgradesSpent--;
			if(canUber & context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberUtility, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberUtility, false).commit();
				upgradesSpent--;
			}
		}
		
		if(SPoints==3 && TRPoints==4){
			if(UPlus.getThing().hasCollided(clickSelection,true) && upgradesTotal-upgradesSpent>0 
					&& !context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberUtility, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberUtility, true).commit();
				upgradesSpent++;
			}else if(UMinus.getThing().hasCollided(clickSelection,true) && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberUtility, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberUtility, false).commit();
				upgradesSpent--;
			}
		}else{
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberUtility, false).commit();
		}
		
		if(speed.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.shipSpeedTitle, Tips.shipSpeed);
		}
		if(turningRadius.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.shipTRTitle, Tips.shipTR);
		}
		if(uber.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.utilityUberTitle, Tips.utilityUber);
		}			
		
		if(back.getThing().hasCollided((clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=1;
		}

		context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesSpent, upgradesSpent).commit();
	}
	
	private int pointsRemaining;
	private int SPoints;
	private int TRPoints;
	private int UPoints;
	public void onDrawFrame(GL10 gl, Context context) {
		title.draw(gl);
		points.draw(gl);
		
		pointsRemaining=
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)-
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		pointNum.updateBitmap(gl, context, "" + pointsRemaining);
		pointNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.upperNumOffset, GlMainMenu.heightScale*(150+13));
		
		speed.draw(gl);
		SPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipSpeed, 1);
		if(SPoints<3)SPlus.draw(gl);
		if(SPoints>1)SMinus.draw(gl);
		SPoints = SPoints -1;
		speedNum.updateBitmap(gl, context, SPoints + "/2");
		speedNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(250+13));


		turningRadius.draw(gl);
		TRPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipTurningRadius, 1);
		if(TRPoints<4)TRPlus.draw(gl);
		if(TRPoints>1)TRMinus.draw(gl);
		TRPoints=TRPoints-1;
		turningRadiusNum.updateBitmap(gl, context, TRPoints + "/3");
		turningRadiusNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(350+13));

		
		uber.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberUtility, false)){
			UPoints=1;
		}else{
			UPoints=0;
		}
		uberNum.updateBitmap(gl, context, UPoints + "/1");
		uberNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(450+13));
		if(SPoints+1==3 && TRPoints+1==4){
			if(!context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberUtility, false)){
				UPlus.draw(gl);
			}else{
				UMinus.draw(gl);
			}
		}
		
		back.draw(gl);
		
	}

}
