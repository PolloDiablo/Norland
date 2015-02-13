package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;

public class Menu_a3s2 extends MenuSuper {

	//Switch ID: 11
	private MenuItem level21;
	private MenuItem level22;
	private MenuItem level23;
	private MenuItem cancel;
	private MenuItem previous;
	private MenuItem next;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a3s2");
		buttons.add(new MenuItem(GlMainMenu.bitmapa3s2, ButtonType.TITLEWIDE));
		level21 = new MenuItem(GlMainMenu.bitmapLevel21, ButtonType.B1);
		buttons.add(level21);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 22){
			level22 = new MenuItem(GlMainMenu.bitmapLevel22, ButtonType.B2);
			buttons.add(level22);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 23){
			level23 = new MenuItem(GlMainMenu.bitmapLevel23, ButtonType.B3);
			buttons.add(level23);
		}
		previous = new MenuItem(GlMainMenu.bitmapPrevious, ButtonType.PREV);
		buttons.add(previous);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 24){
			next = new MenuItem(GlMainMenu.bitmapNext, ButtonType.NEXT);
			buttons.add(next);
		}
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();
		
		if(level21.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(21,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 22){
			if(level22.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(22,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 23){
			if(level23.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(23,context);
			}
		}

		if(previous.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 10;	
		}	
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 24){
			if(next.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.USER_MENU_SELECT = 12;	
			}	
		}	

		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
	}

}
