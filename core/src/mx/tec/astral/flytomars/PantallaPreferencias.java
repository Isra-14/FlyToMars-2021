package mx.tec.astral.flytomars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
/*
Autor Alejandro Quintana
 */
public class PantallaPreferencias extends Pantalla
{
    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenuNiveles;
    private static final String opcionVolumenMusica = "volumen";
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

    //Checa si esta activa la musica
    public boolean musicaActivada()
    {
        return getOpciones().getBoolean(opcionMusicaActiva, true);
    }

    //Activa la musica o la desactiva
   /* public boolean setMusicaActivada(boolean musicaActivada)
    {
        getOpciones().putBoolean(opcionMusicaActiva, musicaActivada);
        getOpciones().flush();
    }
    */

    //Checa el volumen de los sonidos
    public float getVolumenSonido()
    {
        return getOpciones().getFloat(volumenSonido, .5f);
    }

    //Cambia el vvolumen del sonido
    public void setVolumenSonido(float volumen)
    {
        getOpciones().putFloat(volumenSonido, volumen);
        getOpciones().flush();
    }

    //Checa que el sonido este activado
    public boolean sonidoActivado()
    {
        return getOpciones().getBoolean(opcionesSonido, true);
    }

    //Activa el sonido o lo desactiva
    public void setSonidoActivado(boolean sonidoActivo)
    {
        getOpciones().putBoolean(opcionesSonido, sonidoActivo);
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
