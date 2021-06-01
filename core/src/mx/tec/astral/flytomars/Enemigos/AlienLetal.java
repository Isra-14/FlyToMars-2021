package mx.tec.astral.flytomars.Enemigos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import mx.tec.astral.flytomars.EstadoSalto;
import mx.tec.astral.flytomars.EstadosMovimiento;
import mx.tec.astral.flytomars.Tools.Objeto;
import mx.tec.astral.flytomars.Heroe.EstadoHeroe;

public class AlienLetal extends Objeto {
    private float DX = 4;
    private float DY = -4f;
    EstadoAlien estado;
    EstadoSalto estadoSalto;

    // Animacion
    private Animation<TextureRegion> animacionCorre_D;
    private Animation<TextureRegion> animacionCorre_I;
    private float timerAnimation;


    // Mapa
    private int TAM_CELDA;
    private TiledMap mapa;

    // Jump
    private float yBase;     // Floor
    private float tAire;                // Time in the air
    private float tVuelo;               // Fly time
    private final float v0y = 225;      // Y component of velocity
    private final float g = 150f;      // Pixels/s^2 -> Gravity

//    public AlienLetal(Texture textura, float x, float y){
//        super(textura, x, y);
//    }

    public AlienLetal(Texture spriteSheet, float x, float y){
        TextureRegion region = new TextureRegion(spriteSheet);
        TextureRegion[][] texturas = region.split(128, 128);

        TextureRegion[] arrCorrerIzquierda = {texturas[0][0],texturas[0][1],texturas[0][2], texturas[0][3], texturas[0][4], texturas[1][0],
        texturas[1][1], texturas[1][2], texturas[1][3], texturas[1][4]};

        TextureRegion[] arrCorrerDerecha = {texturas[2][0],texturas[2][1],texturas[2][2], texturas[2][3], texturas[2][4], texturas[3][0],
        texturas[3][1], texturas[3][2], texturas[3][3], texturas[3][4]};

        animacionCorre_D = new Animation<>(0.08f, arrCorrerDerecha);
        animacionCorre_D.setPlayMode(Animation.PlayMode.LOOP);

        animacionCorre_I = new Animation<>(0.08f, arrCorrerIzquierda);
        animacionCorre_I.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        timerAnimation = 0;

        sprite = new Sprite(texturas[1][3]);
        sprite.setPosition(x, y);

        // Initial states
        estado = EstadoAlien.DERECHA;
        estadoSalto = EstadoSalto.EN_PISO;
    }

    public EstadoAlien getEstado() { return estado; }

    public void cambiarEstado() {
        switch (estado){
            case IZQUIERDA:
                estado = EstadoAlien.DERECHA;
                break;
            case DERECHA:
                estado = EstadoAlien.IZQUIERDA;
                break;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame;
        float delta = Gdx.graphics.getDeltaTime();
        switch (estado) {
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
                break;
        }
    }

    public void moverHorizontal() {
        switch (estado) {
            case DERECHA:
                sprite.setX(sprite.getX() + DX);
                break;
            case IZQUIERDA:
                sprite.setX(sprite.getX() - DX);
                break;
            default:
                break;
        }

    }

    public void setEstado(EstadoAlien estado){
        this.estado = estado;
    }

    public void setPosition (float newX, float newY){
        sprite.setPosition(newX, newY);
    }

    public Sprite getSprite(){return sprite;}

    public void setX(float dx){
        sprite.setX(dx);
    }

    public void cargarMapa(TiledMap mapa, int TAM_CELDA){
        this.mapa = mapa;
        this.TAM_CELDA = TAM_CELDA;
    }

    public void setyBase(float newYbase) {
        yBase = newYbase;
    }

    public void caer(){
        sprite.setY(sprite.getY() + DY);
    }

    public EstadoSalto getEstadoSalto(){
        return estadoSalto;
    }
    public void setEstadoSalto (EstadoSalto estadoSalto){
        this.estadoSalto = estadoSalto;
    }

    public void verificarPlataforma(int c){
        if ( getEstadoSalto() != EstadoSalto.SUBIENDO ) {
            int celdaX = (int) (sprite.getX() / TAM_CELDA);
            int celdaY = (int) ( (sprite.getY() + DY) / TAM_CELDA);

            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(c);
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
}


