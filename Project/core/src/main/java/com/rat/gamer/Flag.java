package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;

public class Flag extends GameplayObject {
    public boolean raised = false;
    public Flag(float x, float y, Texture image, int width, int height) {
        super(x, y, image, width, height);
    }
}
