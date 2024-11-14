package com.rat.gamer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    //    private Texture texture;
//    private TextureRegion region;
//    private Sprite sprite;

    private Scene currentLevel;
    public int levelNumber;

    public void addBasic() {
        Texture background = new Texture("hotel.png");
        GameplayObject backgroundShow = currentLevel.addGeneral(
            new Platform(0, 0, background, 1920, 1080)
        )[0];
        backgroundShow.tintBlue = 0.8f;
        backgroundShow.tintRed = 0.6f;
        backgroundShow.tintGreen = 0.6f;
    }


    public void swapLevel(int levelNumTo) {
        levelNumber = levelNumTo;
        if(levelNumber < 0) { //Gameover
            currentLevel = new Scene(true, true, this);
            Texture gameOver = new Texture("gameover.png");
            GameplayObject screen = currentLevel.addGeneral(
                new Platform(0, 0, gameOver, 1920, 1080)
            )[0];
            //Gameover screen
        }
        else {
            switch (levelNumber) {
                case 0: { //Starting screen
                    currentLevel = new Scene(true, true, this);
                    Texture welcome = new Texture("welcome.png");
                    currentLevel.addGeneral(
                        new Platform(0, 0, welcome, 1920, 1080)
                    );
                    break;
                }


                case 999: {
                    currentLevel = new Scene(true, false, this);
                    Texture placeholder = new Texture("cat.png");
                    Texture refill = new Texture("spring.png");
                    Texture gravity = new Texture("gravityChange.png");
                    Texture flagBase = new Texture("flagRed.png");

                    addBasic();

                    currentLevel.addPlayer(
                        new Player(0, 0, placeholder, 100, 100)
                    );
                    //Adds to the scene's ArrayList of player objects.

                    currentLevel.addPlatform(
                        new Platform(0, 600, placeholder, 150, 10).oscillateFromTo(-400,0,400f,70, 0.2f, 1),
                        new Platform(0, 600, placeholder, 150, 50).oscillateRound(-400,0,1,2,4),
                        new Platform(250, -240, placeholder, 450, 200)
                    );
                    //Adds to the scene's ArrayList of platform objects.
                    //One oscillates back and forth between two points, while the other revolves in a circle around a point.
                    //One is just static.

                    currentLevel.addGravity(
                        new GravityChange(100,200,gravity,90,90),
                        new GravityChange(-100,200,gravity,90,90)
                    );

                    currentLevel.addRefill(
                        new JumpRefill(500,0,refill,100,100),
                        new JumpRefill(600,0,refill,100,100),
                        new JumpRefill(700,0,refill,100,100),
                        new JumpRefill(400,0,refill,100,100)
                    );

                    currentLevel.addFlag(
                        new Flag(200,0,flagBase,90,125),
                        new Flag(-200,0,flagBase,90,125)
                    );

                    currentLevel.addExit(
                        new LevelExit(0,-450,placeholder,75,75)
                    );

                    break;
                }
                case 1: {
                    currentLevel = new Scene(true, true, this);
                    Texture placeholder = new Texture("cat.png");
                    Texture instruction = new Texture("exitPlaceholder.png");

                    addBasic();

                    currentLevel.addGeneral(
                        new Platform(220, 0, instruction, 800, 400)
                    );

                    Player player = currentLevel.addPlayer(
                        new Player(-600, -500, placeholder, 400, 400)
                    )[0];
                    player.jumpPower = 0;

                    currentLevel.addExit(
                        new LevelExit(750, -350, placeholder, 300, 300)
                    );

                    break;
                }
                case 2: {
                    currentLevel = new Scene(true, true, this);
                    Texture placeholder = new Texture("cat.png");
                    Texture instruction = new Texture("flagPlaceholder.png");
                    Texture flagBase = new Texture("flagRed.png");

                    addBasic();

                    currentLevel.addGeneral(
                        new Platform(220, 0, instruction, 800, 400)
                    );

                    Player player = currentLevel.addPlayer(
                        new Player(-600, -500, placeholder, 400, 400)
                    )[0];

                    player.gravityStrength = 1.2f;
                    player.jumpPower = 1.6f;

                    currentLevel.addExit(
                        new LevelExit(750, -350, placeholder, 300, 300)
                    );

                    currentLevel.addFlag(
                        new Flag(-250, 140, flagBase, 300, 300)
                    );


                    break;
                }
                case 3: {
                    currentLevel = new Scene(true, true, this);
                    Texture placeholder = new Texture("cat.png");
                    Texture instruction = new Texture("holdJumpPlaceholder.png");
                    Texture flagBase = new Texture("flagRed.png");

                    addBasic();

                    currentLevel.addGeneral(
                        new Platform(320, 0, instruction, 800, 400)
                    );

                    Player player = currentLevel.addPlayer(
                        new Player(-600, -500, placeholder, 250, 250)
                    )[0];

                    currentLevel.addExit(
                        new LevelExit(750, -400, placeholder, 200, 200)
                    );

                    currentLevel.addFlag(
                        new Flag(-250, 80, flagBase, 200, 200)
                    );


                    break;
                }
                case 4: {
                    currentLevel = new Scene(true, true, this);
                    Texture placeholder = new Texture("cat.png");
                    Texture instruction = new Texture("doubleJumpPlaceholder.png");
                    Texture flagBase = new Texture("flagRed.png");

                    addBasic();

                    currentLevel.addGeneral(
                        new Platform(320, 0, instruction, 800, 400)
                    );

                    Player player = currentLevel.addPlayer(
                        new Player(-600, -500, placeholder, 250, 250)
                    )[0];

                    currentLevel.addExit(
                        new LevelExit(750, -400, placeholder, 200, 200)
                    );

                    currentLevel.addFlag(
                        new Flag(-250, 250, flagBase, 200, 200)
                    );


                    break;
                }
                default: {
                    currentLevel = new Scene(true, true, this);
                    Texture instruction = new Texture("exitPlaceholder.png");
                    currentLevel.addGeneral(
                        new Platform(220, 220, instruction, 800, 400),
                        new Platform(-220, 220, instruction, 800, 400),
                        new Platform(-220, -220, instruction, 800, 400),
                        new Platform(220, -220, instruction, 800, 400)
                    );
                }
            }
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        swapLevel(0);



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
        batch.setColor(gameplayObject.tintRed, gameplayObject.tintGreen, gameplayObject.tintBlue, gameplayObject.opacity);
        batch.draw(gameplayObject.image, gameplayObject.x - (int)(gameplayObject.width/2.0) + (int)(Global.WIDTH/2), gameplayObject.y - (int)(gameplayObject.height/2.0) + (int)(Global.HEIGHT/2), gameplayObject.width, gameplayObject.height );
    }

    @Override
    public void render() {

        switch(levelNumber) { //Different background clears for each level
            case 0: {
                ScreenUtils.clear(0f, 0f, 0f, 1f); //Clears the screen by drawing the background each frame
                break;
            }
            default: {
                ScreenUtils.clear(0.75f, 0.15f, 0.2f, 1f); //Clears the screen by drawing the background each frame
            }
        }


        batch.begin();
            currentLevel.tick();
            currentLevel.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        for(GameplayObject x : currentLevel.allObjects()) {
            x.image.dispose();
        }
    }
}
