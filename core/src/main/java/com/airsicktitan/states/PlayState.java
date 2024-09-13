package com.airsicktitan.states;

import com.airsicktitan.game.FlappyDemo;
import com.airsicktitan.sprites.Bird;
import com.airsicktitan.sprites.Tube;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PlayState extends State {
    private final Bird bird;
    private final Texture bg;
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private final Array<Tube> tubes;
    private final Texture ground;
    private final Vector2 groundPos1;
    private final Vector2 groundPos2;
    private static final int GROUND_Y_OFFSET = -70;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        cam.setToOrtho(false, (float) FlappyDemo.WIDTH / 2, (float) FlappyDemo.HEIGHT / 2);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        tubes = new Array<>();
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            bird.jump();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        updateGround();
        bird.update(deltaTime);
        cam.position.x = bird.getPos().x + 80;

        for(int i = 0; i < tubes.size; i++) {
            Tube tube = tubes.get(i);

            if(cam.position.x - cam.viewportWidth / 2 > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if(tube.collides(bird.getBounds())){
                gsm.set(new PlayState(gsm));
            }
        }

        if(bird.getPos().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gsm.set(new PlayState(gsm));
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sprite) {
        sprite.setProjectionMatrix(cam.combined);
        sprite.begin();
        sprite.draw(bg, cam.position.x - cam.viewportWidth / 2, 0);
        sprite.draw(bird.getTexture(), bird.getPos().x, bird.getPos().y);
        for(Tube tube : tubes){
            sprite.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sprite.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sprite.draw(ground, groundPos1.x, groundPos1.y);
        sprite.draw(ground, groundPos2.x, groundPos2.y);
        sprite.end();
    }

    @Override
    public void dispose() {
        bird.dispose();
        bg.dispose();
        ground.dispose();

        for(Tube tube : tubes) {
            tube.dispose();
        }
    }

    private void updateGround() {
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }

        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
}
