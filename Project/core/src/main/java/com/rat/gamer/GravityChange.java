package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;

public class GravityChange extends GameplayObject {
    private boolean inScene = true;
    public AnyLambda onCollect = null;
    public GravityChange(float x, float y, Texture image, int width, int height) {
        super(x, y, image, width, height);
        tintRed = 0.8f;
        tintGreen = 0.8f;
    }
    public boolean exists() {
        return inScene;
    }
    public void remove() {
        inScene = false;
        if(onCollect != null) onCollect.run();
    }
    public void tick(Scene scene) {
        if(!inScene) opacity = (float)Math.max(0,opacity - 0.02);
    }
}
