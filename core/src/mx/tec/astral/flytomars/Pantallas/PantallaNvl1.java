package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import mx.tec.astral.flytomars.Enemigos.EstadoAlien;
import mx.tec.astral.flytomars.Tools.Bala;
import mx.tec.astral.flytomars.Enemigos.AlienAgil;
import mx.tec.astral.flytomars.Enemigos.AlienLetal;
import mx.tec.astral.flytomars.Enemigos.AlienTanque;
import mx.tec.astral.flytomars.Tools.EstadoBala;
import mx.tec.astral.flytomars.Heroe.EstadoHeroe;
import mx.tec.astral.flytomars.Heroe.Hero;
import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Tools.PowerUp;
import mx.tec.astral.flytomars.Tools.Texto;
import mx.tec.astral.flytomars.Tools.Vida;

public class PantallaNvl1 extends Pantalla {


    //Cancion nivel 1
    Music music;
    // Font of score.
    Texto texto;

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
//    private AlienAgil aAgil;
    private Array<AlienAgil> arrAliensAgiles;
    private Texture texturaAgil_left;
    private Texture texturaAgil_right;
    private float timerCrearAlienAgil;
    private final float DX_PASO_ALIEN_AGIL = 10;
    private float timerCambioAgil;
    private final float TIEMPO_CREAR_AGIL=10;
    private final float TIEMPO_CAMBIO_AGIL=1.5f;

    //Alien Letal
//    private AlienLetal aLetal;
    private  Array<AlienLetal> arrLetales;
    private Texture texturaLetal;
    private float timerCrearAlienLetal;
    private float timerCambioLetal;
    private final float TIEMPO_CREAR_LETAL=20;
    private final float TIEMPO_CAMBIO_LETAL=6;

    //Alien Tanque
    private Array<AlienTanque> arrTanques;
    private Texture texturaTanque;
    private float timerCrearAlienTanque;
    private float timerCambioTanque;
    private final float TIEMPO_CREAR_TANQUE=15;
    private final float TIEMPO_CAMBIO_TANQUE=6;



    //  Objetos vida
    private Array<Vida> arrVidas;

    // Disparos del personaje
    private Array<Bala> arrBalas;
    private Texture texturaBalaDer;
    private Texture texturaBalaIzq;

    //  Buttons
    private Texture texturaBack;
    private Texture texturaA;
    private Texture texturaB;
    private Texture texturaIzq;
    private Texture texturaDer;
    private Texture texturaPause;

    //Objetos de PowerUps
    private Texture texturaEscudo;
    private Texture texturaMoneda;
    private Texture texturaVida;
    private int numeroPower;
    private int timerPower=3;


    //Clase powerUp
    private PowerUp powerUp;

    //Indican si el Hero se mueve en cierta dirección
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;

    //Score
    private  int score = 0;


    public PantallaNvl1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

        crearFondo();

        crearHero();
        crearAlienAgil();
        //crearAliensAgiles();
        crearAlienLetal();
        crearAlienTanque();

        //crea los objetos
        crearVidas();
        crearPowerUp();

        crearBotonBack();
        crearBotonA();
        crearBotonB();
        crearBotonIzq();
        crearBotonDer();
        crearBotonPause();

        crearTexto();

        crearBalas();
        //Carga la cancion
        playMusic();

        //Ahora la misma pantalla RECIBE Y PROCESA los eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());


    }

    //Carga la musica
    private void playMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Efectos/level1.wav"));
        music.play();
        music.setVolume(.05f);
        music.setLooping(true);
    }

    /*
    private void crearAliensAgiles() {
        Texture texturaAgil= new Texture("enemigos/alienAgil.png");
        arrAliensAgiles = new Array<>(5);
        for(int i= 0; i<5; i++){
            AlienAgil alienAgil = new AlienAgil(texturaAgil, MathUtils.random(0,ANCHO-texturaAgil.getWidth()),MathUtils.random(0,ANCHO-texturaAgil.getHeight()));
            arrAliensAgiles.add(alienAgil);
        }
    }
*/
    private void crearPowerUp()
    {
        texturaEscudo = new Texture("items/shield.png");
        texturaMoneda = new Texture("items/coin.png");
        powerUp = new PowerUp(texturaVida, texturaEscudo, texturaMoneda, 0, 0);
    }

    private void probabilidad(SpriteBatch batch)
    {

        int chance = (int)(Math.random()*100);
        if (chance< 80)
            powerUp.render(batch);

    }

    private void crearBalas() {
        arrBalas = new Array<>();
        texturaBalaDer = new Texture("Shots/shotDer.png");
        texturaBalaIzq = new Texture("Shots/shotIzq.png");
    }
    private void crearTexto() {
        texto = new Texto();
    }

    private void crearBotonPause() {
        texturaPause = new Texture("buttons/pause.png");
    }

    private void crearBotonDer() {
        texturaDer = new Texture("buttons/btnDerecha.png");
    }

    private void crearBotonIzq() {
        texturaIzq = new Texture("buttons/btnIzquierda.png");
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
        Texture texturaDerecha = new Texture("nivel1/character1_right.png");

        hero = new Hero(texturaDerecha, texturaIzquierda, 0, 150);
    }

    private void crearAlienTanque() {
        texturaTanque=new Texture("enemigos/alienTanque2.png");
        arrTanques = new Array<>();

    }

    private void crearAlienLetal() {
        texturaLetal=new Texture("enemigos/alienLetal2.png");
        arrLetales= new Array<>();

    }

    private void crearAlienAgil() {
        texturaAgil_left = new Texture("enemigos/alienAgil_Left.png");
        texturaAgil_right = new Texture("enemigos/alienAgil_Right.png");
        arrAliensAgiles= new Array<>();
    }

    private void crearVidas() {
        texturaVida = new Texture("items/heart.png");

        arrVidas = new Array<>(3);
        for (int i = 0; i < 3; i++){
            Vida vida = new Vida(texturaVida,  ANCHO-(i*60+65),ALTO-60);
            arrVidas.add(vida); //Lo guarda en el arrelo
        }
    }

    @Override
    public void render(float delta) {
        actualizar(delta);
        borrarPantalla(0,0,0); //Borrar con color negro}
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo, 0, 0);

        texto.mostrarMensaje(batch, "Score:", 100, ALTO-25);

        //Vidas
        for (Vida vida: arrVidas) //Visita cada objeto del arreglo
        {
            vida.render(batch);
        }

        //Hero
        hero.render(batch);

        //Enemigos
        //Alien Agil
        //aAgil.render(batch);

        //Alien Agil
        for (AlienAgil aAgil:arrAliensAgiles) {
            aAgil.render(batch);
        }
        //Alien Letal
        for (AlienLetal aLetal:arrLetales){
            aLetal.render(batch);
        }

        //Alien Tanque
        for(AlienTanque atanque: arrTanques){
            atanque.render(batch);
        }

        // Draw A
        batch.draw(texturaA, ANCHO-texturaA.getWidth()*2, texturaA.getHeight()/2f);

        // Draw B
        batch.draw(texturaB, ANCHO-texturaB.getWidth(), texturaB.getHeight()/2f);

//         Draw Back
//        batch.draw(texturaBack, texturaBack.getWidth()/2f, texturaBack.getHeight());

        // Draw izq.
        batch.draw(texturaIzq, texturaIzq.getWidth()/2f, texturaIzq.getHeight()/3f);

        // Draw der.
        batch.draw(texturaDer, 2*texturaDer.getWidth(), texturaDer.getHeight()/3f);

        // Draw pause.
        batch.draw(texturaPause, ANCHO/2 - texturaPause.getWidth()/2f, ALTO - texturaPause.getHeight()*1.5f);

        // Draw shots
        for (Bala bala : arrBalas) {
            bala.render(batch);
        }



        probabilidad(batch);

        batch.end();

    }

    private void actualizar(float delta){
        actualizarHero(hero);
        actualizarBalas(delta);
        actualizarAgil(delta);
        actualizarTanque(delta);
        actualizarLetal(delta);



        // moverAliensAgiles(delta);

        //probarColisionesAlienAgil();
    }
    private void actualizarAgil(float delta) {
        timerCrearAlienAgil+=delta;
        if(timerCrearAlienAgil>=TIEMPO_CREAR_AGIL){
            timerCrearAlienAgil=0;
            //Crear
            float xAgil= MathUtils.random(10,ANCHO-texturaAgil_right.getWidth());
            AlienAgil aAgil= new AlienAgil(texturaAgil_right, texturaAgil_left, xAgil,200, DX_PASO_ALIEN_AGIL);
            arrAliensAgiles.add(aAgil);
        }
        moverAliensAgiles(delta);
    }

    private void moverAliensAgiles(float delta) {
        for (AlienAgil alienAgil : arrAliensAgiles) {
            timerCambioAgil+=delta;
            if(timerCambioAgil >= TIEMPO_CAMBIO_AGIL){
                int tipo = MathUtils.random(1,2);
                timerCambioAgil = 0;
                if( tipo == 1 && alienAgil.getEstado() == EstadoAlien.DERECHA)
                    alienAgil.cambiarEstado();
                else if(tipo == 2 && alienAgil.getEstado() == EstadoAlien.IZQUIERDA)
                    alienAgil.cambiarEstado();
            }
            if(alienAgil.getSprite().getX() >= 0 && alienAgil.getSprite().getX() <= ANCHO - alienAgil.getSprite().getWidth())
                alienAgil.moverHorizontal();
            else {
                if (alienAgil.getSprite().getX() <= 0 && alienAgil.getEstado() == EstadoAlien.DERECHA)
                    alienAgil.setX(1);
                else if (alienAgil.getX() >= ANCHO - alienAgil.getSprite().getWidth() && alienAgil.getEstado() == EstadoAlien.IZQUIERDA)
                    alienAgil.setX(ANCHO - alienAgil.getSprite().getWidth() - 1);
            }
        }
    }

    private void actualizarLetal(float delta) {
        timerCrearAlienLetal+=delta;
        if(timerCrearAlienLetal>=TIEMPO_CREAR_LETAL){
            timerCrearAlienLetal=0;
            //Crear
            float xLetal= MathUtils.random(10,ANCHO-texturaLetal.getWidth());
            AlienLetal aLetal= new AlienLetal(texturaLetal,xLetal,200);
            arrLetales.add(aLetal);
        }
        moverAliensLetales(delta);
    }

    private void moverAliensLetales(float delta) {

        int velocidad =10;
        for (AlienLetal alienLetal : arrLetales) {
            timerCambioLetal += delta;
            if (timerCambioLetal >= TIEMPO_CAMBIO_LETAL) {

                int valor = MathUtils.random(0, 1);
                if (valor >0) {
                    velocidad*=-1;
                    alienLetal.moverHorizontal(30);
                    timerCambioLetal=3;
                } else {
                    velocidad*=-1;
                    alienLetal.moverHorizontal(-30);
                    timerCambioLetal=3;

                }


            }


        }
    }

    private void actualizarTanque(float delta) {

        timerCrearAlienTanque+=delta;
        if(timerCrearAlienTanque>=TIEMPO_CREAR_TANQUE){
            timerCrearAlienTanque=0;
            //Crear
            float xTanque = MathUtils.random(10,ANCHO-texturaTanque.getWidth());
            AlienTanque aTanque = new AlienTanque(texturaTanque,xTanque,100);
            arrTanques.add(aTanque);



        }
        moverAliensTanque(delta);



    }

    private void moverAliensTanque(float delta) {


        int velocidad =10;
        for (AlienTanque alienTanque : arrTanques) {
            timerCambioTanque += delta;

            if (timerCambioTanque >= TIEMPO_CAMBIO_TANQUE) {

                int valor = MathUtils.random(0, 1);
                if (valor >0) {
                    velocidad*=-1;
                    alienTanque.moverHorizontal(10);
                    timerCambioTanque=4;
                } else {
                    velocidad*=-1;
                    alienTanque.moverHorizontal(-10);
                    timerCambioTanque=4;

                }


            }


        }

    }

    /*private void probarColisionesAlienAgil() {
        for(int i= arrAliensAgiles.size-1; i>=0; i--){
            AlienAgil alienAgil = arrAliensAgiles.get(i);
            if(bala.sprite.getBoundingRectangle().overlaps(alien.sprite.getBoundingRectangle())){
                //Le pegó
                alien.setEstado(EstadoAlien.EXPLOTA);
                //Contar puntos
                puntos +=150;
                //Desaparecer la bala
                bala = null; //No regresar al for
                break;
            }
        }
    }*/



    private void actualizarBalas(float delta) {
        for (int i = arrBalas.size-1; i >= 0;i--){
            Bala bala = arrBalas.get(i);

            bala.mover(delta);

            if(bala.getX() > ANCHO || bala.getX() < -200)
                arrBalas.removeIndex(i);
        }
    }

    private void actualizarHero(Hero hero) {
        if(prevState == EstadoHeroe.DERECHA && hero.getEstado() == EstadoHeroe.IZQUIERDA)
            hero.cambiarEstado();
        else if(prevState == EstadoHeroe.IZQUIERDA && hero.getEstado() == EstadoHeroe.DERECHA)
            hero.cambiarEstado();

        if(moviendoDerecha && hero.getSprite().getX() <= (ANCHO- hero.getSprite().getWidth())) {
            hero.mover(DELTA_X_HERO);
            prevState = EstadoHeroe.DERECHA;
        }if(moviendoIzquierda && hero.getSprite().getX() > 0) {
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
        arrVidas.clear();
        batch.dispose();
        arrBalas.clear();
        music.dispose();
        juego.mp3.dispose();

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

            if(v.x >= ANCHO/2 - texturaPause.getWidth()/2f && v.x <= ANCHO/2 + texturaPause.getWidth()/2f &&
                    v.y >= ALTO - texturaPause.getHeight()*1.5f && v.y <= ALTO - texturaPause.getHeight()*0.5f) {
                juego.setScreen(new PantallaJuego(juego));
                music.stop();
            }
                // Back button
//            if(v.x >= texturaBack.getWidth()/2f && v.x <= texturaBack.getWidth()*1.5f &&
//                    v.y >= texturaBack.getHeight() && v.y <= texturaBack.getHeight()*2)
//                juego.setScreen(new PantallaJuego(juego));

                //  A button (Jump)
            else if(v.x >= ANCHO-texturaA.getWidth()*2 && v.x <=ANCHO-texturaA.getWidth() &&
                    v.y >= texturaA.getHeight()/2f && v.y <= texturaA.getHeight()*1.5f) {
                //Gdx.app.log("A_button", "A pressed!");
                //Sonido para saltar
                juego.soundSalto.play(.5f);
                hero.saltar();
            }
            //  B button (Shoot)
            else if(v.x >= ANCHO-texturaB.getWidth() && v.x <= ANCHO &&
                    v.y >= texturaB.getHeight()/2f && v.y <= texturaB.getHeight()*1.5f) {
                //Gdx.app.log("B_button", "B pressed!");

                //Sonido disparo
                juego.soundDisparo.play(.2f);
                Bala bala = new Bala(texturaBalaIzq, texturaBalaDer, hero.getSprite().getX() + hero.getSprite().getWidth(),
                        (hero.getSprite().getY() + hero.getSprite().getHeight()/2f));

                if(hero.getEstado() == EstadoHeroe.DERECHA || prevState == EstadoHeroe.DERECHA) {
                    bala.setEstado(EstadoBala.DERECHA);
                    bala.setPosition((hero.getSprite().getX() + hero.getSprite().getWidth()) - bala.getSprite().getWidth()/2f,
                            (hero.getSprite().getY() + hero.getSprite().getHeight()/2f) - bala.getSprite().getHeight()/2f);
                    arrBalas.add(bala);
                }else if(hero.getEstado() == EstadoHeroe.IZQUIERDA || prevState == EstadoHeroe.IZQUIERDA){
                    bala.setEstado(EstadoBala.IZQUIERDA);
                    bala.setPosition(hero.getSprite().getX() - bala.getSprite().getWidth()/2f,
                            (hero.getSprite().getY() + hero.getSprite().getHeight()/2f)- bala.getSprite().getHeight()/2);
                    arrBalas.add(bala);
                }

            }
            else {
                // Left Button
                if (v.x >= texturaIzq.getWidth() / 2f && v.x <= texturaIzq.getWidth() * 1.5f &&
                        v.y >= texturaIzq.getHeight() / 3f && v.y <= texturaIzq.getHeight() * 1.3f)
                    moviendoIzquierda = true;
                    // Right Button
                else if (v.x >= texturaDer.getWidth() * 2f && v.x <= texturaDer.getWidth() * 3f &&
                        v.y >= texturaDer.getHeight() / 3f && v.y <= texturaDer.getHeight() * 1.3f)
                    moviendoDerecha = true;
            }
//            else{
//                if(v.x < ANCHO/2){
//                    //Primera mitad de la pantalla
//                    moviendoIzquierda = true;
//                }else{
//                    moviendoDerecha = true;
//                }
//            }
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
/*
Pantalla que almacena todos los objetos del nivel 1
Autores: Israel, Misael y Alejandro
 */
