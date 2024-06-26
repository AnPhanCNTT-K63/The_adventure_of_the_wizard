package com.folder.Object;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.Screen.GameScreen;


public class ObjectTest {
    private TiledMap tiledMap;

    private World world;
    private Body body;

    public ObjectTest(GameScreen screen) {
        tiledMap = screen.getMap();
        world = screen.getWorld();

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        for (RectangleMapObject object : tiledMap.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Boot.PPM, (rect.getY() + rect.getHeight() / 2) / Boot.PPM);
            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / Boot.PPM, rect.getHeight() / 2 / Boot.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Boot.OBJECT_TEST_BIT;

            body.createFixture(fixtureDef);
        }
    }

}
