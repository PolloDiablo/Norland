package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;

public class Menu_a3s1 extends MenuSuper {

	//Switch ID: 10
	private MenuItem level18;
	private MenuItem level19;
	private MenuItem level20;
	private MenuItem cancel;
	private MenuItem next;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_a3s1");	
		buttons.add(new MenuItem(GlMainMenu.bitmapa3s1, ButtonType.TITLEWIDE));
		level18 = new MenuItem(GlMainMenu.bitmapLevel18, ButtonType.B1);
		buttons.add(level18);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 19){
			level19 = new MenuItem(GlMainMenu.bitmapLevel19, ButtonType.B2);
			buttons.add(level19);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 20){
			level20 = new MenuItem(GlMainMenu.bitmapLevel20, ButtonType.B3);
			buttons.add(level20);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 21){
			next = new MenuItem(GlMainMenu.bitmapNext, ButtonType.NEXT);
			buttons.add(next);
		}
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();

		if(level18.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(18,context);
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 19){
			if(level19.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(19,context);
			}
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 20){
			if(level20.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.enterGame(20,context);
			}
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 21){
			if(next.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.USER_MENU_SELECT = 11;	
			}	
		}	

		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 2;	
		}	
		
	}

}
