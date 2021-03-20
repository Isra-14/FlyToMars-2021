package mx.tec.astral.flytomars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
/*
Autor Alejandro Quintana
 */
public class PantallaHighScores extends Pantalla {
    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenuNiveles;
    BitmapFont font = new BitmapFont();



    public PantallaHighScores(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMenu();
    }

    private void crearMenu() {
        texturaFondo = new Texture("fondos/fondoHigh.jpg");

        // MENU, necesitamos una escena
        //Escena
        escenaMenuNiveles = new Stage(vista);

        // Actores->Boton
        Button btnBack = crearBoton("Menu/btn_back.png", "Menu/btn_back_press.png");

        btnBack.setPosition(ANCHO/2, 200, Align.center);

        // Agrega los botones a escena
        escenaMenuNiveles.addActor(btnBack);

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });


        // La ESCENA se encarga de ATENDER LOS EVENTOS DE ENTRADA
        Gdx.input.setInputProcessor(escenaMenuNiveles);

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

        font.getData().setScale(3,3);
        font.draw(batch, "Aqui se mostraran la puntuacion mas alta", ANCHO/4, 3*ALTO/4);

        batch.end();

        escenaMenuNiveles.draw();
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
