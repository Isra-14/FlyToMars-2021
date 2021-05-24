package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Pantallas.Pantalla;
/*
Author(s) Alejandro Quintana / Israel Sanchez
Hace una carga previa antes de entrar al menu
 */

public class PantallaSplash extends Pantalla
{
//    private SpriteBatch batch;
//    private Texture texturaSplash;
    private Juego p;
//    private float tiempoEspera = 2f; //2 segundos

    private Stage pruebas;
    public static Texture texturaLogo;

    public PantallaSplash(Juego juego)
    {
        super();
        this.p = juego;
    }

    @Override
    public void show() {
        texturaLogo = new Texture("FondoBienvenida.png");
        texturaLogo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Image image = new Image(texturaLogo);
        Image image2 = new Image(texturaLogo);

        image.setFillParent(true);
        image2.setFillParent(true);

        image.setScaling(Scaling.fillY);
        image2.setScaling(Scaling.fillY);

        pruebas = new Stage();

        pruebas.addActor(image);

        image.addAction(Actions.sequence(Actions.alpha(0),
                Actions.fadeIn(3.0f), Actions.delay(2),
                Actions.fadeOut(3.0f), Actions.delay(1),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenu(p));
                    }
                })));

    }

    @Override
    public void render(float delta)
    {
        //borra _todo lo que hay dentro
        borrarPantalla(0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        pruebas.act();
        pruebas.draw();


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
//        texturaSplash.dispose();
        batch.dispose();
        texturaLogo.dispose();
        pruebas.dispose();

    }
}
