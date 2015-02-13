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

public class Upgrade_Arrows extends UpgradeSuper {

	//Switch ID: 2
	private UpgradeMenuItem title;
	private UpgradeMenuItem points;
	private VisualDynamic pointNum;
	
	private UpgradeMenuItem damage;
	private VisualDynamic damageNum;
	private UpgradeMenuItem DPlus;
	private UpgradeMenuItem DMinus;
	
	private UpgradeMenuItem fireRate;
	private VisualDynamic fireRateNum;
	private UpgradeMenuItem FRPlus;
	private UpgradeMenuItem FRMinus;

	private UpgradeMenuItem uber;
	private VisualDynamic uberNum;
	private UpgradeMenuItem UPlus;
	private UpgradeMenuItem UMinus;
	
	private UpgradeMenuItem back;
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Arrows");
		title = new UpgradeMenuItem(GlRenderer.bitmapTArrows, UpgradeMenuItemType.TITLE, 0);
		menuItems.add(title);
		points = new UpgradeMenuItem(GlRenderer.bitmapPoints, UpgradeMenuItemType.POINTS, 1);
		menuItems.add(points);
		pointNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(pointNum);
		
		damage = new UpgradeMenuItem(GlRenderer.bitmapDamage, UpgradeMenuItemType.TEXT, 2);
		menuItems.add(damage);
		damageNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(damageNum);
		DPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 2);
		menuItems.add(DPlus);
		DMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 2);
		menuItems.add(DMinus);
		
		fireRate = new UpgradeMenuItem(GlRenderer.bitmapFireRate, UpgradeMenuItemType.TEXT, 3);
		menuItems.add(fireRate);
		fireRateNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(fireRateNum);
		FRPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 3);
		menuItems.add(FRPlus);
		FRMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 3);
		menuItems.add(FRMinus);
		
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

		DPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_arrowDamage, 1);
		FRPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_arrowFireRate, 1);

		upgradesTotal = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0);
		upgradesSpent = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		
		canUber=false;
		if(DPoints==5 && FRPoints==3){
			canUber=true;
		}
		

		if(DPlus.getThing().hasCollided(clickSelection,true) && DPoints<5 && upgradesTotal-upgradesSpent>0){
			newAmount=DPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_arrowDamage, newAmount).commit();
			upgradesSpent++;
		}else if(DMinus.getThing().hasCollided(clickSelection,true) && DPoints>1){
			newAmount=DPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_arrowDamage, newAmount).commit();
			upgradesSpent--;
			if(canUber & context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_arrowUber, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_arrowUber, false).commit();
				upgradesSpent--;
			}
		}
		
		if(FRPlus.getThing().hasCollided(clickSelection,true) && FRPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=FRPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_arrowFireRate, newAmount).commit();
			upgradesSpent++;
		}else if(FRMinus.getThing().hasCollided(clickSelection,true) && FRPoints>1){
			newAmount=FRPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_arrowFireRate, newAmount).commit();
			upgradesSpent--;
			if(canUber & context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_arrowUber, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_arrowUber, false).commit();
				upgradesSpent--;
			}
		}
		
		if(DPoints==5 && FRPoints==3){
			if(UPlus.getThing().hasCollided(clickSelection,true) && upgradesTotal-upgradesSpent>0 
					&& !context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_arrowUber, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_arrowUber, true).commit();
				upgradesSpent++;
			}else if(UMinus.getThing().hasCollided(clickSelection,true) && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_arrowUber, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_arrowUber, false).commit();
				upgradesSpent--;
			}
		}else{
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_arrowUber, false).commit();
		}
			
		if(damage.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.arrowDamageTitle, Tips.arrowDamage);
		}
		if(fireRate.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.arrowFRTitle, Tips.arrowFR);
		}
		if(uber.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.arrowUberTitle, Tips.arrowUber);
		}	
		
		if(back.getThing().hasCollided((clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=1;
		}
		
		context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesSpent, upgradesSpent).commit();
	}

	private int pointsRemaining;
	private int DPoints;
	private int FRPoints;
	private int UPoints;
	public void onDrawFrame(GL10 gl, Context context) {
		title.draw(gl);
		points.draw(gl);
		
		pointsRemaining=
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)-
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		pointNum.updateBitmap(gl, context, "" + pointsRemaining);
		pointNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.upperNumOffset, GlMainMenu.heightScale*(150+13)); //TODO
		
		damage.draw(gl);
		DPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_arrowDamage, 1);
		if(DPoints<5)DPlus.draw(gl);
		if(DPoints>1)DMinus.draw(gl);
		DPoints = DPoints -1;
		damageNum.updateBitmap(gl, context, DPoints + "/4");
		damageNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(250+13)); //TODO


		fireRate.draw(gl);
		FRPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_arrowFireRate, 1);
		if(FRPoints<3)FRPlus.draw(gl);
		if(FRPoints>1)FRMinus.draw(gl);
		FRPoints=FRPoints-1;
		fireRateNum.updateBitmap(gl, context, FRPoints + "/2");
		fireRateNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(350+13)); //TODO

		
		uber.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_arrowUber, false)){
			UPoints=1;
		}else{
			UPoints=0;
		}
		uberNum.updateBitmap(gl, context, UPoints + "/1");
		uberNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(450+13)); //TODO
		if(DPoints+1==5 && FRPoints+1==3){
			if(!context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_arrowUber, false)){
				UPlus.draw(gl);
			}else{
				UMinus.draw(gl);
			}
		}
		
		back.draw(gl);
		
	}
	
}