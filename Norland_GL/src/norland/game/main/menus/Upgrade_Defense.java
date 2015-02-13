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

public class Upgrade_Defense extends UpgradeSuper {

	//Switch ID: 4
	private UpgradeMenuItem title;
	private UpgradeMenuItem points;
	private VisualDynamic pointNum;

	private UpgradeMenuItem fire;
	private VisualDynamic fireNum;
	private UpgradeMenuItem FPlus;
	private UpgradeMenuItem FMinus;
	
	private UpgradeMenuItem strength;
	private VisualDynamic strengthNum;
	private UpgradeMenuItem SPlus;
	private UpgradeMenuItem SMinus;
	
	private UpgradeMenuItem damage;
	private VisualDynamic damageNum;
	private UpgradeMenuItem DPlus;
	private UpgradeMenuItem DMinus;
	
	private UpgradeMenuItem uber;
	private VisualDynamic uberNum;
	private UpgradeMenuItem UPlus;
	private UpgradeMenuItem UMinus;
	
	private UpgradeMenuItem back;
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Defense");
		title = new UpgradeMenuItem(GlRenderer.bitmapTDefense, UpgradeMenuItemType.TITLE, 0);
		menuItems.add(title);
		points = new UpgradeMenuItem(GlRenderer.bitmapPoints, UpgradeMenuItemType.POINTS, 1);
		menuItems.add(points);
		pointNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(pointNum);
		
		fire = new UpgradeMenuItem(GlRenderer.bitmapFireResist, UpgradeMenuItemType.TEXT, 2);
		menuItems.add(fire);
		fireNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(fireNum);
		FPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 2);
		menuItems.add(FPlus);
		FMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 2);
		menuItems.add(FMinus);
		
		strength = new UpgradeMenuItem(GlRenderer.bitmapHullResist, UpgradeMenuItemType.TEXT, 3);
		menuItems.add(strength);
		strengthNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(strengthNum);
		SPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 3);
		menuItems.add(SPlus);
		SMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 3);
		menuItems.add(SMinus);
		
		damage = new UpgradeMenuItem(GlRenderer.bitmapNavalRam, UpgradeMenuItemType.TEXT, 4);
		menuItems.add(damage);
		damageNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(damageNum);
		DPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 4);
		menuItems.add(DPlus);
		DMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 4);
		menuItems.add(DMinus);
		
		uber = new UpgradeMenuItem(GlRenderer.bitmapUber, UpgradeMenuItemType.TEXT, 5);
		menuItems.add(uber);
		uberNum = new VisualDynamic(upgradeVisualDynamicSize,upgradeVisualDynamicSize);
		visuals.add(uberNum);
		UPlus = new UpgradeMenuItem(GlRenderer.bitmapPlus, UpgradeMenuItemType.PLUS, 5);
		menuItems.add(UPlus);
		UMinus = new UpgradeMenuItem(GlRenderer.bitmapMinus, UpgradeMenuItemType.MINUS, 5);
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

		FPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipFireResist, 1);
		SPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHullResist, 1);
		DPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHullDamage, 1);
		
		upgradesTotal = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0);
		upgradesSpent = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		
		canUber=false;
		if(FPoints==3 && SPoints==4 && DPoints==2){
			canUber=true;
		}
		
		if(FPlus.getThing().hasCollided(clickSelection,true) && FPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=FPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipFireResist, newAmount).commit();
			upgradesSpent++;
		}else if(FMinus.getThing().hasCollided(clickSelection,true) && FPoints>1){
			newAmount=FPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipFireResist, newAmount).commit();
			upgradesSpent--;
			if(canUber && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, false).commit();
				upgradesSpent--;
			}
		}
		
		if(SPlus.getThing().hasCollided(clickSelection,true) && SPoints<4 && upgradesTotal-upgradesSpent>0){
			newAmount=SPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHullResist, newAmount).commit();
			upgradesSpent++;
		}else if(SMinus.getThing().hasCollided(clickSelection,true) && SPoints>1){
			newAmount=SPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHullResist, newAmount).commit();
			upgradesSpent--;
			if(canUber && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, false).commit();
				upgradesSpent--;
			}
		}
		
		if(DPlus.getThing().hasCollided(clickSelection,true) && DPoints<2 && upgradesTotal-upgradesSpent>0){
			newAmount=DPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHullDamage, newAmount).commit();
			upgradesSpent++;
		}else if(DMinus.getThing().hasCollided(clickSelection,true) && DPoints>1){
			newAmount=DPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHullDamage, newAmount).commit();
			upgradesSpent--;
			if(canUber && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, false).commit();
				upgradesSpent--;
			}
		}
		
		if(FPoints==3 && SPoints==4 && DPoints==2){
			if(UPlus.getThing().hasCollided(clickSelection,true) && upgradesTotal-upgradesSpent>0 
					&& !context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, true).commit();
				upgradesSpent++;
			}else if(UMinus.getThing().hasCollided(clickSelection,true) && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, false).commit();
				upgradesSpent--;
			}
		}else{
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, false).commit();
		}
		
		
		if(fire.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.shipFResistTitle, Tips.shipFResist);
		}
		if(strength.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.shipHResistTitle, Tips.shipHResist);
		}
		if(damage.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.shipHDTitle, Tips.shipHD);
		}
		if(uber.getThing().hasCollided(clickSelection, true)){
			GlRenderer.showTip(Tips.defenseUberTitle, Tips.defenseUber);
		}
		
		if(back.getThing().hasCollided((clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=1;
		}

		context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesSpent, upgradesSpent).commit();
	}

	
	private int pointsRemaining;
	private int DPoints;
	private int FPoints;
	private int SPoints;
	private int UPoints;
	public void onDrawFrame(GL10 gl, Context context) {
		title.draw(gl);
		points.draw(gl);
		
		pointsRemaining=
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)-
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		pointNum.updateBitmap(gl, context, "" + pointsRemaining);
		pointNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.upperNumOffset, GlMainMenu.heightScale*(150+13));
		
		fire.draw(gl);
		FPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipFireResist, 1);
		if(FPoints<3)FPlus.draw(gl);
		if(FPoints>1)FMinus.draw(gl);
		FPoints=FPoints-1;
		fireNum.updateBitmap(gl, context, FPoints + "/2");
		fireNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(250+13));
				
		strength.draw(gl);
		SPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHullResist, 1);
		if(SPoints<4)SPlus.draw(gl);
		if(SPoints>1)SMinus.draw(gl);
		SPoints=SPoints-1;
		strengthNum.updateBitmap(gl, context, SPoints + "/3");
		strengthNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(350+13));
		
		damage.draw(gl);
		DPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHullDamage, 1);
		if(DPoints<2)DPlus.draw(gl);
		if(DPoints>1)DMinus.draw(gl);
		DPoints = DPoints -1;
		damageNum.updateBitmap(gl, context, DPoints + "/1");
		damageNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(450+13));

		uber.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)){
			UPoints=1;
		}else{
			UPoints=0;
		}
		uberNum.updateBitmap(gl, context, UPoints + "/1");
		uberNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(550+13));
		if(FPoints+1==3 && SPoints+1==4 && DPoints+1==2){
			if(!context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)){
				UPlus.draw(gl);
			}else{
				UMinus.draw(gl);
			}
		}
		
		back.draw(gl);
	}

}
