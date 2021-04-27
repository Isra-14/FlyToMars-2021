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
import mx.tec.astral.flytomars.Texto;

/*
    This class manage the Instructions screen.
    Author: Misael Delgado, Israel Sanchez Hinojosa y Alejandro Quintana
*/

public class PantallaInstrucciones extends Pantalla {
    private Juego juego;
    Texture texturaFondo;
    Texture btnLeft;
    Texture btnRigth;
    Texture btnJump;
    Texture btnShoot;
    Texture coin;
    Texture heart;

    private Stage escenaMenuNiveles;
    Texto texto;
    public PantallaInstrucciones(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMenu();
    }

    private void crearMenu() {
        texto = new Texto();
        texturaFondo = new Texture("fondos/fondoInstrucciones.jpg");
        btnLeft = new Texture("buttons/izquierda.png");
        btnRigth = new Texture("buttons/derecha.png");
        btnJump = new Texture("buttons/btn_A.png");
        btnShoot = new Texture("buttons/btn_B.png");
        coin = new Texture("items/coin.png");
        heart = new Texture("items/heart.png");
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

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.draw(btnLeft, ANCHO/7, .69f*ALTO);
        batch.draw(btnRigth, ANCHO/7+ 50, .69f*ALTO + 3);
        batch.draw(btnJump, ANCHO/7, .60f*ALTO,34,43);
        batch.draw(btnShoot, ANCHO/7 + 50, .60f*ALTO, 34, 43);
        batch.draw(coin, ANCHO/7 + 20, .51f*ALTO,34,43);
        batch.draw(heart, ANCHO/7 + 20, .42f*ALTO, 34, 43);
        texto.mostrarMensaje(batch, "Instrucciones del juego", ANCHO/2, .95f*ALTO);
        texto.mostrarMensaje(batch, "Camina de izquierda a derecha", ANCHO/2, .75f*ALTO);
        texto.mostrarMensaje(batch, "A - > Brincar , B -> Disparar", ANCHO/2, .66f*ALTO);
        texto.mostrarMensaje(batch, "Recoge para desbloquear mundos", ANCHO/2, .57f*ALTO);
        texto.mostrarMensaje(batch, "Recoge para obtener vida extra", ANCHO/2, .49f*ALTO);
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