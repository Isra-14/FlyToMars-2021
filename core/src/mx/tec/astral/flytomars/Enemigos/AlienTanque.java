package mx.tec.astral.flytomars.Enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import mx.tec.astral.flytomars.Tools.Objeto;

public class AlienTanque extends Objeto {
    public AlienTanque(Texture textura, float x, float y)
    {
        super(textura, x, y);
    }

    public void moverHorizontal(float dx_paso_alien_tanque) {
        sprite.setX(sprite.getX() + dx_paso_alien_tanque);
    }

    public Sprite getSprite(){return sprite;
    }
    public float getX(){
        return sprite.getX();
    }
    public void setX(float dx){
        sprite.setX(dx);
    }
}
