package com.rat.gamer;

import java.util.ArrayList;

public class Scene {

    //A scene object defines everything in a certain level, or 'scene".

    public ArrayList<GameplayObject> objectsGeneral = new ArrayList<GameplayObject>();
    public ArrayList<Player> objectsPlayer = new ArrayList<Player>();
    public ArrayList<Platform> objectsPlatform = new ArrayList<Platform>();
    //It has an ArrayList for each type of object.
    //EVEN THOUGH "objectsGeneral" ACCEPTS ALL TYPES OF "GameplayObject", CHECKS SUCH AS COLLISION CHECKS WILL NOT BE
    //PERFORMED ON OBJECTS IN IT. ONLY USE IT FOR VISUALS, NOT GAME LOGIC, SUCH AS PLAYERS OR PLATFORMS.


    public void tick() { //Ticks all objects in the scene forward.
        for( GameplayObject x : objectsGeneral ) {
            x.tick(this);
        }
        for( Player x : objectsPlayer ) {
            x.tick(this);
        }
        for( Platform x : objectsPlatform ) {
            x.tick(this);
        }
    }

    //Adds a certain type of object, accepts any number of parameters of the given type.
    public void addGeneral(GameplayObject... objectAdd) {
        for(GameplayObject x : objectAdd) objectsGeneral.add(x);
    }
    public void addPlayer(Player... objectAdd) {
        for(Player x : objectAdd) objectsPlayer.add(x);
    }
    public void addPlatform(Platform... objectAdd) {
        for(Platform x : objectAdd) objectsPlatform.add(x);
    }

}
