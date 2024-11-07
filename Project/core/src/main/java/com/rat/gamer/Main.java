package com.rat.gamer;

import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private double time = 0;

    public Scene currentLevel;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Texture image = new Texture("cat.png"); //Located in the assets file

        currentLevel = new Scene();

        currentLevel.addPlayer(
            new Player(0, 0, image, 100, 100, true, 1)
        );
        //Adds to the scene's ArrayList of player objects.

        currentLevel.addPlatform(
            new Platform(0, 600, image, 150, 10).oscillateFromTo(-400,0,400f,70, 0.2f, 1),
            new Platform(0, 600, image, 150, 50).oscillateRound(-400,0,1,2,4),
            new Platform(250, -240, image, 450, 200)
        );
        //Adds to the scene's ArrayList of platform objects.
        //One oscillates back and forth between two points, while the other revolves in a circle around a point.
        //One is just static.

        //currentLevel.objectsPlatform.get(0)
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

        currentLevel.tick();

        for(GameplayObject x : currentLevel.objectsGeneral) {
            draw(batch,x);
        }
        for(GameplayObject x : currentLevel.objectsPlatform) {
            draw(batch,x);
        }
        for(GameplayObject x : currentLevel.objectsPlayer) {
            draw(batch,x);
        }
        batch.end();
        time++;
    }

    @Override
    public void dispose() {
        batch.dispose();
        for(GameplayObject x : currentLevel.objectsGeneral) {
            x.image.dispose();
        }
    }
}
