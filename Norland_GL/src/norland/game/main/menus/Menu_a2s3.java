package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;

public class Menu_a2s3 extends MenuSuper {

	//Switch ID: 9
	private MenuItem level15;
	private MenuItem level16;
	private MenuItem level17;
	private MenuItem cancel;
	private MenuItem previous;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a2s3");
		
		buttons.add(new MenuItem(GlMainMenu.bitmapa2s3, ButtonType.TITLEWIDE));
		level15 = new MenuItem(GlMainMenu.bitmapLevel15, ButtonType.B1);
		buttons.add(level15);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 16){
			level16 = new MenuItem(GlMainMenu.bitmapLevel16, ButtonType.B2);
			buttons.add(level16);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 17){
			level17 = new MenuItem(GlMainMenu.bitmapLevel17, ButtonType.B3);
			buttons.add(level17);
		}
		previous = new MenuItem(GlMainMenu.bitmapPrevious, ButtonType.PREV);
		buttons.add(previous);
		
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);
		
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();

		if(level15.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(15,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 16){
			if(level16.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(16,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 17){
			if(level17.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(17,context);
			}
		}

		if(previous.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 8;	
		}	

		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
		
	}
	
}
