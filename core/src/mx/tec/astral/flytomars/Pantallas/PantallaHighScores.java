package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import mx.tec.astral.flytomars.Guardar;
import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Tools.Texto;

/*
Autor Alejandro Quintana
 */
public class PantallaHighScores extends Pantalla {
    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenuNiveles;

    //Se guardan los nombres y scores en un arrreglo
    private String titulo  = "HighScores";
    private long[] highScores;
    private String[] names;

    public PantallaHighScores(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMenu();
        Gdx.input.setCatchKey( Input.Keys.BACK, true );
    }

    private void crearMenu() {
        texturaFondo = new Texture("fondos/fondoHigh.jpg");
        //texto = new Texto();

        // MENU, necesitamos una escena
        //Escena
        escenaMenuNiveles = new Stage(vista);

        // Actores->Boton
        Button btnBack = crearBoton("Menu/btn_back.png", "Menu/btn_back_press.png");

        btnBack.setPosition(ANCHO/2, 70, Align.center);

        // Agrega los botones a escena
        escenaMenuNiveles.addActor(btnBack);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.soundBotones.play();
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        // La ESCENA se encarga de ATENDER LOS EVENTOS DE ENTRADA
        Gdx.input.setInputProcessor(escenaMenuNiveles);

        //Se carga el archivo guardado
        Guardar.load();
        highScores = Guardar.dj.getHighScores();
        names = Guardar.dj.getNames();

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

        //Aqui se dibuja todo con Batch
        batch.begin();

        batch.draw(texturaFondo, 0, 0);
        juego.texto.mostrarMensaje(batch, "HighScore", ANCHO/2, .90F*ALTO);
        //se recorrre lo que tenemos guardado
        for (int i = 0; i < highScores.length; i++)
        {
            titulo = String.format("%2d. %7s %s", i + 1, highScores[i], names[i]);
            juego.texto.mostrarMensaje(batch, titulo, ANCHO/2,.80f*ALTO - 48 * i);
        }


        batch.end();

        escenaMenuNiveles.draw();


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
        batch.dispose();

    }
}
