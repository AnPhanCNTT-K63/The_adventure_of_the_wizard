package com.folder.Object;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.folder.Boot;
import com.folder.GameScreen;
import org.w3c.dom.css.Rect;

public class Ground {
    TiledMap tiledMap;

    World world;
    Body body;

    public Ground(GameScreen screen) {
        tiledMap = screen.getMap();

        world = screen.getWorld();

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        for (MapObject object : tiledMap.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / Boot.PPM, (rect.getY() + rect.getHeight() / 2) / Boot.PPM);
            body = world.createBody(bodyDef);

            shape.setAsBox(rect.getWidth() / 2 / Boot.PPM, rect.getHeight() / 2 / Boot.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Boot.GROUND_BIT;
            fixtureDef.filter.maskBits = Boot.CHARACTER_BIT;

            body.createFixture(fixtureDef);
        }
    }
}
