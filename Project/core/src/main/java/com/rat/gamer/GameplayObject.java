package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;

public class GameplayObject {
    public float x = 0;
    public float y = 0;
    public float width;
    public float height;
    public Texture image;


    public boolean useVelocity;
    public float gravityStrength;
    public float xVelocity = 0;
    public float yVelocity = 0;

    public GameplayObject(float x, float y, Texture image, int width, int height, boolean useVelocity, float gravityStrength) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.image = image;
        this.useVelocity = useVelocity;
        this.gravityStrength = gravityStrength;
    }

    public void tick(Scene scene) {
        if(useVelocity) {
            yVelocity -= gravityStrength/10;
            x += xVelocity;
            y += yVelocity;
        }
    }
}
