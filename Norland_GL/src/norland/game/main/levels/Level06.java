package norland.game.main.levels;

import java.util.Random;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.R;
import norland.game.main.Thing;
import norland.game.main.Tips;
import norland.game.main.things.FosseGrim;
import norland.game.main.things.Grindylow;
import norland.game.main.things.Lorelei;
import norland.game.main.things.Vodianoi;
import norland.game.main.things.WaterSprite;
import android.content.Context;
import android.media.MediaPlayer;

public class Level06 extends LevelSuper{

	private int VODCOUNT;
	private int FOSSCOUNT;
	private int GRINDCOUNT;
	private int LORCOUNT;
	private int SPRITECOUNT;
	
	
	public void addStuff() {
		ROCKCOUNT=0;
		ICEBERGCOUNT = 15;
		VODCOUNT = 5;
		GRINDCOUNT=1;
		FOSSCOUNT= 1;
		LORCOUNT = 2;
		SPRITECOUNT= 1;
		GlRenderer.OUTSIDE_SIZE=(int) (340*GlMainMenu.widthScale);
		
		Random num = new Random();
        int z = (int) (4100*GlMainMenu.widthScale);   		
        double rad = num.nextFloat()*2*Math.PI - Math.PI; 
        int x = (int)(z*Math.cos(rad));
        int y = (int)(z*Math.sin(rad));
        objective = new Thing(GlRenderer.bitmapIsland1, x, y, 512, 512);	
        objective.update();
        
		super.addStuff();
		
		for(int i=0 ; i < VODCOUNT ; i++){
			Vodianoi q=new Vodianoi(0,0);
			GlRenderer.randomizeLocation(q);
			enemies.add(q);
		}
		for(int i=0 ; i < FOSSCOUNT ; i++){
			FosseGrim t=new FosseGrim(0,0);
			GlRenderer.randomizeLocation(t);
			enemies.add(t);
		}
		for(int i=0 ; i < GRINDCOUNT ; i++){
			Grindylow t=new Grindylow(0,0);
			GlRenderer.randomizeLocation(t);
			enemies.add(t);
		}
		for(int i=0 ; i < LORCOUNT ; i++){
			Lorelei t=new Lorelei(0,0);
			GlRenderer.randomizeLocation(t);
			enemies.add(t);
		}
		for(int i=0 ; i < SPRITECOUNT ; i++){
			WaterSprite t=new WaterSprite(0,0);
			GlRenderer.randomizeLocation(t);
			friendlies.add(t);
		}
		
		GlRenderer.weaponsOn=true;
		
		tipCounter=0;
		hasShownLorTip=false;	
		hasShownSpriteTip=false;	
	}
	
    private static int tipCounter;
    private boolean hasShownLorTip;
    private boolean hasShownSpriteTip;
    public void handleTips(){
    	tipCounter++;
    	if(!hasShownLorTip && tipCounter>12){
    		GlRenderer.showTip(Tips.loreleiTitle, Tips.lorelei);
    		hasShownLorTip=true;
    	}
      	if(!hasShownSpriteTip && tipCounter>16){
    		GlRenderer.showTip(Tips.spriteTitle, Tips.sprite);
    		hasShownSpriteTip=true;
    	}
    }

	public void finishLevel(Context context) {
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) < 7){
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_levelUnlock, 7).commit();
			if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)<5){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesTotal, 5).commit();
			}
			GlMainMenu.setStartNextLevel(true);
			GlRenderer.startUpgradeScreen=true;
		}else{
			GlRenderer.backToMenu=true;
		}	
	}

	public void startMusic(Context context) {
		GlMainMenu.mediaPlayer = MediaPlayer.create(context, R.raw.gameatlantictheme);
	}

}
