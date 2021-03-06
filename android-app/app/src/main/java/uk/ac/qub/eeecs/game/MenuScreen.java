package uk.ac.qub.eeecs.game;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.R;
import uk.ac.qub.eeecs.gage.engine.AssetStore;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.ui.PushButton;
import uk.ac.qub.eeecs.gage.ui.ToggleButton;
import uk.ac.qub.eeecs.gage.world.GameObject;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.gameHelp.GameHelpController;
import uk.ac.qub.eeecs.game.options.OptionsScreen;
import uk.ac.qub.eeecs.game.performanceScreen.PerformanceScreen;
import uk.ac.qub.eeecs.game.worldScreen.WorldScreen;

/**
 * An exceedingly basic menu screen with a couple of touch buttons
 *
 * @version 1.0
 */
public class MenuScreen extends GameScreen {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Define the buttons for navigating the game
     */
    private PushButton mOptionsButton;
    private PushButton mPerformanceButton;
    private PushButton mHelpButton;
    private PushButton mSinglePlayerButton;
    private PushButton mMultiPlayerButton;
    private ToggleButton mMuteButton;

    /**
     * Make variable for mute toggle
     */

    private boolean soundOn = true;

    /**
     * Make the title and background
     */
    private GameObject mTitleBackground;
    private GameObject mGameTitle;

    /**
     * Handler and Runnable for toast message
     */
    private Handler toastHandler;
    private Runnable toastRunner;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a simple menu screen
     *
     * @param game Game to which this screen belongs
     */
    public MenuScreen(Game game) {
        super("MenuScreen", game);


        // Load in the bitmaps used on the main menu screen
        AssetStore assetManager = mGame.getAssetManager();
        assetManager.loadAndAddBitmap("Title", "img/Title.png");
        assetManager.loadAndAddBitmap("OptionsScreenIcon", "img/cog-icon.png");
        assetManager.loadAndAddBitmap("PerformanceIcon", "img/Performance.png");
        assetManager.loadAndAddBitmap("HelpScreenIcon", "img/QuestionMark.png");
        assetManager.loadAndAddBitmap("SPButton", "img/Singleplayer-Button.png");
        assetManager.loadAndAddBitmap("MPButton", "img/Multiplayer-Button.png");
        assetManager.loadAndAddBitmap("Mute", "img/SoundOn.png");
        assetManager.loadAndAddBitmap("Unmute", "img/SoundOff.png");
        assetManager.loadAndAddBitmap("MainBackground", "img/Lined-Paper.png");


        // Define the spacing that will be used to position the buttons
        int spacingX = game.getScreenWidth() / 5;
        int spacingY = game.getScreenHeight() / 3;

        //make the title and background object
        mTitleBackground = new GameObject(
                game.getScreenWidth() / 2, mGame.getScreenHeight() / 2, mGame.getScreenWidth(), mGame.getScreenHeight(), assetManager.getBitmap("MainBackground"), this);
        mGameTitle = new GameObject(
                spacingX * 2.5f, spacingY * 0.75f, spacingX * 4, spacingY, assetManager.getBitmap("Title"), this);


        // Create the trigger buttons
        mPerformanceButton = new PushButton(
                spacingX * 4.75f, spacingY * 0.25f, spacingX / 2.0f, spacingY / 2.0f, "PerformanceIcon", this);
        mOptionsButton = new PushButton(
                spacingX * 0.25f, spacingY * 0.25f, spacingX / 2.0f, spacingY / 2.0f, "OptionsScreenIcon", this);
        mHelpButton = new PushButton(
                spacingX * 4.75f, game.getScreenHeight() - (spacingY / 4.0f), spacingX / 2.0f, spacingY / 2.0f, "HelpScreenIcon", this);
        mSinglePlayerButton = new PushButton(
                spacingX * 1.5f, spacingY * 1.75f, spacingX * 2.0f, spacingY, "SPButton", this);
        mMultiPlayerButton = new PushButton(
                spacingX * 3.5f, spacingY * 1.75f, spacingX * 2.0f, spacingY, "MPButton", this);
        mMuteButton = new ToggleButton(
                spacingX * 0.25f, game.getScreenHeight() - (spacingY / 4.0f), spacingX / 2.0f, spacingY / 2.0f, "Mute", "Unmute", this);

        toastHandler = new Handler(Looper.getMainLooper());
        toastRunner = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getGame().getContext(), getGame().getContext().getString(R.string.multiplayer_selection), Toast.LENGTH_SHORT).show();
            }
        };
    }
    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Update the menu screen
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Process any touch events occurring since the update
        Input input = mGame.getInput();

        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {

            // Update each button and transition if needed

            mPerformanceButton.update(elapsedTime);
            mOptionsButton.update(elapsedTime);
            mHelpButton.update(elapsedTime);
            mSinglePlayerButton.update(elapsedTime);
            mMultiPlayerButton.update(elapsedTime);
            mMuteButton.update(elapsedTime);


            if (mPerformanceButton.isPushTriggered()) {
                changeToScreen(new PerformanceScreen(mGame));
            } else if (mOptionsButton.isPushTriggered()) {
                changeToScreen(new OptionsScreen(mGame));
            } else if (mHelpButton.isPushTriggered()) {
                new GameHelpController(mGame);
            } else if (mSinglePlayerButton.isPushTriggered()) {
                changeToScreen(new WorldScreen(mGame));
            } else if (mMultiPlayerButton.isPushTriggered()) {
                toastHandler.post(toastRunner);
            } else if (soundOn != mMuteButton.isToggledOn()) {
                soundOn = mMuteButton.isToggledOn();
            }
        }
    }

    /**
     * Remove the current game screen and then change to the specified screen
     *
     * @param screen game screen to become active
     */
    private void changeToScreen(GameScreen screen) {
        mGame.getScreenManager().removeScreen(this.getName());
        mGame.getScreenManager().addScreen(screen);
    }

    /**
     * Draw the menu screen
     *
     * @param elapsedTime Elapsed time information
     * @param graphics2D  Graphics instance
     */
    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        // Clear the screen and draw the buttons
        graphics2D.clear(Color.WHITE);

        mTitleBackground.draw(elapsedTime, graphics2D);
        mGameTitle.draw(elapsedTime, graphics2D);
        mPerformanceButton.draw(elapsedTime, graphics2D);
        mOptionsButton.draw(elapsedTime, graphics2D);
        mHelpButton.draw(elapsedTime, graphics2D);
        mSinglePlayerButton.draw(elapsedTime, graphics2D);
        mMultiPlayerButton.draw(elapsedTime, graphics2D);
        mMuteButton.draw(elapsedTime, graphics2D);

    }
}
