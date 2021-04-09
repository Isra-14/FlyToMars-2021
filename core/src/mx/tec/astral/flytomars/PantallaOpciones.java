package mx.tec.astral.flytomars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
/*
Autor Alejandro Quintana
 */
public class PantallaOpciones extends Pantalla
{
    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenuNiveles;
    private static final String opcionVolumenMusica = "volume";
    private static final String opcionMusicaActiva = "musica activada";
    private static final String opcionesSonido = "Sonido activado";
    private static final String volumenSonido = "Sonido";
    private static final String opciones = "Opciones del Juego";

    @Override
    public void show() { crearMenu();}

    private void crearMenu()
    {
        texturaFondo = new Texture("fondos/fondoOpciones.png");

    }

    //Aqui se optiene las opciones guardadas
    protected Preferences getOpciones()
    {
        return Gdx.app.getPreferences(opciones);
    }

    //regresa el volumen de la musica
    public float getVolumenMusica()
    {
        return getOpciones().getFloat(opcionVolumenMusica, .5f);
    }

    //Cambiara el volumen de la musica
    public void setVolumenMusica(float volumen)
    {
        getOpciones().putFloat(opcionVolumenMusica, volumen);
        getOpciones().flush();
    }
    

    @Override
    public void render(float delta) {

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
