package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Tools.Texto;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
/*
Author Alejandro Quintana
Hace una carga previa antes de entrar al menu
 */

public class PantallaSplash extends Pantalla
{
    private SpriteBatch batch;
    private Texture texturaFondo;
    private Juego p;
    private  float tiempoEspera = 2f; //2 segundos

    //icono alien
    private Texture texturaIcono;
    private Image imagenALien;
    private Stage escena;

    public PantallaSplash(Juego juego)
    {
        super();
        this.p = juego;
        this.batch = new SpriteBatch();
        this.escena = new Stage(new StretchViewport(Pantalla.ANCHO, Pantalla.ALTO, camara));
        Gdx.input.setInputProcessor(escena);
    }

    @Override
    public void show()
    {
        Texturas();
        escena.addActor(imagenALien);
        AlienIcon();


    }

    private void Texturas()
    {
        texturaIcono = new Texture("Menu/Alien.png");
        texturaFondo = new Texture("Menu/FondoMenu.png");

        //se combirte en una imagen
        imagenALien = new Image(texturaIcono);
    }

    //la imagen del alien al principio
    private void AlienIcon()
    {
        imagenALien.setPosition(Pantalla.ANCHO/2 *.7f, Pantalla.ALTO/2*.5f );
        //Se le agrega acccion a la imagen
        imagenALien.addAction(sequence(alpha(0f),parallel(moveBy(200, -150, 1f),fadeIn(1f))));

    }

    @Override
    public void render(float delta)
    {
        escena.act(delta);
        //borra _todo lo que hay dentro
        borrarPantalla(0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //se inicia el batcj
        batch.begin();
        //se carga la imagen de fondo
        batch.draw(texturaFondo, 0, 0);
        batch.end();
        escena.draw();
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
        texturaFondo.dispose();
        batch.dispose();
        escena.dispose();

    }
}
