package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Pantallas.Pantalla;
/*
Author Alejandro Quintana
Hace una carga previa antes de entrar al menu
 */

public class PantallaSplash extends Pantalla
{
    private SpriteBatch batch;
    private Texture texturaSplash;
    private Juego p;
    private  float tiempoEspera = 2f; //2 segundos

    public PantallaSplash(Juego juego)
    {
        super();
        this.p = juego;
        this.batch = new SpriteBatch();
        this.texturaSplash = new Texture("Menu/FondoMenu.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {
        //borra _todo lo que hay dentro
        borrarPantalla(0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //se inicia el batcj
        batch.begin();
        //se carga la imagen de fondo
        batch.draw(texturaSplash, 0, 0);
        batch.end();
        tiempoEspera -= delta;

        //si el tiempo es menor se llama a la clase padre para que haga el cambio
        if (tiempoEspera <= 0)
        {
            //Si se excede le decimos que cambie de pantalla
            p.changeScreen(Juego.PANTALLA_MENU);
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose()
    {
        texturaSplash.dispose();
        batch.dispose();

    }
}
