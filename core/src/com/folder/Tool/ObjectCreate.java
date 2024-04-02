package com.folder.Tool;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.folder.AnimationTileSet.*;
import com.folder.Boot;
import com.folder.GameScreen;
import com.folder.Object.*;

import java.util.LinkedList;

public class ObjectCreate {
    LinkedList<Werewolves> werewolves;
    LinkedList<BossElementEarth> bossElementEarths;

    LinkedList<TreeType1> treeType1s;
    LinkedList<TreeType2> treeType2s;
    LinkedList<FlowerType1> flowerType1s;
    LinkedList<FlowerType2> flowerType2s;
    LinkedList<Brush> brushes;

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

        treeType2s = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(18).getObjects().getByType(RectangleMapObject.class))
            treeType2s.add(new TreeType2(screen, object.getRectangle().x / Boot.PPM, object.getRectangle().y / Boot.PPM));

        flowerType1s = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(19).getObjects().getByType(RectangleMapObject.class))
            flowerType1s.add(new FlowerType1(screen, object.getRectangle().x / Boot.PPM, object.getRectangle().y / Boot.PPM));

        flowerType2s = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(20).getObjects().getByType(RectangleMapObject.class))
            flowerType2s.add(new FlowerType2(screen, object.getRectangle().x / Boot.PPM, object.getRectangle().y / Boot.PPM));

        brushes = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(21).getObjects().getByType(RectangleMapObject.class))
            brushes.add(new Brush(screen, object.getRectangle().x / Boot.PPM, object.getRectangle().y / Boot.PPM));

        bossElementEarths = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(22).getObjects().getByType(RectangleMapObject.class))
            bossElementEarths.add(new BossElementEarth(screen, object.getRectangle().x / Boot.PPM, object.getRectangle().y / Boot.PPM));
    }

    public LinkedList<Enemy> getEnemyList() {
        LinkedList<Enemy> enemies = new LinkedList<>();
        enemies.addAll(werewolves);
        enemies.addAll(bossElementEarths);
        return enemies;
    }

    public LinkedList<AnimationTileSetCreate> getAnimationTileSet() {
        LinkedList<AnimationTileSetCreate> animationTileSets = new LinkedList<>();
        animationTileSets.addAll(treeType1s);
        animationTileSets.addAll(treeType2s);
        animationTileSets.addAll(flowerType1s);
        animationTileSets.addAll(flowerType2s);
        animationTileSets.addAll(brushes);
        return animationTileSets;
    }
}
