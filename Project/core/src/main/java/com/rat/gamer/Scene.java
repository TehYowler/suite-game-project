package com.rat.gamer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Comparator;

public class Scene {

    //A scene object defines everything in a certain level, or 'scene".

    public ArrayList<GameplayObject> objectsGeneral = new ArrayList<GameplayObject>();
    public ArrayList<Player> objectsPlayer = new ArrayList<Player>();
    public ArrayList<Platform> objectsPlatform = new ArrayList<Platform>();
    public ArrayList<GravityChange> objectsGravity = new ArrayList<GravityChange>();

    private int orderID = 0;
    //It has an ArrayList for each type of object.
    //EVEN THOUGH "objectsGeneral" ACCEPTS ALL TYPES OF "GameplayObject", CHECKS SUCH AS COLLISION CHECKS WILL NOT BE
    //PERFORMED ON OBJECTS IN IT. ONLY USE IT FOR VISUALS, NOT GAME LOGIC, SUCH AS PLAYERS OR PLATFORMS.

    public ArrayList<GameplayObject> allObjects() {
        ArrayList<GameplayObject> allObjects = new ArrayList<GameplayObject>();
        allObjects.addAll(objectsGeneral);
        allObjects.addAll(objectsPlayer);
        allObjects.addAll(objectsPlatform);
        allObjects.addAll(objectsGravity);
        allObjects.sort(Comparator.comparingInt(a -> a.order));
        return allObjects;
    }


    public void tick() { //Ticks all objects in the scene forward.
        for( GameplayObject x : allObjects() ) {
            x.tick(this);
        }
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

}
