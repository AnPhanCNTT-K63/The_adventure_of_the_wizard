package com.folder.Object;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.folder.GameScreen;

public abstract class Enemy extends Sprite {
    protected World world;
    protected Body body;
    protected Vector2 velocity;

    public Enemy(GameScreen screen) {
        world = screen.getWorld();
        velocity = new Vector2(0, 0);
        setUpBody();
    }

    public abstract void setUpBody();
    public abstract void update(float deltaTime);

}
