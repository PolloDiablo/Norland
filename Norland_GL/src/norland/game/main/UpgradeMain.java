package norland.game.main;

import norland.game.main.things.Viking;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UpgradeMain {

    public final static int upperNumOffset = 225;
    public final static int numOffset = 66;

    public static void applyUpgrades(Context context) {

        int upgradeNum = 0;
        boolean upgradeBool = false;
        // Arrow damage, Allowed values 5
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_arrowDamage, 1);
        switch (upgradeNum) {
            case 1:
                Viking.arrowDamage = 200;
                break;
            case 2:
                Viking.arrowDamage = 240;
                break;
            case 3:
                Viking.arrowDamage = 290;
                break;
            case 4:
                Viking.arrowDamage = 350;
                break;
            case 5:
                Viking.arrowDamage = 400;
                break;
            default:
                System.err.println("Arrow Damage Upgrade Selection Error");
                Viking.arrowDamage = 200;
                break;
        }

        // Arrow fire rate (decrease to make faster), Allowed values: 3
        upgradeNum = getStandardSharedPreferences(context)
                .getInt(GlMainMenu.LOCAL_arrowFireRate, 1);
        switch (upgradeNum) {
            case 1:
                Viking.arrowSeparation = 21;
                break;
            case 2:
                Viking.arrowSeparation = 16;
                break;
            case 3:
                Viking.arrowSeparation = 10;
                break;
            default:
                System.err.println("Arrow Fire Rate Upgrade Selection Error");
                Viking.arrowSeparation = 21;
                break;
        }

        // Fire fire-arrows, default: false
        upgradeBool = getStandardSharedPreferences(context).getBoolean(GlMainMenu.LOCAL_arrowUber,
                false);
        Viking.makeFireArrows = upgradeBool;

        // Cannon damage, Allowed values: 4
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_cannonDamage, 1);
        switch (upgradeNum) {
            case 1:
                Viking.setProj2Damage(200);
                break;
            case 2:
                Viking.setProj2Damage(240);
                break;
            case 3:
                Viking.setProj2Damage(290);
                break;
            case 4:
                Viking.setProj2Damage(350);
                break;
            default:
                System.err.println("Cannon Damage Upgrade Selection Error");
                Viking.setProj2Damage(200);
                break;
        }

        // Cannon fire rate (decrease to make faster), default: 110 , Allowed
        // values: 3
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_cannonFireRate,
                1);
        switch (upgradeNum) {
            case 1:
                Viking.setProj2Separation(140);
                break;
            case 2:
                Viking.setProj2Separation(110);
                break;
            case 3:
                Viking.setProj2Separation(80);
                break;

            default:
                System.err.println("Cannon Fire Rate Upgrade Selection Error");
                Viking.setProj2Separation(140);
                break;
        }

        // Cannon range, Allowed values: 3
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_cannonRange, 1);
        switch (upgradeNum) {
            case 1:
                Viking.setProj2Range(160);
                break;
            case 2:
                Viking.setProj2Range(210);
                break;
            case 3:
                Viking.setProj2Range(270);
                break;

            default:
                System.err.println("Cannon Range Upgrade Selection Error");
                Viking.setProj2Range(160);
                break;
        }

        // Cannon spread, default: 4, Allowed values: 3
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_cannonSpread, 1);
        switch (upgradeNum) {
            case 1:
                Viking.cannonAmount = 4;
                break;
            case 2:
                Viking.cannonAmount = 6;
                break;
            case 3:
                Viking.cannonAmount = 8;
                break;

            default:
                System.err.println("Cannon Spread Upgrade Selection Error");
                Viking.cannonAmount = 4;
                break;
        }

        // Cannons are UBER, default: false
        upgradeBool = getStandardSharedPreferences(context).getBoolean(GlMainMenu.LOCAL_cannonUber,
                false);
        Viking.cannonUber = upgradeBool;

        // Ship speed, Allowed values: 3
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipSpeed, 1);
        switch (upgradeNum) {
            case 1:
                Viking.MAX_MOVE_SPEED = 0.96;
                break;
            case 2:
                Viking.MAX_MOVE_SPEED = 1.10;
                break;
            case 3:
                Viking.MAX_MOVE_SPEED = 1.26;
                break;

            default:
                System.err.println("Move Speed Upgrade Selection Error");
                Viking.MAX_MOVE_SPEED = 0.96;
                break;
        }

        // Ship turning radius, Allowed values: 4
        upgradeNum = getStandardSharedPreferences(context).getInt(
                GlMainMenu.LOCAL_shipTurningRadius, 1);
        switch (upgradeNum) {
            case 1:
                Viking.TURNING_SPEED = 0.013;
                break;
            case 2:
                Viking.TURNING_SPEED = 0.019;
                break;
            case 3:
                Viking.TURNING_SPEED = 0.026;
                break;
            case 4:
                Viking.TURNING_SPEED = 0.033;
                break;
            default:
                System.err.println("Turning Radius Upgrade Selection Error");
                Viking.TURNING_SPEED = 0.013;
                break;
        }

        upgradeBool = getStandardSharedPreferences(context).getBoolean(
                GlMainMenu.LOCAL_shipUberUtility, false);
        Viking.UTILITY_UBER = upgradeBool;

        // Reduces damage taken by fire, Allowed values: 3
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipFireResist,
                1);
        switch (upgradeNum) {
            case 1:
                Viking.FIRE_RESIST = 0;
                break;
            case 2:
                Viking.FIRE_RESIST = 31;
                break;
            case 3:
                Viking.FIRE_RESIST = 53;
                break;

            default:
                System.err.println("Fire Resist Upgrade Selection Error");
                Viking.FIRE_RESIST = 0;
                break;
        }

        // Reduces percent hull damage taken, Allowed values: 4
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipHullResist,
                1);
        switch (upgradeNum) {
            case 1:
                Viking.HULL_RESIST = 0;
                break;
            case 2:
                Viking.HULL_RESIST = 27;
                break;
            case 3:
                Viking.HULL_RESIST = 48;
                break;
            case 4:
                Viking.HULL_RESIST = 65;
                break;
            default:
                System.err.println("Hull Resist Upgrade Selection Error");
                Viking.HULL_RESIST = 0;
                break;
        }

        // Damage that viking deals on collision with an enemy, Allowed values:
        // 2
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipHullDamage,
                1);
        switch (upgradeNum) {
            case 1:
                Viking.DMG = 500;
                break;
            case 2:
                Viking.DMG = 1200;
                break;
            default:
                System.err.println("Collosion Damage Upgrade Selection Error");
                Viking.DMG = 500;
                break;
        }

        // Uber hull, survive rock collisions, icebergs just slow you down for a
        // second, default: false
        upgradeBool = getStandardSharedPreferences(context).getBoolean(
                GlMainMenu.LOCAL_shipUberHull, false);
        Viking.SURVIVE_ROCKS = upgradeBool;

        // Ship max health, Allowed values: 5
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipHealth, 1);
        switch (upgradeNum) {
            case 1:
                Viking.VIKING_HLTH = 500;
                break;
            case 2:
                Viking.VIKING_HLTH = 640;
                break;
            case 3:
                Viking.VIKING_HLTH = 800;
                break;
            case 4:
                Viking.VIKING_HLTH = 960;
                break;
            case 5:
                Viking.VIKING_HLTH = 1140;
                break;
            default:
                System.err.println("Health Upgrade Selection Error");
                Viking.VIKING_HLTH = 500;
                break;
        }

        // Health regen rate, can only regen to 50% , Allowed values: 3
        upgradeNum = getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipHealthRegen,
                1);
        switch (upgradeNum) {
            case 1:
                Viking.HEALTH_REGEN = (float) 0.20;
                break;
            case 2:
                Viking.HEALTH_REGEN = (float) 0.32;
                break;
            case 3:
                Viking.HEALTH_REGEN = (float) 0.45;
                break;
            default:
                System.err.println("Health Regen Upgrade Selection Error");
                Viking.HEALTH_REGEN = (float) 0.20;
                break;
        }

        // Uber health, can regen to 100%, also faster, plus more max
        // health, default: false
        upgradeBool = getStandardSharedPreferences(context).getBoolean(
                GlMainMenu.LOCAL_shipUberHealth, false);
        if (upgradeBool) {
            Viking.VIKING_HLTH = 1300;
            Viking.HEALTH_REGEN = (float) 0.55;
            Viking.HEALTH_REGEN_100 = true;
        } else {
            Viking.HEALTH_REGEN_100 = false;
        }
        // TEMP
        // context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
        // Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_cannonSpread,
        // 3).commit();
        // context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
        // Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipFireResist,
        // 75).commit();
        // context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
        // Context.MODE_PRIVATE).edit().putInt(GlMainMenu.LOCAL_shipHullResist,
        // 75).commit();

        testUpgrades(context);

    }

    /**
     * @param context
     * @return
     */
    public final static SharedPreferences getStandardSharedPreferences(Context context) {
        return context.getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE);
    }

    // To test the upgrade system counter, here for error checking/handling
    private static void testUpgrades(Context context) {
        int usedCount = 0;
        // Arrow damage, Allowed values 5
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_arrowDamage, 1);
        // Arrow fire rate (decrease to make faster), Allowed values: 3
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_arrowFireRate, 1);
        // Fire fire-arrows, default: false
        if (getStandardSharedPreferences(context).getBoolean(GlMainMenu.LOCAL_arrowUber, false)) {
            usedCount++;
        }
        // Cannon damage, Allowed values: 4
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_cannonDamage, 1);
        // Cannon fire rate (decrease to make faster), default: 110 , Allowed
        // values: 3
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_cannonFireRate, 1);
        // Cannon range, Allowed values: 3
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_cannonRange, 1);
        // Cannon spread, default: 4, Allowed values: 3
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_cannonSpread, 1);
        // Cannons are UBER, default: false
        if (getStandardSharedPreferences(context).getBoolean(GlMainMenu.LOCAL_cannonUber, false)) {
            usedCount++;
        }
        // Ship speed, Allowed values: 3
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipSpeed, 1);
        // Ship turning radius, Allowed values: 4
        usedCount = usedCount
                - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipTurningRadius,
                        1);
        // Uber utility, default: false
        if (getStandardSharedPreferences(context).getBoolean(GlMainMenu.LOCAL_shipUberUtility,
                false)) {
            usedCount++;
        }
        // Reduces damage taken by fire, Allowed values: 3
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipFireResist, 1);
        // Reduces percent hull damage taken, Allowed values: 4
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipHullResist, 1);
        // Damage that viking deals on collision with an enemy, Allowed values:
        // 2
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipHullDamage, 1);

        // Uber hull, survive rock collisions, icebergs just slow you down for a
        // second, default: false
        if (getStandardSharedPreferences(context).getBoolean(GlMainMenu.LOCAL_shipUberHull, false)) {
            usedCount++;
        }
        // Ship max health, Allowed values: 5
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipHealth, 1);
        // Health regen rate, can only regen to 50% , Allowed values: 3
        usedCount = usedCount - 1
                + getStandardSharedPreferences(context).getInt(GlMainMenu.LOCAL_shipHealthRegen, 1);
        // Uber health, can regen to 100%, also faster, plus more max health,
        // default: false
        if (getStandardSharedPreferences(context)
                .getBoolean(GlMainMenu.LOCAL_shipUberHealth, false)) {
            usedCount++;
        }

        if (usedCount != getStandardSharedPreferences(context).getInt(
                GlMainMenu.LOCAL_upgradesSpent, 0)) {
            Log.e("GlRenderer", "LOCAL_upgradesSpent is wrong :(");
            getStandardSharedPreferences(context).edit()
                    .putInt(GlMainMenu.LOCAL_upgradesSpent, usedCount).commit();
        }

    }

}
