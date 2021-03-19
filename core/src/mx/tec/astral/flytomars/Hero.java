package mx.tec.astral.flytomars;

import com.badlogic.gdx.graphics.Texture;

/*
Personaje controlado por el usuario
Autor : Misael Delgado
 */
public class Hero extends Objeto{
    public Hero(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public void mover (float dx){
        sprite.setX(sprite.getX() + dx);
    }
    public float getX(){
        return sprite.getX();
    }
    public float getWidth(){
        return sprite.getWidth();
    }
}