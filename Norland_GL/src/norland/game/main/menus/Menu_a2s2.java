package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;


public class Menu_a2s2 extends MenuSuper  {

	//Switch ID: 8
	private MenuItem level12;
	private MenuItem level13;
	private MenuItem level14;
	private MenuItem next;
	private MenuItem previous;
	private MenuItem cancel;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a2s2");
		buttons.add(new MenuItem(GlMainMenu.bitmapa2s2, ButtonType.TITLEWIDE));
		level12 = new MenuItem(GlMainMenu.bitmapLevel12, ButtonType.B1);
		buttons.add(level12);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 13){
			level13 = new MenuItem(GlMainMenu.bitmapLevel13, ButtonType.B2);
			buttons.add(level13);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 14){
			level14 = new MenuItem(GlMainMenu.bitmapLevel14, ButtonType.B3);
			buttons.add(level14);
		}
		previous = new MenuItem(GlMainMenu.bitmapPrevious, ButtonType.PREV);
		buttons.add(previous);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 15){
			next = new MenuItem(GlMainMenu.bitmapNext, ButtonType.NEXT);
			buttons.add(next);
		}
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();

		if(level12.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(12,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 13){
			if(level13.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(13,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 14){
			if(level14.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(14,context);
			}
		}

		if(previous.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 7;	
		}	
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 15){
			if(next.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.USER_MENU_SELECT = 9;	
			}	
		}	

		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
		
	}

}
