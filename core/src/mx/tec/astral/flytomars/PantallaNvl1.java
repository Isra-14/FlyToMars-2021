package mx.tec.astral.flytomars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
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

                    //  Personaje (Hero)
    private  Hero hero;
    private EstadoHeroe prevState = EstadoHeroe.DERECHA;

                    //  Enemigos
    //Alien Agil
    private AlienAgil aAgil;
    //Alien Letal
    private AlienLetal aLetal;
    //Alien Tanque
    private AlienTanque aTanque;


                    //  Objetos vida
    private Array<Vida> arrVidas;

                    //  Buttons
    private Texture texturaBack;
    private Texture texturaA;
    private Texture texturaB;


    //Indican si el Hero se mueve en cierta dirección
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;


    public PantallaNvl1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

        crearFondo();

        crearHero();
        crearAlienAgil();
        crearAlienLetal();
        crearAlienTanque();
        crearVidas();

        crearBotonBack();
        crearBotonA();
        crearBotonB();

        //Ahora la misma pantalla RECIBE Y PROCESA los eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearFondo() {
        texturaFondo = new Texture("nivel1/lvlEarth.png");
    }

    private void crearBotonA() {
        texturaA = new Texture("buttons/btn_A.png");
    }

    private void crearBotonB() {
        texturaB = new Texture("buttons/btn_B.png");
    }

    private void crearBotonBack() {
        texturaBack = new Texture("Menu/btn_back.png");
    }

    private void crearHero() {
        Texture texturaIzquierda = new Texture("nivel1/character1_left.png");
        Texture texturaDerecha = new Texture("nivel1/character1.png");

        hero = new Hero(texturaDerecha, texturaIzquierda, 0, 150);
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

    @Override
    public void render(float delta) {
//        actualizar(heroL);
//        actualizar(heroR);
        actualizar(hero);
        borrarPantalla(0,0,0); //Borrar con color negro}
        batch.setProjectionMatrix(camara.combined);

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
        hero.render(batch);

        //Enemigos
        //Alien Agil
        aAgil.render(batch);
        //Alien Letal
        aLetal.render(batch);
        //Alien Tanque
        aTanque.render(batch);

        // Draw A
        batch.draw(texturaA, ANCHO-texturaA.getWidth()*2, texturaA.getHeight()/2f);

        // Draw B
        batch.draw(texturaB, ANCHO-texturaB.getWidth(), texturaB.getHeight()/2f);

        // Draw Back
        batch.draw(texturaBack, texturaBack.getWidth()/2f, texturaBack.getHeight());

        batch.end();

    }

    private void actualizar(Hero hero) {
        if(prevState == EstadoHeroe.DERECHA && hero.getEstado() == EstadoHeroe.IZQUIERDA)
            hero.cambiarEstado();
        else if(prevState == EstadoHeroe.IZQUIERDA && hero.getEstado() == EstadoHeroe.DERECHA)
            hero.cambiarEstado();

        if(moviendoDerecha && hero.getX() <= (ANCHO- hero.getWidth())) {
            hero.mover(DELTA_X_HERO);
            prevState = EstadoHeroe.DERECHA;
        }if(moviendoIzquierda && hero.getX() > 0) {
            hero.mover(-DELTA_X_HERO);
            prevState = EstadoHeroe.IZQUIERDA;
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

            // Back button
            if(v.x >= texturaBack.getWidth()/2f && v.x <= texturaBack.getWidth()*1.5f &&
               v.y >= texturaBack.getHeight() && v.y <= texturaBack.getHeight()*2)
                juego.setScreen(new PantallaJuego(juego));

            //  A button (Jump)
            else if(v.x >= ANCHO-texturaA.getWidth()*2 && v.x <=ANCHO-texturaA.getWidth() &&
                    v.y >= texturaA.getHeight()/2f && v.y <= texturaA.getHeight()*1.5f)
                Gdx.app.log("A_button", "A pressed!");

            //  B button (Shoot)
            else if(v.x >= ANCHO-texturaB.getWidth() && v.x <= ANCHO &&
                    v.y >= texturaB.getHeight()/2f && v.y <= texturaB.getHeight()*1.5f)
                Gdx.app.log("B_button", "B pressed!");

            else{
                if(v.x < ANCHO/2){
                    //Primera mitad de la pantalla
                    moviendoIzquierda = true;
                }else{
                    moviendoDerecha = true;
                }
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