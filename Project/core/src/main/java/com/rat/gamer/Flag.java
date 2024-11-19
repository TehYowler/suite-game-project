package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;

public class Flag extends GameplayObject {
    public AnyLambda onCollect = null;
    public boolean raised = false;
    public Flag(float x, float y, Texture image, int width, int height) {
        super(x, y, image, width, height);
    }
    public void raise() {
        raised = true;
        if(onCollect != null) onCollect.run();
        image = new Texture("flagGreen.png");
    }
}
