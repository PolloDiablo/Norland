package norland.game.main.levels;

import java.util.Random;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.R;
import norland.game.main.Thing;
import norland.game.main.things.Grindylow;
import norland.game.main.things.Lorelei;
import norland.game.main.things.Vodianoi;
import norland.game.main.things.WaterSprite;
import android.content.Context;
import android.media.MediaPlayer;

public class level07 extends LevelSuper{

	private int VODCOUNT;
	private int GRINDCOUNT;
	private int LORCOUNT;
	private int SPRITECOUNT;
	
	public void addStuff() {
		ROCKCOUNT=0;
		ICEBERGCOUNT = 9;
		VODCOUNT = 12;
		GRINDCOUNT=2;
		LORCOUNT = 2;
		SPRITECOUNT= 1;
		GlRenderer.OUTSIDE_SIZE=(int) (360*GlMainMenu.widthScale);
		
		Random num = new Random();
        int z = (int) (4300*GlMainMenu.widthScale);   		
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
					
	}

	public void finishLevel(Context context) {
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) < 8){
			context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_levelUnlock, 8).commit();
			if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_upgradesTotal, 0)<6){
				context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_upgradesTotal, 6).commit();
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
