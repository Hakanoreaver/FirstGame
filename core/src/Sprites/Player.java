package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.first.game.FirstGame;

import java.awt.*;

public class Player extends Actor {
    static int dx = 0, dy = 0;
    protected Texture texture;
    boolean grounded;
    public Player(float x, float y) {
        this.setPosition(x , y);
        texture = new Texture(Gdx.files.internal("explosion/tile056.png"));
        setBounds(x,y,50, 50);
    }

    public static void setDy(int change) {
        dy = change;
    }

    public static void setDx(int change) {
        dx = change;
    }


    public void draw(Batch batch, float parentAlpha) {
        checkPhysics();
        batch.draw(texture, this.getX()- getWidth(), this.getY() - getHeight());
        if(!FirstGame.paused) {
            setPosition(getX() + dx, getY() + dy);
        }
    }

    private void checkPhysics() {
        if(!grounded) {
            dy -= 1;
            if(dy > 18) {
                dy = 18;
            }
        }
    }

    public void collide(Rectangle r) {

    }

    public Rectangle getOffsetBounds() {
        return new Rectangle((int) this.getX() + dx, (int) this.getY() + dy, (int) getWidth(),(int) getHeight());
    }

    public void setGrounded(boolean g) {
        grounded = g;
    }

    public void jump() {
        dy = 10;
    }

    public void attack() {

    }

}
