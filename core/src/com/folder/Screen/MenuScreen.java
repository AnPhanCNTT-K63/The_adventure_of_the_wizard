package com.folder.Screen;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.folder.Boot;

import com.folder.Object.*;
import com.folder.Object.Enemy.Enemy;
import com.folder.Tool.*;

public class MenuScreen implements Screen {
    private SpriteBatch batch;

    private Viewport viewport;
    private Stage stage;

    private TextButton exit;
    private TextButton play;
    private TextButton setting;

    private Boot boot;


    public MenuScreen(Boot boot) {
        this.boot = boot;
        batch = boot.batch;
        viewport = new FitViewport(Boot.screenWidth, Boot.screenHeight);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setDebug(false);
        table.setFillParent(true);
        table.top();

        Skin skin = new Skin(Gdx.files.internal("skin/skin.json"));

        play = new TextButton("RESUME", skin);
        setting = new TextButton("SETTING", skin);
        exit = new TextButton("EXIT", skin);

        play.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                MenuHandle.escape = true;
            }
        });

        setting.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        exit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(play).width(300).height(100);
        table.row();
        table.add(setting).width(300).height(100);
        table.row();
        table.add(exit).width(300).height(100);
        table.padTop(300);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (MenuHandle.escape) {
            stage.dispose();
            boot.setScreen(new GameScreen(boot));
            MenuHandle.escape = false;
        }

        stage.draw();

    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        batch.dispose();
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
}
