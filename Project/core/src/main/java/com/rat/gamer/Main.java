package com.rat.gamer;

import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputProcessor;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    //    private Texture texture;
//    private TextureRegion region;
//    private Sprite sprite;

    private Scene currentLevel;
    public int levelNumber;



    public void addBasic() {

        int bgNum = (int)((float)levelNumber/4f) % 3;

        Texture background;

        switch(bgNum) {
            case 1: {
                background = new Texture("lobby.png");
                break;
            }
            case 2: {
                background = new Texture("lobby2.png");
                break;
            }
            case 0:
            default: {
                background = new Texture("hotel.png");
                break;
            }
        }


        Texture pit = new Texture("pit.png");

        GameplayObject backgroundShow = currentLevel.addGeneral(
            new Platform(0, 0, background, 1920, 1080)
        )[0];

        if(!currentLevel.hasCeiling) {
            GameplayObject ceilPit = currentLevel.addGeneral(
                new Platform(0, 0, pit, -1920, -1080)
            )[0];
        }
        if(!currentLevel.hasFloor) {
            GameplayObject floorPit = currentLevel.addGeneral(
                new Platform(0, 0, pit, 1920, 1080)
            )[0];
        }

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

            final Texture placeholder = new Texture("catPlaceholder.png");
            final Texture refill = new Texture("spring.png");
            final Texture gravity = new Texture("gravityChange.png");
            final Texture flagBase = new Texture("flagRed.png");
            final Texture platform = new Texture("platform.png");
            final Texture playerTexture = new Texture("playerkid.png");
            final Texture exit = new Texture("Exitdoortwo.png");

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

                    addBasic();

                    currentLevel.addPlayer(
                        new Player(0, 0, playerTexture, 50, 150)
                    );
                    //Adds to the scene's ArrayList of player objects.

                    currentLevel.addPlatform(
                        new Platform(0, 600, platform, 150, 10).oscillateFromTo(-400,0,400f,70, 0.2f, 1),
                        new Platform(0, 600, platform, 150, 50).oscillateRound(-400,0,1,2,4),
                        new Platform(150, -240, platform, 450, 200)
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
                        new LevelExit(0,-450,exit,100,200)
                    );

                    break;
                }
                case 1: {
                    currentLevel = new Scene(true, true, this);
                    Texture instruction = new Texture("moveTutorial.png");

                    addBasic();

                    currentLevel.addGeneral(
                        new Platform(-200, 100, instruction, 1800, 900)
                    );

                    Player player = currentLevel.addPlayer(
                        new Player(-600, -500, playerTexture, 150, 400)
                    )[0];
                    player.jumpPower = 0;

                    currentLevel.addExit(
                        new LevelExit(750, -300, exit, 200, 400)
                    );

                    break;
                }
                case 2: {
                    currentLevel = new Scene(true, true, this);
                    Texture instruction = new Texture("jumpTutorial.png");

                    addBasic();

                    currentLevel.addGeneral(
                        new Platform(-200, 100, instruction, 1800, 900)
                    );

                    Player player = currentLevel.addPlayer(
                        new Player(-600, -500, playerTexture, 150, 400)
                    )[0];

                    player.gravityStrength = 1.2f;
                    player.jumpPower = 1.6f;

                    currentLevel.addExit(
                        new LevelExit(750, -300, exit, 200, 400)
                    );

                    currentLevel.addFlag(
                        new Flag(-550, 140, flagBase, 300, 300)
                    );


                    break;
                }
                case 3: {
                    currentLevel = new Scene(true, true, this);
                    Texture instruction = new Texture("holdTutorial.png");

                    addBasic();

                    currentLevel.addGeneral(
                        new Platform(-200, 100, instruction, 1800, 900)
                    );

                    currentLevel.addPlayer(
                        new Player(-600, -500, playerTexture, 83, 250)
                    );

                    currentLevel.addExit(
                        new LevelExit(750, -400, exit, 133, 266)
                    );

                    currentLevel.addFlag(
                        new Flag(-550, 80, flagBase, 200, 200)
                    );


                    break;
                }
                case 4: {
                    currentLevel = new Scene(true, true, this);
                    Texture instruction = new Texture("doubleJump.png");

                    addBasic();

                    currentLevel.addGeneral(
                        new Platform(-200, 100, instruction, 1800, 900)
                    );

                    currentLevel.addPlayer(
                        new Player(-600, -500, playerTexture, 83, 250)
                    );

                    currentLevel.addExit(
                        new LevelExit(750, -400, exit, 133, 266)
                    );

                    currentLevel.addFlag(
                        new Flag(-550, 250, flagBase, 200, 200)
                    );


                    break;
                }
                case 5: {
                    currentLevel = new Scene(true, true, this);

                    addBasic();

                    Player player = currentLevel.addPlayer(
                        new Player(-600, -500, playerTexture, 50, 150)
                    )[0];
                    player.jumpPower = 0.8f;

                    currentLevel.addPlatform(
                        new Platform(-200,-300,platform,360,20)
                    );

                    currentLevel.addExit(
                        new LevelExit(750, -440, exit, 100, 200)
                    );

                    currentLevel.addFlag(
                        new Flag(0, 50, flagBase, 150, 150)
                    );


                    break;
                }
                case 6: {
                    currentLevel = new Scene(true, true, this);

                    addBasic();

                    Player player = currentLevel.addPlayer(
                        new Player(-860, -500, playerTexture, 50, 150)
                    )[0];
                    player.jumpPower = 0.8f;

                    currentLevel.addPlatform(
                        new Platform(-300,-300,platform,360,20),
                        new Platform(100,-100,platform,360,20),
                        new Platform(-475,55,platform,10,700)
                    );

                    currentLevel.addExit(
                        new LevelExit(0, -440, exit, 100, 200)
                    );

                    currentLevel.addFlag(
                        new Flag(0, 50, flagBase, 150, 150),
                        new Flag(700, 150, flagBase, 150, 150)
                    );


                    break;
                }
                case 7: {
                    currentLevel = new Scene(true, true, this);

                    Texture instruction = new Texture("jumpRefill.png");
                    Texture instructionRetry = new Texture("retry.png");

                    addBasic();

                    currentLevel.addGeneral(
                        new Platform(-600, 100, instruction, 1800, 900),
                        new Platform(600, -300, instructionRetry, 600, 300)
                    );

                    Player player = currentLevel.addPlayer(
                        new Player(-860, -500, playerTexture, 50, 150)
                    )[0];
                    player.jumpPower = 0.8f;

                    currentLevel.addPlatform(
                        new Platform(-600,-300,platform,160,20),
                        new Platform(500,0,platform,160,20)
                    );

                    currentLevel.addExit(
                        new LevelExit(800, 150, exit, 100, 200)
                    );

                    currentLevel.addFlag(
                        new Flag(550, 80, flagBase, 150, 150)
                    );

                    currentLevel.addRefill(
                        new JumpRefill(100, 100, refill, 150, 150)
                        //,new JumpRefill(700, 150, placeholder, 150, 150)
                    );


                    break;
                }
                case 8: {
                    currentLevel = new Scene(true, true, this);
                    addBasic();

                    Player player = currentLevel.addPlayer(
                        new Player(-860, -500, playerTexture, 50, 150)
                    )[0];
                    player.jumpPower = 0.8f;

                    currentLevel.addGravity(
                        new GravityChange(-500, -440, gravity, 100, 110),
                        new GravityChange(500, 440, gravity, 100, 110)
                    );

                    currentLevel.addExit(
                        new LevelExit(480, -140, exit, 100, 200)
                    );

                    currentLevel.addFlag(
                        new Flag(-300, 450, flagBase, 150, 150)
                    );

                    break;
                }
                case 9: {
                    currentLevel = new Scene(true, false, this);
                    addBasic();

                    Texture warn = new Texture("voidWarn.png");

                    currentLevel.addGeneral(
                        new Platform(-100,0,warn,1200,600)
                    );

                    Player player = currentLevel.addPlayer(
                        new Player(-860, -500, playerTexture, 50, 150)
                    )[0];
                    player.jumpPower = 0.8f;

                    currentLevel.addPlatform(
                        new Platform(500, 540, platform, 400, 60).oscillateFromTo(500,440,-500,440,1,1)
                    );

                    currentLevel.addGravity(
                        new GravityChange(-500, -440, gravity, 100, 110),
                        new GravityChange(500, 270, gravity, 100, 110)
                    );

                    currentLevel.addExit(
                        new LevelExit(0, -440, exit, 100, 200)
                    );

                    currentLevel.addFlag(
                        new Flag(500, 350, flagBase, 150, 150),
                        new Flag(-300, 350, flagBase, 150, 150)
                    );

                    break;
                }
                case 10: {
                    currentLevel = new Scene(true, true, this);
                    addBasic();

                    Texture instruction = new Texture("overchargeTutorial.png");

                    currentLevel.addGeneral(
                        new Platform(200, -300, instruction, 1800, 900)
                    );

                    Player player = currentLevel.addPlayer(
                        new Player(-860, -500, playerTexture, 50, 150)
                    )[0];
                    player.jumpPower = 0.8f;

                    currentLevel.addPlatform(
                        new Platform(0, 0, platform, 400, 30)
                    );

                    currentLevel.addRefill(
                        new JumpRefill(-550,-400,refill,50,50),
                        new JumpRefill(-450,-300,refill,50,50),
                        new JumpRefill(-350,-200,refill,50,50),
                        new JumpRefill(-250,-100,refill,50,50)
                    );

                    currentLevel.addExit(
                        new LevelExit(0, -440, exit, 100, 200)
                    );

                    currentLevel.addFlag(
                        new Flag(500, 350, flagBase, 150, 150),
                        new Flag(-300, 350, flagBase, 150, 150)
                    );

                    break;
                }
                case 11: {
                    currentLevel = new Scene(false, false, this);
                    addBasic();

                    Player player = currentLevel.addPlayer(
                        new Player(-860, -300, playerTexture, 50, 150)
                    )[0];
                    player.jumpPower = 0.8f;

                    currentLevel.addPlatform(
                        new Platform(-700, -540, platform, 500, 30),
                        new Platform(-500, -400, platform, 400, 30),
                        new Platform(-300, -255, platform, 40, 600),
                        new Platform(-100, 200, platform, 80, 1000),
                        new Platform(100, -200, platform, 80, 1000),
                        new Platform(-100, 840, platform, 200, 30).oscillateFromTo(0,-540,-200,-540,0.3f,1.2f),
                        new Platform(-100, -840, platform, 200, 30).oscillateFromTo(200,540,0,540,0.3f,0.8f)
                    );

                    currentLevel.addRefill(
                        new JumpRefill(-500,-200,refill,50,50),
                        new JumpRefill(400,400,refill,50,50),
                        new JumpRefill(700,100,refill,50,50),
                        new JumpRefill(400,-200,refill,50,50),
                        new JumpRefill(650,-300,refill,50,50)
                    );

                    currentLevel.addGravity(
                        new GravityChange(0, -240, gravity, 100, 100)
                    );

                    Flag flagTrigger = currentLevel.addFlag(
                        new Flag(850, -450, flagBase, 100, 150)
                    )[0];

                    flagTrigger.onCollect = () -> {
                        currentLevel.addPlatform(
                            new Platform(850, -270, platform, 300, 50),
                            new Platform(-550, 300, platform, 80, 800),
                            new Platform(-400, -840, platform, 50, 30).oscillateRound(-300, 400, 2, 0.1f, 2)
                        );
                        currentLevel.addGravity(
                            new GravityChange(-200, -240, gravity, 100, 100),
                            new GravityChange(0, 250, gravity, 100, 100),
                            new GravityChange(-400, 0, gravity, 100, 100)
                        );
                        currentLevel.addExit(
                            new LevelExit(-600,-290,exit,100,200)
                        );
                    };

                    //flagTrigger.raise();

                    break;
                }
                default: {
                    currentLevel = new Scene(true, true, this);
                    Texture instruction = new Texture("win.png");
                    currentLevel.addGeneral(
                        new Platform(0, 0, instruction, 1920, 1080)
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
