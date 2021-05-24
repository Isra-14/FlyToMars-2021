package mx.tec.astral.flytomars.Heroe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

import mx.tec.astral.flytomars.Enemigos.AlienAgil;
import mx.tec.astral.flytomars.Enemigos.AlienLetal;
import mx.tec.astral.flytomars.Enemigos.AlienTanque;
import mx.tec.astral.flytomars.Enemigos.EstadoAlien;
import mx.tec.astral.flytomars.EstadoPowerUps;
import mx.tec.astral.flytomars.EstadoSalto;
import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Tools.Objeto;
import mx.tec.astral.flytomars.Tools.PowerUp;

/*
Personaje controlado por el usuario
Autor(es) : Misael Delgado, Israel Sanchez
 */

public class Hero extends Objeto {

    private int vidas;

    // Mapa
    private int TAM_CELDA;
    private TiledMap mapa;

    private Sprite idleD, idleI;

    // Animacion
    private Animation <TextureRegion> animacionCorre_D;
    private Animation <TextureRegion> animacionCorre_I;
    private float timerAnimation;
//    private Texture texturaSalto;
//    private Texture texturaMuere;

    // Velocity
    private static final float DX = 8;
    private static final float DY = -4f;

    // Jump
    private float yBase;     // Floor
    private float tAire;                // Time in the air
    private float tVuelo;               // Fly time
    private final float v0y = 225;      // Y component of velocity
    private final float g = 150f;      // Pixels/s^2 -> Gravity

    //sonidos
    public Sound soundHerido = Gdx.audio.newSound(Gdx.files.internal("Efectos/hurt.wav"));;

    public void setyBase(float newYbase) {
        yBase = newYbase;
    }

    public Float getVelocity(){
        return DY;
    }

    private EstadoHeroe estado;     //  States of the player (IZQUIERDA, DERECHA, MUERE)
//    private EstadoHeroe estadoPrev;
    private EstadoSalto estadoSalto;

    private boolean tieneEscudo;
    private boolean obtuvoMoneda;
//
//    // Default constructor
//    public Hero(Texture textura, float x, float y){
//        super(textura, x, y);
//    }
//
//    // Two different textures constructor
//    public Hero (Texture texturaDerecha, Texture texturaIzquierda, float x, float y){
//        super( texturaDerecha, x, y);
//        this.texturaDerecha = texturaDerecha;
//        this.texturaIzquierda = texturaIzquierda;
//        estado = EstadoHeroe.DERECHA;
//    }

    // Constructor with a spriteSheet
    public Hero ( Texture texture){

        TextureRegion region = new TextureRegion(texture);
        TextureRegion[][] texturas = region.split(84, 128);

        // Frames to walk
        TextureRegion[] arrFramesCorrerIzq = { texturas[0][0], texturas[0][1], texturas[0][2], texturas[0][3], texturas[0][4], texturas[0][5],
                                            texturas[0][6], texturas[0][7], texturas[0][8], texturas[0][9], texturas[0][10], texturas[0][11]};

        TextureRegion[] arrFramesCorrerDer = { texturas[1][0], texturas[1][1], texturas[1][2], texturas[1][3], texturas[1][4], texturas[1][5],
                                            texturas[1][6], texturas[1][7], texturas[1][8], texturas[1][9], texturas[1][10], texturas[1][11]};

        animacionCorre_D = new Animation<>(0.08f, arrFramesCorrerDer);
        animacionCorre_D.setPlayMode(Animation.PlayMode.LOOP);

        animacionCorre_I = new Animation<>(0.08f, arrFramesCorrerIzq);
        animacionCorre_I.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        timerAnimation = 0;

        // IDLE
        idleD = new Sprite(texturas[1][3]);
        idleI = new Sprite(texturas[0][2]);

        sprite = new Sprite(texturas[1][3]);

        // Initial states
        estado = EstadoHeroe.DERECHA;
        estadoSalto = EstadoSalto.EN_PISO;

        obtuvoMoneda = false;
        tieneEscudo = false;

        vidas = 3;

    }

    public Sprite getSprite(){ return sprite; }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame;
        float delta = Gdx.graphics.getDeltaTime();
        switch (estado){
            case IDLE_D:
                batch.draw(idleD, sprite.getX(), sprite.getY());
                break;
            case IDLE_I:
                batch.draw(idleI, sprite.getX(), sprite.getY());
                break;
            case DERECHA:
                timerAnimation += delta;
                frame = animacionCorre_D.getKeyFrame(timerAnimation);
                batch.draw(frame, sprite.getX(), sprite.getY());
                break;
            case IZQUIERDA:
                timerAnimation += delta;
                frame = animacionCorre_I.getKeyFrame(timerAnimation);
                batch.draw(frame, sprite.getX(), sprite.getY());
                break;
            default:
                Gdx.app.log("Caso no contemplado al dibujar ", estado.toString());
                break;
        }
    }

    public void setEstado( EstadoHeroe nuevoEstado){
        estado = nuevoEstado;
//        if(nuevoEstado == EstadoHeroe.MUERE)
//            sprite.setTexture(texturaMuere);
    }

    public EstadoHeroe getEstado() { return estado; }

    public void caer(){
        sprite.setY(sprite.getY() + DY);
    }

    public void mover (){
        switch (estado){
            case DERECHA:
                sprite.setX(sprite.getX() + DX);
                break;
            case IZQUIERDA:
                sprite.setX(sprite.getX() - DX);
                break;
            default:
                Gdx.app.log("Estado no contemplado mover ", estado.toString());
        }

    }

    public EstadoSalto getEstadoSalto(){
        return estadoSalto;
    }

    public void setEstadoSalto (EstadoSalto estadoSalto){
        this.estadoSalto = estadoSalto;
    }

    public void setPosition (float newX, float newY){
        sprite.setPosition(newX, newY);
    }

    public void setX(float newX){
        sprite.setX(newX);
    }

    public void saltar(){
        if(estadoSalto == EstadoSalto.EN_PISO){
            tAire = 0;
            tVuelo = 2 * v0y / g;
            //estadoPrev = estado;
            estadoSalto = EstadoSalto.SUBIENDO;
        }
    }

    public void actualizarVuelo(){
        float delta = Gdx.graphics.getDeltaTime();
        tAire += 2.5f*delta;
        float y = yBase + v0y * tAire - 0.5f * g * tAire *tAire;
        sprite.setY(y);

        if ( tAire > tVuelo/2 )
            estadoSalto = EstadoSalto.BAJANDO;

        if(tAire >= tVuelo || y <= yBase){
//            estado = estadoPrev;
            estadoSalto = EstadoSalto.EN_PISO;
            sprite.setY(yBase);
        }
    }

    public void cargarMapa(TiledMap mapa, int TAM_CELDA){
        this.mapa = mapa;
        this.TAM_CELDA = TAM_CELDA;
    }

    public void verificarPlataforma(){
        if ( getEstadoSalto() != EstadoSalto.SUBIENDO ) {
            int celdaX = (int) (sprite.getX() / TAM_CELDA);
            int celdaY = (int) ( (sprite.getY() + DY) / TAM_CELDA);

            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(2);
            TiledMapTileLayer.Cell celdaAbajo = capa.getCell(celdaX, celdaY);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(celdaX+1, celdaY);

            if( celdaAbajo==null && celdaDerecha==null ){
                caer();
                setEstadoSalto(EstadoSalto.CAIDA_LIBRE);
            }else {
                if( sprite.getY() >= 2* TAM_CELDA ) {
                    setPosition(sprite.getX(), (celdaY + 1) * TAM_CELDA);
                    setEstadoSalto(EstadoSalto.EN_PISO);
                    setyBase((celdaY + 1) * TAM_CELDA);
                }
            }
        }
    }

    /**
     * Metodo generico para identificar las colisiones contra el personaje principal
     * Recibe un arreglo de objetos de tipo T llamados objetosColision.
     *
     * */
    public < T > void colision(Array < T > objetosColision){
        for(int i = objetosColision.size-1; i>=0; i--){

            /**
             * Identifica el tipo de objeto al que pertenece el arreglo
             */

            if ( objetosColision.get(i) instanceof AlienAgil ) {
                AlienAgil alienAgil = (AlienAgil) objetosColision.get(i);
                if ( sprite.getBoundingRectangle().overlaps(alienAgil.getSprite().getBoundingRectangle())) {
                    alienAgil.setEstado(EstadoAlien.MUERE);
                    soundHerido.play();
                    vidas--;
                }
            } else if ( objetosColision.get(i) instanceof AlienTanque ){
                AlienTanque alienTanque = (AlienTanque) objetosColision.get(i);
                if (sprite.getBoundingRectangle().overlaps(alienTanque.getSprite().getBoundingRectangle()))
                {
                    alienTanque.setEstado(EstadoAlien.MUERE);
                    soundHerido.play();
                    vidas--;
                }

            } else if ( objetosColision.get(i) instanceof AlienLetal){
                AlienLetal alienLetal = (AlienLetal) objetosColision.get(i);
                if (sprite.getBoundingRectangle().overlaps(alienLetal.getSprite().getBoundingRectangle()))
                {
                    alienLetal.setEstado(EstadoAlien.MUERE);
                    soundHerido.play();
                    vidas--;
                }

            } else if ( objetosColision.get(i) instanceof PowerUp){
                PowerUp powerUp = (PowerUp) objetosColision.get(i);
                if (sprite.getBoundingRectangle().overlaps(powerUp.getSprite().getBoundingRectangle())) {
                    powerUp.setEstado(EstadoPowerUps.TOMADO);
                    switch (powerUp.getTipo()) {
                        // VIDA EXTRA
                        case 0:
                            if (vidas < 3)
                                vidas++;
                            break;

                        // Escudo
                        case 1:
                            tieneEscudo = true;
                            break;

                        // Moneda
                        case 2:
                            obtuvoMoneda = true;
                            break;

                        default:
                            Gdx.app.log("Hero Colision err:", "Error en la colision de powerUp, Caso no contemplado...");
                            break;
                    }
                }
            }
        }
    }

    public int getVidas(){
        return vidas;
    }

    public boolean getTieneEscudo() {
        return tieneEscudo;
    }

    public void setTieneEscudo(boolean tieneEscudo) {
        this.tieneEscudo = tieneEscudo;
    }

    public boolean getObtuvoMoneda() {
        return obtuvoMoneda;
    }

    public void setObtuvoMoneda(boolean obtuvoMoneda) {
        this.obtuvoMoneda = obtuvoMoneda;
    }
}