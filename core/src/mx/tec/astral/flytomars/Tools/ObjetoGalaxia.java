package mx.tec.astral.flytomars.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
    Desc. : Clase principal del movimiento de objetos de la pantalla principal
    Author: Israel Sanchez
    Created: 14 - 05 - 2021
*/

public class ObjetoGalaxia extends Objeto {
    protected float DX;
    protected float DY;

    protected boolean posX;
    protected boolean posY;

    protected ObjetoGalaxia(Texture textura, float x, float y){
        sprite = new Sprite(textura);
        posX = x <= 640;
        posY = y <= 360;

        if ( posX ) {
            sprite.setX(-80);
        } else {
            sprite.setX(1300);
        }
        sprite.setY(y);
    }

    public void mover(){
        if(posX){
            sprite.setX(sprite.getX() + DX);
        }
        if (!posX){
            sprite.setX(sprite.getX() - DX);
        }
        if(posY){
            sprite.setY(sprite.getY() + DY);
        }
        if (!posY){
            sprite.setY(sprite.getY() - DY);
        }

    }

    public Sprite getSprite(){
        return sprite;
    }
}
