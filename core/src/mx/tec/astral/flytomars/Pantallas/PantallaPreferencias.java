package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import javax.xml.transform.sax.SAXResult;

import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Pantallas.Pantalla;
import mx.tec.astral.flytomars.Tools.Texto;

/*
Autor Alejandro Quintana
 */
public class PantallaPreferencias extends Pantalla {
    private Juego juego;
    private Stage escenaPreferencias;
    private static final String opcionVolumenMusica = "volumen";
    private static final String opcionMusicaActiva = "musica activada";
    private static final String opcionesSonido = "Sonido activado";
    private static final String volumenSonido = "Sonido";
    private static final String opciones = "Opciones del Juego";

    Texture texturaFondo;
    Texto texto;

    public PantallaPreferencias(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMenu();
        Gdx.input.setCatchKey( Input.Keys.BACK, true );
    }

    private void crearMenu() {
        texto = new Texto();

        escenaPreferencias = new Stage();
        texturaFondo = new Texture("fondos/fondoInstrucciones.png");

        // Actores->Boton
        Button btnQuitarMusica = crearBoton("buttons/Mute_Idle.png", "buttons/Mute_Pushed.png");
        Button btnPonerMusica = crearBoton("buttons/Volume_2_Idle.png", "buttons/Volume_2_Pushed.png");
        Button btnQuitarMusicaBotones = crearBoton("buttons/Mute_Idle.png", "buttons/Mute_Pushed.png");
        Button btnPonerMusicaBotones = crearBoton("buttons/Volume_2_Idle.png", "buttons/Volume_2_Pushed.png");
        Button btnBack = crearBoton("Menu/btn_back.png", "Menu/btn_back_press.png");

        btnQuitarMusica.setPosition( 1.5f*ANCHO/3, 0.6f * ALTO, Align.center);
        btnPonerMusica.setPosition(2f*ANCHO/3, 0.6f * ALTO, Align.center);
        btnQuitarMusicaBotones.setPosition(1.5f*ANCHO/3, 0.2f * ALTO, Align.center);
        btnPonerMusicaBotones.setPosition(2f*ANCHO/3, 0.2f * ALTO, Align.center);
        btnBack.setPosition(150, ALTO-50, Align.center);

        // Agrega los botones a escena
        escenaPreferencias.addActor(btnQuitarMusica);
        escenaPreferencias.addActor(btnPonerMusica);
        escenaPreferencias.addActor(btnQuitarMusicaBotones);
        escenaPreferencias.addActor(btnPonerMusicaBotones);
        escenaPreferencias.addActor(btnBack);

        btnQuitarMusica.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ( juego.mp3.getVolume() > 0f ){
                    juego.soundBotones.play();
                    juego.mp3.setVolume(0f);
                }
            }
        });

        btnPonerMusica.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ( juego.mp3.getVolume() <= 0f ){
                    juego.soundBotones.play();
                    juego.mp3.setVolume(0.12f);
                }
            }
        });

        btnQuitarMusicaBotones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ( juego.soundBotones.getVolume() > 0f ){
                    juego.soundBotones.play();
                    juego.soundBotones.setVolume(0f);
                }
            }
        });

        btnPonerMusicaBotones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ( juego.soundBotones.getVolume() <= 0f ){
                    juego.soundBotones.setVolume(1f);
                    juego.soundBotones.play();
                }
            }
        });

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.soundBotones.play();
                juego.setScreen(new PantallaMenu(juego));
            }
        });


        // La ESCENA se encarga de ATENDER LOS EVENTOS DE ENTRADA
        Gdx.input.setInputProcessor(escenaPreferencias);

    }

    private Button crearBoton(String archivo, String clickeado) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtn = new TextureRegionDrawable(texturaBoton);
        // Clickeado
        Texture texturaClickeada = new Texture(clickeado);
        TextureRegionDrawable trdBtnClick = new TextureRegionDrawable(texturaClickeada);
        return new Button(trdBtn, trdBtnClick);
    }


    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        texto.mostrarMensaje(batch, "Preferencias del juego", ANCHO/2, .95f*ALTO);
        texto.mostrarMensaje(batch, "Musica de fondo", ANCHO/2, .75f*ALTO);
        texto.mostrarMensaje(batch, "Musica botones", ANCHO/2, .40f*ALTO);
        batch.end();

        escenaPreferencias.draw();


        if ( Gdx.input.isKeyPressed(Input.Keys.BACK) )
            juego.setScreen( new PantallaMenu(juego) );
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
