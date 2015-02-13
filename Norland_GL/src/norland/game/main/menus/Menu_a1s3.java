package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;

public class Menu_a1s3 extends MenuSuper  {

	//Switch ID: 6
	private MenuItem level6;
	private MenuItem level7;
	private MenuItem level8;
	private MenuItem cancel;
	private MenuItem previous;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a1s3");
		buttons.add(new MenuItem(GlMainMenu.bitmapa1s3, ButtonType.TITLEWIDE));
		level6 = new MenuItem(GlMainMenu.bitmapLevel6, ButtonType.B1);
		buttons.add(level6);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 7){
			level7 = new MenuItem(GlMainMenu.bitmapLevel7, ButtonType.B2);
			buttons.add(level7);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 8){
			level8 = new MenuItem(GlMainMenu.bitmapLevel8, ButtonType.B3);
			buttons.add(level8);
		}
		previous = new MenuItem(GlMainMenu.bitmapPrevious, ButtonType.PREV);
		buttons.add(previous);
		
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);	
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();
		
		if(level6.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(6,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 7){
			if(level7.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(7,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 8){
			if(level8.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(8,context);
			}
		}

		if(previous.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 5;	
		}	

		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}		
	}

}
