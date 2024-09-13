package com.airsicktitan.states;

import com.airsicktitan.game.FlappyDemo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {
    private final Texture bg;
    private final Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sprite) {
        sprite.begin();
        sprite.draw(bg, 0, 0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
        sprite.draw(playBtn, (float) FlappyDemo.WIDTH / 2 - ((float) playBtn.getWidth() / 2), (float) FlappyDemo.HEIGHT / 2);
        sprite.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        playBtn.dispose();
    }
}
