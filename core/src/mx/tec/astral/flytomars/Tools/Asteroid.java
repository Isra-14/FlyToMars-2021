package mx.tec.astral.flytomars.Tools;

/*
    Desc. : Clase para implementar asteroides de manera visual (no forma parte del juego en si)
    Author: Israel Sanchez
    Created: 13-05-2021
*/

import com.badlogic.gdx.graphics.Texture;

public class Asteroid extends Objeto{
    private float DX = 8;
    private float DY = 2;

    private boolean posX;
    private boolean posY;

    public Asteroid(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public void mover(){
        /*
*               if (posX == true)
                {
                    x += speed;
                    if(x >= 1720) { posX = !posX; }
                }
                if(posX == false)
                {
                    x -= speed;
                    if(x <= 0) { posX = !posX; }
                }
                if (posY == true)
                {
                    y += speed;
                    if (y >= 870) { posY = !posY; }
                }
                if (posY == false)
                {
                    y -= speed;
                    if (y <= 0) { posY = !posY; }
                }
        *
        * */
        if(posX){
            sprite.setX(sprite.getX() + DX);
            if (sprite.getX() >= 1280 - sprite.getWidth())
                posX = !posX;
        }
        if (!posX){
            sprite.setX(sprite.getX() - DX);
            if (sprite.getX() <= 0)
                posX = !posX;
        }
        if(posY){
            sprite.setY(sprite.getY() + DY);
            if (sprite.getY() >= 720 - sprite.getHeight())
                posY = !posY;
        }
        if (!posY){
            sprite.setY(sprite.getY() - DY);
            if (sprite.getY() <= 0)
                posY = !posY;
        }

    }
}
