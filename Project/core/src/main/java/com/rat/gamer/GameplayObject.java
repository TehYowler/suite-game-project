package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    public float opacity = 1;
    public float tintRed = 1;
    public float tintGreen = 1;
    public float tintBlue = 1;
    public int order = 0;

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

    public void draw(SpriteBatch batch) {
        batch.setColor(this.tintRed, this.tintGreen, this.tintBlue, this.opacity);
        batch.draw(this.image, this.x - (int)(this.width/2.0) + (int)(Global.WIDTH/2), this.y - (int)(this.height/2.0) + (int)(Global.HEIGHT/2), this.width, this.height );
    }
}
