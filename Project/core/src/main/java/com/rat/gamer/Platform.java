package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;

public class Platform extends GameplayObject {

    private float oscillationProgress = 0;
    private float oscillationStart = 0;
    public OscillateTick oscillateType;

    public final ObjectOscillate backAndForth = (float x1, float y1, float x2, float y2, float speedInitial, float speedOscillate) -> {
        return () -> {
            oscillationStart += speedInitial / 400f;
            oscillationProgress += speedOscillate / 200f;

            oscillationStart = Math.clamp(oscillationStart, 0,1);
            oscillationProgress %= 2;

            float oscillateValue = (float)Math.sin(oscillationProgress*Math.PI/2);
            oscillateValue = (float)Math.pow(oscillateValue, 2);

            this.x = this.x * ( 1 - oscillationStart ) + ( x2 * oscillateValue + x1 * ( 1 - oscillateValue ) ) * oscillationStart;
            this.y = this.y * ( 1 - oscillationStart ) + ( y2 * oscillateValue + y1 * ( 1 - oscillateValue ) ) * oscillationStart;
        };
    };

    public Platform(float x, float y, Texture image, int width, int height) {
        super(x, y, image, width, height, false,0);
        oscillateType = null;
    }

    public void tick() {
        if(oscillateType != null) {
            //oscillateType(1, 2, 3, 3);
            oscillateType.run();
        }
    }

}
