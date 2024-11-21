package com.rat.gamer;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Player extends GameplayObject {

    public float jumpPower = 1.1f; //How powerful the player's jumps are

    public final float floor; //A variable where the floor to rest at is.
    public final float ceil; //A variable where the floor to rest at is.

    private boolean holdingUp = false;
    private boolean holdingDown = false;

    private boolean gravityInvert = false;

    private boolean sitting = false;
    private int extraJumps = 0;
    public float previousX;
    public float previousY;
    private ArrayList<Platform> intersectCount = new ArrayList<>();

    private boolean movedLeft = false;

    public Player(float x, float y, Texture image, int width, int height) {
        super(x, y, image, width, height);
        previousX = x;
        previousY = y;
        floor = height/2f - (float)Global.HEIGHT/2;
        ceil = -height/2f + (float)Global.HEIGHT/2;
        gravityStrength = 0.9f;
    }

    private void registerSit(boolean isSitting) {
        this.sitting = isSitting;
        if(isSitting) extraJumps = Math.max(1,extraJumps);
    }

    private boolean intersecting(GameplayObject object) {
        float thresholdX = this.width/2 + object.width/2;
        float thresholdY = this.height/2 + object.height/2;

        return Math.abs(this.x - object.x) <= thresholdX && Math.abs(this.y - object.y) <= thresholdY;
    }
    private boolean intersecting(GameplayObject object, float leeway) {
        float thresholdX = this.width/2 + object.width/2 + leeway;
        float thresholdY = this.height/2 + object.height/2 + leeway;

        return Math.abs(this.x - object.x) <= thresholdX && Math.abs(this.y - object.y) <= thresholdY;
    }
    private float phaseCalc(GameplayObject object) {
        final float multi = 1f;
        final float add = 0.5f;
        final float div = 10f;
        return multi/(   (  Math.abs(this.x - object.x) )/10f    + 0.5f) + multi/(   (  Math.abs(this.y - object.y) )/10f    + 0.5f);
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

//    private boolean moveIntersect(GameplayObject object, float leeway) {
//
//        float thresholdX = this.width/2 + object.width/2;
//        float thresholdY = this.height/2 + object.height/2;
//
//        float thisBottom = this.y - this.height/2;
//        float thisTop = this.y + this.height/2;
//        float thisLeft = this.x - this.width/2;
//        float thisRight = this.x + this.width/2;
//
//        float platformBottom = object.y - object.height/2;
//        float platformTop = object.y + object.height/2;
//        float platformLeft = object.x - object.width/2;
//        float platformRight = object.x + object.width/2;
//
//        boolean betweenForX = thisBottom < platformTop && thisTop > platformBottom;
//        boolean betweenForY = thisLeft < platformRight && thisRight > platformLeft;
//
//        //Establishes several variables to be used for collision checks.
//
//        boolean addIntersect = false;
//
//        if(Math.min(this.x,previousX) + thresholdX - leeway <= object.x && object.x <= Math.max(this.x,previousX) + thresholdX + leeway && betweenForX) return true;
//
//        return false;
//    }


    public void tick(Scene scene) {

        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            scene.restart = true;
            return;
        }

        float flashing1 = (float)Math.sin(scene.getFrame()/20)/4f + 0.75f;
        float flashing2 = (float)Math.sin(scene.getFrame()/4)/5f + 0.2f;

        switch(this.extraJumps) {
            case 0: {
                this.tintRed = 0.8f;
                this.tintGreen = 0.7f;
                this.tintBlue = 0.7f;
                break;
            }
            case 1: {
                this.tintRed = 1;
                this.tintGreen = 1;
                this.tintBlue = 1;
                break;
            }
            case 2: {
                this.tintBlue = 0;
                this.tintRed = 0;
                this.tintGreen = flashing1;
                break;
            }
            case 3: {
                this.tintBlue = flashing1;
                this.tintRed = 0;
                this.tintGreen = 0;
                break;
            }
            case 4: {
                this.tintBlue = 0;
                this.tintRed = flashing1;
                this.tintGreen = 0;
                break;
            }
            default: {
                this.tintBlue = flashing1*1.4f;
                this.tintRed = flashing2*3.2f;
                this.tintGreen = 0;
            }
        }


        if(Gdx.input.isKeyPressed(Input.Keys.A)) moveX(true);
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) moveX(false);

        boolean tapUp = (!holdingUp && Gdx.input.isKeyPressed(Input.Keys.W));
        boolean tapDown = (!holdingDown && Gdx.input.isKeyPressed(Input.Keys.S));
        /*
        If the player was not holding the W key the last time it was evaluated, but is holding it this frame, it is
        considered as a "tap" or a "press", as opposed to a hold, and stays true for only one frame.
        This is an important variable to have, since executing the player's double jump will not be when they hold
        the jump button, but when they press it in midair again.
        */

        holdingUp = Gdx.input.isKeyPressed(Input.Keys.W); //Gets if the player is holding up or not.
        holdingDown = Gdx.input.isKeyPressed(Input.Keys.S); //Gets if the player is holding down or not.

        /*
        Steps the player's movement, moving them up and down and constantly making their y velocity do down due to gravity.
        Much like in Undertale's Blue Soul mode or other games with platforms, holding the jump key (W) allows for the
        player to jump higher, and releasing it allows for the player to quickly fall down when they want.
        This is simulated by decreasing the gravity when the jump key is held, and increasing it when it is released.
        The jump key boolean is supplied through the method, and overrides the inherited method.
        */

        if(!this.sitting) {
            if(!gravityInvert) yVelocity -= holdingUp ? gravityStrength / 4.5f : gravityStrength / 1.5f;
            else yVelocity -= holdingDown ? -gravityStrength / 4.5f : -gravityStrength / 1.5f;
        }

        //The xVelocity variable constantly decelerates, as shown by the division. This is to slow it down to a halt when
        //the player releases the A/D keys in a smooth and gradual way.
        // Furthermore, it is capped at a speed of seven, as shown by the clamp values (may not be a final feature to cap it).
        xVelocity = xVelocity/1.16f;


        previousX = x;
        previousY = y;
        //Then, adds the velocities to the positions, if there is not a platform in the way while it is sitting.
        //The previous x and y positions are also saved.
        if(!intersectingPredictAnyPlatform(scene, x+xVelocity, y+yVelocity) || !sitting) {
            y += yVelocity;
            x += xVelocity;
        }

        registerSit(((y <= floor) && !gravityInvert && scene.hasFloor) || ((y >= ceil) && gravityInvert && scene.hasCeiling) );
        //If the player is on the floor with normal gravity, set them to resting. Otherwise, makes it false.
        //If the player is on the ceiling with inverted gravity, set them to resting. Otherwise, makes it false.

        intersectCount = new ArrayList<Platform>();

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

            //Traditional collision:
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
                if(!gravityInvert) {
                    registerSit(true);
                    x.moveBind = this;
                }
            }
            if(Math.min(this.y,previousY) + thresholdY - leeway <= x.y && x.y <= Math.max(this.y,previousY) + thresholdY + leeway && betweenForY) {
                this.y = Math.min(this.y, x.y - thresholdY - 0.5f);
                this.yVelocity = Math.min(0,this.yVelocity);

                if(gravityInvert) {
                    registerSit(true);
                    x.moveBind = this;
                }
            }

            //Collision to handle phasing:
            if(intersecting(x, -2f)) {
                intersectCount.add(x);
            }
        }



        if(!intersectCount.isEmpty()) {
            float totalX = 0;
            float totalY = 0;
            for(Platform x : intersectCount) {
                totalX += 12*(this.x - x.x)/Math.max(x.width,40);
                totalY += 12*(this.y - x.y)/Math.max(x.height,40);
            }
            this.x += totalX;
            this.xVelocity += totalX/12f;

            this.y += totalY;
            this.yVelocity += totalY/12f;
        }

        for (GravityChange pickup : scene.objectsGravity) { //check for collision on gravity change object and changes gravity
            if (intersecting(pickup) && pickup.exists()) {
                gravityInvert = !gravityInvert;
                pickup.remove(); // Remove pickup from the scene once collected
                break;
            }
        }

        for (Flag flag : scene.objectsFlag) { //check for collision on gravity change object and changes gravity
            if (intersecting(flag) && !flag.raised) {
                flag.raise();
                break;
            }
        }

        for (LevelExit exit : scene.objectsExit) { //check for collision on gravity change object and changes gravity
            if (intersecting(exit)) {
                boolean canExit = true;
                for (Flag flag : scene.objectsFlag) { //check for collision on gravity change object and changes gravity
                    canExit = canExit && flag.raised;
                }
                if(canExit) scene.next = true;
            }
        }

        for (JumpRefill pickup : scene.objectsRefill) { //check for collision on gravity change object and changes gravity
            if (intersecting(pickup) && pickup.exists()) {
                extraJumps++;
                pickup.remove(); // Remove pickup from the scene once collected
                break;
            }
        }


        if(Gdx.input.isKeyPressed(Input.Keys.W) && !gravityInvert) {
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
        else if(Gdx.input.isKeyPressed(Input.Keys.S) && gravityInvert) {
            if(sitting) {
                jump();
            }
            else if(extraJumps > 0 && tapDown) {
                jump();
                extraJumps--;
            }
            //Performs an inverted gravity jump.
        }

        //And for y, it cannot go any lower than the floor (might not be final if there is a void or a place to fall).
        //And they cannot go any higher than the ceiling.
        if(scene.hasFloor) {
            y = Math.max(y, floor);
            if(y == floor) yVelocity = Math.max(yVelocity,0);
        }
        else if(y < -Global.HEIGHT/2f-height/2f-200) scene.dead = true;
        if(scene.hasCeiling) {
            y = Math.min(y, ceil);
            if(y == ceil) yVelocity = Math.min(yVelocity,0);
        }
        else if(y > Global.HEIGHT/2f+height/2f+200) scene.dead = true;

        x = Math.clamp(x, -Global.WIDTH/2f+width/2f, Global.WIDTH/2f-width/2f);
        //y = Math.min(y, ceil);
    }

    public void draw(SpriteBatch batch) {
        batch.setColor(this.tintRed, this.tintGreen, this.tintBlue, this.opacity);
        float invertY = gravityInvert ? -1 : 1;
        float invertX = movedLeft ? -1 : 1;
        batch.draw(this.image, this.x - (int)(this.width/2.0)*invertX + (int)(Global.WIDTH/2), this.y - (int)(this.height/2.0)*invertY + (int)(Global.HEIGHT/2), this.width*invertX, invertY*this.height );
    }

    public void moveX(boolean isLeft) { //Increases the x velocity either left or right, depending on the input.
        xVelocity += (width/50f) * (isLeft ? -1 : 1);
        movedLeft = isLeft;
    }
    public void jump() {
        yVelocity = jumpPower * 10 * (gravityInvert ? -1 : 1);
    }
}
