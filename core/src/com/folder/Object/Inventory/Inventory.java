package com.folder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Inventory {
    private Array<InventoryItem> items;
    private boolean isVisible;
    private Stage stage;
    private Table mainTable;
    private Table itemsTable;
    public static Inventory INSTANCE;

    public Inventory() {
        Skin skin = new Skin(Gdx.files.internal("skin4/craftacular-ui.json"));
        INSTANCE = this;
        items = new Array<>();
        isVisible = false;
        stage = new Stage(new ScreenViewport());
        mainTable = new Table(skin);
        itemsTable = new Table(skin);

        stage.addActor(mainTable);
        mainTable.setVisible(isVisible);

        mainTable.add(new Label("INVENTORY=", skin, "title")).center().pad(10);
        mainTable.row();

        mainTable.add(itemsTable).center();
        mainTable.row();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                Table cell = new Table(skin);
                cell.setBackground(skin.getTiledDrawable("dirt"));
                itemsTable.add(cell).size(64).pad(4);
            }
            itemsTable.row();
        }

        mainTable.setSize(5 * (64 + 4), 5 * (64 + 4) + 50); // Kích thước dựa trên số ô và khoảng cách, cộng thêm chiều cao tiêu đề
        mainTable.setPosition(
                (stage.getViewport().getWorldWidth() - mainTable.getWidth()) / 2,
                (stage.getViewport().getWorldHeight() - mainTable.getHeight()) / 2
        );
    }

    public void addItem(InventoryItem item) {
        items.add(item);
        updateInventoryUI();
    }

    public void removeItem(InventoryItem item) {
        items.removeValue(item, true);
        updateInventoryUI();
    }

    private void updateInventoryUI() {
        int index = 0;
        for (Actor actor : itemsTable.getChildren()) {
            if (actor instanceof Table) {
                Table cell = (Table) actor;
                cell.clear();
                if (index < items.size) {
                    InventoryItem item = items.get(index);
                    Image image = new Image(item.getTexture());
                    image.setScaling(Scaling.fit);
                    cell.add(image).size(64, 64);
                    index++;
                }
            }
        }
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
        mainTable.setVisible(isVisible);
    }

    public void draw() {
        if (isVisible) {
            stage.act();
            stage.draw();
        }
    }

    public Stage getStage() {
        return stage;
    }
}
