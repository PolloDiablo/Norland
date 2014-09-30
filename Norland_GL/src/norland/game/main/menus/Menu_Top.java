package norland.game.main.menus;

import javax.microedition.khronos.opengles.GL10;

import norland.game.main.GlMainMenu;
import norland.game.main.GlRenderer;
import norland.game.main.MainMenu_Activity;
import norland.game.main.Thing;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Menu_Top implements MenuState {

    // Switch ID: 1
    private Thing title;
    private Thing beta; //TODO
    private Thing Campaign;
    private Thing Options;
    private Thing Extras;
    private Thing Feedback;
    private Thing upgrades;

    public void addStuff() {
        // Log.d(GlMainMenu.TAGMM, "Menu: Menu_Top");

        title = new Thing(GlMainMenu.bitmapTitle_top, GlMainMenu.WIDTH / 2,
                GlMainMenu.heightScale * 100, 256, 64);
        beta = new Thing(GlMainMenu.bitmapBeta, 160 + GlMainMenu.WIDTH / 2,
                GlMainMenu.heightScale * 125, 128, 32);
        Campaign = new Thing(GlMainMenu.bitmapCampaign, GlMainMenu.WIDTH / 2,
                GlMainMenu.heightScale * 250, 256, 64);
        Options = new Thing(GlMainMenu.bitmapOptions, GlMainMenu.WIDTH / 2,
                GlMainMenu.heightScale * 350, 256, 64);
        Extras = new Thing(GlMainMenu.bitmapExtras, GlMainMenu.WIDTH / 2,
                GlMainMenu.heightScale * 450, 256, 64);
        Feedback = new Thing(GlMainMenu.bitmapFeedback, GlMainMenu.WIDTH / 2,
                GlMainMenu.heightScale * 550, 256, 64);
        upgrades = new Thing(GlMainMenu.bitmapUpgrades, GlMainMenu.WIDTH / 2,
                GlMainMenu.heightScale * 650, 256, 64);
    }

    public void update(Context context) {

        title.setX(GlMainMenu.WIDTH / 2 + GlMainMenu.menuShiftX);
        title.setY(GlMainMenu.heightScale * 100 + GlMainMenu.menuShiftY);

        beta.setX(GlMainMenu.widthScale * 160 + GlMainMenu.WIDTH / 2 + GlMainMenu.menuShiftX);
        beta.setY(GlMainMenu.heightScale * 125 + GlMainMenu.menuShiftY);

        Campaign.setX(GlMainMenu.WIDTH / 2 + GlMainMenu.menuShiftX);
        Campaign.setY(GlMainMenu.heightScale * 250 + GlMainMenu.menuShiftY);

        Options.setX(GlMainMenu.WIDTH / 2 + GlMainMenu.menuShiftX);
        Options.setY(GlMainMenu.heightScale * 350 + GlMainMenu.menuShiftY);

        Extras.setX(GlMainMenu.WIDTH / 2 + GlMainMenu.menuShiftX);
        Extras.setY(GlMainMenu.heightScale * 450 + GlMainMenu.menuShiftY);

        Feedback.setX(GlMainMenu.WIDTH / 2 + GlMainMenu.menuShiftX);
        Feedback.setY(GlMainMenu.heightScale * 550 + GlMainMenu.menuShiftY);

        upgrades.setX(GlMainMenu.WIDTH / 2 + GlMainMenu.menuShiftX);
        upgrades.setY(GlMainMenu.heightScale * 650 + GlMainMenu.menuShiftY);

        Campaign.update();
        Options.update();
        Extras.update();
        Feedback.update();
        upgrades.update();

        if (Campaign.hasCollided((GlMainMenu.clickSelection), true)) {
            if (context
                    .getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                    .getInt(GlMainMenu.LOCAL_levelUnlock, 1) == 1) {
            	GlMainMenu.enterGame(1,context);
            } else {
                GlMainMenu.USER_MENU_SELECT = 2;
            }
        }
        if (Options.hasCollided((GlMainMenu.clickSelection), true)) {
            GlMainMenu.USER_MENU_SELECT = 3;
        }
        if (Extras.hasCollided((GlMainMenu.clickSelection), true)) {
            GlMainMenu.USER_MENU_SELECT = 13;
        }
        if (Feedback.hasCollided((GlMainMenu.clickSelection), true)) {
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
            if (upgrades.hasCollided(GlMainMenu.clickSelection, true)) {
            	//Launch upgrade menu
            	GlMainMenu.enterGame(100,context);
        		GlRenderer.startUpgradeScreen=true;
            }
        }

        GlMainMenu.clickSelection.setX(10000);
        GlMainMenu.clickSelection.setY(10000);

    }

    public void initiateShapes(GL10 gl, Context context) {
        title.initShape(gl, context);
        beta.initShape(gl, context);
        Campaign.initShape(gl, context);
        Options.initShape(gl, context);
        Extras.initShape(gl, context);
        Feedback.initShape(gl, context);
        upgrades.initShape(gl, context);
    }

    public void onDrawFrame(GL10 gl, Context context) {
        title.draw(gl);
        beta.draw(gl);
        Campaign.draw(gl);
        Options.draw(gl);
        Extras.draw(gl);
        Feedback.draw(gl);
        if (context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getInt(GlMainMenu.LOCAL_levelUnlock, 1) >= 4) {
            upgrades.draw(gl);
        }
    }

}
