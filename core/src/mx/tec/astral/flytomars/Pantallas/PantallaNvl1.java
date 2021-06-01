package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.tec.astral.flytomars.Enemigos.EstadoAlien;
import mx.tec.astral.flytomars.EstadoJuego;
import mx.tec.astral.flytomars.EstadoPowerUps;
import mx.tec.astral.flytomars.EstadoSalto;
import mx.tec.astral.flytomars.EstadosMovimiento;
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

/**=======================================================
//              Pantalla nivel 1                        ||
// EN ESTA CLASE SE CREAN LOS OBJETOS DEL NIVEL 1       ||
//======================================================||
// IN THIS CLASS ARE CREATED LEVEL 1 OBJECTS            ||
// AUTHOR(S): ISRAEL SANCHEZ, MISAEL DELGADO, ALEJANDRO ||
 //====================================================*/


public class PantallaNvl1 extends Pantalla {
    // Estados del juego
    EstadoJuego estadoJuego;

    // Background
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer rendererMapa;
    private Music bgMusic;

    // Font of score.
    Texto texto;
    Texto textoPausa;
    Texto textoPasado;
    private int puntos = 0;

    private final Juego juego;
//    Texture texturaFondo;
//    private Stage escenaMenuNiveles;

    private final int PUNTOS_SIGUIENTE_NIVEL = 2500;

    //  Personaje (Hero)
    private Hero hero;
    private EstadoHeroe prevState = EstadoHeroe.DERECHA;
    public static final int TAM_CELDA = 32;

    //  Enemigos

    //Alien Agil
//    private AlienAgil aAgil;
    private Array<AlienAgil> arrAliensAgiles;
    private Texture spriteSheetAgil;
    private float TAMANIO_REGION_AGIL = 128f;
    private float timerCrearAlienAgil = 10;
    private float timerCambioAgil;
    private final float TIEMPO_CREAR_AGIL = 10;
    private final float TIEMPO_CAMBIO_AGIL = 1f;

    //Alien Letal
//    private AlienLetal aLetal;
    private Array<AlienLetal> arrLetales;
    Texture spriteSheetLetal;
    private float TAMANIO_REGION_LETAL = 128f;
    private float timerCrearAlienLetal=10;
    private float timerCambioLetal;
    private final float TIEMPO_CREAR_LETAL = 30;
    private final float TIEMPO_CAMBIO_LETAL = 6f;
    private int counterHitLetal = 0;

    //Alien Tanque
    private Array<AlienTanque> arrTanques;
    private Texture spriteSheetTanque;
    private float TAMANIO_REGION_TANQUE = 128f;
    private float TIMER_CREAR_TANQUE= 20;
    private float timerCambioTanque;
    private final float TIEMPO_CAMBIO_TANQUE = 6f;
    private int counterHitTanque = 0;


    //  Objetos vida
    private Array<Vida> arrVidas;

    // BALAS
    private Array<Bala> arrBalas;
    private Texture texturaBalaDer;
    private Texture texturaBalaIzq;
    private Bala bala;

    //  Buttons
//    private Texture texturaBack;
    private Texture texturaA;
    private Texture texturaB;
    private Texture texturaIzq;
    private Texture texturaDer;
    private Texture texturaPause;

//  Objetos de PowerUps
    private Texture texturaVida;
    private float timerPower = 0f;
    private final float TIEMPO_CREAR_ITEM = 10.0f;


    private Array<PowerUp> arrPowerUps;


    //Clase powerUp
    private PowerUp powerUp;

    //Indican si el Hero se mueve en cierta dirección
    private boolean moviendoIzquierda = false;
    private boolean moviendoDerecha = false;

    // PAUSE
    private EscenaPausa escenaPausa;

    private ProcesadorEntrada procesadorEntrada;


    public PantallaNvl1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

        crearFondo();
        cargarMusica();

        crearAlienAgil();
        crearAlienLetal();
        crearAlienTanque();

        //crea los objetos
        crearHero();
        crearVidas();
        crearPowerUp();

//        crearBotonBack();
        crearBotonA();
        crearBotonB();
        crearBotonIzq();
        crearBotonDer();
        crearBotonPause();

        crearTexto();

        crearBalas();

        //Ahora la misma pantalla RECIBE Y PROCESA los eventos
        procesadorEntrada = new ProcesadorEntrada();
        Gdx.input.setInputProcessor(procesadorEntrada);

        Gdx.input.setCatchKey( Input.Keys.BACK, true );

        estadoJuego = EstadoJuego.EN_JUEGO;
    }

/**======================================================
//              CRERACION DE OBJETOS                   ||
//====================================================*/

    private void crearFondo() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load("mapas/Mapa1.tmx", TiledMap.class);
        manager.finishLoading();
        mapa = manager.get("mapas/Mapa1.tmx");
        rendererMapa = new OrthogonalTiledMapRenderer(mapa);

    }

    private void crearTexto() {
        texto = new Texto();
        textoPausa = new Texto();
        textoPasado = new Texto();
    }

    private void cargarMusica() {
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Efectos/SPACE!!!.mp3"));
        bgMusic.play();
        bgMusic.setVolume(0.12f);
        bgMusic.setLooping(true);
    }


/**======================================================
//                      BOTONES                        ||
//====================================================*/

    private void crearBotonPause() {
        texturaPause = new Texture("buttons/Pause_Idle.png");
    }

    private void crearBotonDer() {
        texturaDer = new Texture("buttons/btnDerecha.png");
    }

    private void crearBotonIzq() {
        texturaIzq = new Texture("buttons/btnIzquierda.png");
    }

     private void crearBotonA() {
        texturaA = new Texture("buttons/btn_A.png");
    }

    private void crearBotonB() {
        texturaB = new Texture("buttons/btn_B.png");
    }

//    private void crearBotonBack() {
//        texturaBack = new Texture("Menu/btn_back.png");
//    }

/**======================================================
//                    PERSONAJE                        ||
//====================================================*/

    private void crearHero() {
        Texture spriteSheet = new Texture("nivel1/AstronautaSPRITES.png");

        hero = new Hero(spriteSheet);
        hero.setPosition(0, 64);

        hero.cargarMapa(mapa, TAM_CELDA, 2);
    }

    private void crearPowerUp() {
        arrPowerUps = new Array<>();
    }

    private void crearBalas() {
        arrBalas = new Array<>();
        texturaBalaDer = new Texture("Shots/shotDer.png");
        texturaBalaIzq = new Texture("Shots/shotIzq.png");
    }

    private void crearVidas() {
        texturaVida = new Texture("items/heart.png");

        arrVidas = new Array<>(hero.getVidas());
        for (int i = 0; i < 3; i++){
            Vida vida = new Vida(texturaVida,  ANCHO-(i*60+65),ALTO-60);
            arrVidas.add(vida); //Lo guarda en el arrelo
        }
    }

/**======================================================
//                     ENEMIGOS                        ||
//====================================================*/

    private void crearAlienTanque() {
        spriteSheetTanque = new Texture("enemigos/Tanque.png");
        arrTanques = new Array<>();

    }

    private void crearAlienLetal() {
        spriteSheetLetal = new Texture("enemigos/Letal.png");
        arrLetales= new Array<>();

    }

    private void crearAlienAgil() {
        spriteSheetAgil = new Texture("enemigos/Agil.png");
        arrAliensAgiles= new Array<>();
    }


/**======================================================
//                UPDATE DEL JUEGO                    ||
//===================================================*/
    @Override
    public void render(float delta) {
        if(estadoJuego == EstadoJuego.EN_JUEGO)
            actualizar(delta);
        else if ( estadoJuego == EstadoJuego.PERDIO)
            bgMusic.setVolume(0);

        borrarPantalla(0, 0, 0); //Borrar con color negro}
        batch.setProjectionMatrix(camara.combined);


/**======================================================
//                      MAPA                           ||
//======================================================
*/

        rendererMapa.setView(camara);
        rendererMapa.render();

        batch.begin();

        //batch.draw(texturaFondo, 0, 0);

        //Vidas
        for (Vida vida : arrVidas) //Visita cada objeto del arreglo
        {
            vida.render(batch);
        }

/**======================================================
//                       ENEMIGOS                      ||
//======================================================
*/

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

/**======================================================
//                PERSONAJE PRINCIPAL                  ||
//======================================================
*/
        hero.render(batch);

        for (PowerUp powerUp : arrPowerUps ) {
            powerUp.render(batch);
        }

/**======================================================
//                       BOTONES                       ||
//======================================================
*/
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

        if ( estadoJuego == EstadoJuego.PAUSA && escenaPausa != null)
            escenaPausa.draw();

        if ( Gdx.input.isKeyPressed(Input.Keys.BACK) )
            juego.setScreen( new PantallaJuego(juego) );

        if ( !juego.isPassedLvl1 && puntos >= PUNTOS_SIGUIENTE_NIVEL )
            juego.isPassedLvl1 = true;

        if (estadoJuego == EstadoJuego.PAUSA) {
            batch.begin();
            textoPausa.mostrarMensaje(batch, "Score actual: " + puntos, ANCHO/2 - 50, ALTO - 250);
            if ( !juego.isPassedLvl1 )
                textoPasado.mostrarMensaje(batch, "Siguiente nivel a: " + (PUNTOS_SIGUIENTE_NIVEL - puntos) + " puntos", ANCHO/2 - 40, ALTO - 350);
            else
                textoPasado.mostrarMensaje(batch, "Nivel 2 desbloqueado!", ANCHO/2 - 50, ALTO - 350);

            batch.end();
        }

        if (estadoJuego == EstadoJuego.PERDIO){

        }
    }

    private void actualizar(float delta){
        actualizarHero(hero);
        actualizarBalas(delta);
        crearAgil(delta);
        crearTanque(delta);
        crearLetal(delta);
        actualizarItems(delta);
        depurarPowerUps();

        if(arrBalas.size != 0){
            colisionesAlienAgil();
            colisionesAlienLetal();
            colisionesAlienTanque();

    }
    }

    private void actualizarItems(float delta) {
        timerPower += delta;
        // Aproximadamente se crea un powerUp c/30 seg.
        if ( timerPower >= TIEMPO_CREAR_ITEM) {
            timerPower = 0;
            int posX = (int) Math.floor(Math.random()*((ANCHO-(80)-14)+15));
            int posY = (int) Math.floor(Math.random()*((ALTO-(40))-2*TAM_CELDA+1)+2*TAM_CELDA);

            int tipo = (int) Math.floor(Math.random()*3);
            powerUp = new PowerUp(posX, posY, tipo);

            arrPowerUps.add(powerUp);

        }
    }

    private void comprobarVidas() {
        if(arrVidas.size <= 0) {
            estadoJuego = EstadoJuego.PERDIO;
            juego.perder.play();
        }
    }

/**======================================================
//                    E. AGILES                        ||
//====================================================*/

    private void crearAgil(float delta) {
        timerCrearAlienAgil += delta;

        if(timerCrearAlienAgil >= TIEMPO_CREAR_AGIL){
            timerCrearAlienAgil = 0;

            //Crear
            float xAgil= MathUtils.random(10,ANCHO-TAMANIO_REGION_AGIL);
            float yAgil= MathUtils.random(10,ALTO-TAMANIO_REGION_AGIL);
            AlienAgil aAgil= new AlienAgil(spriteSheetAgil, xAgil,yAgil);
            aAgil.cargarMapa(mapa, TAM_CELDA);
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

            alienAgil.verificarPlataforma(2);

            if(alienAgil.getSprite().getY() < 2*TAM_CELDA)
                alienAgil.caer();
            if ( alienAgil.getEstadoSalto() == EstadoSalto.CAIDA_LIBRE  && alienAgil.getSprite().getY() <= -alienAgil.getSprite().getHeight())
                alienAgil.getSprite().setY(ALTO);
            depurarAlienAgil();
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
                    puntos +=50;
                    //Desaparecer la bala

                    arrBalas.removeIndex(j);
                }
            }
        }
    }

    private void depurarAlienAgil() {
        for (int i = arrAliensAgiles.size-1; i>=0; i--){
            AlienAgil agil = arrAliensAgiles.get(i);
            if(agil.getEstado() == EstadoAlien.MUERE)
                arrAliensAgiles.removeIndex(i);
        }
    }


/**======================================================
//                  E. LETALES                         ||
//====================================================*/

    private void crearLetal(float delta) {
        timerCrearAlienLetal+=delta;
        if(timerCrearAlienLetal>=TIEMPO_CREAR_LETAL){
            timerCrearAlienLetal=0;
            //Crear
            float xLetal= MathUtils.random(10,ANCHO-TAMANIO_REGION_LETAL);
            float yLetal= MathUtils.random(10,ALTO-TAMANIO_REGION_LETAL);
            AlienLetal aLetal = new AlienLetal(spriteSheetLetal, xLetal, yLetal);
            aLetal.cargarMapa(mapa, TAM_CELDA);
            arrLetales.add(aLetal);
        }
        moverAliensLetales(delta);
    }

    private void moverAliensLetales(float delta) {
        for (AlienLetal alienLetal : arrLetales) {
            timerCambioLetal+=delta;
            if(timerCambioLetal >= TIEMPO_CAMBIO_LETAL){
                int tipo = MathUtils.random(1,2);
                timerCambioLetal = 0;
                if( tipo == 1 && alienLetal.getEstado() == EstadoAlien.DERECHA)
                    alienLetal.cambiarEstado();
                else if(tipo == 2 && alienLetal.getEstado() == EstadoAlien.IZQUIERDA)
                    alienLetal.cambiarEstado();
            }

            alienLetal.moverHorizontal();


            if(alienLetal.getSprite().getX() <= 0 - alienLetal.getSprite().getWidth())
                alienLetal.setX(ANCHO);
            else if (alienLetal.getSprite().getX() >= ANCHO)
                alienLetal.setX(0);

            alienLetal.verificarPlataforma(2);

            if(alienLetal.getSprite().getY() < 2*TAM_CELDA)
                alienLetal.caer();
            if ( alienLetal.getEstadoSalto() == EstadoSalto.CAIDA_LIBRE  && alienLetal.getSprite().getY() <= -alienLetal.getSprite().getHeight())
                alienLetal.getSprite().setY(ALTO);

            depurarAlienLetal();
        }
    }

    private void colisionesAlienLetal() {
        for(int i= arrLetales.size-1; i>=0; i--){
            AlienLetal alienLetal = arrLetales.get(i);
            if (alienLetal.getEstado() == EstadoAlien.MUERE )
                arrLetales.removeIndex(i);

            for( int j = arrBalas.size-1; j >=0; j--){
                bala = arrBalas.get(j);

                if(bala.getSprite().getBoundingRectangle().overlaps(alienLetal.getSprite().getBoundingRectangle())){
                    counterHitLetal ++;
                    if (counterHitLetal == 3) {
                        //Le pegó
                        alienLetal.setEstado(EstadoAlien.MUERE);
                        //Contar puntos
                        puntos += 150;
                        //Desaparecer la bala
                        counterHitLetal = 0;
                    }
                    arrBalas.removeIndex(j);
                }
            }
        }
    }

    private void depurarAlienLetal() {
        for (int i = arrLetales.size-1; i>=0; i--){
            AlienLetal letal = arrLetales.get(i);
            if(letal.getEstado() == EstadoAlien.MUERE)
                arrLetales.removeIndex(i);
        }
    }

/**======================================================
//                  E. TANQUE                          ||
//====================================================*/

    private void crearTanque(float delta) {
    TIMER_CREAR_TANQUE += delta;

    if(TIMER_CREAR_TANQUE >= TIEMPO_CREAR_AGIL){
        TIMER_CREAR_TANQUE = 0;

        //Crear
        float xTanque= MathUtils.random(10,ANCHO-TAMANIO_REGION_TANQUE);
        float yTanque= MathUtils.random(10,ALTO-TAMANIO_REGION_TANQUE);
        AlienTanque aTanque= new AlienTanque(spriteSheetTanque, xTanque,yTanque);
        aTanque.cargarMapa(mapa, TAM_CELDA);
        arrTanques.add(aTanque);
    }
    moverAliensTanques(delta);
}

    private void moverAliensTanques(float delta) {
        for (AlienTanque alienTanque : arrTanques) {
            timerCambioTanque+=delta;
            if(timerCambioTanque >= TIEMPO_CAMBIO_TANQUE){
                int tipo = MathUtils.random(1,2);
                timerCambioTanque = 0;
                if( tipo == 1 && alienTanque.getEstado() == EstadoAlien.DERECHA)
                    alienTanque.cambiarEstado();
                else if(tipo == 2 && alienTanque.getEstado() == EstadoAlien.IZQUIERDA)
                    alienTanque.cambiarEstado();
            }

            alienTanque.moverHorizontal();


            if(alienTanque.getSprite().getX() <= 0 - alienTanque.getSprite().getWidth())
                alienTanque.setX(ANCHO);
            else if (alienTanque.getSprite().getX() >= ANCHO)
                alienTanque.setX(0);

            alienTanque.verificarPlataforma(2);

            if(alienTanque.getSprite().getY() < 2*TAM_CELDA)
                alienTanque.caer();
            if ( alienTanque.getEstadoSalto() == EstadoSalto.CAIDA_LIBRE  && alienTanque.getSprite().getY() <= -alienTanque.getSprite().getHeight())
                alienTanque.getSprite().setY(ALTO);


            depurarAlienTanque();
        }
    }

    private void colisionesAlienTanque() {
        for(int i= arrTanques.size-1; i>=0; i--){
            AlienTanque alienTanque = arrTanques.get(i);
            if (alienTanque.getEstado() == EstadoAlien.MUERE )
                arrTanques.removeIndex(i);

            for( int j = arrBalas.size-1; j >=0; j--){
                bala = arrBalas.get(j);

                if(bala.getSprite().getBoundingRectangle().overlaps(alienTanque.getSprite().getBoundingRectangle())){
                    counterHitTanque ++;
                    System.out.println("" + counterHitTanque);
                    if (counterHitTanque == 5) {
                        //Le pegó
                        alienTanque.setEstado(EstadoAlien.MUERE);
                        //Contar puntos
                        puntos += 200;
                        //Desaparecer la bala
                        counterHitTanque = 0;
                    }
                    arrBalas.removeIndex(j);
                }
            }
        }
    }

    private void depurarAlienTanque() {
        for (int i = arrTanques.size-1; i>=0; i--){
            AlienTanque tanque = arrTanques.get(i);
            if(tanque.getEstado() == EstadoAlien.MUERE)
                arrTanques.removeIndex(i);
        }
    }



/**======================================================
//                Objetos Disparo                      ||
//====================================================*/

    private void actualizarBalas(float delta) {
        for (int i = arrBalas.size-1; i >= 0;i--){
            Bala bala = arrBalas.get(i);

            bala.mover(delta);

            if(bala.getX() > ANCHO || bala.getX() < -200)
                arrBalas.removeIndex(i);
        }
    }

/**======================================================
//                PERSONAJE PRINC.                     ||
//====================================================*/

    private void actualizarHero(Hero hero) {
        if (moviendoDerecha && hero.getEstado() != EstadoHeroe.SALTO)
            hero.setEstado(EstadoHeroe.DERECHA);

        else if (moviendoIzquierda && hero.getEstado() != EstadoHeroe.SALTO)
            hero.setEstado(EstadoHeroe.IZQUIERDA);

        if ( moviendoIzquierda || moviendoDerecha) {
//            hero.mover();

            hero.probarChoqueParedes();
        }

        else if ( hero.getEstado() != EstadoHeroe.SALTO ){

            if (prevState == EstadoHeroe.DERECHA)
                hero.setEstado(EstadoHeroe.IDLE_D);

            else if (prevState == EstadoHeroe.IZQUIERDA)
                hero.setEstado(EstadoHeroe.IDLE_I);

            else if (hero.getEstadoMovimiento() == EstadosMovimiento.QUIETO && prevState == EstadoHeroe.DERECHA)
                hero.setEstado(EstadoHeroe.IDLE_D);

            else if (hero.getEstadoMovimiento() == EstadosMovimiento.QUIETO && prevState == EstadoHeroe.IZQUIERDA)
                hero.setEstado(EstadoHeroe.IDLE_I);
        }

        if( moviendoDerecha )
            prevState = EstadoHeroe.DERECHA;
        else if( moviendoIzquierda )
            prevState = EstadoHeroe.IZQUIERDA;

        if ( moviendoDerecha && hero.getSprite().getX() >= ANCHO )
            hero.setX( 0 - hero.getSprite().getWidth() );

        if ( hero.getEstadoSalto() == EstadoSalto.CAIDA_LIBRE  && hero.getSprite().getY() <= -hero.getSprite().getHeight())
            hero.getSprite().setY(ALTO);


        else if ( moviendoIzquierda && hero.getSprite().getX() < 0 - hero.getSprite().getWidth() )
            hero.setX( ANCHO );

        switch (hero.getEstadoSalto()){
            case SUBIENDO:
            case BAJANDO:
                hero.actualizarVuelo();
        }

        if(hero.getSprite().getY() < 2*TAM_CELDA)
            hero.caer();

        if ( hero.getSprite().getX() > 0 && hero.getSprite().getX() < ANCHO )
        hero.verificarPlataforma();

        hero.colision(arrAliensAgiles);
        hero.colision(arrPowerUps);
        hero.colision(arrLetales);
        hero.colision(arrTanques);
        arrVidas.size = hero.getVidas();

        if(hero.getObtuvoMoneda()) {
            puntos += 50;
            hero.setObtuvoMoneda(false);
        }

        comprobarVidas();

    }

    private void depurarPowerUps() {
        for (int i = arrPowerUps.size-1; i>=0; i--){
            PowerUp powerUp = arrPowerUps.get(i);
            if(powerUp.getEstado() == EstadoPowerUps.TOMADO)
                arrPowerUps.removeIndex(i);
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
        if (arrVidas.size > 0)
            arrVidas.clear();
        batch.dispose();
        arrBalas.clear();
        arrAliensAgiles.clear();
        arrTanques.clear();
        arrLetales.clear();
        bgMusic.dispose();
//        juego.mp3.dispose();

    }


/**======================================================
//              PROCESADOR ENTRADA                     ||
//====================================================*/

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
                    v.y >= ALTO - texturaPause.getHeight()*1.5f && v.y <= ALTO - texturaPause.getHeight()*0.5f) {
//                juego.setScreen(new PantallaJuego(juego));
                if ( escenaPausa == null )
                    escenaPausa = new EscenaPausa(vista);
                estadoJuego = EstadoJuego.PAUSA;
                Gdx.input.setInputProcessor(escenaPausa);
//                bgMusic.pause();
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

            if(estadoJuego != EstadoJuego.PERDIO) {
                //  A button (Jump)
                if (v.x >= ANCHO - texturaA.getWidth() * 2 && v.x <= ANCHO - texturaA.getWidth() &&
                        v.y >= texturaA.getHeight() / 2f && v.y <= texturaA.getHeight() * 1.5f) {

                    if(hero.getEstadoSalto() == EstadoSalto.EN_PISO)
                        juego.soundSalto.play(.5f);
                    hero.saltar();
                }

                //  B button (Shoot)
                else if (v.x >= ANCHO - texturaB.getWidth() && v.x <= ANCHO &&
                        v.y >= texturaB.getHeight() / 2f && v.y <= texturaB.getHeight() * 1.5f) {

                    //Sonido disparo
                    juego.soundDisparo.play(.2f);
                    Bala bala = new Bala(texturaBalaIzq, texturaBalaDer, hero.getSprite().getX() + hero.getSprite().getWidth(),
                            (hero.getSprite().getY() + hero.getSprite().getHeight() / 2f));

                    if (hero.getEstado() == EstadoHeroe.DERECHA || prevState == EstadoHeroe.DERECHA) {
                        bala.setEstado(EstadoBala.DERECHA);
                        bala.setPosition((hero.getSprite().getX() + hero.getSprite().getWidth()) - bala.getSprite().getWidth() / 2f,
                                (hero.getSprite().getY() + hero.getSprite().getHeight() / 2f) - bala.getSprite().getHeight() / 2f);
                        arrBalas.add(bala);
                    } else if (hero.getEstado() == EstadoHeroe.IZQUIERDA || prevState == EstadoHeroe.IZQUIERDA) {
                        bala.setEstado(EstadoBala.IZQUIERDA);
                        bala.setPosition(hero.getSprite().getX() - bala.getSprite().getWidth() / 2f,
                                (hero.getSprite().getY() + hero.getSprite().getHeight() / 2f) - bala.getSprite().getHeight() / 2);
                        arrBalas.add(bala);
                    }
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

    private class EscenaPausa extends Stage{
        private Texture texturaFondo;
        private int volumen = 2;

        public EscenaPausa(Viewport vista){
            super(vista);

            texturaFondo = new Texture("pausa/fondoPausa.png");
            Image imageFondo = new Image(texturaFondo);
            imageFondo.setPosition(ANCHO/2, ALTO/2, Align.center);
            addActor(imageFondo);

            Texture textureBtnContinuar = new Texture("pausa/Play_Idle.png");
            TextureRegionDrawable trdContinuar = new TextureRegionDrawable(textureBtnContinuar);
            Button btnContinuar = new Button(trdContinuar);

            addActor(btnContinuar);
            btnContinuar.setPosition(ANCHO/2, 0.3f*ALTO, Align.center);

            Texture textureBtnBajarV = new Texture("pausa/Mute_Idle.png");
            TextureRegionDrawable trdBajarV = new TextureRegionDrawable(textureBtnBajarV);
            Button btnBajarV = new Button(trdBajarV);

            addActor(btnBajarV);
            btnBajarV.setPosition(ANCHO/3, 0.3f*ALTO, Align.center);

            Texture textureBtnSubirV = new Texture("pausa/Volume_2_Idle.png");
            TextureRegionDrawable trdSubirV = new TextureRegionDrawable(textureBtnSubirV);
            Button btnSubirV = new Button(trdSubirV);

            addActor(btnSubirV);
            btnSubirV.setPosition(ANCHO*2/3, 0.3f*ALTO, Align.center);


            btnContinuar.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if(arrVidas.size > 0) {
                        estadoJuego = EstadoJuego.EN_JUEGO;
//                        bgMusic.play();
                    } else
                        estadoJuego = EstadoJuego.PERDIO;

                    Gdx.input.setInputProcessor(procesadorEntrada);
                }
            });

            btnBajarV.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if(volumen > 0 ) {
                        volumen--;
                        switch (volumen){
                            case 0:
                                bgMusic.setVolume(0f);
                                break;
                            case 1:
                                bgMusic.setVolume(0.06f);
                                break;
                            case 2:
                                bgMusic.setVolume(0.12f);
                                break;
                            case 3:
                                bgMusic.setVolume(0.18f);
                                break;
                            default:
                                break;
                        }
                    }
                }
            });

            btnSubirV.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if(volumen < 4 ) {
                        volumen++;
                        switch (volumen){
                            case 1:
                                bgMusic.setVolume(0.06f);
                                break;
                            case 2:
                                bgMusic.setVolume(0.12f);
                                break;
                            case 3:
                                bgMusic.setVolume(0.18f);
                                break;
                            case 4:
                                bgMusic.setVolume(0.24f);
                                break;
                            default:
                                break;
                        }
                    }
                }
            });

        }
    }
}
