package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;

public class Menu_EasterEggs extends MenuSuper {

	//Switch ID: 14
	private MenuItem rubberDucky;
	private MenuItem maliciousIcebergs;
	private MenuItem cancel;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_EasterEggs");
		buttons.add(new MenuItem(GlMainMenu.bitmapTitle_eastereggs, ButtonType.TITLEWIDE));
		rubberDucky = new MenuItem(GlMainMenu.bitmapRubberDuckyMode, ButtonType.B1);
		buttons.add(rubberDucky);
		maliciousIcebergs = new MenuItem(GlMainMenu.bitmapEvilIcebergsMode, ButtonType.B2);
		buttons.add(maliciousIcebergs);
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);	
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();
		
		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT=13;
		}
		
		if(rubberDucky.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.showRubberDuckyAlert();
		}
		if(maliciousIcebergs.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.showEvilIcebergsAlert();
		}
	}

}
