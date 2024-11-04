package com.rat.gamer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input.Keys;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private Platform platform;
    private double time = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Texture image = new Texture("cat.png"); //Located in the assets file
        player = new Player(0, 0, image, 100, 100, true, 1); //Makes a new player object
        platform = new Platform(0, 600, image, 150, 10); //Makes a new platform object
        platform.oscillateType = platform.backAndForth.run(-400,0,400f,0, 0.2f, 1); //Makes the platform oscillate between two points.

        //The scale of any GameplayObject image correlates directly to their width and height.
    }

    //These two draw functions draw an image. They are automatically centered at the middle of the window.
    //Using floats for the scale uses the image size as a base and scales up from there.
    //Using integers as a scale makes the image the exact height of the integers.
    public void draw(SpriteBatch batch, Texture image, int x, int y, double xScale, double yScale) {
        int finalScaleX = (int)(image.getWidth() * xScale);
        int finalScaleY = (int)(image.getHeight() * yScale);
        x -= (int)(finalScaleX/2.0) - (int)(Global.WIDTH/2);
        y -= (int)(finalScaleY/2.0) - (int)(Global.HEIGHT/2);
        batch.draw(image, x, y, finalScaleX, finalScaleY);
    }
    public void draw(SpriteBatch batch, Texture image, int x, int y, int xSize, int ySize) {
        x -= (int)(xSize/2.0) - (int)(Global.WIDTH/2);
        y -= (int)(ySize/2.0) - (int)(Global.HEIGHT/2);
        batch.draw(image, x, y, xSize, ySize );
    }
    public void draw(SpriteBatch batch, GameplayObject gameplayObject) {
        batch.draw(gameplayObject.image, gameplayObject.x - (int)(gameplayObject.width/2.0) + (int)(Global.WIDTH/2), gameplayObject.y - (int)(gameplayObject.height/2.0) + (int)(Global.HEIGHT/2), gameplayObject.width, gameplayObject.height );
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.75f, 0.15f, 0.2f, 1f); //Clears the screen by drawing the background each frame
        batch.begin();

        //For left and right movement, takes the D and A keys.
        if(Gdx.input.isKeyPressed(Keys.D)) player.moveX(false);
        if(Gdx.input.isKeyPressed(Keys.A)) player.moveX(true);

        player.tick(Gdx.input.isKeyPressed(Keys.W)); //Steps the player's movement, and provides the jump key boolean.
        platform.tick();

        if(player.y <= player.floor && Gdx.input.isKeyPressed(Keys.W)) {
            //If the player is on (or below, for whatever reason) the floor, and presses the jump jey, they jump.
            player.jump();
        }

        //draw(batch, player.getImage(), player.getX(), player.getY(), 0.2, 0.2);
        //draw(batch, player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        draw(batch, player);
        draw(batch, platform);
        //draw(batch, player.getImage(), player.getX(), player.getY(), player.getWidth() / player.getImage().getWidth() , player.getHeight() / player.getImage().getHeight());
        //Draws the image of the player. This is where the image is downscaled by 5.
        batch.end();
        time++;
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.image.dispose();
    }
}
