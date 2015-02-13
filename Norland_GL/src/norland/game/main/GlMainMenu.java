package norland.game.main;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import norland.game.main.menus.MenuState;
import norland.game.main.menus.Menu_EasterEggs;
import norland.game.main.menus.Menu_Extras;
import norland.game.main.menus.Menu_Options;
import norland.game.main.menus.Menu_Play;
import norland.game.main.menus.Menu_Return;
import norland.game.main.menus.Menu_Top;
import norland.game.main.menus.Menu_a1s1;
import norland.game.main.menus.Menu_a1s2;
import norland.game.main.menus.Menu_a1s3;
import norland.game.main.menus.Menu_a2s1;
import norland.game.main.menus.Menu_a2s2;
import norland.game.main.menus.Menu_a2s3;
import norland.game.main.menus.Menu_a3s1;
import norland.game.main.menus.Menu_a3s2;
import norland.game.main.menus.Menu_a3s3;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

@SuppressLint("ViewConstructor")
public class GlMainMenu extends GLSurfaceView implements Renderer {

    // Width and height of the display
    public static int HEIGHT;
    public static int WIDTH;
    public static double heightScale;
    public static double widthScale;
    public static float magicalScreenSizeNumber;
    // Used to translate camera
    private float menuGLScreenLocX;
    private float menuGLScreenLocY;
    public static double menuShiftX;
    public static double menuShiftY;

    // Used for camera movement
    private static double backgroundMinX;
    private static double backgroundMaxX;
    private static double backgroundMinY;
    private static double backgroundMaxY;
    private static double backgroundTargetX;
    private static double backgroundTargetY;
    private static double previousTargetX;
    private static double previousTargetY;
    int wait;

    // For debugging
    public static final String TAGMM = GlMainMenu.class.getSimpleName();

    // The background picture (Norland Map)
    private Thing mapPicture;

    // Used for moving the GL camera (take the coords of this and transform
    // them)
    private Thing ourCamera;

    // All the bitmaps for menu items

    private static Bitmap bitmapBackground;
    private static Bitmap bitmapSelector;
    public static Bitmap bitmapCancel;

    public static Bitmap bitmapTitle_top;
    public static Bitmap bitmapBeta;
    public static Bitmap bitmapCampaign;
    public static Bitmap bitmapOptions;
    public static Bitmap bitmapExtras;
    public static Bitmap bitmapFeedback;
    public static Bitmap bitmapUpgrades;

    public static Bitmap bitmapTitle_options;
    public static Bitmap bitmapSound;
    public static Bitmap bitmapTips;
    public static Bitmap bitmapNetwork;

    public static Bitmap bitmapTitle_campaign;
    public static Bitmap bitmapNext;
    public static Bitmap bitmapPrevious;
    public static Bitmap bitmapResume;

    public static Bitmap bitmapTitle_extras;
    public static Bitmap bitmapEasterEggs256;
    public static Bitmap bitmapCredits;
    public static Bitmap bitmapHighScores;

    public static Bitmap bitmapTitle_eastereggs;
    public static Bitmap bitmapRubberDuckyMode;
    public static Bitmap bitmapEvilIcebergsMode;

    public static Bitmap bitmapTitle_death;
    public static Bitmap bitmapRetry;
    public static Bitmap bitmapExit;

    public static Bitmap bitmapAct1;
    public static Bitmap bitmapAct2;
    public static Bitmap bitmapAct3;
    public static Bitmap bitmapLevel1;
    public static Bitmap bitmapLevel2;
    public static Bitmap bitmapLevel3;
    public static Bitmap bitmapLevel4;
    public static Bitmap bitmapLevel5;
    public static Bitmap bitmapLevel6;
    public static Bitmap bitmapLevel7;
    public static Bitmap bitmapLevel8;
    public static Bitmap bitmapLevel9;
    public static Bitmap bitmapLevel10;
    public static Bitmap bitmapLevel11;
    public static Bitmap bitmapLevel12;
    public static Bitmap bitmapLevel13;
    public static Bitmap bitmapLevel14;
    public static Bitmap bitmapLevel15;
    public static Bitmap bitmapLevel16;
    public static Bitmap bitmapLevel17;
    public static Bitmap bitmapLevel18;
    public static Bitmap bitmapLevel19;
    public static Bitmap bitmapLevel20;
    public static Bitmap bitmapLevel21;
    public static Bitmap bitmapLevel22;
    public static Bitmap bitmapLevel23;
    public static Bitmap bitmapLevel24;
    public static Bitmap bitmapLevel25;
    public static Bitmap bitmapLevel26;
    public static Bitmap bitmapLevel27;
    public static Bitmap bitmapa1s1;
    public static Bitmap bitmapa1s2;
    public static Bitmap bitmapa1s3;
    public static Bitmap bitmapa2s1;
    public static Bitmap bitmapa2s2;
    public static Bitmap bitmapa2s3;
    public static Bitmap bitmapa3s1;
    public static Bitmap bitmapa3s2;
    public static Bitmap bitmapa3s3;

    // Placed on the screen when you click, test collision with menu items
    public static Thing clickSelection;

    // What level the user is on, should be "1" when the game is first loaded
    public static final String LOCAL_levelUnlock = "LOCAL_levelUnlock";

    // True if user has finished the game
    public static final String LOCAL_hasCompleted = "LOCAL_hasCompleted";

    /** Options, maps between strings */
    public static final String LOCAL_soundOn = "LOCAL_soundOn";
    public static final String LOCAL_tipsOn = "LOCAL_tipsOn";
    public static final String LOCAL_networkOn = "LOCAL_networkOn";

    /** Easter Egg: Ship is a Rubber Duck */
    public static final String LOCAL_rubberDuckyMode = "LOCAL_rubberDuckyMode";
    /** Easter Egg: Icebergs are Malicious */
    public static final String LOCAL_evilIcebergsMode = "LOCAL_evilIcebergsMode";
    /** TODO Easter Egg: Random cannon direction */
    public static final String LOCAL_drunkenDeckPartyMode = "LOCAL_drunkenDeckParty";
    /**
     * TODO Easter Egg: Music notes from Fosse Grim are instant heal (green) and
     * instant death (red)
     */
    public static final String LOCAL_inTune = "LOCAL_inTune";

    /** Upgrades, maps between strings */
    public static final String LOCAL_arrowDamage = "LOCAL_arrowDamage";
    public static final String LOCAL_arrowFireRate = "LOCAL_arrowFireRate";
    public static final String LOCAL_arrowUber = "LOCAL_arrowUber";
    public static final String LOCAL_cannonDamage = "LOCAL_cannonDamage";
    public static final String LOCAL_cannonFireRate = "LOCAL_cannonFireRate";
    public static final String LOCAL_cannonRange = "LOCAL_cannonRange";
    public static final String LOCAL_cannonSpread = "LOCAL_cannonSpread";
    public static final String LOCAL_cannonUber = "LOCAL_cannonUber";
    public static final String LOCAL_shipSpeed = "LOCAL_shipSpeed";
    public static final String LOCAL_shipTurningRadius = "LOCAL_shipTurningRadius";
    public static final String LOCAL_shipUberUtility = "shipUberUtility";
    public static final String LOCAL_shipFireResist = "LOCAL_shipFireResist";
    public static final String LOCAL_shipHullResist = "LOCAL_shipHull";
    public static final String LOCAL_shipHullDamage = "LOCAL_shipHullDamage";
    public static final String LOCAL_shipUberHull = "LOCAL_shipUberHull";
    public static final String LOCAL_shipHealth = "LOCAL_shipHealth";
    public static final String LOCAL_shipHealthRegen = "LOCAL_shipHealthRegen";
    public static final String LOCAL_shipUberHealth = "LOCAL_shipUberHealth";

    // **Always update upgradesSpent immediately after changing an upgrade;
    public static final String LOCAL_upgradesTotal = "LOCAL_upgradesTotal";
    public static final String LOCAL_upgradesSpent = "LOCAL_upgradesSpent";

    /** The current menu (which buttons are visible), set in the switch */
    public static MenuState myMenuState;
    /** Stores which GlMainMenu.myMenuState to switch to. */
    public static int USER_MENU_SELECT;

    /** The activity is the parent of the renderer */
    private static MainMenu_Activity myParent;

    /**
     * Set to true when onPause() is called and the media player has been
     * released
     */
    public static boolean onPauseCalled;

    /** The media player used by the menu and game */
    public static MediaPlayer mediaPlayer;
    private static boolean hasPlayedStartMusic = false; // Set to true in
                                                        // onPause
                                                        // Norland_GLActivity

    private static boolean launchDeathScreen = false; // Starts a special
                                                      // menu

    public static void launchDeathScreen() {
        launchDeathScreen = true;
    }

    public static boolean isLaunchingDeathScreen() {
        return launchDeathScreen;
    }

    public static int levelThatTheUserDiedOn; // So it knows what level to
                                              // resume on

    /**
     * If the player is progressing in the campaign. We will auto-launch next
     * level
     */
    private static boolean startNextLevel = false;

    public static void setStartNextLevel(boolean startNextLevel) {
        GlMainMenu.startNextLevel = startNextLevel;
    }

    /** Constructor */
    public GlMainMenu(MainMenu_Activity myParent) {
        super(myParent);
        setRenderer(this);
        GlMainMenu.myParent = myParent;
        onPauseCalled = false;
    }

    /** Used to navigate through the menus */
    private void changeMenuState(GL10 gl) {
        // seriously Jer...enum this stuff :P
        // pffft switches are awesome
        switch (USER_MENU_SELECT) {
            case 1:
                myMenuState = new Menu_Top();
                break;
            case 2:
                myMenuState = new Menu_Play();
                break;
            case 3:
                myMenuState = new Menu_Options();
                break;
            case 4:
                myMenuState = new Menu_a1s1();
                break;
            case 5:
                myMenuState = new Menu_a1s2();
                break;
            case 6:
                myMenuState = new Menu_a1s3();
                break;
            case 7:
                myMenuState = new Menu_a2s1();
                break;
            case 8:
                myMenuState = new Menu_a2s2();
                break;
            case 9:
                myMenuState = new Menu_a2s3();
                break;
            case 10:
                myMenuState = new Menu_a3s1();
                break;
            case 11:
                myMenuState = new Menu_a3s2();
                break;
            case 12:
                myMenuState = new Menu_a3s3();
                break;
            case 13:
                myMenuState = new Menu_Extras();
                break;
            case 14:
                myMenuState = new Menu_EasterEggs();
                break;
            case 15:
                myMenuState = new Menu_Return();
                break;
            default:
                System.err.println("Menu State ERROR");
                break;
        }
        // Initialize the new menu screen
        myMenuState.addStuff();
        myMenuState.initiateShapes(gl, getContext());
        // Reset the switch
        USER_MENU_SELECT = 0;
    }

    /** Called when the game is launched, initializes stuff */
    private void initializeEverything() {

        // Default: Level 1 unlocked
        getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getInt(LOCAL_levelUnlock, 1);

        // TODO Temporary for testing: unlock all the levels
        // getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
        // Context.MODE_PRIVATE).edit().putInt(LOCAL_levelUnlock, 27).commit();

        // Default: Game not completed, used to unlock easter eggs
        getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getBoolean(LOCAL_hasCompleted, false);
        // Default: All easter eggs are off
        getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getBoolean(LOCAL_rubberDuckyMode, false);
        getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getBoolean(LOCAL_evilIcebergsMode, false);

        // TODO Temporary for testing: easter eggs, upgrades
        // getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
        // Context.MODE_PRIVATE)
        // .edit().putBoolean(LOCAL_hasCompleted, true).commit();
        // getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
        // Context.MODE_PRIVATE).edit().putInt(LOCAL_upgradesTotal,
        // 37).commit();

        // Default: Sound on
        getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getBoolean(LOCAL_soundOn, true);
        // Default: Tips on
        getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getBoolean(LOCAL_tipsOn, true);
        // Default: Network Communication on
        getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getBoolean(LOCAL_networkOn, true);

        // Default: 0 upgrade points acquired, 0 upgrades spent
        getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getInt(LOCAL_upgradesTotal, 0);
        getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE, Context.MODE_PRIVATE)
                .getInt(LOCAL_upgradesSpent, 0);

        // TODO Add more Easter Eggs

        // If the main theme has not been played, start it, else start the main
        // loop
        if (!hasPlayedStartMusic) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.norlandmaintitle);
            // no need to call prepare(); create() does that for you
            mediaPlayer.setLooping(false);
            // Add a listener to switch tracks when the song finishes
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.norlandmainscreenloop);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }
            });
        } else {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.norlandmainscreenloop);
            // no need to call prepare(); create() does that for you
            mediaPlayer.setLooping(true);
        }

        // If not immediately starting a level and the sound is on then start
        // the music
        if (!startNextLevel
                && getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
                        Context.MODE_PRIVATE).getBoolean(LOCAL_soundOn, true)) {
            mediaPlayer.start();
        }

        // Get the width and height
        WIDTH = getWidth();
        HEIGHT = getHeight();

        GlRenderer.WIDTH = WIDTH;
        GlRenderer.HEIGHT = HEIGHT;
        GlRenderer.WIDTH_HALF = (int) (WIDTH * 0.5);
        GlRenderer.HEIGHT_HALF = (int) (HEIGHT * 0.5);

        // For resizing:
        heightScale = HEIGHT / 854.0;
        widthScale = WIDTH / 480.0;
        magicalScreenSizeNumber = (float) 42.7 / HEIGHT;

        // Create all the bitmaps
        bitmapBackground = BitmapFactory.decodeResource(getResources(), R.drawable.menu_norlandmap);
        bitmapSelector = BitmapFactory.decodeResource(getResources(), R.drawable.proj_cannonball);
        bitmapCampaign = BitmapFactory.decodeResource(getResources(), R.drawable.menut_campaign);
        bitmapCancel = BitmapFactory.decodeResource(getResources(), R.drawable.menut_cancel);
        bitmapExtras = BitmapFactory.decodeResource(getResources(), R.drawable.menut_extras);
        bitmapFeedback = BitmapFactory.decodeResource(getResources(), R.drawable.menut_feedback);
        bitmapUpgrades = BitmapFactory.decodeResource(getResources(), R.drawable.menut_upgrades);
        bitmapCredits = BitmapFactory.decodeResource(getResources(), R.drawable.menut_credits);
        bitmapEasterEggs256 = BitmapFactory.decodeResource(getResources(),
                R.drawable.menut_eastereggs);
        bitmapLevel1 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level1);
        bitmapLevel2 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level2);
        bitmapLevel3 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level3);
        bitmapLevel4 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level4);
        bitmapLevel5 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level5);
        bitmapLevel6 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level6);
        bitmapLevel7 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level7);
        bitmapLevel8 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level8);
        bitmapLevel9 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level9);
        bitmapLevel10 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level10);
        bitmapLevel11 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level11);
        bitmapLevel12 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level12);
        bitmapLevel13 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level13);
        bitmapLevel14 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level14);
        bitmapLevel15 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level15);
        bitmapLevel16 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level16);
        bitmapLevel17 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level17);
        bitmapLevel18 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level18);
        bitmapLevel19 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level19);
        bitmapLevel20 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level20);
        bitmapLevel21 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level21);
        bitmapLevel22 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level22);
        bitmapLevel23 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level23);
        bitmapLevel24 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level24);
        bitmapLevel25 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level25);
        bitmapLevel26 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level26);
        bitmapLevel27 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_level27);
        bitmapOptions = BitmapFactory.decodeResource(getResources(), R.drawable.menut_options);
        bitmapSound = BitmapFactory.decodeResource(getResources(), R.drawable.menut_sound);
        bitmapTips = BitmapFactory.decodeResource(getResources(), R.drawable.menut_tips);
        bitmapNetwork = BitmapFactory.decodeResource(getResources(), R.drawable.menut_network);
        bitmapAct1 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act1);
        bitmapAct2 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act2);
        bitmapAct3 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act3);
        bitmapTitle_top = BitmapFactory.decodeResource(getResources(), R.drawable.menut_title_top);
        bitmapTitle_options = BitmapFactory.decodeResource(getResources(),
                R.drawable.menut_title_options);
        bitmapTitle_extras = BitmapFactory.decodeResource(getResources(),
                R.drawable.menut_title_extras);
        bitmapTitle_campaign = BitmapFactory.decodeResource(getResources(),
                R.drawable.menut_title_campaign256);
        bitmapTitle_eastereggs = BitmapFactory.decodeResource(getResources(),
                R.drawable.menut_title_eastereggs);
        bitmapRubberDuckyMode = BitmapFactory.decodeResource(getResources(),
                R.drawable.menut_rubberduckymode);
        bitmapEvilIcebergsMode = BitmapFactory.decodeResource(getResources(),
                R.drawable.menut_evilicebergsmode);
        bitmapa1s1 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act1s1);
        bitmapa1s2 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act1s2);
        bitmapa1s3 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act1s3);
        bitmapa2s1 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act2s1);
        bitmapa2s2 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act2s2);
        bitmapa2s3 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act2s3);
        bitmapa3s1 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act3s1);
        bitmapa3s2 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act3s2);
        bitmapa3s3 = BitmapFactory.decodeResource(getResources(), R.drawable.menut_act3s3);
        bitmapNext = BitmapFactory.decodeResource(getResources(), R.drawable.menu_next);
        bitmapPrevious = BitmapFactory.decodeResource(getResources(), R.drawable.menu_previous);
        bitmapResume = BitmapFactory.decodeResource(getResources(), R.drawable.menut_resume);
        bitmapBeta = BitmapFactory.decodeResource(getResources(), R.drawable.menut_beta);
        bitmapRetry = BitmapFactory.decodeResource(getResources(), R.drawable.menut_retry);
        bitmapExit = BitmapFactory.decodeResource(getResources(), R.drawable.menut_exit);
        bitmapTitle_death = BitmapFactory.decodeResource(getResources(),
                R.drawable.menut_title_return);
        bitmapHighScores = BitmapFactory
                .decodeResource(getResources(), R.drawable.menut_highscores);

        // Initialize background image.
        // Want to scale at least 2x in both dimensions.
        final double phoneScreenAdjust = Math.max(WIDTH * 2.0 / bitmapBackground.getWidth(), HEIGHT
                * 2.0 / bitmapBackground.getHeight());
        final double mapWidth = bitmapBackground.getWidth() * (phoneScreenAdjust) / widthScale;
        final double mapHeight = bitmapBackground.getHeight() * (phoneScreenAdjust) / heightScale;
        this.mapPicture = new Thing(bitmapBackground, WIDTH / 2, HEIGHT / 2, mapWidth, mapHeight);
        ourCamera = new Thing(null, WIDTH / 2, HEIGHT / 2, 20, 20);

        // Camera movement limits
        backgroundMinX = -(mapWidth - (WIDTH));
        backgroundMaxX = mapWidth;
        backgroundMinY = -(mapHeight - (HEIGHT) - 300);
        backgroundMaxY = mapHeight - 300;

        // For camera movement stuff
        backgroundTargetX = 0;
        backgroundTargetY = 0;
        previousTargetX = 0;
        previousTargetY = 0;
        wait = 0;

        // The game starts in the top menu unless returning to menu after dying
        if (launchDeathScreen) {
            launchDeathScreen = false;
            myMenuState = new Menu_Return();
        } else {
            myMenuState = new Menu_Top();
        }
        myMenuState.addStuff();

        // Create the click selector
        clickSelection = new Thing(bitmapSelector, 10000, 10000, 20, 20);

        // For the switch, change menu is only called if this is !=0
        USER_MENU_SELECT = 0;
    }

    /** Makes the menu screen look all fancy, smooth camera movement */
    double cameraMoveSpeed;
    double cameraMoveSpeedToPrevious;

    public void updateCameraLocation() {

        // Used for smooth screen movement
        wait++;

        // Pick a new destination for the camera to move,
        // This should only evaluate true when the menu first starts (or if a
        // location happens to be 0,0)
        if (backgroundTargetX == 0 && backgroundTargetY == 0) {
            newTarget();
            ourCamera.setAngle(Math.atan2(backgroundTargetY - ourCamera.getY(), backgroundTargetX
                    - ourCamera.getX()));
        }
        // When a target location is reached, get a new one
        if (ourCamera.distanceTo(backgroundTargetX, backgroundTargetY) < 20 && wait > 20) {
            newTarget();
            ourCamera.setAngle(Math.atan2(backgroundTargetY - ourCamera.getY(), backgroundTargetX
                    - ourCamera.getX()));
            wait = 0;
        }

        // After a slight pause the camera will begin moving to a new
        // destination
        if (wait > 30) {
            // Calculate move speed to previous destination and current
            // destination, choose lower of the two.
            // This stops the camera from going super fast as soon as it gets a
            // new target
            cameraMoveSpeed = 1 - (1 / (1 + 0.0001 * Math.pow(
                    ourCamera.distanceTo(backgroundTargetX, backgroundTargetY), 2)));
            cameraMoveSpeedToPrevious = 1 - (1 / (1 + 0.0001 * Math.pow(
                    ourCamera.distanceTo(previousTargetX, previousTargetY), 2)));
            if (cameraMoveSpeedToPrevious < cameraMoveSpeed) {
                cameraMoveSpeed = cameraMoveSpeedToPrevious;
            }
            if (cameraMoveSpeed < 0.1) {
                cameraMoveSpeed = 0.1;
            }
            // This is normally done in the update cycle, but we don't need the
            // rest of the stuff in the update cycle, so I just did it here
            // instead.
            ourCamera.setX(ourCamera.getX() + Math.cos(ourCamera.getAngle()) * cameraMoveSpeed);
            ourCamera.setY(ourCamera.getY() + Math.sin(ourCamera.getAngle()) * cameraMoveSpeed);

        }

        // Calculate the base of our coordinate system relative to our "camera"
        // location
        menuShiftX = ourCamera.getX() - WIDTH / 2;
        menuShiftY = ourCamera.getY() - HEIGHT / 2;

        // Calculate the actual OpenGL location of the camera
        menuGLScreenLocX = (float) (WIDTH / 2 * magicalScreenSizeNumber - ourCamera.getX()
                * magicalScreenSizeNumber);
        menuGLScreenLocY = (float) (-HEIGHT / 2 * magicalScreenSizeNumber + ourCamera.getY()
                * magicalScreenSizeNumber);

    }

    public void onDrawFrame(GL10 gl) {

        // This will be true when the next level is automatically launched
        // during campaign progression
        // TODO make this a function call, not a boolean
        if (startNextLevel) {
            startNextLevel = false;
            enterGame(
                    getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
                            Context.MODE_PRIVATE).getInt(LOCAL_levelUnlock, 1), getContext());
        }

        // Don't do anything if the game is about to launch, onPause may have
        // already been called.
        // Also don't do anything if game is auto-starting next level
        // if(!onPauseCalled && !startNextLevel){
        if (!onPauseCalled) {

            try {
                // If sound is on and the player isn't playing it means the user
                // just turned the sound on...
                // TODO should check this ONCE on return from menu or something
                if (!mediaPlayer.isPlaying()
                        && getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
                                Context.MODE_PRIVATE).getBoolean(LOCAL_soundOn, true)) {
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.norlandmainscreenloop);
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }

                // If the media player is playing and the sound is off, stop the
                // music
                // TODO should check this ONCE on return from menu or something
                if (mediaPlayer.isPlaying()
                        && !getContext().getSharedPreferences(MainMenu_Activity.SHAREDPREFFILE,
                                Context.MODE_PRIVATE).getBoolean(LOCAL_soundOn, true)) {
                    mediaPlayer.stop();
                    hasPlayedStartMusic = true;
                }
            } catch (Exception e) {
                Log.d("GlMainMenu", "Media player is trying to crash, again...");
            }

        }

        // Calculate the new camera location
        updateCameraLocation();

        // Change the menu if the user has pressed a button
        if (USER_MENU_SELECT != 0) {
            this.changeMenuState(gl);
        }

        // Updates where the user clicked (important because it places the
        // hit-box)
        clickSelection.update();

        // Do all the specific updating for each menu screen
        myMenuState.update(getContext());

        // OpenGL magic
        gl.glClearColorx(50, 50, 50, 1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); // Reset the Modelview Matrix

        // Move the camera based on our calculations
        gl.glTranslatef(menuGLScreenLocX, menuGLScreenLocY, (float) -51.6);
        // CURRENT -51.6

        // Don't draw if the menu technically isn't appearing
        if (!startNextLevel) {
            // Draw the map (do it first so it is behind everything else)
            mapPicture.draw(gl);

            // clickSelection.draw(gl);

            // Draw all the menu items
            myMenuState.onDrawFrame(gl, getContext());
        }

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

    /** Called when the menu starts */
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping ( NEW )
        gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1f); // Black Background
        gl.glClearDepthf(1.0f); // Depth Buffer Setup
        gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
        gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
        // Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        initializeEverything();
        initShapes(gl);
    }

    private void initShapes(GL10 gl) {
        mapPicture.initShape(gl, getContext());
        clickSelection.initShape(gl, getContext());
        myMenuState.initiateShapes(gl, getContext());
    }

    /** Handles touches */
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Places a Thing at the touch location, can test that for collision
            // against buttons
            clickSelection.setX(event.getX() + menuShiftX);
            clickSelection.setY(event.getY() + menuShiftY);
        }
        return true;
    }

    private static Random rx = new Random();
    private static Random ry = new Random();

    /**
     * Used to randomly generate a target location for the camera to move to
     * (overtop of the picture)
     */
    private static void newTarget() {
        previousTargetX = backgroundTargetX;
        previousTargetY = backgroundTargetY;
        backgroundTargetX = rx.nextInt((int) (backgroundMaxX - backgroundMinX)) + backgroundMinX;
        backgroundTargetY = ry.nextInt((int) (backgroundMaxY - backgroundMinY)) + backgroundMinY;

        if (Math.abs(previousTargetX - backgroundTargetX) < 150
                && Math.abs(previousTargetY - backgroundTargetY) < 150) {
            backgroundTargetX = previousTargetX;
            backgroundTargetY = previousTargetY;
            newTarget();
        }
    }

    public static void showCredits(Context context) {
        GlRenderer.setNextLevel(101);
        Log.d("GlMainMenu", "Starting credits.");
        Log.d("GlMainMenu", "Starting VidMenu_Activity intent");
        context.startActivity(new Intent(context, VidMenu_Activity.class));
    }

    public static void showTipsAlert() {
        myParent.displayAlert("Tips", LOCAL_tipsOn);
    }

    public static void showSoundAlert() {
        myParent.displayAlert("Sound", LOCAL_soundOn);
    }

    public static void showNetworkAlert() {
        myParent.displayAlert("Network Communication", LOCAL_networkOn);
    }

    public static void showRubberDuckyAlert() {
        myParent.displayAlert("Rubber Ducky Mode", LOCAL_rubberDuckyMode);
    }

    public static void showEvilIcebergsAlert() {
        myParent.displayAlert("Evil Icebergs Mode", LOCAL_evilIcebergsMode);
    }

    /**
     * Enters the game, you MUST set GlRenderer.USER_LVL_SELECT before calling
     * this function
     */
    public static void enterGame(int levelToLaunch, Context context) {
        GlRenderer.setNextLevel(levelToLaunch);
        // If launching a level with a cutscene, then start the video player,
        // else go straight to the game
        // Also do not play the cutscene if the player failed a level and is
        // "retrying"
        if ((levelToLaunch == 3 || levelToLaunch == 6 || levelToLaunch == 9)
                && GlMainMenu.myMenuState.getClass() != Menu_Return.class) {
            Log.d("GlMainMenu", "Starting VidMenu_Activity intent.");
            context.startActivity(new Intent(context, VidMenu_Activity.class));
        } else {
            Log.d("GlMainMenu", "Launching Norland_GlActivity intent.");
            context.startActivity(new Intent(context, Norland_GLActivity.class));
        }
    }

}
