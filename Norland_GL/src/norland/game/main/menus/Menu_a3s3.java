package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;

public class Menu_a3s3 extends MenuSuper  {

	//Switch ID: 12
	private MenuItem level24;
	private MenuItem level25;
	private MenuItem level26;
	private MenuItem level27;
	private MenuItem cancel;
	private MenuItem previous;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a3s3");
		buttons.add(new MenuItem(GlMainMenu.bitmapa3s3, ButtonType.TITLEWIDE));
		level24 = new MenuItem(GlMainMenu.bitmapLevel24, ButtonType.B1);
		buttons.add(level24);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 25){
			level25 = new MenuItem(GlMainMenu.bitmapLevel25, ButtonType.B2);
			buttons.add(level25);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 26){
			level26 = new MenuItem(GlMainMenu.bitmapLevel26, ButtonType.B3);
			buttons.add(level26);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 27){
			level27 = new MenuItem(GlMainMenu.bitmapLevel27, ButtonType.B4);
			buttons.add(level27);
		}
		previous = new MenuItem(GlMainMenu.bitmapPrevious, ButtonType.PREV);
		buttons.add(previous);
		
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();
		
		if(level24.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(24,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 25){
			if(level25.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(25,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 26){
			if(level26.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(26,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 27){
			if(level27.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(27,context);
			}
		}

		if(previous.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 11;	
		}	

		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
	}
	
}
