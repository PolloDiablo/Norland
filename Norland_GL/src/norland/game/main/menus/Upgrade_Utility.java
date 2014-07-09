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

public class Upgrade_Utility implements MenuState {

	//Switch ID: 6
	private Thing title;
	private Thing points;
	private VisualDynamic pointNum;
	
	private Thing speed;
	private VisualDynamic speedNum;
	private Thing SPlus;
	private Thing SMinus;
	
	private Thing turningRadius;
	private VisualDynamic turningRadiusNum;
	private Thing TRPlus;
	private Thing TRMinus;

	private Thing uber;
	private VisualDynamic uberNum;
	private Thing UPlus;
	private Thing UMinus;
	
	private Thing back;
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Utility");
		title = new Thing(GlRenderer.bitmapTUtility, GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*75, 256, 64);
		points = new Thing(GlRenderer.bitmapPoints, GlRenderer.WIDTH_HALF-GlMainMenu.widthScale*25, GlMainMenu.heightScale*150, 512, 64);
		pointNum = new VisualDynamic(100,100);
		
		speed = new Thing(GlRenderer.bitmapSpeed, GlMainMenu.widthScale*77, GlMainMenu.heightScale*250, 256, 64);
		speedNum = new VisualDynamic(100, 100);
		SPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*250, 64, 64);
		SMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*250, 64, 64);
		
		turningRadius = new Thing(GlRenderer.bitmapTurning, GlMainMenu.widthScale*94, GlMainMenu.heightScale*350, 256, 64);
		turningRadiusNum = new VisualDynamic(100, 100);
		TRPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*350, 64, 64);
		TRMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*350, 64, 64);
		
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
		SPlus.update();
		SMinus.update();
		TRPlus.update();
		TRMinus.update();
		UPlus.update();
		UMinus.update();
		
		speed.update();
		turningRadius.update();
		uber.update();
		
		SPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipSpeed, 1);
		TRPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipTurningRadius, 1);

		upgradesTotal = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0);
		upgradesSpent = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		
		canUber=false;
		if(SPoints==3 && TRPoints==4){
			canUber=true;
		}
		

		if(SPlus.hasCollided(GlRenderer.clickSelection,true) && SPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=SPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipSpeed, newAmount).commit();
			upgradesSpent++;
		}else if(SMinus.hasCollided(GlRenderer.clickSelection,true) && SPoints>1){
			newAmount=SPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipSpeed, newAmount).commit();
			upgradesSpent--;
			if(canUber & context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberUtility, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberUtility, false).commit();
				upgradesSpent--;
			}
		}
		
		if(TRPlus.hasCollided(GlRenderer.clickSelection,true) && TRPoints<4 && upgradesTotal-upgradesSpent>0){
			newAmount=TRPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipTurningRadius, newAmount).commit();
			upgradesSpent++;
		}else if(TRMinus.hasCollided(GlRenderer.clickSelection,true) && TRPoints>1){
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
			if(UPlus.hasCollided(GlRenderer.clickSelection,true) && upgradesTotal-upgradesSpent>0 
					&& !context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberUtility, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberUtility, true).commit();
				upgradesSpent++;
			}else if(UMinus.hasCollided(GlRenderer.clickSelection,true) && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberUtility, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberUtility, false).commit();
				upgradesSpent--;
			}
		}else{
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberUtility, false).commit();
		}
		
		if(speed.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.shipSpeedTitle, Tips.shipSpeed);
		}
		if(turningRadius.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.shipTRTitle, Tips.shipTR);
		}
		if(uber.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.utilityUberTitle, Tips.utilityUber);
		}			
		
		if(back.hasCollided((GlRenderer.clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=1;
		}
		GlRenderer.clickSelection.setX(10000);
		GlRenderer.clickSelection.setY(10000);	
		
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

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		points.initShape(gl, context);
		pointNum.initShape(gl, context);
		pointNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		speed.initShape(gl, context);
		speedNum.initShape(gl, context);
		speedNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		SPlus.initShape(gl, context);
		SMinus.initShape(gl, context);
		turningRadius.initShape(gl, context);
		turningRadiusNum.initShape(gl, context);
		turningRadiusNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		TRPlus.initShape(gl, context);
		TRMinus.initShape(gl, context);
		uber.initShape(gl, context);
		uberNum.initShape(gl, context);
		uberNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		UPlus.initShape(gl, context);
		UMinus.initShape(gl, context);
		back.initShape(gl, context);
	}

}
