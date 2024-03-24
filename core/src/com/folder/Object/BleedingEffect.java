package com.folder.Object;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.GameScreen;

public class BleedingEffect extends Sprite {
    SpriteBatch batch;
    Animation<TextureRegion> Bleed;
    float stateTime;

    public static float bleedingDuration;

    public BleedingEffect(GameScreen screen) {
        batch = screen.batch;

        bleedingDuration = 0;

        Array<TextureRegion> frames = new Array<>();
        setBounds(0, 0, 96 / Boot.PPM, 96 / Boot.PPM);
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(screen.getEnemyAtlas().findRegion("Bleed"), i * 48, 0, 48, 48));
        Bleed = new Animation<TextureRegion>(1 / 10f, frames);
        frames.clear();
    }

    public void update(float deltaTime, float x, float y) {
        stateTime += deltaTime;
        setBounds(x - getWidth() / 2, y - getHeight() / 2, 96 / Boot.PPM, 96 / Boot.PPM);

        TextureRegion region;
        region = Bleed.getKeyFrame(stateTime, true);
        setRegion(region);
    }

}
