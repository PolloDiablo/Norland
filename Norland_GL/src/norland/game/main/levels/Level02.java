package norland.game.main.levels;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.R;
import norland.game.main.Thing;
import norland.game.main.Tips;
import norland.game.main.VisualDynamic;


import android.content.Context;
import android.media.MediaPlayer;

public class Level02 extends LevelSuper{
	
	public static int objectiveKillCount;
	
	public final static int objectiveCompleteCondition1=5;
	public final static int objectiveCompleteCondition2=3;
	private VisualDynamic objectiveNum;
	
	public void addStuff() {	
		
		ROCKCOUNT = 0;
		ICEBERGCOUNT = 35;
		GlRenderer.OUTSIDE_SIZE=(int) (340*GlMainMenu.widthScale);

		Random num = new Random();
		//Sketchy work-around
        int z = (int) (50000*GlMainMenu.widthScale);   		
        double rad = num.nextFloat()*2*Math.PI - Math.PI; 
        int x = (int)(z*Math.cos(rad));
        int y = (int)(z*Math.sin(rad));
        objective = new Thing(GlRenderer.bitmapIsland1, x, y, 512, 512);
        objective.update();
        
		super.addStuff();
		
		objectiveNum = new VisualDynamic(140, 140);
		
		
		GlRenderer.weaponsOn=true;
        GlRenderer.showCompass=false;
        GlRenderer.showCompassDistance=false;
        
        tipCounter=0;
        hasShownAttacksTip = false;
        hasShownHealthTip = false;
        showSecondObjectiveTip = false;
        showThirdObjectiveTip = false;
        hasShownSecondObjectiveTip= false;
        hasShownThirdObjectiveTip = false;
        
        objectiveKillCount=0;
	}
	

    
    public void update(){
    	super.update();
    	//Log.d("Level 2",""+objectiveKillCount);
    	if(!hasShownSecondObjectiveTip){
    		if(objectiveKillCount>=objectiveCompleteCondition1){
    			objectiveKillCount=0;
    			showSecondObjectiveTip=true;
    		}	
    	}else{
    		if(objectiveKillCount>=objectiveCompleteCondition2){
    			objectiveKillCount=0;
    			showThirdObjectiveTip=true;
    		}
    	}
	
    }
    
	public void onDrawFrame(GL10 gl, Context context) {
		super.onDrawFrame(gl, context);	
		if(!hasShownSecondObjectiveTip && !hasShownThirdObjectiveTip){
			if( objectiveKillCount<=objectiveCompleteCondition1){
				objectiveNum.updateBitmap(gl, context, " " + objectiveKillCount + "/" + objectiveCompleteCondition1);
			}
		}else{
			if( objectiveKillCount<=objectiveCompleteCondition2){
				objectiveNum.updateBitmap(gl, context, " " + objectiveKillCount + "/" + objectiveCompleteCondition2);
			}
		}

    	objectiveNum.draw(gl, (GlRenderer.shipLocX + GlRenderer.WIDTH_HALF -GlMainMenu.widthScale*65), 
    				GlRenderer.shipLocY-GlRenderer.HEIGHT_HALF+  GlMainMenu.heightScale*(45));   
	}
    
    
    public void initiateShapes(GL10 gl,Context context){
    	super.initiateShapes(gl, context);
    	objectiveNum.initShape(gl, context);
    }
    
    
    private static int tipCounter;
    private boolean hasShownAttacksTip;
    private boolean hasShownHealthTip;
    private boolean showSecondObjectiveTip;
    public static boolean hasShownSecondObjectiveTip;
    private boolean showThirdObjectiveTip;
    private static boolean hasShownThirdObjectiveTip;
    
    public void handleTips(){
    	tipCounter++;
    	if(!hasShownHealthTip && tipCounter>12){
    		GlRenderer.showTip(Tips.healthTitle, Tips.health);
    		hasShownHealthTip=true;
    	}
    	if(!hasShownAttacksTip && tipCounter>16){
    		GlRenderer.showTip(Tips.attacksTitle, Tips.attacks);
    		hasShownAttacksTip=true;
    	}
    	if(showSecondObjectiveTip){
    		showSecondObjectiveTip=false;
    		hasShownSecondObjectiveTip=true;
    		GlRenderer.showTip(Tips.attacks2Title, Tips.attacks2);
    	}
    	if(showThirdObjectiveTip){
    		hasShownThirdObjectiveTip=true;
    		showThirdObjectiveTip=false;
    		GlRenderer.showTip(Tips.attacks3Title, Tips.attacks3);
    		tipCounter=0;
    	}
    	if(hasShownThirdObjectiveTip && tipCounter>10){
    		GlRenderer.levelFinished = true;
    	}
    	
    }
	


	public void finishLevel(Context context) {

		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) < 3){
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_levelUnlock, 3).commit();
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
