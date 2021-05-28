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

//import sun.security.util.AnchorCertificates;

/*
Autor(es)
Israel
Alejandro Quintana
 */

public class PantallaJuego extends Pantalla {
    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenuNiveles;

    private int abiertas = 0;

    //Texturas para la historia
    private Texture textruaHistoria2;
    private Texture texturaHistoria3;

    public PantallaJuego(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        //checamos si la musica fue parada al regresar a esta pantalla
        if (!juego.getMusica()){
            juego.mp3.play();
            juego.mp3.setVolume(.15f);
        }
        crearMenu();
        crearHistoria();
    }
    //Historia
    PantallaHistoria historia;
    Texture texturaHistoria;
    private void crearHistoria()
    {
        texturaHistoria = new Texture("fondos/historyBG.png");
        historia = new PantallaHistoria(juego);
        textruaHistoria2 = new Texture("fondos/historyBG2.jpg");
        texturaHistoria3 = new Texture("fondos/BG3.jpg");
    }


    private void crearMenu() {
        texturaFondo = new Texture("Menu/FondoMenu.png");

        // MENU, necesitamos una escena
        //Escena
        escenaMenuNiveles = new Stage(vista);

        // Actores->Boton
        Button btnBack = crearBoton("Menu/btn_back.png", "Menu/btn_back_press.png");
        Button btnNvl1 = crearBoton("MenuNiveles/earth.png", "MenuNiveles/earth_pressed.png");
        Button btnNvl2 = crearBoton("MenuNiveles/moon.png", "MenuNiveles/moon_pressed.png");
        Button btnNvl3 = crearBoton("MenuNiveles/mars.png", "MenuNiveles/mars_pressed.png");

        //Se les da un espacio en la pantalla
        btnBack.setPosition(ANCHO/2 - 100 , 85);
        btnNvl1.setPosition(ANCHO/6, ALTO/2, Align.center);
        btnNvl2.setPosition(3*ANCHO/6, ALTO/2, Align.center);
        btnNvl3.setPosition(5*ANCHO/6, ALTO/2, Align.center);

        // Agrega los botones a escena
        escenaMenuNiveles.addActor(btnBack);
        escenaMenuNiveles.addActor(btnNvl1);
        escenaMenuNiveles.addActor(btnNvl2);
        escenaMenuNiveles.addActor(btnNvl3);

        btnNvl1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.soundBotones.play();
                juego.mp3.stop();
                historia.setNumeroNivel(1);
                historia.setTexturaFondo(texturaHistoria);
                if(abiertas == 0) {
                    //abiertas++;
                    juego.setScreen(historia);
                }
                else {
                    juego.setScreen(new PantallaNvl1(juego));
                }
            }
        });

        btnNvl2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.soundBotones.play();
                juego.mp3.stop();
                historia.setNumeroNivel(2);
                historia.setTexturaFondo(textruaHistoria2);
                if (abiertas == 0)
                {
                    juego.setScreen(historia);
                }
                else{
                juego.setScreen(new PantallaNvl2(juego));}
            }
        });

        btnNvl3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.soundBotones.play();
                juego.mp3.stop();
                historia.setTexturaFondo(texturaHistoria3);
                historia.setNumeroNivel(3);
                if(abiertas == 0)
                {
                    juego.setScreen(historia);
                }
                else {
                    juego.setScreen(new PantallaNvl3(juego));
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
        escenaMenuNiveles.dispose();

    }
}