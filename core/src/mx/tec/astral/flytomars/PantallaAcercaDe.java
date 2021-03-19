package mx.tec.astral.flytomars;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*
Autor Alejandro Quintana
Fly to Mars V 1.0
 */
public class PantallaAcercaDe extends Pantalla {
    private SpriteBatch batch;
    private Texture texturaFondo;
    private Juego juego;

    public PantallaAcercaDe(Juego juego)
    {
        this.juego = juego;
        this.texturaFondo = new Texture("menu/FondoMenu.png");
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        borrarPantalla(0,0,0);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        batch.end();


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
