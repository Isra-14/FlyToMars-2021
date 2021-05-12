package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import mx.tec.astral.flytomars.Enemigos.EstadoAlien;
import mx.tec.astral.flytomars.EstadoJuego;
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
    // Estados del juego
    EstadoJuego estadoJuego;

    // Background
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;

    // Font of score.
    Texto texto;
    private int puntos = 0;


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
    private float timerCrearAlienAgil = 10;
    private final float DX_PASO_ALIEN_AGIL = 10;
    private float timerCambioAgil;
    private final float TIEMPO_CREAR_AGIL = 10;
    private final float TIEMPO_CAMBIO_AGIL = 1f;

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

    // BALAS
    private Array<Bala> arrBalas;
    private Texture texturaBalaDer;
    private Texture texturaBalaIzq;
    private Bala bala;

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


    public PantallaNvl1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

        crearFondo();

        crearAlienAgil();
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

        crearHero();
        //Ahora la misma pantalla RECIBE Y PROCESA los eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());

        estadoJuego = EstadoJuego.EN_JUEGO;
    }

    private void crearPowerUp() {
        texturaEscudo = new Texture("items/shield.png");
        texturaMoneda = new Texture("items/coin.png");
        powerUp = new PowerUp(texturaVida, texturaEscudo, texturaMoneda, 0, 0);
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
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapas/Mapa1.tmx", TiledMap.class);
        manager.finishLoading();
        mapa = manager.get("mapas/Mapa1.tmx");
        rendererMapa = new OrthogonalTiledMapRenderer(mapa);

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
        Texture spriteSheet = new Texture("nivel1/heroSprites.png");

        hero = new Hero(spriteSheet);
        hero.setPosition(0, 64);

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

//======================================================
//                UPDATE DEL JUEGO                    ||
//======================================================

    @Override
    public void render(float delta) {
        if(estadoJuego != EstadoJuego.PERDIO)
            actualizar(delta);

        borrarPantalla(0, 0, 0); //Borrar con color negro}
        batch.setProjectionMatrix(camara.combined);

        rendererMapa.setView(camara);
        rendererMapa.render();

        batch.begin();

        //batch.draw(texturaFondo, 0, 0);

        //Vidas
        for (Vida vida : arrVidas) //Visita cada objeto del arreglo
        {
            vida.render(batch);
        }

//======================================================
//                      ENEMIGOS                      ||
//======================================================

        //Alien Agil
        for (AlienAgil aAgil : arrAliensAgiles) {
            aAgil.render(batch);
        }

        //Alien Letal
        for (AlienLetal aLetal : arrLetales) {
            aLetal.render(batch);
        }

        //Alien Tanque
        for (AlienTanque atanque : arrTanques) {
            atanque.render(batch);
        }

        // Draw shots
        for (Bala bala : arrBalas) {
            bala.render(batch);
        }

//======================================================
//                PERSONAJE PRINCIPAL                  ||
//======================================================

        hero.render(batch);


//======================================================
//                      BOTONES                       ||
//======================================================

        // Draw A
        batch.draw(texturaA, ANCHO - texturaA.getWidth() * 2, texturaA.getHeight() / 2f);

        // Draw B
        batch.draw(texturaB, ANCHO - texturaB.getWidth(), texturaB.getHeight() / 2f);

        // Draw izq.
        batch.draw(texturaIzq, texturaIzq.getWidth() / 2f, texturaIzq.getHeight() / 3f);

        // Draw der.
        batch.draw(texturaDer, 2 * texturaDer.getWidth(), texturaDer.getHeight() / 3f);

        // Draw pause.
        batch.draw(texturaPause, ANCHO / 2 - texturaPause.getWidth() / 2f, ALTO - texturaPause.getHeight() * 1.5f);

        texto.mostrarMensaje(batch, "Score:" + puntos, 150, ALTO - 25);

        //probabilidad(batch);

        batch.end();
    }

    private void actualizar(float delta){
        actualizarHero(hero);
        actualizarBalas(delta);
        crearAgil(delta);
        actualizarTanque(delta);
        actualizarLetal(delta);


        // moverAliensAgiles(delta);

        if(arrBalas.size != 0)
            colisionesAlienAgil();
    }

    private void probabilidad(SpriteBatch batch) {

        int chance = (int)(Math.random()*100);
        if (chance < 80)
            powerUp.render(batch);

    }

    private void comprobarVidas() {
        if(arrVidas.size <= 0)
            estadoJuego = EstadoJuego.PERDIO;
    }

    private void crearAgil(float delta) {
        timerCrearAlienAgil += delta;

        if(timerCrearAlienAgil >= TIEMPO_CREAR_AGIL){
            timerCrearAlienAgil = 0;

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

            alienAgil.moverHorizontal();


            if(alienAgil.getSprite().getX() <= 0 - alienAgil.getSprite().getWidth())
                alienAgil.setX(ANCHO);
            else if (alienAgil.getSprite().getX() >= ANCHO)
                alienAgil.setX(0);

//                          Movimiento Solo dentro de pantalla
//            if(alienAgil.getSprite().getX() >= 0 && alienAgil.getSprite().getX() <= ANCHO - alienAgil.getSprite().getWidth())
//                alienAgil.moverHorizontal();
//            else {
//                if (alienAgil.getSprite().getX() <= 0 && alienAgil.getEstado() == EstadoAlien.DERECHA)
//                    alienAgil.setX(1);
//                else if (alienAgil.getX() >= ANCHO - alienAgil.getSprite().getWidth() && alienAgil.getEstado() == EstadoAlien.IZQUIERDA)
//                    alienAgil.setX(ANCHO - alienAgil.getSprite().getWidth() - 1);
//            }
            depurarAlienAgil();
        }
    }

    private void depurarAlienAgil() {
        for (int i = arrAliensAgiles.size-1; i>=0; i--){
            AlienAgil agil = arrAliensAgiles.get(i);
            if(agil.getEstado() == EstadoAlien.MUERE)
                arrAliensAgiles.removeIndex(i);
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

        int velocidad = 10;
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

    private void colisionesAlienAgil() {
        for(int i= arrAliensAgiles.size-1; i>=0; i--){
            AlienAgil alienAgil = arrAliensAgiles.get(i);
            if (alienAgil.getEstado() == EstadoAlien.MUERE )
                arrAliensAgiles.removeIndex(i);

            for( int j = arrBalas.size-1; j >=0; j--){
                bala = arrBalas.get(j);

                if(bala.getSprite().getBoundingRectangle().overlaps(alienAgil.getSprite().getBoundingRectangle())){
                    //Le pegó
                    alienAgil.setEstado(EstadoAlien.MUERE);
                    //Contar puntos
                    puntos +=150;
                    //Desaparecer la bala

                    arrBalas.removeIndex(j);
                }
            }
        }
    }


//    private void moverAliensAgiles(float delta) {
////        int velocidad =10;
//        for (AlienAgil alienAgil : arrAliensAgiles) {
//            timerCambioAgil += delta;
//            if (timerCambioAgil >= TIEMPO_CAMBIO_AGIL) {
//                int valor = MathUtils.random(0, 2);
//                if (valor <=1) {
////                    velocidad*=-1;
//                    alienAgil.moverHorizontal(DX_PASO_ALIEN_AGIL);
//                } else {
////                    velocidad*=-1;
//                    alienAgil.moverHorizontal(-DX_PASO_ALIEN_AGIL);
//
//                }
//                timerCambioAgil=0;
//            }
//
//
//        }
//    }


    private void actualizarBalas(float delta) {
        for (int i = arrBalas.size-1; i >= 0;i--){
            Bala bala = arrBalas.get(i);

            bala.mover(delta);

            if(bala.getX() > ANCHO || bala.getX() < -200)
                arrBalas.removeIndex(i);
        }
    }

    private void actualizarHero(Hero hero) {
        if (moviendoDerecha && hero.getEstado() != EstadoHeroe.SALTO)
            hero.setEstado(EstadoHeroe.DERECHA);

        else if (moviendoIzquierda && hero.getEstado() != EstadoHeroe.SALTO)
            hero.setEstado(EstadoHeroe.IZQUIERDA);

//        if(prevState == EstadoHeroe.DERECHA && hero.getEstado() == EstadoHeroe.IZQUIERDA)
//            hero.cambiarEstado();
//        else if(prevState == EstadoHeroe.IZQUIERDA && hero.getEstado() == EstadoHeroe.DERECHA)
//            hero.cambiarEstado();

        if ( moviendoIzquierda || moviendoDerecha)
            hero.mover();

        else if ( hero.getEstado() != EstadoHeroe.SALTO ){

            if (prevState == EstadoHeroe.DERECHA)
                hero.setEstado(EstadoHeroe.IDLE_D);

            else if (prevState == EstadoHeroe.IZQUIERDA)
                hero.setEstado(EstadoHeroe.IDLE_I);

        }

        if( moviendoDerecha )
            prevState = EstadoHeroe.DERECHA;
        else if( moviendoIzquierda )
            prevState = EstadoHeroe.IZQUIERDA;

        if ( moviendoDerecha && hero.getSprite().getX() >= ANCHO )
            hero.setX( 0 - hero.getSprite().getWidth() );

        else if ( moviendoIzquierda && hero.getSprite().getX() < 0 - hero.getSprite().getWidth() )
            hero.setX( ANCHO );

        switch (hero.getEstadoSalto()){
            case SUBIENDO:
            case BAJANDO:
                hero.actualizarVuelo();
        }

        colsionesHero();
    }

    private void colsionesHero() {
        for(int i = arrAliensAgiles.size-1; i>=0; i--){
            AlienAgil alienAgil = arrAliensAgiles.get(i);
            if( hero.getSprite().getBoundingRectangle().overlaps(alienAgil.getSprite().getBoundingRectangle())){
                alienAgil.setEstado(EstadoAlien.MUERE);
                arrVidas.removeIndex(arrVidas.size-1);

                comprobarVidas();
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
        arrVidas.clear();
        batch.dispose();
        arrBalas.clear();
        arrAliensAgiles.clear();
        arrTanques.clear();
        arrLetales.clear();

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

            // Pause button
            if(v.x >= ANCHO/2 - texturaPause.getWidth()/2f && v.x <= ANCHO/2 + texturaPause.getWidth()/2f &&
                    v.y >= ALTO - texturaPause.getHeight()*1.5f && v.y <= ALTO - texturaPause.getHeight()*0.5f)
                juego.setScreen(new PantallaJuego(juego));

            //  A button (Jump)
            else if(v.x >= ANCHO-texturaA.getWidth()*2 && v.x <=ANCHO-texturaA.getWidth() &&
                    v.y >= texturaA.getHeight()/2f && v.y <= texturaA.getHeight()*1.5f) {
                hero.saltar();
            }

            //  B button (Shoot)
            else if(v.x >= ANCHO-texturaB.getWidth() && v.x <= ANCHO &&
                    v.y >= texturaB.getHeight()/2f && v.y <= texturaB.getHeight()*1.5f) {

                Bala bala = new Bala(texturaBalaIzq, texturaBalaDer, hero.getSprite().getX() + hero.getSprite().getWidth(),
                        (hero.getSprite().getY() + hero.getSprite().getHeight()/2f));

                if(hero.getEstado() == EstadoHeroe.DERECHA || prevState == EstadoHeroe.DERECHA) {
                    bala.setEstado(EstadoBala.DERECHA);
                    bala.setPosition((hero.getSprite().getX() + hero.getSprite().getWidth()) - bala.getSprite().getWidth()/2f,
                            (hero.getSprite().getY() + hero.getSprite().getHeight()/2f) - bala.getSprite().getHeight()/2f);
                    arrBalas.add(bala);
                } else if(hero.getEstado() == EstadoHeroe.IZQUIERDA || prevState == EstadoHeroe.IZQUIERDA){
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
