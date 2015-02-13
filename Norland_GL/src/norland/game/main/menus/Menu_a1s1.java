package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;

public class Menu_a1s1 extends MenuSuper {
	
	//Switch ID: 4
	private MenuItem level1;
	private MenuItem level2;
	private MenuItem cancel;
	private MenuItem next;

	public void addStuff(Context context) {
		buttons.add(new MenuItem(GlMainMenu.bitmapa1s1, ButtonType.TITLEWIDE));
		level1 = new MenuItem(GlMainMenu.bitmapLevel1, ButtonType.B1);
		buttons.add(level1);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 2){
			level2 = new MenuItem(GlMainMenu.bitmapLevel2, ButtonType.B2);
			buttons.add(level2);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 3){
			next = new MenuItem(GlMainMenu.bitmapNext, ButtonType.NEXT);
			buttons.add(next);
		}	
	
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();

		if(level1.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(1,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 2){
			if(level2.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(2,context);
			}
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 3){
			if(next.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.USER_MENU_SELECT = 5;	
			}	
		}	

		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
	}

}
