package com.folder.Object.MagicEffect.Effect;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;
import com.folder.Object.MagicEffect.MagicEffect;
import com.folder.Screen.GameScreen;

public class FireShield extends MagicEffect {
    public FireShield(GameScreen screen) {
        super(screen);

        Array<TextureRegion> frames = new Array<>();
        setBounds(0, 0, 128 / Boot.PPM, 128 / Boot.PPM);
        for (int i = 0; i < 8; i++)
            frames.add(new TextureRegion(screen.getEffectAtlas().findRegion("FireShield"), i * 128, 0, 128, 128));
        animation = new Animation<TextureRegion>(1 / 5f, frames);
        frames.clear();
    }

    public void update(float deltaTime, float x, float y) {
        stateTime += deltaTime;
        setBounds(x - getWidth() / 2, y - getHeight() / 2, 96 / Boot.PPM, 96 / Boot.PPM);

        TextureRegion region;
        region = animation.getKeyFrame(stateTime, true);
        setRegion(region);
    }

}
