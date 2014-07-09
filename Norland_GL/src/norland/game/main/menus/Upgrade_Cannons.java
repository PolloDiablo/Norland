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

public class Upgrade_Cannons implements MenuState {

	//Switch ID: 3
	private Thing title;
	private Thing points;
	private VisualDynamic pointNum;
	
	private Thing damage;
	private VisualDynamic damageNum;
	private Thing DPlus;
	private Thing DMinus;
	
	private Thing fireRate;
	private VisualDynamic fireRateNum;
	private Thing FRPlus;
	private Thing FRMinus;
	
	private Thing range;
	private VisualDynamic rangeNum;
	private Thing RPlus;
	private Thing RMinus;
	
	private Thing spread;
	private VisualDynamic spreadNum;
	private Thing SPlus;
	private Thing SMinus;
	
	private Thing uber;
	private VisualDynamic uberNum;
	private Thing UPlus;
	private Thing UMinus;
	
	private Thing back;
	
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Cannons");
		title = new Thing(GlRenderer.bitmapTCannons, GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*75, 256, 64);
		points = new Thing(GlRenderer.bitmapPoints, GlRenderer.WIDTH_HALF-GlMainMenu.widthScale*25, GlMainMenu.heightScale*150, 512, 64);
		pointNum = new VisualDynamic(100,100);
		
		damage = new Thing(GlRenderer.bitmapDamage, GlMainMenu.widthScale*90, GlMainMenu.heightScale*250, 256, 64);
		damageNum = new VisualDynamic(100, 100);
		DPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*250, 64, 64);
		DMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*250, 64, 64);
		
		fireRate = new Thing(GlRenderer.bitmapFireRate, GlMainMenu.widthScale*105, GlMainMenu.heightScale*350, 256, 64);
		fireRateNum = new VisualDynamic(100, 100);
		FRPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*350, 64, 64);
		FRMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*350, 64, 64);
		
		range = new Thing(GlRenderer.bitmapRange, GlMainMenu.widthScale*74, GlMainMenu.heightScale*450, 256, 64);
		rangeNum = new VisualDynamic(100, 100);
		RPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*450, 64, 64);
		RMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*450, 64, 64);
		
		spread = new Thing(GlRenderer.bitmapSpread, GlMainMenu.widthScale*81, GlMainMenu.heightScale*550, 256, 64);
		spreadNum = new VisualDynamic(100, 100);
		SPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*550, 64, 64);
		SMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*550, 64, 64);
		
		uber = new Thing(GlRenderer.bitmapUber,GlMainMenu.widthScale*63, GlMainMenu.heightScale*650, 256, 64);
		uberNum = new VisualDynamic(100, 100);
		UPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*650, 64, 64);
		UMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*650, 64, 64);
		
		back = new Thing(GlRenderer.bitmapBack, GlRenderer.WIDTH_HALF, GlRenderer.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
		
		
	}

	private int newAmount;
	private int upgradesTotal;
	private int upgradesSpent;
	private boolean canUber;
	public void update(Context context) {
		back.update();

		DPlus.update();
		DMinus.update();
		
		FRPlus.update();
		FRMinus.update();
		
		RPlus.update();
		RMinus.update();
		
		SPlus.update();
		SMinus.update();

		UPlus.update();
		UMinus.update();
		
		damage.update();
		fireRate.update();
		range.update();
		spread.update();
		uber.update();		
		
		DPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_cannonDamage, 1);
		FRPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_cannonFireRate, 1);
		SPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_cannonSpread, 1);
		RPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_cannonRange, 1);
		
		

		upgradesTotal = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0);
		upgradesSpent = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		
		canUber=false;
		if(DPoints==4 && FRPoints==3 && RPoints==3 && SPoints==3){
			canUber=true;
		}
		

		if(DPlus.hasCollided(GlRenderer.clickSelection,true) && DPoints<4 && upgradesTotal-upgradesSpent>0){
			newAmount=DPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_cannonDamage, newAmount).commit();
			upgradesSpent++;
		}else if(DMinus.hasCollided(GlRenderer.clickSelection,true) && DPoints>1){
			newAmount=DPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_cannonDamage, newAmount).commit();
			upgradesSpent--;
			if(canUber & context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_cannonUber, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_cannonUber, false).commit();
				upgradesSpent--;
			}
		}
		
		if(FRPlus.hasCollided(GlRenderer.clickSelection,true) && FRPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=FRPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_cannonFireRate, newAmount).commit();
			upgradesSpent++;
		}else if(FRMinus.hasCollided(GlRenderer.clickSelection,true) && FRPoints>1){
			newAmount=FRPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_cannonFireRate, newAmount).commit();
			upgradesSpent--;
			if(canUber && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_cannonUber, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_cannonUber, false).commit();
				upgradesSpent--;
			}
		}
		
		if(RPlus.hasCollided(GlRenderer.clickSelection,true) && RPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=RPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_cannonRange, newAmount).commit();
			upgradesSpent++;
		}else if(RMinus.hasCollided(GlRenderer.clickSelection,true) && RPoints>1){
			newAmount=RPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_cannonRange, newAmount).commit();
			upgradesSpent--;
			if(canUber && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_cannonUber, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_cannonUber, false).commit();
				upgradesSpent--;
			}
		}
		
		if(SPlus.hasCollided(GlRenderer.clickSelection,true) && SPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=SPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_cannonSpread, newAmount).commit();
			upgradesSpent++;
		}else if(SMinus.hasCollided(GlRenderer.clickSelection,true) && SPoints>1){
			newAmount=SPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_cannonSpread, newAmount).commit();
			upgradesSpent--;
			if(canUber && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_cannonUber, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_cannonUber, false).commit();
				upgradesSpent--;
			}
		}
		
		if(DPoints==4 && FRPoints==3 && RPoints==3 && SPoints==3){
			if(UPlus.hasCollided(GlRenderer.clickSelection,true) && upgradesTotal-upgradesSpent>0 
					&& !context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_cannonUber, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_cannonUber, true).commit();
				upgradesSpent++;
			}else if(UMinus.hasCollided(GlRenderer.clickSelection,true) && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_cannonUber, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_cannonUber, false).commit();
				upgradesSpent--;
			}
		}else{
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_cannonUber, false).commit();
		}
		
		if(damage.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.cannonDamageTitle, Tips.cannonDamage);
		}
		if(fireRate.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.cannonFRTitle, Tips.cannonFR);
		}
		if(range.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.cannonRangeTitle, Tips.cannonRange);
		}
		if(spread.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.cannonSpreadTitle, Tips.cannonSpread);
		}
		if(uber.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.cannonUberTitle, Tips.cannonUber);
		}	
		
		if(back.hasCollided((GlRenderer.clickSelection),true)){
			GlRenderer.USER_UPGRADE_SELECT=1;
		}
		GlRenderer.clickSelection.setX(10000);
		GlRenderer.clickSelection.setY(10000);	
		
		context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesSpent, upgradesSpent).commit();
	}

	private int pointsRemaining;
	private int DPoints;
	private int FRPoints;
	private int RPoints;
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
		
		damage.draw(gl);
		DPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_cannonDamage, 1);
		if(DPoints<4)DPlus.draw(gl);
		if(DPoints>1)DMinus.draw(gl);
		DPoints = DPoints -1;
		damageNum.updateBitmap(gl, context, DPoints + "/3");
		damageNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(250+13));


		fireRate.draw(gl);
		FRPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_cannonFireRate, 1);
		if(FRPoints<3)FRPlus.draw(gl);
		if(FRPoints>1)FRMinus.draw(gl);
		FRPoints=FRPoints-1;
		fireRateNum.updateBitmap(gl, context, FRPoints + "/2");
		fireRateNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(350+13));
		
		range.draw(gl);
		RPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_cannonRange, 1);
		if(RPoints<3)RPlus.draw(gl);
		if(RPoints>1)RMinus.draw(gl);
		RPoints=RPoints-1;
		rangeNum.updateBitmap(gl, context, RPoints + "/2");
		rangeNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(450+13));
		
		spread.draw(gl);
		SPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_cannonSpread, 1);
		if(SPoints<3)SPlus.draw(gl);
		if(SPoints>1)SMinus.draw(gl);
		SPoints=SPoints-1;
		spreadNum.updateBitmap(gl, context, SPoints + "/2");
		spreadNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(550+13));
		


		uber.draw(gl);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_cannonUber, false)){
			UPoints=1;
		}else{
			UPoints=0;
		}
		uberNum.updateBitmap(gl, context, UPoints + "/1");
		uberNum.draw(gl, GlRenderer.WIDTH_HALF+GlMainMenu.widthScale*UpgradeMain.numOffset, GlMainMenu.heightScale*(650+13));
		if(DPoints+1==4 && FRPoints+1==3 && SPoints+1==3 && RPoints+1==3){
			if(!context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_cannonUber, false)){
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
		
		damage.initShape(gl, context);
		damageNum.initShape(gl, context);
		damageNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		DPlus.initShape(gl, context);
		DMinus.initShape(gl, context);
		
		fireRate.initShape(gl, context);
		fireRateNum.initShape(gl, context);
		fireRateNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		FRPlus.initShape(gl, context);
		FRMinus.initShape(gl, context);
		
		range.initShape(gl, context);
		rangeNum.initShape(gl, context);
		rangeNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		RPlus.initShape(gl, context);
		RMinus.initShape(gl, context);
		
		spread.initShape(gl, context);
		spreadNum.initShape(gl, context);
		spreadNum.createBitmap(gl, context,R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		SPlus.initShape(gl, context);
		SMinus.initShape(gl, context);
			
		uber.initShape(gl, context);
		uberNum.initShape(gl, context);
		uberNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		UPlus.initShape(gl, context);
		UMinus.initShape(gl, context);
		back.initShape(gl, context);
	}

}
