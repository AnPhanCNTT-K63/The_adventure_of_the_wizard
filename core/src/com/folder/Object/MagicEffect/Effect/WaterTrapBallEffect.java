package com.folder.Object.MagicEffect.Effect;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.folder.Boot;

import com.folder.Object.MagicEffect.MagicEffect;
import com.folder.Screen.GameScreen;

public class WaterTrapBallEffect extends MagicEffect {
    public static float x;
    public static float y;

    public WaterTrapBallEffect(GameScreen screen) {
        super(screen);

        setBounds(0, 0, 128 / Boot.PPM, 128 / Boot.PPM);

        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 14; i++)
            frames.add(new TextureRegion(screen.getEffectAtlas().findRegion("WaterTrapBall"), i * 128, 0, 128, 128));
        animation = new Animation<TextureRegion>(1 / 6f, frames);
        frames.clear();
    }

    @Override
    public void update(float deltaTime, float posX, float posY) {
        stateTime += deltaTime;
        setBounds(posX - getWidth() / 2, posY - getHeight() / 2, 256 / Boot.PPM, 256 / Boot.PPM);

        TextureRegion region;
        region = animation.getKeyFrame(stateTime, true);
        setRegion(region);
    }
}
