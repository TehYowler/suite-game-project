package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;

public class GravityChange extends GameplayObject {
    private boolean inScene = true;
    public GravityChange(float x, float y, Texture image, int width, int height, boolean useVelocity, float gravityStrength) {
        super(x, y, image, width, height, useVelocity, gravityStrength);
        tintRed = 0f;
        tintGreen = 0f;
    }
    public boolean exists() {
        return inScene;
    }
    public void remove() {
        inScene = false;
    }
    public void tick(Scene scene) {
        if(!inScene) opacity = (float)Math.max(0,opacity - 0.02);
    }
}
