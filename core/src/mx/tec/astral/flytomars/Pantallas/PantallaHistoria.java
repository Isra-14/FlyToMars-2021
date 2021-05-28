package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Tools.Texto;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**======================================================||
 // En esta clase se crea una pequenia historia         ||
 // AUTHOR(S): ALEJANDRO QUINTANA                      ||
 //====================================================*/

public class PantallaHistoria extends Pantalla
{
    Sound efectoCrash;
    private int nivel;

    private Stage escena;

    private Texture texturaFondo;

    private Juego juego;

    public PantallaHistoria(Juego juego){
        super();
        this.juego = juego;
    }

    public void setTexturaFondo(Texture textura)
    {
        this.texturaFondo = textura;
    }

    @Override
    public void show()
    {
        crearPantalla();
        cargarEfecto();
    }

    private void cargarEfecto()
    {
        efectoCrash = Gdx.audio.newSound(Gdx.files.internal("Efectos/Crushingship.wav"));
        efectoCrash.play(.20f);

    }
    public void setNumeroNivel( int nivel){
        this.nivel = nivel;
    }

    private void crearPantalla()
    {
        texturaFondo.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        escena = new Stage();
        Image imagen = new Image(texturaFondo);
        imagen.setFillParent(true);
        imagen.setScaling(Scaling.fillY);
        escena.addActor(imagen);
        imagen.addAction(sequence(alpha(0),
                fadeIn(15),
                fadeOut(3),
                run(new Runnable() {
                    @Override
                    public void run() {
                        cambiarPantalla();
                    }
                })));
    }

    private void cambiarPantalla()
    {
        if (nivel == 1) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaNvl1(juego));
            efectoCrash.stop();
        }
        if (nivel == 2){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaNvl2(juego));
            efectoCrash.stop();
        }
        if (nivel == 3)
        {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaNvl3(juego));
            efectoCrash.stop();
        }

    }

    @Override
    public void render(float delta)
    {
        borrarPantalla(0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        escena.act();
        escena.draw();

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        escena.dispose();

    }
}
