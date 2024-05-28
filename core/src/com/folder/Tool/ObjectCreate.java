package com.folder.Tool;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.folder.AnimationTileSet.*;
import com.folder.Boot;

import com.folder.Object.Enemy.Boss.BossElementEarth;
import com.folder.Object.Enemy.Creep.HellDog;
import com.folder.Object.Enemy.Creep.Werewolves;
import com.folder.Object.Enemy.Enemy;
import com.folder.Object.InteractiveObject.*;
import com.folder.Object.MainCharacter;
import com.folder.Screen.GameScreen;
import jdk.javadoc.internal.doclets.toolkit.taglets.snippet.Style;

import java.util.LinkedList;

public class ObjectCreate {
    private TiledMap map;
    private GameScreen screen;

    private LinkedList<Werewolves> werewolves;
    private LinkedList<HellDog> hellDogs;
    private LinkedList<BossElementEarth> bossElementEarths;
    private LinkedList<Ground> grounds;
    private LinkedList<Wall> walls;
    private LinkedList<TouchPointOnAir> touchPointOnAirs;
    private LinkedList<HangPoint> hangPoints;
    private LinkedList<Ladder> ladders;
    private LinkedList<Door> doors;

    private LinkedList<TreeType1> treeType1s;
    private LinkedList<TreeType2> treeType2s;
    private LinkedList<FlowerType1> flowerType1s;
    private LinkedList<FlowerType2> flowerType2s;
    private LinkedList<Brush> brushes;

    public ObjectCreate(GameScreen screen) {
        this.screen = screen;
        map = screen.getMap();
        create();
    }

    public void create() {
        grounds = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class))
            grounds.add(new Ground(screen, object));

        walls = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class))
            walls.add(new Wall(screen, object));

        touchPointOnAirs = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class))
            touchPointOnAirs.add(new TouchPointOnAir(screen, object));

        hangPoints = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class))
            hangPoints.add(new HangPoint(screen, object));

        ladders = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(23).getObjects().getByType(RectangleMapObject.class))
            ladders.add(new Ladder(screen, object));

        doors = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(24).getObjects().getByType(RectangleMapObject.class))
            doors.add(new Door(screen, object));

        werewolves = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(16).getObjects().getByType(RectangleMapObject.class))
            werewolves.add(new Werewolves(screen, object.getRectangle().x / Boot.PPM, object.getRectangle().y / Boot.PPM));

        hellDogs = new LinkedList<>();
        for (RectangleMapObject object : map.getLayers().get(25).getObjects().getByType(RectangleMapObject.class))
            hellDogs.add(new HellDog(screen, object.getRectangle().x / Boot.PPM, object.getRectangle().y / Boot.PPM));

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
        enemies.addAll(hellDogs);
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

    public void clear() {
        for (BossElementEarth bossElementEarth : bossElementEarths)
            bossElementEarth.destroyWhenNextMap();
        bossElementEarths.clear();

        for (Werewolves wolf : werewolves)
            wolf.destroyWhenNextMap();
        werewolves.clear();

        for (HellDog hellDog : hellDogs)
            hellDog.destroyWhenNextMap();
        hellDogs.clear();

        for (Ground ground : grounds)
            ground.destroyBody();
        grounds.clear();

        for (Wall wall : walls)
            wall.destroyBody();
        walls.clear();

        for (TouchPointOnAir touchPointOnAir : touchPointOnAirs)
            touchPointOnAir.destroyBody();
        touchPointOnAirs.clear();

        for (Ladder ladder : ladders)
            ladder.destroyBody();
        ladders.clear();

        for (Door door : doors)
            door.destroyBody();
        doors.clear();

        for (HangPoint hangPoint : hangPoints)
            hangPoint.destroyBody();
        hangPoints.clear();

        for (TreeType1 treeType1 : treeType1s)
            treeType1.isExist = false;
    }

}
