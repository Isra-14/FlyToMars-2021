package mx.tec.astral.flytomars.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
Representa objetos en el juego (Hero, Alien)
Autor: Misael Delgado
 */
public class Objeto {

    //Protected para que las clases que heredan puedan acceder a la variable
    protected Sprite sprite; //imagen, posicion

    //Constructor
    public Objeto(Texture textura,float x, float y){
        sprite = new Sprite(textura);
        sprite.setPosition(x, y);
    }

    //Constructor por default
    public Objeto(){
    }

    //Dibujar el objeto -> llamar dentro del begin y end <-
    public void render (SpriteBatch batch){
        sprite.draw(batch);
    }
}