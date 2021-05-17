package mx.tec.astral.flytomars.Tools;

/*
    Desc. : Clase para implementar asteroides de manera visual (no forma parte del juego en si)
    Author: Israel Sanchez
    Created: 13-05-2021
*/

import com.badlogic.gdx.graphics.Texture;

public class Asteroid extends ObjetoGalaxia{

    public Asteroid(Texture textura, float x, float y){
        super(textura, x, y);
        DX = 8;
        DY = 4;
    }

}
