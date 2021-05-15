package mx.tec.astral.flytomars.Tools;

import com.badlogic.gdx.graphics.Texture;

/*
    Desc. :
    Author: Israel Sanchez
    Created: 15-05-2021
*/
public class Star extends ObjetoGalaxia {
    public Star(Texture textura, float x, float y) {
        super(textura, x, y);
        DX = 10;
        DY = 2;
    }
}
