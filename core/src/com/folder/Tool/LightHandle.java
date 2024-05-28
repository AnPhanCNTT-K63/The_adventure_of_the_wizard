package com.folder.Tool;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.folder.Object.MainCharacter;
import com.folder.Screen.GameScreen;

public class LightHandle {
    private RayHandler rayHandler;
    private World world;
    private GameScreen screen;
    private Light light;
    private Light enemyLight;

    public LightHandle(GameScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0, 0, 0, 0.2f);

        light = new PointLight(rayHandler, 64, new Color(1, 1, 1, 0.7f), 4, MainCharacter.body.getPosition().x, MainCharacter.body.getPosition().y);

        light.attachToBody(MainCharacter.body);
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }
}
