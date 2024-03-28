package com.folder.Object;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.GameScreen;

public abstract class InteractiveObject {
    protected TiledMap tiledMap;
    protected Fixture fixture;

    protected World world;
    protected Body body;
    protected Rectangle rect;
    protected RectangleMapObject object;

    public InteractiveObject(GameScreen screen, RectangleMapObject object) {
        tiledMap = screen.getMap();
        world = screen.getWorld();

        this.object = object;

        rect = object.getRectangle();

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Boot.PPM, (rect.getY() + rect.getHeight() / 2) / Boot.PPM);
        body = world.createBody(bodyDef);

        shape.setAsBox(rect.getWidth() / 2 / Boot.PPM, rect.getHeight() / 2 / Boot.PPM);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);


    }

    public void setCategory(short bit) {
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }
}
