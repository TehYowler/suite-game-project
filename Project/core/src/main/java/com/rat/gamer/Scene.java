package com.rat.gamer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Comparator;

public class Scene {

    //A scene object defines everything in a certain level, or 'scene".

    private float frame = 0;

    public ArrayList<GameplayObject> objectsGeneral = new ArrayList<GameplayObject>();
    public ArrayList<Player> objectsPlayer = new ArrayList<Player>();
    public ArrayList<Platform> objectsPlatform = new ArrayList<Platform>();
    public ArrayList<GravityChange> objectsGravity = new ArrayList<GravityChange>();
    public ArrayList<Flag> objectsFlag = new ArrayList<Flag>();
    public ArrayList<JumpRefill> objectsRefill = new ArrayList<JumpRefill>();
    public ArrayList<LevelExit> objectsExit = new ArrayList<LevelExit>();
    //It has an ArrayList for each type of object.
    //EVEN THOUGH "objectsGeneral" ACCEPTS ALL TYPES OF "GameplayObject", CHECKS SUCH AS COLLISION CHECKS WILL NOT BE
    //PERFORMED ON OBJECTS IN IT. ONLY USE IT FOR VISUALS, NOT GAME LOGIC, SUCH AS PLAYERS OR PLATFORMS.

    private int orderID = 0;
    //Specifies default object order.

    private Main main;
    //A reference back to the main class using this object.

    public final boolean hasFloor;
    public final boolean hasCeiling;
    public Scene(boolean hasFloor, boolean hasCeiling, Main main) {
        this.hasFloor = hasFloor;
        this.hasCeiling = hasCeiling;
        this.main = main;
    }

    public boolean dead = false;
    public boolean restart = false;
    public boolean next = false;

    public ArrayList<GameplayObject> allObjects() {
        ArrayList<GameplayObject> allObjects = new ArrayList<GameplayObject>();
        allObjects.addAll(objectsGeneral);
        allObjects.addAll(objectsPlayer);
        allObjects.addAll(objectsPlatform);
        allObjects.addAll(objectsGravity);
        allObjects.addAll(objectsFlag);
        allObjects.addAll(objectsRefill);
        allObjects.addAll(objectsExit);
        allObjects.sort(Comparator.comparingInt(a -> a.order));
        return allObjects;
    }


    public void tick() { //Ticks all objects in the scene forward.
        for( GameplayObject x : allObjects() ) {
            x.tick(this);
        }
        if(main.levelNumber == 0 && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            main.swapLevel(1);
        }
        else if(main.levelNumber < 0 && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            main.swapLevel(Math.abs(main.levelNumber));
        }
        if(this.next) {
            main.swapLevel(main.levelNumber + 1);
        }
        else if(this.restart) {
            main.swapLevel(Math.abs(main.levelNumber));
        }
        else if(this.dead) {
            main.swapLevel(-Math.abs(main.levelNumber));
        }
        frame++;
    }
    public float getFrame() {
        return frame;
    }
    public void draw(SpriteBatch batch) { //Ticks all objects in the scene forward.
        for( GameplayObject x : allObjects() ) {
            x.draw(batch);
        }
    }

    //Adds a certain type of object, accepts any number of parameters of the given type.
    public GameplayObject[] addGeneral(GameplayObject... objectAdd) {
        for(GameplayObject x : objectAdd) {
            x.order = orderID; orderID++;
            objectsGeneral.add(x);
        }
        return objectAdd;
    }
    public Player[] addPlayer(Player... objectAdd) {
        for(Player x : objectAdd) {
            x.order = orderID; orderID++;
            objectsPlayer.add(x);
        }
        return objectAdd;
    }
    public Platform[] addPlatform(Platform... objectAdd) {
        for(Platform x : objectAdd) {
            x.order = orderID; orderID++;
            objectsPlatform.add(x);
        }
        return objectAdd;
    }
    public GravityChange[] addGravity(GravityChange... objectAdd) {
        for(GravityChange x : objectAdd) {
            x.order = orderID; orderID++;
            objectsGravity.add(x);
        }
        return objectAdd;
    }
    public LevelExit[] addExit(LevelExit... objectAdd) {
        for(LevelExit x : objectAdd) {
            x.order = orderID; orderID++;
            objectsExit.add(x);
        }
        return objectAdd;
    }
    public Flag[] addFlag(Flag... objectAdd) {
        for(Flag x : objectAdd) {
            x.order = orderID; orderID++;
            objectsFlag.add(x);
        }
        return objectAdd;
    }
    public JumpRefill[] addRefill(JumpRefill... objectAdd) {
        for(JumpRefill x : objectAdd) {
            x.order = orderID; orderID++;
            objectsRefill.add(x);
        }
        return objectAdd;
    }

}
