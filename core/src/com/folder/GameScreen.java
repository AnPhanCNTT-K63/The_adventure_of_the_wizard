package com.folder;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.folder.HUD.Status;
import com.folder.Object.*;
import com.folder.Tool.AnimationTileSetCreate;
import com.folder.Tool.CollisionHandle;
import com.folder.Tool.ObjectCreate;


public class GameScreen implements Screen {

    private Status hud;

    public SpriteBatch batch;

    public static OrthographicCamera camera;
    public Viewport gamePort;

    private World world;

    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Box2DDebugRenderer debug;

    private TextureAtlas atlas;
    private TextureAtlas enemyAtlas;
    private TextureAtlas AnimationTileSetAtlas;
    private TextureAtlas bossAtlas;

    //Object
    private ObjectCreate object;
    //ObjectTest objectTest;

    MainCharacter player;


    public GameScreen() {
        world = new World(new Vector2(0, -10f), false);

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        //gamePort = new StretchViewport(Boot.screenWidth / Boot.PPM, Boot.screenHeight / Boot.PPM, camera);
        gamePort = new StretchViewport(Boot.screenWidth / Boot.PPM - 1040 / Boot.PPM, Boot.screenHeight / Boot.PPM - 660 / Boot.PPM, camera);
        hud = new Status(batch);

        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("bossMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / Boot.PPM);

        debug = new Box2DDebugRenderer();

        atlas = new TextureAtlas("wizard.atlas");
        enemyAtlas = new TextureAtlas("werewolves.atlas");
        AnimationTileSetAtlas = new TextureAtlas("AnimationTileset.atlas");
        bossAtlas = new TextureAtlas("ElementEnemy.atlas");

        // object = new ObjectTest(this);


        player = new MainCharacter(this);

        object = new ObjectCreate(this);

        world.setContactListener(new CollisionHandle());
    }

    public void update(float deltaTime) {
        Status.addScore(50);

        world.step(1 / 60f, 6, 2);

        camera.position.x = player.getBody().getPosition().x;
        //camera.position.y = player.getBody().getPosition().y;

        if (camera.position.x <= 440 / Boot.PPM)
            camera.position.x = 440 / Boot.PPM;

        camera.update();

        mapRenderer.setView(camera);

        player.update(deltaTime);

        for (Enemy enemy : object.getEnemyList())
            enemy.update(deltaTime);

        for (AnimationTileSetCreate animationTileSet : object.getAnimationTileSet())
            animationTileSet.update(deltaTime);

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();
        hud.stage.draw();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        player.draw(batch);

        for (Enemy enemy : object.getEnemyList())
            enemy.draw(batch);

        for (AnimationTileSetCreate animationTileSet : object.getAnimationTileSet())
            animationTileSet.draw(batch);

        batch.end();

        //debug.render(world, camera.combined);
    }


    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return tiledMap;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TextureAtlas getEnemyAtlas() {
        return enemyAtlas;
    }

    public TextureAtlas getAnimationTileSetAtlas() {
        return AnimationTileSetAtlas;
    }

    public TextureAtlas getBossAtlas() {
        return bossAtlas;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, true);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        world.dispose();
        batch.dispose();
        hud.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public OrthographicCamera getCamera() {
        return camera;
    }

}
