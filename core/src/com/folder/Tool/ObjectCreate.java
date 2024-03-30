package com.folder.Tool;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.folder.Boot;
import com.folder.GameScreen;
import com.folder.Object.*;

import java.util.LinkedList;

public class ObjectCreate {

    LinkedList<Werewolves> werewolves;
    LinkedList<TreeType1> treeType1s;

    public ObjectCreate(GameScreen screen) {
        TiledMap map = screen.getMap();

        for (RectangleMapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class))
            new Ground(screen, object);

        for (RectangleMapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class))
            new Wall(screen, object);

        for (RectangleMapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class))
            new TouchPointOnAir(screen, object);

        for (RectangleMapObject object : map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class))
            new HangPoint(screen, object);

        werewolves = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(16).getObjects().getByType(RectangleMapObject.class))
            werewolves.add(new Werewolves(screen, object.getRectangle().x / Boot.PPM, object.getRectangle().y / Boot.PPM));

        treeType1s = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(17).getObjects().getByType(RectangleMapObject.class))
            treeType1s.add(new TreeType1(screen, object.getRectangle().x / Boot.PPM, object.getRectangle().y / Boot.PPM));
    }

    public LinkedList<Enemy> getEnemyList() {
        LinkedList<Enemy> enemies = new LinkedList<>();
        enemies.addAll(werewolves);
        return enemies;
    }

    public LinkedList<AnimationTileSet> getAnimationTileSet() {
        LinkedList<AnimationTileSet> animationTileSets = new LinkedList<>();
        animationTileSets.addAll(treeType1s);
        return animationTileSets;
    }
}
