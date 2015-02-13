package norland.game.main;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import norland.game.main.Thing.State;
import norland.game.main.levels.Level01;
import norland.game.main.levels.Level02;
import norland.game.main.levels.Level03;
import norland.game.main.levels.Level04;
import norland.game.main.levels.Level05;
import norland.game.main.levels.Level06;
import norland.game.main.levels.LevelSuper;
import norland.game.main.levels.level07;
import norland.game.main.levels.level08;
import norland.game.main.levels.level09;
import norland.game.main.levels.level10;
import norland.game.main.levels.level100;
import norland.game.main.levels.level11;
import norland.game.main.levels.level12;
import norland.game.main.levels.level13;
import norland.game.main.levels.level14;
import norland.game.main.levels.level15;
import norland.game.main.levels.level16;
import norland.game.main.levels.level17;
import norland.game.main.levels.level18;
import norland.game.main.levels.level19;
import norland.game.main.levels.level20;
import norland.game.main.levels.level21;
import norland.game.main.levels.level22;
import norland.game.main.levels.level23;
import norland.game.main.levels.level24;
import norland.game.main.levels.level25;
import norland.game.main.levels.level26;
import norland.game.main.levels.level27;
import norland.game.main.menus.UpgradeSuper;
import norland.game.main.menus.Upgrade_Arrows;
import norland.game.main.menus.Upgrade_Cannons;
import norland.game.main.menus.Upgrade_Defense;
import norland.game.main.menus.Upgrade_Health;
import norland.game.main.menus.Upgrade_Top;
import norland.game.main.menus.Upgrade_Utility;
import norland.game.main.projectiles.FireArrow;
import norland.game.main.projectiles.Projectile;
import norland.game.main.serverComm.Utilities;
import norland.game.main.things.Viking;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

@SuppressLint("ViewConstructor")
public class GlRenderer extends GLSurfaceView implements Renderer {

    public static int HEIGHT;
    public static int WIDTH;
    public static int HEIGHT_HALF;
    public static int WIDTH_HALF;

    // Location of ship on our coordinate system
    public static double shipLocX;
    public static double shipLocY;
    public static double shipLeadX;
    public static double shipLeadY;
    // Used to translate camera
    public static float GLshipLocX;
    public static float GLshipLocY;
    // Location of ship relative to

    public static final String TAG = GlRenderer.class.getSimpleName();

    // All the things.
    // private Visual water;
    private MapBackground waterStationary;
    // private VisualDynamic scoreDisplay;

    private int healthBarOffset;
    private Visual healthBack;
    private Visual healthBar;
    private Visual healthBorder;
    private Visual shieldSymbol;

    public static Bitmap bitmapCannonCooldown;
    private static double cooldownComparison;
    private static final int CDRadius = 390;
    private Thing cannonCooldown1;
    private Thing cannonCooldown2;
    private Thing cannonCooldown3;
    private Thing cannonCooldown4;
    private Thing cannonCooldown5;

    private Visual upgradeBackground;

    // Used to locate (0,0) will need these later for screen resizing
    //private Visual test,test2;


    private static LevelSuper myLevel;

    public static LevelSuper getLevel() {
        return myLevel;
    }

    private static int nextLevel;
    public static void setNextLevel(int nextLevel){
    	GlRenderer.nextLevel=nextLevel;
    }
    public static int getNextLevel(){
    	return GlRenderer.nextLevel;
    }

    public static Viking viking;
    public static ArrayList<Projectile> projectiles;
    public static ArrayList<Projectile> wakes;

    public static int OUTSIDE_SIZE; // DO NOT SET TO 0!!!

    public static Bitmap bitmapWater;
    public static Bitmap bitmapShip;
    public static Bitmap bitmapArrow;
    public static Bitmap bitmapCannonball;
    public static Bitmap bitmapMine;
    public static Bitmap bitmapWakeL;
    public static Bitmap bitmapWakeR;
    public static Bitmap bitmapWakeC;

    public static Bitmap bitmapIceberg;
    public static Bitmap bitmapRock;
    public static Bitmap bitmapRock2;
    public static Bitmap bitmapRock3;
    public static Bitmap bitmapDragon;
    public static Bitmap bitmapVod;
    public static Bitmap bitmapFosse;
    public static Bitmap bitmapGrindylow;
    public static Bitmap bitmapLorelei;
    public static Bitmap bitmapSprite;
    public static Bitmap bitmapFrigate;
    public static Bitmap bitmapRedNote;
    public static Bitmap bitmapGreenNote;
    public static Bitmap bitmapFireball;
    public static Bitmap bitmapFireBreath;
    public static Bitmap bitmapFireArrow;
    public static Bitmap bitmapLoreleiProj;
    private static Bitmap bitmapDuck;
    public static Bitmap bitmapIsland1;

    // public static Bitmap bitmapCompass;
    public static Bitmap bitmapCompassPointer;
    public static boolean showCompass;
    public static boolean showCompassDistance;

    // public static Visual compass;
    public static Thing compassPointer;
    private static VisualDynamic compassDistance;
    private static String compassDistanceText;

    /** Set inside each level */
    public static boolean weaponsOn;
  
    private static int fireSecondCannonVolleyCounter;
    private static boolean fireSecondCannonVolley;

    /**
     * True if the game is in a paused state (you pressed the button, tip
     * appeared, upgrade menu started, etc.)
     */
    public static boolean paused;

    /**
     * Set this to true if you are directly going back to the main menu (or
     * launching the next level) Do not modify this if you wish to launch the
     * upgrade screen. The upgrade screen automatically sets this to true when
     * you are done.
     */
    public static boolean backToMenu;

    // For displaying tips
    private static boolean showTip;
    private static String tipTitleID;
    private static String tipMessageID;

    // FUN STUFF
    public static boolean evilIcebergs;
    public static int bergKillCount;
    public static int dragonKillCount;

    /** Set this to true if the end-of-level trigger/quest has been completed */
    public static boolean levelFinished;

    /**
     * Don't register touches until the update cycle has called at least once,
     * don't modify this :)
     */
    private static boolean hasFinishedLoading;

    /**
     * Set this to true if you want to launch the upgrade screen, note that the
     * level will end after the user is done upgrading stuff
     */
    public static boolean startUpgradeScreen;
    /** True if the upgrade screen is running, shouldn't need to touch this */
    public static boolean runningUpgradeScreen;
    /**
     * True if the upgrade screen is exited by the user, shouldn't need to touch
     * this
     */
    public static boolean endUpgradeScreen;

    /** Main object for the current menu being displayed */
    private UpgradeSuper myUpgradeState;
    /** Stores which sub-menu is currently being displayed */
    public static int USER_UPGRADE_SELECT;

    private static Thing clickSelection;
    public static Bitmap bitmapDamage;
    public static Bitmap bitmapFireRate;
    public static Bitmap bitmapFireResist;
    public static Bitmap bitmapHealth;
    public static Bitmap bitmapHullResist;
    public static Bitmap bitmapMinus;
    public static Bitmap bitmapNavalRam;
    public static Bitmap bitmapPlus;
    public static Bitmap bitmapPoints;
    public static Bitmap bitmapRange;
    public static Bitmap bitmapRegen;
    public static Bitmap bitmapSpeed;
    public static Bitmap bitmapSpread;
    public static Bitmap bitmapTurning;
    public static Bitmap bitmapUber;
    public static Bitmap bitmapBArrows;
    public static Bitmap bitmapBCannons;
    public static Bitmap bitmapBDefense;
    public static Bitmap bitmapBHealth;
    public static Bitmap bitmapBUtility;
    public static Bitmap bitmapTArrows;
    public static Bitmap bitmapTCannons;
    public static Bitmap bitmapTDefense;
    public static Bitmap bitmapTHealth;
    public static Bitmap bitmapTTop;
    public static Bitmap bitmapTUtility;
    public static Bitmap bitmapExit;
    public static Bitmap bitmapBack;
    public static Bitmap bitmapUBackground;
    public static Bitmap bitmapUNum;

    public static boolean onPauseCalled; // Set to true when onPause() is called
                                         // and the media player has been
                                         // released
    // public static MediaPlayer mediaPlayer;

    private Norland_GLActivity myParent;

    /** Constructor */
    public GlRenderer(Norland_GLActivity myParent) {
        super(myParent);
        // this.context = context;
        setRenderer(this);
        this.myParent = myParent;
        hasFinishedLoading = false;
        onPauseCalled = false;
        // setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); //apparently we
        // need this? <--From opengl-es10 tutorial
    }/* constructor */

    /** Called when the game is launched, initializes stuff */
    private void initializeEverything() {
        weaponsOn = true;
        evilIcebergs = getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
                Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_evilIcebergsMode, false);
        bergKillCount = 0;
        dragonKillCount = 0;

        levelFinished = false;
        paused = false;
        backToMenu = false;

        // Get the width/height of the screen, store to variables
        WIDTH = getWidth();
        HEIGHT = getHeight();
        WIDTH_HALF = WIDTH / 2;
        HEIGHT_HALF = HEIGHT / 2;
        // Starting location of the ship
        shipLocX = WIDTH_HALF;
        shipLocY = HEIGHT_HALF;
        // Just so there won't be a null pointer for these
        shipLeadX = WIDTH_HALF;
        shipLeadY = HEIGHT_HALF;

        initializeBitmaps();

        r = new Random();

        // compass = new Visual(bitmapCompass, WIDTH - compassOffset, HEIGHT -
        // compassOffset, 64, 64);
        compassPointer = new Thing(bitmapCompassPointer, 0, 0, 440, 440);
        compassDistance = new VisualDynamic(300, 140);
        compassDistanceText = "0m";
        showCompass = true;
        showCompassDistance = true;

        // Initialize water
        // this.water = new Visual(bitmapWater,WIDTH/2,HEIGHT/2,WIDTH,HEIGHT);
        this.waterStationary = new MapBackground(bitmapWater, WIDTH_HALF, HEIGHT_HALF, WIDTH
                / GlMainMenu.widthScale, HEIGHT / GlMainMenu.heightScale);

        // Initialize projectiles Arraylist
        projectiles = new ArrayList<Projectile>();
        wakes = new ArrayList<Projectile>();


        fireArrowPrototype = new FireArrow(Viking.fireArrowDamage, Viking.fireArrowRange*GlMainMenu.widthScale, true);
        
        
        // Apply upgrades
        UpgradeMain.applyUpgrades(getContext());

        // create viking
        if (!getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
                Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_rubberDuckyMode, false)) {
            bitmapShip = BitmapFactory.decodeResource(getResources(), R.drawable.thing_ship);
            Viking.BoxWidth = 60;
            Viking.BoxHeight = 30;
            viking = new Viking(bitmapShip, (int) shipLocX, (int) shipLocY);
        } else {
            bitmapDuck = BitmapFactory.decodeResource(getResources(), R.drawable.thing_rubberduck);
            Viking.BoxWidth = 60;
            Viking.BoxHeight = 30;
            viking = new Viking(bitmapDuck, (int) shipLocX, (int) shipLocY);
        }

        // Initialize visuals and health display
        cannonCooldown1 = new Thing(bitmapCannonCooldown, 0, 0, CDRadius, CDRadius);
        cannonCooldown1.setAngle(0);
        cannonCooldown2 = new Thing(bitmapCannonCooldown, 0, 0, CDRadius, CDRadius);
        cannonCooldown2.setAngle(Math.PI * 72 / 180);
        cannonCooldown3 = new Thing(bitmapCannonCooldown, 0, 0, CDRadius, CDRadius);
        cannonCooldown3.setAngle(Math.PI * 144 / 180);
        cannonCooldown4 = new Thing(bitmapCannonCooldown, 0, 0, CDRadius, CDRadius);
        cannonCooldown4.setAngle(Math.PI * 216 / 180);
        cannonCooldown5 = new Thing(bitmapCannonCooldown, 0, 0, CDRadius, CDRadius);
        cannonCooldown5.setAngle(Math.PI * 288 / 180);

        healthBack = new Visual(BitmapFactory.decodeResource(getResources(),
                R.drawable.game_health_back), 200, 34);
        healthBar = new Visual(BitmapFactory.decodeResource(getResources(),
                R.drawable.game_health_bar), 200, 34);
        healthBorder = new Visual(BitmapFactory.decodeResource(getResources(),
                R.drawable.game_health_border), 200, 34);
        shieldSymbol = new Visual(BitmapFactory.decodeResource(getResources(),
                R.drawable.game_shield), 40, 40);

        // The backgound for the upgrade screen
        upgradeBackground = new Visual(bitmapUBackground, WIDTH / GlMainMenu.widthScale, HEIGHT
                / GlMainMenu.heightScale);

        // The two yellow lines for map scaling, will remove once we get scaling
        // working for various screen sizes
       // test2 = new Visual(BitmapFactory.decodeResource(getResources(),R.drawable.game_test), 4, 80);
        //test = new Visual(BitmapFactory.decodeResource(getResources(),R.drawable.game_test2), 80, 4);
        chooseLevel();
        myLevel.addStuff();

        if (getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
                Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_soundOn, true)) {
            myLevel.startMusic(getContext());
            GlMainMenu.mediaPlayer.start(); // no need to call prepare(), create() does that for you        
            try {
          	  GlMainMenu.mediaPlayer.setLooping(true);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
        } else {
            // ***A mediaPlayer will always be created, but not necessarily
            // started
            myLevel.startMusic(getContext());
            Log.d("GlRenderer", "Created media player, but did not start it");
        }
        // For the switch, change upgrade is only called if this is !=0
        USER_UPGRADE_SELECT = 0;
    }

    private void chooseLevel() {
    	desiredAngle = Double.NaN;
        viking.setMoveSpeed(0);
        viking.setAngle(0);
        switch (nextLevel) {

            case 1:
                myLevel = new Level01();
                break;
            case 2:
                myLevel = new Level02();
                break;
            case 3:
                myLevel = new Level03();
                break;
            case 4:
                myLevel = new Level04();
                break;
            case 5:
                myLevel = new Level05();
                break;
            case 6:
                myLevel = new Level06();
                break;
            case 7:
                myLevel = new level07();
                break;
            case 8:
                myLevel = new level08();
                break;
            case 9:
                myLevel = new level09();
                break;
            case 10:
                myLevel = new level10();
                break;
            case 11:
                myLevel = new level11();
                break;
            case 12:
                myLevel = new level12();
                break;
            case 13:
                myLevel = new level13();
                break;
            case 14:
                myLevel = new level14();
                break;
            case 15:
                myLevel = new level15();
                break;
            case 16:
                myLevel = new level16();
                break;
            case 17:
                myLevel = new level17();
                break;
            case 18:
                myLevel = new level18();
                break;
            case 19:
                myLevel = new level19();
                break;
            case 20:
                myLevel = new level20();
                break;
            case 21:
                myLevel = new level21();
                break;
            case 22:
                myLevel = new level22();
                break;
            case 23:
                myLevel = new level23();
                break;
            case 24:
                myLevel = new level24();
                break;
            case 25:
                myLevel = new level25();
                break;
            case 26:
                myLevel = new level26();
                break;
            case 27:
                myLevel = new level27();
                break;
            case 100:
                myLevel = new level100();
                break;
            default:
                myLevel = new Level01();
                System.err.println("Level Selection ERROR");
                break;
        }
    }

    private void initializeBitmaps() {
        bitmapWater = BitmapFactory.decodeResource(getResources(), R.drawable.game_wateruse3);
        bitmapArrow = BitmapFactory.decodeResource(getResources(), R.drawable.proj_arrow);
        bitmapCannonball = BitmapFactory.decodeResource(getResources(), R.drawable.proj_cannonball);
        bitmapMine = BitmapFactory.decodeResource(getResources(), R.drawable.proj_mine);
        bitmapWakeL = BitmapFactory.decodeResource(getResources(), R.drawable.game_wake_left);
        bitmapWakeR = BitmapFactory.decodeResource(getResources(), R.drawable.game_wake_right);
        bitmapWakeC = BitmapFactory.decodeResource(getResources(), R.drawable.game_wake_center);

        bitmapIceberg = BitmapFactory.decodeResource(getResources(), R.drawable.thing_iceberg);
        bitmapRock = BitmapFactory.decodeResource(getResources(), R.drawable.thing_rock);
        bitmapRock2 = BitmapFactory.decodeResource(getResources(), R.drawable.thing_rock2);
        bitmapRock3 = BitmapFactory.decodeResource(getResources(), R.drawable.thing_rock3);
        bitmapDragon = BitmapFactory.decodeResource(getResources(), R.drawable.thing_dragon);
        bitmapVod = BitmapFactory.decodeResource(getResources(), R.drawable.thing_vod);
        bitmapFosse = BitmapFactory.decodeResource(getResources(), R.drawable.thing_fosse);
        bitmapGrindylow = BitmapFactory.decodeResource(getResources(), R.drawable.thing_grindylow);
        bitmapLorelei = BitmapFactory.decodeResource(getResources(), R.drawable.thing_lorelei);
        bitmapSprite = BitmapFactory.decodeResource(getResources(), R.drawable.thing_sprite);
        bitmapFrigate = BitmapFactory.decodeResource(getResources(), R.drawable.thing_frigate);
        bitmapRedNote = BitmapFactory.decodeResource(getResources(), R.drawable.proj_note_red);
        bitmapGreenNote = BitmapFactory.decodeResource(getResources(), R.drawable.proj_note_green);
        bitmapFireball = BitmapFactory.decodeResource(getResources(), R.drawable.proj_fireball);
        bitmapFireBreath = BitmapFactory.decodeResource(getResources(), R.drawable.proj_firebreath);
        bitmapFireArrow = BitmapFactory.decodeResource(getResources(), R.drawable.proj_firearrow);
        bitmapLoreleiProj = BitmapFactory.decodeResource(getResources(), R.drawable.proj_lorelei);
        bitmapIsland1 = BitmapFactory.decodeResource(getResources(), R.drawable.thing_island);
        // bitmapCompass = BitmapFactory.decodeResource(getResources(),
        // R.drawable.game_compass);
        bitmapCompassPointer = BitmapFactory.decodeResource(getResources(),
                R.drawable.game_compassneedle);
        bitmapCannonCooldown = BitmapFactory.decodeResource(getResources(),
                R.drawable.game_cooldown);

        // Upgrade Menu
        bitmapDamage = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_damage);
        bitmapFireRate = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_firerate);
        bitmapFireResist = BitmapFactory.decodeResource(getResources(),
                R.drawable.upgrade_fireresist);
        bitmapHealth = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_health);
        bitmapHullResist = BitmapFactory.decodeResource(getResources(),
                R.drawable.upgrade_hullresist);
        bitmapMinus = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_minus);
        bitmapNavalRam = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_navalram);
        bitmapPlus = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_plus);
        bitmapPoints = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_points);
        bitmapRange = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_range);
        bitmapRegen = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_regen);
        bitmapSpeed = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_speed);
        bitmapSpread = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_spread);
        bitmapTurning = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_turning);
        bitmapUber = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_uber);
        bitmapBArrows = BitmapFactory.decodeResource(getResources(), R.drawable.upgradeb_arrows);
        bitmapBCannons = BitmapFactory.decodeResource(getResources(), R.drawable.upgradeb_cannons);
        bitmapBDefense = BitmapFactory.decodeResource(getResources(), R.drawable.upgradeb_defense);
        bitmapBHealth = BitmapFactory.decodeResource(getResources(), R.drawable.upgradeb_health);
        bitmapBUtility = BitmapFactory.decodeResource(getResources(), R.drawable.upgradeb_utility);
        bitmapTArrows = BitmapFactory.decodeResource(getResources(), R.drawable.upgradet_arrows);
        bitmapTCannons = BitmapFactory.decodeResource(getResources(), R.drawable.upgradet_cannons);
        bitmapTDefense = BitmapFactory.decodeResource(getResources(), R.drawable.upgradet_defense);
        bitmapTHealth = BitmapFactory.decodeResource(getResources(), R.drawable.upgradet_health);
        bitmapTTop = BitmapFactory.decodeResource(getResources(), R.drawable.upgradet_top);
        bitmapTUtility = BitmapFactory.decodeResource(getResources(), R.drawable.upgradet_utility);
        bitmapExit = BitmapFactory.decodeResource(getResources(), R.drawable.menut_exit);
        bitmapBack = BitmapFactory.decodeResource(getResources(), R.drawable.menut_cancel);
        bitmapUBackground = BitmapFactory.decodeResource(getResources(),
                R.drawable.upgrade_background);
        bitmapUNum = BitmapFactory.decodeResource(getResources(), R.drawable.upgrade_blanknumber);

    }

    public static void addProjectile(Projectile t) {
        synchronized (projectiles) {
            projectiles.add(t);
        }
        //Log.d("GlRenderer","Proj #:"+projectiles.size() );
    }

    public static void addWake(Projectile t) {
        synchronized (wakes) {
            wakes.add(t);
        }
    }

    /** Called before draw during every cycle */
    public void update() {

    	//TODO This is stupid, just make this a function, so we aren't constantly checking
        // Checks to see if viking has finished dying
        if (viking.getState() == State.DEAD && !GlMainMenu.isLaunchingDeathScreen()) {
            Log.d("GlRenderer", "Exiting game after viking death. Launching Main_MenuActivity.");
            GlMainMenu.launchDeathScreen();
            GlMainMenu.levelThatTheUserDiedOn = nextLevel;
            getContext().startActivity(new Intent(getContext(), MainMenu_Activity.class));

        }

        // Updates viking
        updateVikingDir();
        viking.update();
        shipLocX = viking.getX();
        shipLocY = viking.getY();

        shipLeadX = Math.cos(GlRenderer.viking.getAngle()) * Viking.BoxWidth * 0.7*viking.getBaseMoveSpeed();
        shipLeadY = Math.sin(GlRenderer.viking.getAngle()) * Viking.BoxWidth * 0.7*viking.getBaseMoveSpeed();

        if (fireSecondCannonVolley) {
            fireSecondCannonVolleyCounter++;
            if (fireSecondCannonVolleyCounter > 13) {
                fireSecondCannonVolleyCounter = 0;
                fireSecondCannonVolley = false;
                viking.fireCannons();
            }
        }

        // Shifting the Gl Coord system to the location of the ship
        GLshipLocX = (float) (WIDTH_HALF * GlMainMenu.magicalScreenSizeNumber - shipLocX * GlMainMenu.magicalScreenSizeNumber);
        GLshipLocY = (float) (-HEIGHT_HALF * GlMainMenu.magicalScreenSizeNumber + shipLocY * GlMainMenu.magicalScreenSizeNumber);

        myLevel.update();

        compassPointer.setX(shipLocX);
        compassPointer.setY(shipLocY);  

        if (!hasFinishedLoading) {
            hasFinishedLoading = true;
        }

    }// update

    /**
     * Upload high score to the server.
     */
    private void updateHighScore() {
        long uid = GetInformation.getUID(getContext());
        String uName = GetInformation.getUserName(getContext());
        Utilities.updateHighScore(uid, uName, dragonKillCount, bergKillCount);
    }

    public void onDrawFrame(GL10 gl) {
        synchronized (projectiles) {

        	//TODO make this a function call, not a boolean
            if (levelFinished) {
                myLevel.finishLevel(getContext());
                levelFinished = false;
            }

            //TODO make this a function call, not a boolean
            if (backToMenu) {
                Log.d(TAG, "Exiting game after back-button pressed. Launching Main_MenuActivity.");
                backToMenu = false;
                // Don't update high score for the upgrade screen.. And don't upgrade high score if network communication is off
                if (GlRenderer.nextLevel != 100 && getContext().getSharedPreferences(
                		MainMenu_Activity.SHAREDPREFFILE,Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_networkOn, true)) {
                    updateHighScore();
                }
                Intent intent = new Intent();
                intent.setClass(getContext(), MainMenu_Activity.class);
                getContext().startActivity(intent);
                gl.glFinish();
            } else {

                if (!onPauseCalled) {

                    try {
                        // If sound is on and the player isn't playing, start the music
                    	//TODO should call this ONCE on return from menu or something
                        if (!GlMainMenu.mediaPlayer.isPlaying()
                                && getContext().getSharedPreferences(
                                        MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                                        .getBoolean(GlMainMenu.LOCAL_soundOn, true)) {
                            Log.d("GlRenderer",
                                    "Start level track if music is not playing and sound is on");
                            GlMainMenu.mediaPlayer.release();
                            myLevel.startMusic(getContext());
                            GlMainMenu.mediaPlayer.start();
                            try {
                            	  GlMainMenu.mediaPlayer.setLooping(true);
                  			} catch (IllegalStateException e) {
                  				e.printStackTrace();
                  			}
                        }
                        // If the media player is playing and the sound is off, stop the music
                      //TODO should call this ONCE on return from menu or something
                        if (GlMainMenu.mediaPlayer.isPlaying()
                                && !getContext().getSharedPreferences(
                                        MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                                        .getBoolean(GlMainMenu.LOCAL_soundOn, true)) {
                            Log.d("GlRenderer",
                                    "Stop the music if it is playing and the sound is off");
                            GlMainMenu.mediaPlayer.stop();
                        }
                    } catch (Exception e) {
                        Log.d("GlRenderer", "Media player is trying to crash, again...");
                    }
                }
                //TODO make this a function call, not a boolean
                if (startUpgradeScreen) {
                    // Log.d("GlRenderer","Starting Upgrade Screen");
                    startUpgradeScreen = false;
                    paused = true;
                    runningUpgradeScreen = true;
                    myUpgradeState = new Upgrade_Top();
                    myUpgradeState.addStuff();
                    myUpgradeState.initiateShapes(gl, getContext());
                    clickSelection = new Thing(null, 10000, 10000, 20, 20);
                    hasFinishedLoading = true;
                }
                //TODO make this a function call, not a boolean
                if (endUpgradeScreen) {
                    endUpgradeScreen = false;
                    paused = false;
                    runningUpgradeScreen = false;
                    myUpgradeState = null;
                    clickSelection = null;
                    UpgradeMain.applyUpgrades(getContext());
                    GlRenderer.backToMenu = true;
                }
                //TODO make this a function call, not a boolean
                if (runningUpgradeScreen) {
                    if (USER_UPGRADE_SELECT != 0) {
                        // Log.d("GlRenderer","Changing State");
                        this.changeUpgradeState(gl);
                    }
                    clickSelection.update();
                    myUpgradeState.update(clickSelection, getContext());
                    // Move clicker back to the middle of nowhere
            		clickSelection.setX(10000);
            		clickSelection.setY(10000);
                }

                //TODO make this a function call, not a boolean
                if (showTip) {
                    if (getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
                            Context.MODE_PRIVATE).getBoolean(GlMainMenu.LOCAL_tipsOn, true)
                            || GlRenderer.nextLevel == 100) {
                        myParent.displayTip(tipTitleID, tipMessageID);
                    }
                    showTip = false;
                }

                if (!paused && !runningUpgradeScreen) {
                    for (Projectile p : projectiles) {
                        p.update();
                    }
                    for (Projectile w : wakes) {
                        w.update();
                    }
                    update();
                }

                // OpenGL magic
               gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
               gl.glLoadIdentity(); // Reset the Modelview Matrix

                // Drawing
                if (!runningUpgradeScreen && nextLevel != 100) {
                    // moves the "camera"
                    gl.glTranslatef(GLshipLocX, GLshipLocY, -51.6f);
                    // CURRENT -51.6

                    waterStationary.draw(gl, shipLocX, shipLocY);

                    // draw your projectiles
                    for (Projectile w : wakes) {
                        if (!w.hasBeenInitiated) {
                            w.hasBeenInitiated = true;
                            w.initShape(gl, getContext());
                        }
                        w.alphaReduce(gl);
                        w.draw(gl);
                    }

                    for (Projectile p : projectiles) {
                        if (!p.hasBeenInitiated) {
                            // Log.d("GlRenderer","Initializing Projectile");
                            p.hasBeenInitiated = true;
                            p.initShape(gl, getContext());
                        }
                        p.draw(gl);
                    }

                    myLevel.onDrawFrame(gl, getContext());

                    // Draw the viking
                    viking.draw(gl);

                    // TODO
                    // scoreDisplay.updateBitmap(gl, getContext(), "Icebergs: "+
                    // bergKillCount + "  Dragons: "
                    // + dragonKillCount + "  Health: " + vH);

                    // Log.d("GlRenderer","Life Test Percent: " +
                    // (100*viking.getHealth()/Viking.VIKING_HLTH));
                    healthBarOffset = (int) (197 * viking.getHealth() / Viking.VIKING_HLTH - 100);

                    healthBack.draw(gl, 96 * GlMainMenu.widthScale + shipLocX - WIDTH_HALF, 24
                            * GlMainMenu.heightScale + shipLocY - HEIGHT_HALF);
                    healthBar.draw(gl, healthBarOffset * GlMainMenu.widthScale + shipLocX
                            - WIDTH_HALF, 24 * GlMainMenu.heightScale + shipLocY - HEIGHT_HALF);
                    healthBorder.draw(gl, 96 * GlMainMenu.widthScale + shipLocX - WIDTH_HALF, 24
                            * GlMainMenu.heightScale + shipLocY - HEIGHT_HALF);
                    if (Viking.SHOW_ARMOR_ICON) {
                        shieldSymbol.draw(gl, 220 * GlMainMenu.widthScale + shipLocX - WIDTH_HALF,
                                24 * GlMainMenu.heightScale + shipLocY - HEIGHT_HALF);
                    }

                    cooldownComparison = 1.000001 * viking.getProj2Timer() / viking.getProj2Separation();
                    if (cooldownComparison < 1 && cooldownComparison > 0) {
                        cannonCooldown1.setX(shipLocX);
                        cannonCooldown1.setY(shipLocY);
                        cannonCooldown1.draw(gl);
                        if (cooldownComparison > 0.2) {
                            cannonCooldown2.setX(shipLocX);
                            cannonCooldown2.setY(shipLocY);
                            cannonCooldown2.draw(gl);
                            if (cooldownComparison > 0.4) {
                                cannonCooldown3.setX(shipLocX);
                                cannonCooldown3.setY(shipLocY);
                                cannonCooldown3.draw(gl);
                                if (cooldownComparison > 0.6) {
                                    cannonCooldown4.setX(shipLocX);
                                    cannonCooldown4.setY(shipLocY);
                                    cannonCooldown4.draw(gl);
                                    if (cooldownComparison > 0.8) {
                                        cannonCooldown5.setX(shipLocX);
                                        cannonCooldown5.setY(shipLocY);
                                        cannonCooldown5.draw(gl);
                                    }
                                }
                            }
                        }
                    }

                    /*
                     * scoreDisplay.updateBitmap(gl, getContext(), "Level: " +
                     * nextLevel + "        " + "Health: " + vH + "/" +
                     * vHM); // Draw visuals scoreDisplay.draw(gl, shipLocX,
                     * 25*GlMainMenu.heightScale + shipLocY - HEIGHT / 2);
                     */
                    //test2.draw(gl, shipLocX - WIDTH_HALF, shipLocY - HEIGHT_HALF);
                    //test.draw(gl, shipLocX - WIDTH_HALF, shipLocY - HEIGHT_HALF);
                    //test2.draw(gl, Frigate.targetX, Frigate.targetY);
                    //test.draw(gl, Frigate.targetX, Frigate.targetY);
                    //test2.draw(gl, Frigate.xCollisionCoord3, Frigate.yCollisionCoord3);
                    //test.draw(gl, Frigate.xCollisionCoord3, Frigate.yCollisionCoord3);
                    
                    // compass.draw(gl, WIDTH - compassOffset + shipLocX - WIDTH
                    // / 2, HEIGHT
                    // - compassOffset + shipLocY - HEIGHT / 2);
                    if (showCompass) {
                        compassPointer.draw(gl);
                        if( showCompassDistance ){
                			compassDistance.updateBitmap(gl, getContext(), compassDistanceText);
                            compassDistance.draw(gl, (GlRenderer.shipLocX - GlRenderer.WIDTH_HALF + GlMainMenu.widthScale*160), 
                    				GlRenderer.shipLocY+GlRenderer.HEIGHT_HALF-GlMainMenu.heightScale*(15));             	
                        }
                    }


                // If not running the upgrade screen
            	} else if (runningUpgradeScreen) {
                    gl.glTranslatef(0, 0, -51.6f);
                    upgradeBackground.draw(gl, WIDTH_HALF, HEIGHT_HALF);
                    myUpgradeState.onDrawFrame(gl, getContext());
                }
            }// If not going back to menu
        }// Synchronized projectiles
    }// draw

    // Used to navigate through the menus
    public void changeUpgradeState(GL10 gl) {
        Log.d("GlRenderer", "Changing Upgrade State.");
        switch (USER_UPGRADE_SELECT) {

            case 1:
                myUpgradeState = new Upgrade_Top();
                break;

            case 2:
                myUpgradeState = new Upgrade_Arrows();
                break;

            case 3:
                myUpgradeState = new Upgrade_Cannons();
                break;

            case 4:
                myUpgradeState = new Upgrade_Defense();
                break;

            case 5:
                myUpgradeState = new Upgrade_Health();
                break;

            case 6:
                myUpgradeState = new Upgrade_Utility();
                break;

            default:
                System.err.println("Upgrade State ERROR");
                Log.d("GlRenderer", "Upgrade State ERROR");
                break;
        }
        // Initialize the new menu screen
        myUpgradeState.addStuff();
        myUpgradeState.initiateShapes(gl, getContext());
        // Reset the switch
        USER_UPGRADE_SELECT = 0;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {

        if (height == 0) { // Prevent A Divide By Zero By
            height = 1; // Making Height Equal One
        }

        gl.glViewport(0, 0, width, height); // Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select The Projection Matrix
        gl.glLoadIdentity(); // Reset The Projection Matrix

        // Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW); // Select The Modelview Matrix

    }

    // Called when the game starts
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping ( NEW )
        gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1f); // Black Background
        gl.glClearDepthf(1.0f); // Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

        // http://stackoverflow.com/questions/2361602/transparent-texture-in-opengl-es-for-android
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

        // Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // float size = .01f * (float) Math.tan(Math.toRadians(45.0) / 2);
        // float ratio = WIDTH / HEIGHT;
        // perspective:
        // gl.glFrustumf(-size, size, -size / ratio, size / ratio, 0.01f,
        // 100.0f);

        // Arraylists, visuals, viking, screen width/height stored in variables, etc.
        initializeEverything();

        // Now initialize shapes (initial vertices creation and texture binding)
        initShapes(gl);
    }

    /** Initialize all of the shapes when the surface is first created */
    private void initShapes(GL10 gl) {

        waterStationary.initShape(gl, getContext());
        //test2.initShape(gl, getContext());
       // test.initShape(gl, getContext());

        cannonCooldown1.initShape(gl, getContext());
        cannonCooldown2.initShape(gl, getContext());
        cannonCooldown3.initShape(gl, getContext());
        cannonCooldown4.initShape(gl, getContext());
        cannonCooldown5.initShape(gl, getContext());

        healthBack.initShape(gl, getContext());
        healthBar.initShape(gl, getContext());
        healthBorder.initShape(gl, getContext());
        shieldSymbol.initShape(gl, getContext());
        // scoreDisplay.initShape(gl, getContext());
        // scoreDisplay.createBitmap(gl, getContext(), R.drawable.game_background,28);
        viking.initShape(gl, getContext());
        // compass.initShape(gl, getContext());
        compassPointer.initShape(gl, getContext());
    	compassDistance.initShape(gl, getContext());
    	compassDistance.createBitmap(gl, getContext(),  R.drawable.upgrade_blanknumber,UpgradeMain.textSize);
    	compassDistance.setTextColour(255, 211,204,19);

        myLevel.initiateShapes(gl, getContext());
        upgradeBackground.initShape(gl, getContext());

    }

    private static Double desiredAngle = Double.NaN;
    private static double curAngle;
    private static double dif;

    private void updateVikingDir() {
        if (!desiredAngle.isNaN()) {
            // Get the difference between the two angles
            curAngle = viking.getAngle();
            while (curAngle > Math.PI) {
                curAngle -= 2 * Math.PI;
            }
            while (curAngle < -Math.PI) {
                curAngle += 2 * Math.PI;
            }
            dif = desiredAngle - curAngle;
            double ang;

            // The last little bit, just bumps you to exactly what you want.
            // Log.d("GlRenderer","DIF = " + dif + " Desired = "+ desiredAngle +
            // " Current = "+ curAngle);
            if (Math.abs(dif) < Viking.TURNING_SPEED) {
                ang = desiredAngle;
                desiredAngle = Double.NaN;
            } else {
                // We rotate by as much as we can...in the correct direction.
                if (dif < 0) {
                    if (dif < -Math.PI) {
                        // Log.d("GlRenderer","1");
                        ang = curAngle + Viking.TURNING_SPEED;
                    } else {
                        // Log.d("GlRenderer","2");
                        ang = curAngle - Viking.TURNING_SPEED;
                    }
                } else {
                    if (dif > Math.PI) {
                        // Log.d("GlRenderer","3");
                        ang = curAngle - Viking.TURNING_SPEED;
                    } else {
                        // Log.d("GlRenderer","4");
                        ang = curAngle + Viking.TURNING_SPEED;
                    }
                }
            }
            viking.setMoveSpeed(Viking.MAX_MOVE_SPEED);
            viking.setAngle(ang);
        } else {
            // We are not turning currently, hold your heading! :D
        }
    }

    // Called on screen touch to rotate ship
    private void setVikingDir(double x, double y) {
        double dirX = x - WIDTH_HALF;
        double dirY = y - HEIGHT_HALF;
        desiredAngle = Math.atan2(dirY, dirX);
    }

    // Handles touches
    public boolean onTouchEvent(final MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("GlRenderer", "Touch Event");
            // Only create projectiles and stuff if update() has called at least once
            if (hasFinishedLoading) {
                if (!runningUpgradeScreen) {
                    if (Math.abs(event.getX() - WIDTH_HALF) < 64
                            && Math.abs(event.getY() - HEIGHT_HALF) < 64) {
                        if (viking.getProj2Timer() > viking.getProj2Separation() && weaponsOn) {
                            viking.fireCannons();
                            if (Viking.cannonUber) {
                                fireSecondCannonVolley = true;
                                fireSecondCannonVolleyCounter = 0;
                            }
                        }
                    } else {

                        // Set the Viking to move at a new angle
                        setVikingDir(event.getX(), event.getY());

                        curAngle = viking.getAngle();
                        dif = desiredAngle - curAngle;
                        // If the ship is facing close to the click direction
                        // then fire an arrow
                        if (Math.abs(dif) < 0.4 && weaponsOn) {
                            if (viking.getArrowTimer() > viking.getArrowSeparation()) {
                                viking.firePROJ1(event.getX(), event.getY());
                            }
                        }

                    }

                } else {
                    // If running upgrade state:
                    // Touch places a Thing at the touch location, can test that
                    // for collision against buttons
                    clickSelection.setX(event.getX());
                    clickSelection.setY(event.getY());
                }
            }// If has finished loading
        }
        return true;
    }

    private static Random r;

    /**
     * Takes in a Thing, will randomize its location to somewhere outside the
     * screen and within OUTSIDE_SIZE Also Randomizes the angle
     */
    public static void randomizeLocation(Thing item) {
        int x;
        int y;

        int num = r.nextInt(4);
        if (num == 0) {
            // To the left
            x = (int) (GlRenderer.shipLocX - GlRenderer.WIDTH_HALF + (-item.getCollisionBoxWidth() - r
                    .nextInt(OUTSIDE_SIZE - item.getCollisionBoxWidth())));
            y = (int) (GlRenderer.shipLocY - GlRenderer.HEIGHT_HALF + (r.nextInt(GlRenderer.HEIGHT
                    + 2 * OUTSIDE_SIZE - item.getCollisionBoxHeight()) - (OUTSIDE_SIZE - item.getCollisionBoxHeight())));

        } else if (num == 1) {
            // To the right
            x = (int) (GlRenderer.shipLocX - GlRenderer.WIDTH_HALF + (GlRenderer.WIDTH
                    + item.getCollisionBoxWidth() + r.nextInt(OUTSIDE_SIZE - item.getCollisionBoxWidth())));
            y = (int) (GlRenderer.shipLocY - GlRenderer.HEIGHT_HALF + (r.nextInt(GlRenderer.HEIGHT
                    + 2 * OUTSIDE_SIZE - item.getCollisionBoxHeight()) - (OUTSIDE_SIZE - item.getCollisionBoxHeight())));

        } else if (num == 2) {
            // Above
            x = (int) (GlRenderer.shipLocX - GlRenderer.WIDTH_HALF + (r.nextInt(GlRenderer.WIDTH
                    + 2 * OUTSIDE_SIZE - item.getCollisionBoxWidth()) - (OUTSIDE_SIZE - item.getCollisionBoxWidth())));
            y = (int) (GlRenderer.shipLocY - GlRenderer.HEIGHT_HALF + (-item.getCollisionBoxHeight() - r
                    .nextInt(OUTSIDE_SIZE - item.getCollisionBoxHeight())));

        } else {
            // Below
            x = (int) (GlRenderer.shipLocX - GlRenderer.WIDTH_HALF + (r.nextInt(GlRenderer.WIDTH
                    + 2 * OUTSIDE_SIZE - item.getCollisionBoxWidth()) - (OUTSIDE_SIZE - item.getCollisionBoxWidth())));
            y = (int) (GlRenderer.shipLocY - GlRenderer.HEIGHT_HALF + (GlRenderer.HEIGHT
                    + item.getCollisionBoxHeight() + r.nextInt(OUTSIDE_SIZE - item.getCollisionBoxHeight())));
        }
        item.setX(x);
        item.setY(y);

        // Randomize Angle
        item.setAngle(Math.PI - r.nextFloat() * (Math.PI * 2));
    }

    /**
     * Call this from levels (or the upgrade menu which is also a "level") to
     * cause a tip to appear Use title and message IDs from the Tips.java class
     */
    public static void showTip(String titleID, String messageID) {
        showTip = true;
        tipTitleID = titleID;
        tipMessageID = messageID;
    }
    
    /**This prototype is used to turn a projectile into a fire arrow 
     * e.g. on the collision of a Viking Arrow and Dragon Breath
     * */
    private static Projectile fireArrowPrototype;
    
    public static void copyProjectileToFireArrow(Projectile oldArrow){
    	fireArrowPrototype.setX(oldArrow.getX());
    	fireArrowPrototype.setY(oldArrow.getY());
    	fireArrowPrototype.setAngle(oldArrow.getAngle());
    	addProjectile(Projectile.getProjectile(fireArrowPrototype));
    }
    
    public static void setCompassDistanceText(String newText){
    	compassDistanceText = newText;
    }
}
