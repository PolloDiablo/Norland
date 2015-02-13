package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;


public class Menu_a2s1 extends MenuSuper {

	//Switch ID: 7
	private MenuItem level9;
	private MenuItem level10;
	private MenuItem level11;
	private MenuItem cancel;
	private MenuItem next;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a2s1");
		buttons.add(new MenuItem(GlMainMenu.bitmapa2s1, ButtonType.TITLEWIDE));
		level9 = new MenuItem(GlMainMenu.bitmapLevel9, ButtonType.B1);
		buttons.add(level9);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 10){
			level10 = new MenuItem(GlMainMenu.bitmapLevel10, ButtonType.B2);
			buttons.add(level10);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 11){
			level11 = new MenuItem(GlMainMenu.bitmapLevel11, ButtonType.B3);
			buttons.add(level11);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 12){
			next = new MenuItem(GlMainMenu.bitmapNext, ButtonType.NEXT);
			buttons.add(next);
		}
		
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();

		if(level9.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(9,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 10){
			if(level10.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(10,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 11){
			if(level11.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(11,context);
			}
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 12){
			if(next.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.USER_MENU_SELECT = 8;	
			}	
		}	

		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
	}

}
