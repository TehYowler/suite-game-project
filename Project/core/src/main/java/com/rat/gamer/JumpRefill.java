package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;

public class JumpRefill extends GameplayObject {
    private boolean inScene = true;
    public JumpRefill(float x, float y, Texture image, int width, int height) {
        super(x, y, image, width, height);
        tintRed = 0f;
        tintBlue = 0f;
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
