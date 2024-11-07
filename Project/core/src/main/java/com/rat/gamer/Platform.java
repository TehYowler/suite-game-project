package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;

public class Platform extends GameplayObject {

    private float oscillationProgress = 0;
    private float oscillationStart = 0;
    public AnyLambda oscillateType;
    public Player moveBind = null;

    public Platform oscillateFromTo(float x1, float y1, float x2, float y2, float speedInitial, float speedOscillate) {
        oscillationStart = 0;
        oscillationProgress = 0;
        this.oscillateType = () -> {
            oscillationStart += speedInitial / 400f;
            oscillationProgress += speedOscillate / 200f;

            oscillationStart = Math.clamp(oscillationStart, 0,1);
            oscillationProgress %= 2;

            float oscillateValue = (float)Math.sin(oscillationProgress*Math.PI/2);
            oscillateValue = (float)Math.pow(oscillateValue, 2);

            this.x = this.x * ( 1 - oscillationStart ) + ( x2 * oscillateValue + x1 * ( 1 - oscillateValue ) ) * oscillationStart;
            this.y = this.y * ( 1 - oscillationStart ) + ( y2 * oscillateValue + y1 * ( 1 - oscillateValue ) ) * oscillationStart;
        };
        return this;
    }

    public Platform oscillateRound(float x, float y, float radius, float speedInitial, float speedOscillate) {
        oscillationStart = 0;
        oscillationProgress = 0;
        this.oscillateType = () -> {
            oscillationStart += speedInitial / 400f;
            oscillationProgress += speedOscillate / 200f;

            oscillationStart = Math.clamp(oscillationStart, 0,1);
            oscillationProgress %= 2;

            float xTo = x + (float)Math.cos(oscillationProgress * Math.PI) * radius * 50;
            float yTo = y + (float)Math.sin(oscillationProgress * Math.PI) * radius * 50;

            float previousX = this.x;
            float previousY = this.y;

            this.x = this.x * ( 1 - oscillationStart ) + ( xTo ) * oscillationStart;
            this.y = this.y * ( 1 - oscillationStart ) + ( yTo ) * oscillationStart;
        };
        return this;
    }

    public Platform(float x, float y, Texture image, int width, int height) {
        super(x, y, image, width, height, false,0);
        oscillateType = null;
    }

    public void tick(Scene scene) {
        if(oscillateType != null) {
            //oscillateType(1, 2, 3, 3);
            float previousX = x;
            float previousY = y;
            oscillateType.run();

            if(moveBind != null) {
                moveBind.x += (this.x - previousX);
                moveBind.y += (this.y - previousY);
                this.moveBind = null;
            }
        }
    }

}
