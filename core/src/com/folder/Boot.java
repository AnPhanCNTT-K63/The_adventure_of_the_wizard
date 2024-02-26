package com.folder;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class Boot extends Game {

    public static final float PPM = 100f;

    public static final float screenWidth = 1280; //1920
    public static final float screenHeight = 720; //1080

    public static final short GROUND_BIT = 1;
    public static final short CHARACTER_BIT = 2;
    public static final short ENEMY_BIT = 4;

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

    }

}
