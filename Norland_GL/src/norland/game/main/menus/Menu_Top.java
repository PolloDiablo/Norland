package norland.game.main.menus;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import norland.game.main.menus.MenuItem.ButtonType;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Menu_Top extends MenuSuper {

    // Switch ID: 1
    private MenuItem campaign;
    private MenuItem options;
    private MenuItem extras;
    private MenuItem feedback;
    private MenuItem upgrades;

    public void addStuff(Context context) {
        // Log.d(GlMainMenu.TAGMM, "Menu: Menu_Top");
		buttons.add(new MenuItem(GlMainMenu.bitmapTitle_top, ButtonType.TITLEREG));
		buttons.add(new MenuItem(GlMainMenu.bitmapBeta, ButtonType.BETA));
        campaign = new MenuItem(GlMainMenu.bitmapCampaign, ButtonType.B1);
		buttons.add(campaign);
        options = new MenuItem(GlMainMenu.bitmapOptions, ButtonType.B2);
		buttons.add(options);
        extras = new MenuItem(GlMainMenu.bitmapExtras, ButtonType.B3);
		buttons.add(extras);
        feedback = new MenuItem(GlMainMenu.bitmapFeedback, ButtonType.B4);
		buttons.add(feedback);
        if (context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 4) {
            upgrades = new MenuItem(GlMainMenu.bitmapUpgrades, ButtonType.B5);
    		buttons.add(upgrades);
        }
    }

    public void update(Thing clickSelection, Context context) {
		super.updateButtonPositions();
    	
        if (campaign.getThing().hasCollided(clickSelection, true)) {
            if (context
                    .getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                    .getInt(GlMainMenu.LOCAL_levelUnlock, 1) == 1) {
            	GlMainMenu.enterGame(1,context);
            } else {
                GlMainMenu.USER_MENU_SELECT = 2;
            }
        }
        if (options.getThing().hasCollided(clickSelection, true)) {
            GlMainMenu.USER_MENU_SELECT = 3;
        }
        if (extras.getThing().hasCollided(clickSelection, true)) {
            GlMainMenu.USER_MENU_SELECT = 13;
        }
        if (feedback.getThing().hasCollided(clickSelection, true)) {
            // launches a feedback form web browser.
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://docs.google.com/spreadsheet/viewform?formkey=dHBkcUhpeXRtekhaUmxCeUNPUHFHR3c6MQ#gid=0"));
            context.startActivity(intent);
            // The feedback form. :)
            // https://docs.google.com/spreadsheet/viewform?formkey=dHBkcUhpeXRtekhaUmxCeUNPUHFHR3c6MQ#gid=0
        }
        
        if (context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 4) {
            if (upgrades.getThing().hasCollided(clickSelection, true)) {
            	//Launch upgrade menu
            	GlMainMenu.enterGame(100,context);
        		GlRenderer.startUpgradeScreen=true;
            }
        }

    }
}
