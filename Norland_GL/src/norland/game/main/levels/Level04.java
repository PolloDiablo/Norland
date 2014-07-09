package norland.game.main.levels;

import java.util.Random;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.R;
import norland.game.main.Thing;
import norland.game.main.Tips;
import norland.game.main.things.FosseGrim;
import norland.game.main.things.Vodianoi;
import android.content.Context;
import android.media.MediaPlayer;


public class Level04 extends LevelSuper{
	
	private int VODCOUNT;
	private int FOSSCOUNT;
	
	public void addStuff() {
		ROCKCOUNT=0;
		ICEBERGCOUNT = 22;
		VODCOUNT = 8;
		FOSSCOUNT= 4;
		GlRenderer.OUTSIDE_SIZE=(int) (340*GlMainMenu.widthScale);


		
		Random num = new Random();
        int z =(int) (3400*GlMainMenu.widthScale);   		
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
		
		GlRenderer.weaponsOn=true;
		
		tipCounter=0;
		hasShownFosseTip=false;	
	}
	
    private static int tipCounter;
    private boolean hasShownFosseTip;
    public void handleTips(){
    	tipCounter++;
    	if(!hasShownFosseTip && tipCounter>20){
    		GlRenderer.showTip(Tips.fosseTitle, Tips.fosse);
    		hasShownFosseTip=true;
    	}
    }
	
	public void finishLevel(Context context) {
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) < 5){
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_levelUnlock, 5).commit();
			if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)<3){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesTotal, 3).commit();
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
