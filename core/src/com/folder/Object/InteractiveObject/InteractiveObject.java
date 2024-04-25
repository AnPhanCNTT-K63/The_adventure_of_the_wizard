package com.folder.Object.InteractiveObject;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.Screen.GameScreen;


public abstract class InteractiveObject {
    protected Fixture fixture;

    protected World world;
    protected Body body;
    protected Rectangle rect;
    protected RectangleMapObject object;
    protected boolean isDestroy;

    public InteractiveObject(GameScreen screen, RectangleMapObject object) {
        world = screen.getWorld();

        this.object = object;

        this.rect = object.getRectangle();

        isDestroy = false;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Boot.PPM, (rect.getY() + rect.getHeight() / 2) / Boot.PPM);
        body = world.createBody(bodyDef);

        shape.setAsBox(rect.getWidth() / 2 / Boot.PPM, rect.getHeight() / 2 / Boot.PPM);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    public void setCategory(short bit) {
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);
    }

    public void destroyBody() {
        if (!isDestroy) {
            world.destroyBody(body);
            isDestroy = true;
        }
    }

}
