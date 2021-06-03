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
    Texture texturaBloqueo2;
    Texture texturaBloqueo3;
    private Stage escenaMenuNiveles;

    private int abiertas = 0;

    //Texturas para la historia
    private Texture texturaHistoria2;
    private Texture texturaHistoria3;

    //Historia
    PantallaHistoria historia;
    Texture texturaHistoria;

    public PantallaJuego(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        //checamos si la musica fue parada al regresar a esta pantalla
        if (!juego.getMusica() && !juego.isSoundMuted){
            juego.mp3.play();
            juego.mp3.setVolume(.12f);
        }
        crearMenu();
        crearHistoria();


        Gdx.input.setCatchKey( Input.Keys.BACK, true );
    }

    private void crearHistoria()
    {
        historia = new PantallaHistoria(juego);
        texturaHistoria = new Texture("fondos/historyBG.png");
        texturaHistoria2 = new Texture("fondos/historyBG2.jpg");
        texturaHistoria3 = new Texture("fondos/BG3.jpg");
    }


    private void crearMenu() {
        texturaFondo = new Texture("Menu/FondoMenu.png");
        texturaBloqueo2 = new Texture("MenuNiveles/moon_disabled.png");
        texturaBloqueo3 = new Texture("MenuNiveles/mars_disabled.png");

        // MENU, necesitamos una escena
        //Escena
        escenaMenuNiveles = new Stage(vista);

        // Actores->Boton
        Button btnBack = crearBoton("Menu/btn_back.png", "Menu/btn_back_press.png");
        Button btnNvl1 = crearBoton("MenuNiveles/earth.png", "MenuNiveles/earth_pressed.png");
        Button btnNvl2 = crearBoton("MenuNiveles/moon.png", "MenuNiveles/moon_pressed.png");
        Button btnNvl3 = crearBoton("MenuNiveles/mars.png", "MenuNiveles/mars_pressed.png");

        //Se les da un espacio en la pantalla
        btnBack.setPosition(150 , ALTO - 50, Align.center);
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
                if(!juego.isViewedStory1) {
                    juego.isViewedStory1 = true;
                    historia.setNumeroNivel(1);
                    historia.setTexturaFondo(texturaHistoria);
                    juego.setScreen(historia);
                } else {
                    juego.setScreen(new PantallaNvl1(juego));
                }
            }
        });

        btnNvl2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ( juego.isPassedLvl1 ) {
                    juego.soundBotones.play();
                    juego.mp3.stop();
                    if (!juego.isViewedStory2) {
                        historia.setNumeroNivel(2);
                        historia.setTexturaFondo(texturaHistoria2);
                        juego.setScreen(historia);
                    } else
                        juego.setScreen(new PantallaNvl2(juego));
                } else {
                    juego.error.setVolume(1f);
                    juego.error.play();
                }
            }
        });

        btnNvl3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if ( juego.isPassedLvl2 ){
                    juego.soundBotones.play();
                    juego.mp3.stop();
                    if (!juego.isViewedStory3) {
                        historia.setNumeroNivel(3);
                        historia.setTexturaFondo(texturaHistoria3);
                        juego.setScreen(historia);
                    }else
                        juego.setScreen(new PantallaNvl3(juego));
                } else {
                    juego.error.setVolume(1f);
                    juego.error.play();
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

        if ( !juego.isPassedLvl1 ){
            batch.begin();
            batch.draw(texturaBloqueo2, (3*ANCHO/6) - (texturaBloqueo2.getWidth()/2f) , (ALTO/2) - (texturaBloqueo2.getHeight()/2f) );
            batch.end();
        }

        if ( !juego.isPassedLvl2 ) {
            batch.begin();
            batch.draw(texturaBloqueo3, (5 * ANCHO / 6) - (texturaBloqueo3.getWidth() / 2f), (ALTO / 2) - (texturaBloqueo3.getHeight() / 2f));
            batch.end();
        }


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
        escenaMenuNiveles.dispose();

    }
}