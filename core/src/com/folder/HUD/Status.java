package com.folder.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.folder.Boot;

public class Status {

    private Texture texture;

    public Stage stage;
    private Viewport viewport;

    private static int score;

    static Label scoreLabel;
    Label textLabel;

    private UI ui;

    public Status(SpriteBatch batch) {
        viewport = new FitViewport(Boot.screenWidth, Boot.screenHeight);
        stage = new Stage(viewport, batch);

        texture = new Texture("hud.png");
        ui = new UI(texture, -50, Boot.screenHeight - (float) texture.getHeight() / 3, Boot.screenWidth * 3 / 8, Boot.screenHeight / 4);

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("HalloweenSlimePersonalUse-4B80D.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        BitmapFont fontFree = generator.generateFont(parameter);

        scoreLabel = new Label(String.format("%6d", score), new Label.LabelStyle(fontFree, Color.WHITE));
        textLabel = new Label("This is a test", new Label.LabelStyle(fontFree, Color.WHITE));

        table.add(scoreLabel).padTop(75).padRight(830);
        table.row();
        table.add(textLabel).padRight(800);
        stage.addActor(table);
        stage.addActor(ui);

    }

    public static void addScore(int scoreAdded) {
        score += scoreAdded;
        scoreLabel.setText(String.format("%06d", score));
    }

    public void dispose() {
        texture.dispose();
        stage.dispose();
    }
}
