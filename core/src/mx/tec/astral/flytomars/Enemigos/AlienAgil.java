package mx.tec.astral.flytomars.Enemigos;

import com.badlogic.gdx.graphics.Texture;

import mx.tec.astral.flytomars.Tools.Objeto;

public class AlienAgil extends Objeto {
    public AlienAgil(Texture textura, float x, float y){

        super(textura, x, y);
    }
    public void moverHorizontal(float dx_paso_alien_agil) {
        sprite.setX(sprite.getX() + dx_paso_alien_agil);
    }
    public float getX(){
        return sprite.getX();
    }
    public void setX(float dx){
        sprite.setX(dx);
    }
}
