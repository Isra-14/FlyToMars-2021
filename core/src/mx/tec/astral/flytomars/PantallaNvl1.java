package mx.tec.astral.flytomars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

/*
Pantalla que almacena todos los objetos del nivel 1
Autores: Israel, Misael y Alejandro
 */
public class PantallaNvl1 extends Pantalla {
    // Font of score.
    BitmapFont font = new BitmapFont(); //or use alex answer to use custom font

    //Velocidad del Hero
    private static  final float DELTA_X_HERO = 10;

    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenuNiveles;

    //Personaje (Hero)
    private  Hero heroR;
    private  Hero heroL;

    //Enemigos
    //Alien Agil
    private AlienAgil aAgil;
    //Alien Letal
    private AlienLetal aLetal;
    //Alien Tanque
    private AlienTanque aTanque;


    //Objetos vida
    private Array<Vida> arrVidas;

    //Indican si el Hero se mueve en cierta dirección
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;
    private boolean posDerecha = true;
    private boolean posIzquierda = false;

    //Checa si el juego se pauso
    boolean paused;


    public PantallaNvl1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMenu();
        heroR = crearHero(heroR, false);
        heroL = crearHero(heroL, true);
        crearAlienAgil();
        crearAlienLetal();
        crearAlienTanque();
        crearVidas();
        //Ahora la misma pantalla RECIBE Y PROCESA los eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearAlienTanque() {
        Texture texturaTanque=new Texture("enemigos/alienTanque.png");
        aTanque=new AlienTanque(texturaTanque, 4*ANCHO/10, 2*ALTO/12);
    }

    private void crearAlienLetal() {
        Texture texturaLetal=new Texture("enemigos/alienLetal.png");
        aLetal=new AlienLetal(texturaLetal, 6*ANCHO/10, 2*ALTO/12);

    }

    private void crearAlienAgil() {
        Texture teturaAgil= new Texture("enemigos/alienAgil.png");
        aAgil=new AlienAgil(teturaAgil,8*ANCHO/10,2*ALTO/12);
    }

    private void crearVidas() {
        Texture texturaVida = new Texture("items/heart.png");

        arrVidas = new Array<>(3);
            for (int i = 0; i < 3; i++){
                Vida vida = new Vida(texturaVida,  ANCHO-(i*60+65),ALTO-60);
                arrVidas.add(vida); //Lo guarda en el arrelo
            }
    }

    //Crea la imagen de nuestro protagonista
    private Hero crearHero(Hero myHero, boolean isLeft) {
        Texture texturaHero;
        if(!isLeft)
             texturaHero = new Texture("nivel1/character1.png");
        else
            texturaHero = new Texture("nivel1/character1_left.png");
        myHero = new Hero(texturaHero,0,150);
        return myHero;
    }

    private void crearMenu() {
        texturaFondo = new Texture("nivel1/lvlEarth.png");


        // MENU, necesitamos una escena
        //Escena
        escenaMenuNiveles = new Stage(vista);

        // Actores->Boton
        Button btnA = crearBoton("buttons/btn_A.png", "buttons/btn_A_press.png");
        Button btnB = crearBoton("buttons/btn_B.png", "buttons/btn_B_press.png");
        Button btnBack = crearBoton("Menu/btn_back.png", "Menu/btn_back_press.png");


        //Se les agrega una posicion en pantalla
        btnA.setPosition(ANCHO-btnA.getWidth()*2, 100, Align.center);
        btnB.setPosition(ANCHO-btnB.getWidth(), 100, Align.center);
        btnBack.setPosition(ANCHO/4 - btnBack.getMinWidth(), 100, Align.center);

        // Agrega los botones a escena
        escenaMenuNiveles.addActor(btnA);
        escenaMenuNiveles.addActor(btnB);
        escenaMenuNiveles.addActor(btnBack);

        //Aqui se agrega la accion a ejecutar cuando se presiona
        btnA.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaNvl1(juego));
            }
        });

        btnB.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaNvl2(juego));
            }
        });

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
        actualizar(heroL);
        actualizar(heroR);
        borrarPantalla(0,0,0); //Borrar con color negro}
        batch.setProjectionMatrix(camara.combined);
        camara.update();

        batch.begin();

        batch.draw(texturaFondo, 0, 0);

        font.getData().setScale(3,3);
        font.draw(batch, "Score:", 25, ALTO - 25 );



        //Vidas
        for (Vida vida: arrVidas) //Visita cada objeto del arreglo
        {
            vida.render(batch);
        }

        //Hero
        if(moviendoDerecha || posDerecha)
            heroR.render(batch);
        else if(moviendoIzquierda || posIzquierda)
            heroL.render(batch);

        //Enemigos
        //Alien Agil
        aAgil.render(batch);
        //Alien Letal
        aLetal.render(batch);
        //Alien Tanque
        aTanque.render(batch);

        batch.end();



        escenaMenuNiveles.draw();
    }

    private void actualizar(Hero hero) {
        if(moviendoDerecha && hero.getX() <= (ANCHO- hero.getWidth())){
            hero.mover(DELTA_X_HERO);
        }if(moviendoIzquierda && hero.getX() > 0)
        {
            hero.mover(-DELTA_X_HERO);
        }
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private class ProcesadorEntrada implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        //Cuando el usuario toca la pantalla. Pone el dedo sobre la pantalla
        //Mover nave derecha si toco la parte derecha de la pantalla, izquierda del otro caso
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            //pointer = dice qué dedo y que posicion lo puse
            //button = dice si presione el boton izq o derecho
            Vector3 v = new Vector3(screenX,screenY,0);
            camara.unproject(v); //Convierte de coordenadas FISICAS a LÓGICAS
            if(v.x < ANCHO/2){
                //Primera mitad de la pantalla
                //nave.mover(-DELTA_X);
                moviendoIzquierda = true;
                posDerecha = false;
                posIzquierda = true;
            }else{
                heroR.mover(DELTA_X_HERO);
                moviendoDerecha = true;
                posDerecha = true;
                posIzquierda = false;
            }
            return true; //Porque el juego ya proceso el evento
        }

        //Cuando el usuario quita el dedo de la pantalla
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            Vector3 v = new Vector3(screenX,screenY,0);
            camara.unproject(v); //Convierte de coordenadas FISICAS a LÓGICAS
            moviendoDerecha = false;
            moviendoIzquierda = false;
            return true; //Porque el juego ya proceso el evento
        }

        //Cuando arrastro el dedo sonre la pantalla
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }
}