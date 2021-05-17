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
//        super(textura, x, y);
        sprite = new Sprite(textura);
        posX = x <= 640;
        posY = y <= 360;

        if ( posX ) {
            sprite.setX(-80);
            Gdx.app.log("posX<",  Float.toString(sprite.getX()));
        } else {
            sprite.setX(1300);
            Gdx.app.log("posX>",  Float.toString(sprite.getX()));
        }
        sprite.setY(y);
        Gdx.app.log("pos(X,Y)",  sprite.getX() + " " + sprite.getY());
    }

    public void mover(){
        if(posX){
            sprite.setX(sprite.getX() + DX);
//            if (sprite.getX() >= 1280 - sprite.getWidth())
//                posX = !posX;
        }
        if (!posX){
            sprite.setX(sprite.getX() - DX);
//            if (sprite.getX() <= 0)
//                posX = !posX;
        }
        if(posY){
            sprite.setY(sprite.getY() + DY);
//            if (sprite.getY() >= 720 - sprite.getHeight())
//                posY = !posY;
        }
        if (!posY){
            sprite.setY(sprite.getY() - DY);
//            if (sprite.getY() <= 0)
//                posY = !posY;
        }

    }

    public Sprite getSprite(){
        return sprite;
    }
}
