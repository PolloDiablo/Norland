package norland.game.main.levels;

import java.util.Random;
import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.R;
import norland.game.main.Thing;
import norland.game.main.Tips;
import android.content.Context;
import android.media.MediaPlayer;

public class Level01 extends LevelSuper {

    public void addStuff() {
    	
    	ROCKCOUNT = 50;
    	ICEBERGCOUNT = 0;
		GlRenderer.OUTSIDE_SIZE=(int) (480*GlMainMenu.widthScale);
  
        Random num = new Random();
        //Randomize objective location
        int z = (int) (1600*GlMainMenu.widthScale);   		
        double rad = num.nextFloat()*2*Math.PI - Math.PI; 
        int x = (int)(z*Math.cos(rad));
        int y = (int)(z*Math.sin(rad));
        objective = new Thing(GlRenderer.bitmapIsland1, x, y, 512, 512);
        objective.update();
                  
       	super.addStuff();
       	
        GlRenderer.weaponsOn=true;

        
        tipCounter=0;
        hasShownObjectivesTip=false;
        hasShownMovementTip=false;
    }

    
    private static int tipCounter;
    private boolean hasShownObjectivesTip;
    private boolean hasShownMovementTip;
    
    public void handleTips(){
    	tipCounter++;
    	if(!hasShownObjectivesTip && tipCounter>12){
    		GlRenderer.showTip(Tips.objectivesTitle, Tips.objectives);
    		hasShownObjectivesTip=true;
    	}
    	if(!hasShownMovementTip && tipCounter>16){
    		GlRenderer.showTip(Tips.movementTitle, Tips.movement);
    		hasShownMovementTip=true;
    	}
    }
    
    public void finishLevel(Context context) {

        if (context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getInt(GlMainMenu.LOCAL_levelUnlock, 1) < 2) {
            context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                    .edit().putInt(GlMainMenu.LOCAL_levelUnlock, 2).commit();
            GlMainMenu.setStartNextLevel(true);
            GlRenderer.backToMenu = true;
        } else {
            GlRenderer.backToMenu = true;
        }

    }

	public void startMusic(Context context) {
		GlMainMenu.mediaPlayer = MediaPlayer.create(context, R.raw.gamecaribbeantheme);
	}


}
