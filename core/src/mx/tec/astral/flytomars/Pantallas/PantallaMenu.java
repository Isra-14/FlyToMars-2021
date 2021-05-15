package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Tools.Asteroid;
import mx.tec.astral.flytomars.Tools.Moon;
import mx.tec.astral.flytomars.Tools.Star;

/*
Autor(es) Alejandro Quintana, Israel Sanchez
*/
public class PantallaMenu extends Pantalla {
    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenu;
    private float timerCrearAsteroid = 3;
    private float timerCrearMoon = 7;
    private float timerCrearStar = 12;

    private Array<Asteroid> asteroids = new Array<>();
    private Array<Moon> moons = new Array<>();
    private Array<Star> stars = new Array<>();

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

    private void crearAsteroide() {
        int prob = (int)Math.floor(Math.random()*(1+1)+0);
        Texture texture;
        if (prob == 0)
            texture = new Texture("galaxy/asteroid_big.png");
        else
            texture = new Texture("galaxy/asteroid_Small.png");

        int posX = (int) Math.floor(Math.random()*((ANCHO + 80)-(-80)+1)+(-80));
        int posY = (int) Math.floor(Math.random()*((ALTO + 80)-(-80)+1)+(-80));
//        int posX;
//        int posY;
//        if( prob == 0 ) {
//            posX = -80;
//            posY = (int) Math.floor(Math.random()*((ALTO + 80)-(-80)+1)+(-80));
//        }else{
//            posX = (int)ANCHO + 80;
//            posY = (int) Math.floor(Math.random()*((ALTO + 80)-(-80)+1)+(-80));
//
//        }
        Asteroid asteroid = new Asteroid(texture, posX, posY);
        asteroids.add(asteroid);
    }

    private void crearMoon(){
        int prob = (int)Math.floor(Math.random()*(2+1)+0);
        Texture texture;
        if (prob == 0)
            texture = new Texture("galaxy/moon_1.png");
        else if (prob == 1)
            texture = new Texture("galaxy/moon_2.png");
        else
            texture = new Texture("galaxy/moon_3.png");


        int posX = (int) Math.floor(Math.random()*((ANCHO + 80)-(-80)+1)+(-80));
        int posY = (int) Math.floor(Math.random()*((ALTO + 80)-(-80)+1)+(-80));

        Moon moon = new Moon(texture, posX, posY);
        moons.add(moon);
    }

    private void crearStar(){
        int prob = (int)Math.floor(Math.random()*(3+1)+0);
        Texture texture;
        switch (prob){
            case 0:
                texture = new Texture("galaxy/stars_blue.png");
                break;
            case 1:
                texture = new Texture("galaxy/stars_pink.png");
                break;
            case 2:
                texture = new Texture("galaxy/stars_red.png");
                break;
            case 3:
                texture = new Texture("galaxy/stars_yellow.png");
                break;
            default:
                texture = new Texture("galaxy/stars_blue.png");
                break;

        }

        int posX = (int) Math.floor(Math.random()*((ANCHO + 80)-(-80)+1)+(-80));
        int posY = (int) Math.floor(Math.random()*((ALTO + 80)-(-80)+1)+(-80));

        Star star = new Star(texture, posX, posY);
        stars.add(star);
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
                juego.soundBotones.play();
                juego.setScreen(new PantallaJuego(juego));
            }
        });

        btnHighScores.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.soundBotones.play();
                juego.setScreen(new PantallaHighScores(juego));
            }
        });

        btnInstrucciones.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.soundBotones.play();
                juego.setScreen(new PantallaInstrucciones(juego));
            }
        });

        btnAcercaDe.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.soundBotones.play();
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
        actualizar(delta);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo, 0, 0);

        for (Asteroid asteroid : asteroids ) {
            asteroid.render(batch);
        }

        for (Moon moon : moons ) {
            moon.render(batch);
        }

        for (Star star : stars ) {
            star.render(batch);
        }

        juego.texto.mostrarMensaje(batch, "Fly to Mars" , ANCHO/2, ALTO*.9f);

        batch.end();

        escenaMenu.draw();
    }

    private void actualizar(float delta) {
        timerCrearAsteroid += delta;
        timerCrearMoon += delta;
        timerCrearStar += delta;

        if (timerCrearAsteroid >= 3.0f) {
            crearAsteroide();
            timerCrearAsteroid = 0;
        }

        if (timerCrearMoon >= 7.0f) {
            crearMoon();
            timerCrearMoon = 0;
        }

        if (timerCrearStar >= 12.0f) {
            crearStar();
            timerCrearStar = 0;
        }

        for (Asteroid asteroid : asteroids ) {
            asteroid.mover();
        }

        for (Moon moon : moons ) {
            moon.mover();
        }

        for (Star star : stars ) {
            star.mover();
        }

        depurarObjetos();

    }

    private void depurarObjetos() {
        for ( int i = asteroids.size - 1; i >=0; i-- ) {
            Asteroid asteroid = asteroids.get(i);
            if ( ( asteroid.getSprite().getX() >= ANCHO + 100 || asteroid.getSprite().getX() <= -100 ) ||
                 ( asteroid.getSprite().getY() >= ALTO + 100 || asteroid.getSprite().getY() <= -100  ) ){
                asteroids.removeIndex(i);
            }
        }

        for ( int i = moons.size - 1; i >= 0; i-- ) {
            Moon moon = moons.get(i);
            if ( ( moon.getSprite().getX() >= ANCHO + 100 || moon.getSprite().getX() <= -100 ) ||
                 ( moon.getSprite().getY() >= ALTO + 100 || moon.getSprite().getY() <= -100  ) ){
                moons.removeIndex(i);
            }
        }

        for ( int i = stars.size - 1; i >= 0; i-- ) {
            Star star = stars.get(i);
            if ( ( star.getSprite().getX() >= ANCHO + 100 || star.getSprite().getX() <= -100 ) ||
                 ( star.getSprite().getY() >= ALTO + 100 || star.getSprite().getY() <= -100  ) ){
                stars.removeIndex(i);
            }
        }

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

