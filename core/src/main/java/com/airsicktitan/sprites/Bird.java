package com.airsicktitan.sprites;

import com.airsicktitan.game.FlappyDemo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    private final Vector3 pos;
    private final Vector3 vel;
    private static final int GRAVITY = -5;
    private static  final int MOVEMENT = 100;

    //private Texture bird;
    private final float birdHeight;
    private final Rectangle bounds;

    private final Animation birdAnimation;
    Texture texture = new Texture("birdanimation.png");

    private final Sound flap;

    public Bird(int x, int y) {
        pos = new Vector3(x, y, 0);
        vel = new Vector3(0, 0, 0);

        //bird = new Texture("bird.png");
        birdHeight = texture.getHeight();
        bounds = new Rectangle(x, y, (float) texture.getWidth() / 3, texture.getHeight());

        birdAnimation = new Animation(new TextureRegion(texture), 3, .2F);
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public void update(float deltaTime) {
        // Stops adding gravity if the bird is already on the ground
        birdAnimation.update(deltaTime);
        if(pos.y > 0) {
            vel.add(0, GRAVITY, 0);
        }
        vel.scl(deltaTime);
        pos.add(0, vel.y, 0);
        pos.add(MOVEMENT * deltaTime, vel.y, 0);

        // Prevents the bird from going below the window
        if(pos.y < 0) {
            pos.y = 0;
        }

        // Prevents the bird from going above the window
        if(pos.y + birdHeight > (float) FlappyDemo.HEIGHT / 2) {

            pos.y = (float) FlappyDemo.HEIGHT / 2 - birdHeight;
        }

        vel.scl(1 / deltaTime);
        bounds.setPosition(pos.x, pos.y);
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public Vector3 getPos() {
        return pos;
    }

    public void jump() {
        vel.y = 100;
        flap.play(.3F);

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
        flap.dispose();
    }
}
