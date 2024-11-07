package com.rat.gamer;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;

public class Player extends GameplayObject {

    private final float jumpPower = 1.1f; //How powerful the player's jumps are

    public final float floor; //A variable where the floor to rest at is.

    private boolean holdingUp = false;

    private boolean sitting = false;
    private int extraJumps = 0;
    private float previousX;
    private float previousY;

    public Player(float x, float y, Texture image, int width, int height, boolean useVelocity, float gravityStrength) {
        super(x, y, image, width, height, useVelocity, gravityStrength);
        previousX = x;
        previousY = y;
        floor = height/2 - (float)Global.HEIGHT/2;
    }

    private boolean intersecting(GameplayObject object) {
            float thresholdX = this.width/2 + object.width/2;
            float thresholdY = this.height/2 + object.height/2;

            return Math.abs(this.x - object.x) <= thresholdX && Math.abs(this.y - object.y) <= thresholdY;
    }
    private boolean intersectingPredictAnyPlatform(Scene scene, float x, float y) {
        for(Platform platforms : scene.objectsPlatform) {
            float thresholdX = this.width/2 + platforms.width/2;
            float thresholdY = this.height/2 + platforms.height/2;

            if(Math.abs(this.x - platforms.x) <= thresholdX && Math.abs(this.y - platforms.y) <= thresholdY) {
                return true;
            }
        }
        return false;

    }


    public void tick(Scene scene) {

        if(Gdx.input.isKeyPressed(Input.Keys.A)) moveX(true);
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) moveX(false);

        boolean tapUp = (!holdingUp && Gdx.input.isKeyPressed(Input.Keys.W));
        /*
        If the player was not holding the W key the last time it was evaluated, but is holding it this frame, it is
        considered as a "tap" or a "press", as opposed to a hold, and stays true for only one frame.
        This is an important variable to have, since executing the player's double jump will not be when they hold
        the jump button, but when they press it in midair again.
        */

        holdingUp = Gdx.input.isKeyPressed(Input.Keys.W); //Gets if the player is holding up or not.

        /*
        Steps the player's movement, moving them up and down and constantly making their y velocity do down due to gravity.
        Much like in Undertale's Blue Soul mode or other games with platforms, holding the jump key (W) allows for the
        player to jump higher, and releasing it allows for the player to quickly fall down when they want.
        This is simulated by decreasing the gravity when the jump key is held, and increasing it when it is released.
        The jump key boolean is supplied through the method, and overrides the inherited method.
        */
        if(!this.sitting) yVelocity -= holdingUp ? gravityStrength/4.5f : gravityStrength/1.5f;

        //The xVelocity variable constantly decelerates, as shown by the division. This is to slow it down to a halt when
        //the player releases the A/D keys in a smooth and gradual way.
        // Furthermore, it is capped at a speed of seven, as shown by the clamp values (may not be a final feature to cap it).
        xVelocity = Math.clamp((float)(xVelocity/1.16),-7,7);


        previousX = x;
        previousY = y;
        //Then, adds the velocities to the positions, if there is not a platform in the way while it is sitting.
        //The previous x and y positions are also saved.
        if(!intersectingPredictAnyPlatform(scene, x+xVelocity, y+yVelocity) || !sitting) {
            y += yVelocity;
            x += xVelocity;
        }

        sitting = y <= floor; //If the player is on the floor, set them to resting. Otherwise, makes it false.

        for(Platform x : scene.objectsPlatform) { //Iterates through every platform in the scene.
            float thresholdX = this.width/2 + x.width/2;
            float thresholdY = this.height/2 + x.height/2;

            float thisBottom = this.y - this.height/2;
            float thisTop = this.y + this.height/2;
            float thisLeft = this.x - this.width/2;
            float thisRight = this.x + this.width/2;

            float platformBottom = x.y - x.height/2;
            float platformTop = x.y + x.height/2;
            float platformLeft = x.x - x.width/2;
            float platformRight = x.x + x.width/2;

            boolean betweenForX = thisBottom < platformTop && thisTop > platformBottom;
            boolean betweenForY = thisLeft < platformRight && thisRight > platformLeft;

            //Establishes several variables to be used for collision checks.

            float leeway = 5;
            if(Math.min(this.x,previousX) + thresholdX - leeway <= x.x && x.x <= Math.max(this.x,previousX) + thresholdX + leeway && betweenForX) {
                this.x = Math.min(this.x,x.x - thresholdX - 0.5f);
                this.xVelocity = Math.min(0,this.xVelocity);
            }
            if(Math.min(this.x,previousX) - thresholdX - leeway <= x.x && x.x <= Math.max(this.x,previousX) - thresholdX + leeway && betweenForX) {
                this.x = Math.max(x.x + thresholdX+0.5f,this.x);
                this.xVelocity = Math.max(0,this.xVelocity);
            }
            if(Math.min(this.y,previousY) - thresholdY - leeway <= x.y && x.y <= Math.max(this.y,previousY) - thresholdY + leeway && betweenForY) {
                this.y = Math.max(this.y,x.y + thresholdY + 0.5f);
                this.yVelocity = Math.max(0,this.yVelocity);
                this.sitting = true;
                x.moveBind = this;
                extraJumps = Math.max(1,extraJumps);
            }
            if(Math.min(this.y,previousY) + thresholdY - leeway <= x.y && x.y <= Math.max(this.y,previousY) + thresholdY + leeway && betweenForY) {
                this.y = Math.min(this.y, x.y - thresholdY - 0.5f);
                this.yVelocity = Math.min(0,this.yVelocity);
            }
        }

        if(sitting) {
            extraJumps = Math.max(1,extraJumps);
            //If the player is resting, reset the jump count.
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(sitting) {
                jump();
            }
            else if(extraJumps > 0 && tapUp) {
                jump();
                extraJumps--;
            }
            //Performs a jump by setting the y velocity upwards.
            //If the player is sitting on the ground, no double jumps are used, and they will jump without fail while the button is held.
            //If the player jumps in the air, a double jump charge is used up if it can be.
            //If the player does multi jump charge in the air, they will not jump.
        }

        //And for y, it cannot go any lower than the floor (might not be final if there is a void or a place to fall).
        y = Math.max(y, floor);
    }
    public void moveX(boolean isLeft) { //Increases the x velocity either left or right, depending on the input.
        xVelocity += 1.0 * (isLeft ? -1 : 1);
    }
    public void jump() {
        yVelocity = jumpPower * 10;
    }
}
