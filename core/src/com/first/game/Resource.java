package com.first.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Resource extends Actor{

    protected Texture texture;
    protected String type;

    public Resource(float x, float y, String type) {
        this.setPosition(x , y);
        texture = new Texture(Gdx.files.internal("Textures/" + type + ".png"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX(), this.getY());
    }

}
