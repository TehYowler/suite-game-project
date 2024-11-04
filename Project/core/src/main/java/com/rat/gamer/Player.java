package com.rat.gamer;

import com.badlogic.gdx.graphics.Texture;

public class Player extends GameplayObject {

    private final float jumpPower = 1.1f; //How powerful the player's jumps are

    public final float floor; //A variable where the floor to rest at is.

    public Player(float x, float y, Texture image, int width, int height, boolean useVelocity, float gravityStrength) {
        super(x, y, image, width, height, useVelocity, gravityStrength);
        floor = height/2 - (float)Global.HEIGHT/2;
    }


    public void tick(boolean holdingUp) {
        /*
        Steps the player's movement, moving them up and down and constantly making their y velocity do down due to gravity.
        Much like in Undertale's Blue Soul mode or other games with platforms, holding the jump key (W) allows for the
        player to jump higher, and releasing it allows for the player to quickly fall down when they want.
        This is simulated by decreasing the gravity when the jump key is held, and increasing it when it is released.
        The jump key boolean is supplied through the method, and overrides the inherited method.
        */
        yVelocity -= holdingUp ? gravityStrength/4.5f : gravityStrength/1.5f;

        //The xVelocity variable constantly decelerates, as shown by the division. This is to slow it down to a halt when
        //the player releases the A/D keys in a smooth and gradual way.
        // Furthermore, it is capped at a speed of seven, as shown by the clamp values (may not be a final feature to cap it).
        xVelocity = Math.clamp((float)(xVelocity/1.16),-7,7);

        //Then, adds the velocities to the positions.
        y += yVelocity;
        x += xVelocity;

        //And for y, it cannot go any lower than the floor (might not be final if there is a void or a place to fall).
        y = Math.max(y, floor);
    }
    public void moveX(boolean isLeft) { //Increases the x velocity either left or right, depending on the input.
        xVelocity += 1.0 * (isLeft ? -1 : 1);
    }
    public void jump() { //Performs a jump by setting the y velocity upwards.
        yVelocity = jumpPower * 10;
    }
}
