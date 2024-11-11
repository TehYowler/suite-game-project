package com.rat.gamer;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
// new class for GravityPickup or just add to gameplayobject
    public class GravityPickup extends GameplayObject {             //adds a object to pickup causing gravity change in gameplay class
        public GravityPickup(float x, float y, Texture image) { //replace texture images w good image for object
            super(x, y, image, 20, 20, false, 0); //example cordinates
        }
//PLAYER CLASS
        private boolean gravityInverted = false;        //Boolean to track gravity direction starting at false goes in player class
    }
    private void toggleGravity() { // toggle gravity
        gravityInverted = !gravityInverted;
        gravityStrength = -gravityStrength; // Invert gravity strength
    }

    for (GravityPickup pickup : scene.gravityPickups) { //check for collision on gravity change object and changes gravity 
        if (intersecting(pickup)) {
            toggleGravity();
            scene.removeGravityPickup(pickup); // Remove pickup from the scene once collected
            break;
        }
    }
}
// scene class
public ArrayList<GravityPickup> gravityPickups;
gravityPickups = new ArrayList<>(); //gravity pickup for scene class
public void addGravityPickup(float x, float y, Texture texture) {
        gravityPickups.add(new GravityPickup(x, y, texture));
    }

    public void removeGravityPickup(GravityPickup pickup) {
        gravityPickups.remove(pickup);
    }

