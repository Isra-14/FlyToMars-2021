package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Pantallas.Pantalla;
import mx.tec.astral.flytomars.Pantallas.PantallaAcercaDe;
import mx.tec.astral.flytomars.Pantallas.PantallaHighScores;
import mx.tec.astral.flytomars.Pantallas.PantallaInstrucciones;
import mx.tec.astral.flytomars.Pantallas.PantallaJuego;

/*
Autor Alejandro Quintana
*/
public class PantallaMenu extends Pantalla {
    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenu;

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }

    /*
        Se ejecuta al inicio, antes de mostrar la pantalla
        INICIALIZAR los objetos
    */
    @Override
    public void show() {
        crearMenu();
    }

    private void crearMenu() {
        texturaFondo = new Texture("Menu/FondoMenu.png");

        // MENU, necesitamos una escena
        //Escena
        escenaMenu = new Stage(vista);

        // Actores->Boton

        Button btnInicio = crearBoton("Menu/btn_jugar.png", "Menu/btn_jugar_press.png");
        Button btnHighScores = crearBoton("Menu/btn_high-scores.png", "Menu/btn_high-scores_press.png");
        Button btnInstrucciones = crearBoton("Menu/btn_instrucciones.png", "Menu/btn_instrucciones_press.png");
        Button btnAcercaDe = crearBoton("Menu/btn_acerca-de.png", "Menu/btn_acerca-de_press.png");


        btnInicio.setPosition(ANCHO/2, 4*ALTO/8+ALTO/5, Align.center);
        btnHighScores.setPosition(ANCHO/2, 3*ALTO/8+ALTO/5, Align.center);
        btnInstrucciones.setPosition(ANCHO/2, 2*ALTO/8+ALTO/5, Align.center);
        btnAcercaDe.setPosition(ANCHO/2, 1*ALTO/8+ALTO/5, Align.center);

        // Agrega los botones a escena
        escenaMenu.addActor(btnInicio);
        escenaMenu.addActor(btnHighScores);
        escenaMenu.addActor(btnInstrucciones);
        escenaMenu.addActor(btnAcercaDe);

        btnInicio.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaJuego(juego));
            }
        });

        btnHighScores.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaHighScores(juego));
            }
        });

        btnInstrucciones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaInstrucciones(juego));
            }
        });

        btnAcercaDe.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaAcercaDe(juego));
            }
        });

        // La ESCENA se encarga de ATENDER LOS EVENTOS DE ENTRADA
        Gdx.input.setInputProcessor(escenaMenu);

    }

    private Button crearBoton(String archivo, String clickeado) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtn = new TextureRegionDrawable(texturaBoton);
        // Clickeado
        Texture texturaClickeada = new Texture(clickeado);
        TextureRegionDrawable trdBtnClick = new TextureRegionDrawable(texturaClickeada);
        return new Button(trdBtn, trdBtnClick);
    }

    // Se ejecuta automáticamente (60 veces/seg.)
    // delta es el tiempo que ha transcurrido entre frames
    @Override
    public void render(float delta) {
        borrarPantalla(0,1,1);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo, 0, 0);

        batch.end();

        escenaMenu.draw();
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

