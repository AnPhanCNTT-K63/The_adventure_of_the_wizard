package com.folder;

import com.badlogic.gdx.graphics.Texture;

public class InventoryItem {
    private String name;
    private Texture texture; // Ảnh đại diện cho vật phẩm
    private int quantity; // Số lượng vật phẩm

    public InventoryItem(String name, Texture texture, int quantity) {
        this.name = name;
        this.texture = texture;
        this.quantity = quantity;
    }

    // Getter và Setter cho các thuộc tính
    public String getName() {
        return name;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

