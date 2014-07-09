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

public class Upgrade_Health implements MenuState {

	//Switch ID: 5
	private Thing title;
	private Thing points;
	private VisualDynamic pointNum;
	
	private Thing max;
	private VisualDynamic maxNum;
	private Thing MPlus;
	private Thing MMinus;
	
	private Thing regen;
	private VisualDynamic regenNum;
	private Thing RPlus;
	private Thing RMinus;

	private Thing uber;
	private VisualDynamic uberNum;
	private Thing UPlus;
	private Thing UMinus;
	
	private Thing back;
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Health");
		
		title = new Thing(GlRenderer.bitmapTHealth, GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*75, 256, 64);
		points = new Thing(GlRenderer.bitmapPoints, GlRenderer.WIDTH_HALF-GlMainMenu.widthScale*25, GlMainMenu.heightScale*150, 512, 64);
		pointNum = new VisualDynamic(100,100);
		
		max = new Thing(GlRenderer.bitmapHealth, GlMainMenu.widthScale*62, GlMainMenu.heightScale*250, 256, 64);
		maxNum = new VisualDynamic(100, 100);
		MPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*250, 64, 64);
		MMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*250, 64, 64);
		
		regen = new Thing(GlRenderer.bitmapRegen, GlMainMenu.widthScale*72, GlMainMenu.heightScale*350, 256, 64);
		regenNum = new VisualDynamic(100, 100);
		RPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*350, 64, 64);
		RMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*350, 64, 64);
		
		uber = new Thing(GlRenderer.bitmapUber,GlMainMenu.widthScale*63, GlMainMenu.heightScale*450, 256, 64);
		uberNum = new VisualDynamic(100, 100);
		UPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*450, 64, 64);
		UMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*450, 64, 64);
		back = new Thing(GlRenderer.bitmapBack, GlRenderer.WIDTH_HALF, GlRenderer.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
	}

	private int newAmount;
	private int upgradesTotal;
	private int upgradesSpent;
	private boolean canUber;
	public void update(Context context) {
		back.update();
		MPlus.update();
		MMinus.update();
		RPlus.update();
		RMinus.update();
		UPlus.update();
		UMinus.update();
		
		max.update();
		regen.update();
		uber.update();
		
		MPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHealth, 1);
		RPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHealthRegen, 1);

		upgradesTotal = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0);
		upgradesSpent = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		
		canUber=false;
		if(MPoints==5 && RPoints==3){
			canUber=true;
		}
		

		if(MPlus.hasCollided(GlRenderer.clickSelection,true) && MPoints<5 && upgradesTotal-upgradesSpent>0){
			newAmount=MPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHealth, newAmount).commit();
			upgradesSpent++;
		}else if(MMinus.hasCollided(GlRenderer.clickSelection,true) && MPoints>1){
			newAmount=MPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHealth, newAmount).commit();
			upgradesSpent--;
			if(canUber & context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHealth, false).commit();
				upgradesSpent--;
			}
		}
		
		if(RPlus.hasCollided(GlRenderer.clickSelection,true) && RPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=RPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHealthRegen, newAmount).commit();
			upgradesSpent++;
		}else if(RMinus.hasCollided(GlRenderer.clickSelection,true) && RPoints>1){
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
			if(UPlus.hasCollided(GlRenderer.clickSelection,true) && upgradesTotal-upgradesSpent>0 
					&& !context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHealth, true).commit();
				upgradesSpent++;
			}else if(UMinus.hasCollided(GlRenderer.clickSelection,true) && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHealth, false).commit();
				upgradesSpent--;
			}
		}else{
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHealth, false).commit();
		}
		
		if(max.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.shipHealthTitle, Tips.shipHealth);
		}
		if(regen.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.shipHRegenTitle, Tips.shipHRegen);
		}
		if(uber.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.healthUberTitle, Tips.healthUber);
		}
				
		if(back.hasCollided((GlRenderer.clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=1;
		}
		GlRenderer.clickSelection.setX(10000);
		GlRenderer.clickSelection.setY(10000);	
		
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

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		points.initShape(gl, context);
		pointNum.initShape(gl, context);
		pointNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		max.initShape(gl, context);
		maxNum.initShape(gl, context);
		maxNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		MPlus.initShape(gl, context);
		MMinus.initShape(gl, context);
		regen.initShape(gl, context);
		regenNum.initShape(gl, context);
		regenNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		RPlus.initShape(gl, context);
		RMinus.initShape(gl, context);
		uber.initShape(gl, context);
		uberNum.initShape(gl, context);
		uberNum.createBitmap(gl, context,  R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		UPlus.initShape(gl, context);
		UMinus.initShape(gl, context);
		back.initShape(gl, context);
	}

}
