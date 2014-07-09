package norland.game.main.levels;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.R;
import norland.game.main.Thing;
import norland.game.main.Tips;
import norland.game.main.UpgradeMain;
import norland.game.main.VisualDynamic;
import norland.game.main.things.Dragon;
import android.content.Context;
import android.media.MediaPlayer;

public class level09 extends LevelSuper{

	private int DRAGONCOUNT;
	private int addDragon;
	
	public static int objectiveKillCount;
	private VisualDynamic objectiveNum;

	public void addStuff() {
		ROCKCOUNT = 13;
		ICEBERGCOUNT = 40;
		DRAGONCOUNT = 4;
		GlRenderer.OUTSIDE_SIZE=(int) (350*GlMainMenu.widthScale);
		
		Random num = new Random();
        int z = (int) (50000*GlMainMenu.widthScale);   		
        double rad = num.nextFloat()*2*Math.PI - Math.PI; 
        int x = (int)(z*Math.cos(rad));
        int y = (int)(z*Math.sin(rad));
        objective = new Thing(GlRenderer.bitmapIsland1, x, y, 512, 512);	
        
		super.addStuff();
		
		for(int i=0 ; i <= DRAGONCOUNT ; i++){
			Dragon q=new Dragon(0,0);
			GlRenderer.randomizeLocation(q);
			enemies.add(q);
		}
		addDragon=0;
		
		GlRenderer.weaponsOn=true;
		GlRenderer.showCompass=false;
		
		onlyTestCollisionsEvery4Cycles=0;
		objectiveNum = new VisualDynamic(300, 140);
		objectiveKillCount=0;
		
		tipCounter=0;
		hasShownChallengeTip=false;
	}		
	
    private static int tipCounter;
    private boolean hasShownChallengeTip;
    public void handleTips(){
    	tipCounter++;
    	if(!hasShownChallengeTip && tipCounter>12){
    		GlRenderer.showTip(Tips.betaTitle, Tips.beta);
    		hasShownChallengeTip=true;
    	}
    }
	
	public void onDrawFrame(GL10 gl, Context context) {	
		super.onDrawFrame(gl, context);
		addDragon++;
		if(addDragon>400){
			addDragon=0;
			Dragon q=new Dragon(0,0);
			GlRenderer.randomizeLocation(q);
			q.initShape(gl, context);
			enemies.add(q);		
			if(enemies.size()>=25){
				q=new Dragon(0,0);
				GlRenderer.randomizeLocation(q);
				q.initShape(gl, context);
				enemies.add(q);
			}
		}	
		
		objectiveNum.updateBitmap(gl, context, "Kills: " + objectiveKillCount);   
		//Tested, this can currently display up to 99999 without going off the edge of the screen
    	objectiveNum.draw(gl, (GlRenderer.shipLocX + GlRenderer.WIDTH_HALF -GlMainMenu.widthScale*95), 
    				GlRenderer.shipLocY-GlRenderer.HEIGHT_HALF+  GlMainMenu.heightScale*(45));   
		
		

	}
    public void initiateShapes(GL10 gl,Context context){
    	super.initiateShapes(gl, context);
    	objectiveNum.initShape(gl, context);
		objectiveNum.createBitmap(gl, context,  R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
    }

	public void finishLevel(Context context) {

		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) < 10){
			//TODO This is a sandbox for now
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_levelUnlock, 9).commit();
			GlMainMenu.setStartNextLevel(true);
			GlRenderer.backToMenu = true;
		}else{
			GlRenderer.backToMenu = true;
		}
				
	}
	
	public void startMusic(Context context) {
		// TODO Auto-generated method stub
		GlMainMenu.mediaPlayer = MediaPlayer.create(context, R.raw.gameatlantictheme);
	}

}
