package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Menu_Extras extends MenuSuper {

	// Switch ID: 13
	private MenuItem easterEggs;
	private MenuItem highScores;
	private MenuItem credits;
	private MenuItem cancel;

	@Override
	public void addStuff(Context context) {
		// Log.d(GlMainMenu.TAGMM,"Menu: Menu_Extras");
		buttons.add(new MenuItem(GlMainMenu.bitmapTitle_extras,
				ButtonType.TITLEREG));
		highScores = new MenuItem(GlMainMenu.bitmapHighScores, ButtonType.B1);
		buttons.add(highScores);
		credits = new MenuItem(GlMainMenu.bitmapCredits, ButtonType.B2);
		buttons.add(credits);
		if (context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
				Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_hasCompleted,
				false)) {
			easterEggs = new MenuItem(GlMainMenu.bitmapEasterEggs256,
					ButtonType.B3);
			buttons.add(easterEggs);
		}
		cancel = new MenuItem(GlMainMenu.bitmapCancel, ButtonType.BOTTOM);
		buttons.add(cancel);
	}

	@Override
	public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();

		if (context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
				Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_hasCompleted,
				false)) {
			if (easterEggs.getThing().hasCollided(clickSelection, true)) {
				GlMainMenu.USER_MENU_SELECT = 14;
			}
		}

		if (highScores.getThing().hasCollided(clickSelection, true)) {
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://norland.casualt.ca/")); // TODO: Tony, fix
																// me :P
			context.startActivity(intent);

		}

		if (credits.getThing().hasCollided(clickSelection, true)) {
			GlMainMenu.showCredits(context);

		}

		if (cancel.getThing().hasCollided(clickSelection, true)) {
			GlMainMenu.USER_MENU_SELECT = 1;
		}

	}

}
