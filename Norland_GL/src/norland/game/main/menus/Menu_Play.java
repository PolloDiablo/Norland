package norland.game.main.menus;


import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;


public class Menu_Play extends MenuSuper {
	
	//Switch ID: 2
	private MenuItem resume;
	private MenuItem act1;
	private MenuItem act2;
	private MenuItem act3;
	private MenuItem cancel;

	public void addStuff(Context context) {
		//Log.d(GlMainMenu.TAGMM,"Menu: Menu_Play");
		buttons.add(new MenuItem(GlMainMenu.bitmapTitle_campaign, ButtonType.TITLEWIDE));
		resume = new MenuItem(GlMainMenu.bitmapResume, ButtonType.B1);
		buttons.add(resume);
		act1 = new MenuItem(GlMainMenu.bitmapAct1, ButtonType.B2);
		buttons.add(act1);
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 9){
			act2 = new MenuItem(GlMainMenu.bitmapAct2, ButtonType.B3);
			buttons.add(act2);
		}
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 18){
			act3 = new MenuItem(GlMainMenu.bitmapAct3, ButtonType.B4);
			buttons.add(act3);
		}
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);	
		
	}

	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();
		
		if(resume.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.enterGame(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1),context);
		}
		
		if(act1.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT=4;
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 9){
			if(act2.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.USER_MENU_SELECT=7;
			}
		}
		
		if(context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE).getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 18){
			if(act3.getThing().hasCollided(clickSelection,true)){
				GlMainMenu.USER_MENU_SELECT=10;
			}
		}
			
		if(cancel.getThing().hasCollided(clickSelection,true)){
			GlMainMenu.USER_MENU_SELECT=1;
		}	
	}

}
