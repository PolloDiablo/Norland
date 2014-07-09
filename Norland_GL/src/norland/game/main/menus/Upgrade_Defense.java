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

public class Upgrade_Defense implements MenuState {

	//Switch ID: 4
	private Thing title;
	private Thing points;
	private VisualDynamic pointNum;
	

	
	private Thing fire;
	private VisualDynamic fireNum;
	private Thing FPlus;
	private Thing FMinus;
	
	private Thing strength;
	private VisualDynamic strengthNum;
	private Thing SPlus;
	private Thing SMinus;
	
	private Thing damage;
	private VisualDynamic damageNum;
	private Thing DPlus;
	private Thing DMinus;
	
	private Thing uber;
	private VisualDynamic uberNum;
	private Thing UPlus;
	private Thing UMinus;
	
	private Thing back;
	
	public void addStuff() {
		//Log.d(GlRenderer.TAG,"Upgrade: Upgrade_Defense");
		title = new Thing(GlRenderer.bitmapTDefense, GlRenderer.WIDTH_HALF, GlMainMenu.heightScale*75, 256, 64);
		points = new Thing(GlRenderer.bitmapPoints, GlRenderer.WIDTH_HALF-GlMainMenu.widthScale*25, GlMainMenu.heightScale*150, 512, 64);
		pointNum = new VisualDynamic(100,100);
		
		fire = new Thing(GlRenderer.bitmapFireResist, GlMainMenu.widthScale*123, GlMainMenu.heightScale*250, 256, 64);
		fireNum = new VisualDynamic(100,100);
		FPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*250, 64, 64);
		FMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*250, 64, 64);
		
		strength = new Thing(GlRenderer.bitmapHullResist, GlMainMenu.widthScale*120, GlMainMenu.heightScale*350, 256, 64);
		strengthNum = new VisualDynamic(100, 100);
		SPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*350, 64, 64);
		SMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*350, 64, 64);
		
		damage = new Thing(GlRenderer.bitmapNavalRam, GlMainMenu.widthScale*123, GlMainMenu.heightScale*450, 256, 64);
		damageNum = new VisualDynamic(100, 100);
		DPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*450, 64, 64);
		DMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*450, 64, 64);
		
		uber = new Thing(GlRenderer.bitmapUber,GlMainMenu.widthScale*63, GlMainMenu.heightScale*550, 256, 64);
		uberNum = new VisualDynamic(100, 100);
		UPlus = new Thing(GlRenderer.bitmapPlus, GlRenderer.WIDTH-GlMainMenu.widthScale*45, GlMainMenu.heightScale*550, 64, 64);
		UMinus = new Thing(GlRenderer.bitmapMinus, GlRenderer.WIDTH-GlMainMenu.widthScale*115, GlMainMenu.heightScale*550, 64, 64);
		
		back = new Thing(GlRenderer.bitmapBack, GlRenderer.WIDTH_HALF, GlRenderer.HEIGHT-GlMainMenu.heightScale*100, 256, 64);
	}

	private int newAmount;
	private int upgradesTotal;
	private int upgradesSpent;
	private boolean canUber;
	public void update(Context context) {
		back.update();
		
		FPlus.update();
		FMinus.update();
		
		SPlus.update();
		SMinus.update();
		
		DPlus.update();
		DMinus.update();

		UPlus.update();
		UMinus.update();
		
		fire.update();
		strength.update();
		damage.update();
		uber.update();
		
		
		FPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipFireResist, 1);
		SPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHullResist, 1);
		DPoints=context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_shipHullDamage, 1);
		
		

		upgradesTotal = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0);
		upgradesSpent = context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesSpent, 0);
		
		canUber=false;
		if(FPoints==3 && SPoints==4 && DPoints==2){
			canUber=true;
		}
		
		if(FPlus.hasCollided(GlRenderer.clickSelection,true) && FPoints<3 && upgradesTotal-upgradesSpent>0){
			newAmount=FPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipFireResist, newAmount).commit();
			upgradesSpent++;
		}else if(FMinus.hasCollided(GlRenderer.clickSelection,true) && FPoints>1){
			newAmount=FPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipFireResist, newAmount).commit();
			upgradesSpent--;
			if(canUber && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, false).commit();
				upgradesSpent--;
			}
		}
		
		if(SPlus.hasCollided(GlRenderer.clickSelection,true) && SPoints<4 && upgradesTotal-upgradesSpent>0){
			newAmount=SPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHullResist, newAmount).commit();
			upgradesSpent++;
		}else if(SMinus.hasCollided(GlRenderer.clickSelection,true) && SPoints>1){
			newAmount=SPoints-1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHullResist, newAmount).commit();
			upgradesSpent--;
			if(canUber && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)==true){
				canUber=false;
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, false).commit();
				upgradesSpent--;
			}
		}
		
		if(DPlus.hasCollided(GlRenderer.clickSelection,true) && DPoints<2 && upgradesTotal-upgradesSpent>0){
			newAmount=DPoints+1;
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHullDamage, newAmount).commit();
			upgradesSpent++;
		}else if(DMinus.hasCollided(GlRenderer.clickSelection,true) && DPoints>1){
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
			if(UPlus.hasCollided(GlRenderer.clickSelection,true) && upgradesTotal-upgradesSpent>0 
					&& !context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, true).commit();
				upgradesSpent++;
			}else if(UMinus.hasCollided(GlRenderer.clickSelection,true) && context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, false).commit();
				upgradesSpent--;
			}
		}else{
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putBoolean(GlMainMenu.LOCAL_shipUberHull, false).commit();
		}
		
		
		if(fire.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.shipFResistTitle, Tips.shipFResist);
		}
		if(strength.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.shipHResistTitle, Tips.shipHResist);
		}
		if(damage.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.shipHDTitle, Tips.shipHD);
		}
		if(uber.hasCollided(GlRenderer.clickSelection, true)){
			GlRenderer.showTip(Tips.defenseUberTitle, Tips.defenseUber);
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

	public void initiateShapes(GL10 gl, Context context) {
		title.initShape(gl, context);
		points.initShape(gl, context);
		pointNum.initShape(gl, context);
		pointNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		
		fire.initShape(gl, context);
		fireNum.initShape(gl, context);
		fireNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		FPlus.initShape(gl, context);
		FMinus.initShape(gl, context);
		
		strength.initShape(gl, context);
		strengthNum.initShape(gl, context);
		strengthNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		SPlus.initShape(gl, context);
		SMinus.initShape(gl, context);
		
		damage.initShape(gl, context);
		damageNum.initShape(gl, context);
		damageNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		DPlus.initShape(gl, context);
		DMinus.initShape(gl, context);
		
		uber.initShape(gl, context);
		uberNum.initShape(gl, context);
		uberNum.createBitmap(gl, context, R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
		UPlus.initShape(gl, context);
		UMinus.initShape(gl, context);	
		back.initShape(gl, context);
	}

}
