package norland.game.main.levels;

import java.util.Random;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.R;
import norland.game.main.Thing;
import norland.game.main.Tips;
import norland.game.main.things.Vodianoi;

import android.content.Context;
import android.media.MediaPlayer;

public class Level03 extends LevelSuper{

	private int VODCOUNT;
	
	public void addStuff() {

		ROCKCOUNT = 3;
		ICEBERGCOUNT = 10;
		VODCOUNT = 11;
		GlRenderer.OUTSIDE_SIZE=(int) (355*GlMainMenu.widthScale);

		Random num = new Random();
        int z = (int) (3300*GlMainMenu.widthScale);   		
        double rad = num.nextFloat()*2*Math.PI - Math.PI; 
        int x = (int)(z*Math.cos(rad));
        int y = (int)(z*Math.sin(rad));
        objective = new Thing(GlRenderer.bitmapIsland1, x, y, 512, 512);
        objective.update();
        
		super.addStuff();
		
		for(int i=0 ; i < VODCOUNT ; i++){
			Vodianoi v=new Vodianoi(0,0);
			GlRenderer.randomizeLocation(v);
			enemies.add(v);
		}
		
		GlRenderer.weaponsOn=true;
        
		tipCounter=0;
		hasShownVodTip=false;
	}
	
    private static int tipCounter;
    private boolean hasShownVodTip;
    public void handleTips(){
    	tipCounter++;
    	if(!hasShownVodTip && tipCounter>12){
    		GlRenderer.showTip(Tips.vodTitle, Tips.vod);
    		hasShownVodTip=true;
    	}
    }

	public void finishLevel(Context context) {
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) < 4){		
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_levelUnlock, 4).commit();
			if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)<2){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesTotal, 2).commit();
			}
			GlMainMenu.setStartNextLevel(true);
			GlRenderer.startUpgradeScreen=true;
		}else{
			GlRenderer.backToMenu = true;
		}
	}

	public void startMusic(Context context) {
		GlMainMenu.mediaPlayer = MediaPlayer.create(context, R.raw.gameatlantictheme);
	}

}
