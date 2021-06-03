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

import mx.tec.astral.flytomars.Enemigos.AlienAgil;
import mx.tec.astral.flytomars.Enemigos.AlienLetal;
import mx.tec.astral.flytomars.Enemigos.AlienTanque;
import mx.tec.astral.flytomars.Enemigos.EstadoAlien;
import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Tools.Texto;

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
    Texture shield;

    //Enemies
    private Texture spriteSheetAgil;
    private AlienAgil aAgil;

    private Texture spriteSheetTanque;
    private AlienTanque tAlien;

    private Texture spriteSheetLetal;
    private AlienLetal letal;

    private Stage escenaMenuNiveles;
    Texto texto;
    public PantallaInstrucciones(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMenu();
        Gdx.input.setCatchKey( Input.Keys.BACK, true );
    }

    private void crearMenu() {
        texto = new Texto();
        texturaFondo = new Texture("fondos/fondoInstrucciones.png");
        btnLeft = new Texture("buttons/izquierda.png");
        btnRigth = new Texture("buttons/derecha.png");
        btnJump = new Texture("buttons/btn_A.png");
        btnShoot = new Texture("buttons/btn_B.png");
        coin = new Texture("items/coin.png");
        heart = new Texture("items/heart.png");
        shield = new Texture("items/shield.png");
        spriteSheetAgil = new Texture("enemigos/Agil.png");
        spriteSheetLetal = new Texture("enemigos/Letal.png");
        spriteSheetTanque = new Texture("enemigos/Tanque.png");
        // MENU, necesitamos una escena
        //Escena
        escenaMenuNiveles = new Stage(vista);

        // Actores->Boton
        Button btnBack = crearBoton("Menu/btn_back.png", "Menu/btn_back_press.png");

        btnBack.setPosition(150, ALTO-50, Align.center);

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

        //Enemies
        aAgil= new AlienAgil(spriteSheetAgil, ANCHO/40 ,.36f*ALTO);
        tAlien= new AlienTanque(spriteSheetTanque, ANCHO/7-50 ,.18f*ALTO);
        letal= new AlienLetal(spriteSheetLetal, ANCHO/40 ,.07f*ALTO);

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
        batch.draw(btnLeft, ANCHO/7, .80f*ALTO);
        batch.draw(btnRigth, ANCHO/7+ 50, .80f*ALTO + 3);
        batch.draw(btnJump, ANCHO/7, .71f*ALTO,34,43);
        batch.draw(btnShoot, ANCHO/7 + 50, .71f*ALTO, 34, 43);
        batch.draw(coin, ANCHO/7 + 20, .62f*ALTO,34,43);
        batch.draw(heart, ANCHO/7 + 20, .53f*ALTO, 34, 43);
        batch.draw(shield, ANCHO/7 + 20, .44f*ALTO, 34, 43);
        aAgil.setEstado(EstadoAlien.DERECHA);
        //aAgil.moverHorizontal();
        aAgil.render(batch);
        tAlien.render(batch);
        letal.render(batch);
        texto.mostrarMensaje(batch, "Instrucciones del juego", ANCHO/2, .96f*ALTO);
        texto.mostrarMensaje(batch, "Camina de izquierda a derecha", ANCHO/2, .85f*ALTO);
        texto.mostrarMensaje(batch, "A - > Brincar , B -> Disparar", ANCHO/2, .76f*ALTO);
        texto.mostrarMensaje(batch, "Recoge para aumentar tu score!", ANCHO/2, .67f*ALTO);
        texto.mostrarMensaje(batch, "Recoge para obtener vida extra", ANCHO/2, .59f*ALTO);
        texto.mostrarMensaje(batch, "Recoge para obtener inmunidad!", ANCHO/2, .50f*ALTO);
        texto.mostrarMensaje(batch, "Matalo con 1 disparo si eres muy rapido", ANCHO/2, .41f*ALTO);
        texto.mostrarMensaje(batch, "Te tomara mas tiempo eliminarlo", ANCHO/2, .28f*ALTO);
        texto.mostrarMensaje(batch, "Si te toca una sola vez ya estas muerto", ANCHO/2, .15f*ALTO);
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