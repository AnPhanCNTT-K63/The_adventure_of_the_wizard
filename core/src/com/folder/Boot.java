package com.folder;

import com.badlogic.gdx.Game;

public class Boot extends Game {

    public static final float PPM = 100f;

    public static final float screenWidth = 1920;
    public static final float screenHeight = 1080;

    public static final short NONE = 1;
    public static final short GROUND_BIT = 1;
    public static final short CHARACTER_BIT = 2;
    public static final short ENEMY_BIT = 4;
    public static final short ATTACK_BIT = 8;
    public static final short ENEMY_ATTACK_BIT = 16;
    public static final short WALL_BIT = 32;
    public static final short TOUCH_POINT_ON_AIR = 64;
    public static final short HANG_POINT_BIT = 128;
    public static final short OBJECT_TEST_BIT = 12;

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
