package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.first.game.FirstGame;

public class Terrain extends Actor {
    static int dx = 0, dy = 0;
    protected Texture texture;
    boolean touchable;
    String type;
    public Terrain(float x, float y) {
        this.setPosition(x , y);
        texture = new Texture(Gdx.files.internal("Textures/tree.png"));
        System.out.println(texture.getWidth());
        setBounds(x,y,texture.getWidth(), texture.getHeight());
    }
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, this.getX()- getWidth(), this.getY() - getHeight());
    }
}
