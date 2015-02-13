package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;

public class Menu_Options extends MenuSuper {

	//Switch ID: 3
	private MenuItem sound;
	private MenuItem tips;
	private MenuItem network;
	private MenuItem cancel;
	
	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_Options");	
		buttons.add(new MenuItem(GlMainMenu.bitmapTitle_options, ButtonType.TITLEREG));
		sound = new MenuItem(GlMainMenu.bitmapSound, ButtonType.B1);
		buttons.add(sound);
		tips = new MenuItem(GlMainMenu.bitmapTips, ButtonType.B2);
		buttons.add(tips);
		network = new MenuItem(GlMainMenu.bitmapNetwork, ButtonType.B3);
		buttons.add(network);
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();
		
		if(sound.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.showSoundAlert();
		}
		
		if(tips.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.showTipsAlert();
		}
		
		if(network.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.showNetworkAlert();
		}

		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT = 1;	
		}	
	}

}
