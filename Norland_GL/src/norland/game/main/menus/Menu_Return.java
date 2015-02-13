package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;


public class Menu_Return extends MenuSuper {

	//Switch ID: 15
	private MenuItem retry;
	private MenuItem returnToMenu;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_Return");
		buttons.add(new MenuItem(GlMainMenu.bitmapTitle_death, ButtonType.TITLEHUGE));
		retry = new MenuItem(GlMainMenu.bitmapRetry, ButtonType.B1);
		buttons.add(retry);
		returnToMenu = new MenuItem(GlMainMenu.bitmapExit, ButtonType.BOTTOM);
		buttons.add(returnToMenu);
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();
		
		if(retry.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(GlMainMenu.levelThatTheUserDiedOn,context);
		}
		
		if(returnToMenu.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT=1;
		}
	}
}
