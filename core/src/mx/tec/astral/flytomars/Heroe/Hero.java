package mx.tec.astral.flytomars.Heroe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mx.tec.astral.flytomars.EstadoSalto;
import mx.tec.astral.flytomars.Tools.Objeto;

/*
Personaje controlado por el usuario
Autor(es) : Misael Delgado, Israel Sanchez
 */

public class Hero extends Objeto {
    private Texture texturaDerecha;
    private Texture texturaIzquierda;

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

    public void setyBase(float newYbase) {
        yBase = newYbase;
    }

    public Float getVelocity(){
        return DY;
    }

    private EstadoHeroe estado;     //  States of the player (IZQUIERDA, DERECHA, MUERE)
//    private EstadoHeroe estadoPrev;
    private EstadoSalto estadoSalto;

    // Default constructor
    public Hero(Texture textura, float x, float y){
        super(textura, x, y);
    }

    // Two different textures constructor
    public Hero (Texture texturaDerecha, Texture texturaIzquierda, float x, float y){
        super( texturaDerecha, x, y);
        this.texturaDerecha = texturaDerecha;
        this.texturaIzquierda = texturaIzquierda;
        estado = EstadoHeroe.DERECHA;
    }

    // Constructor with a spriteSheet
    public Hero ( Texture texture){

        TextureRegion region = new TextureRegion(texture);
        TextureRegion[][] texturas = region.split(84, 128);

        // Frames to walk
        TextureRegion[] arrFramesCorrerIzq = { texturas[0][0], texturas[0][1], texturas[0][2], texturas[0][3], texturas[0][4], texturas[0][5],
                                            texturas[0][6], texturas[0][7], texturas[0][8], texturas[0][9], texturas[0][10], texturas[0][11]};

        TextureRegion[] arrFramesCorrerDer = { texturas[1][0], texturas[1][1], texturas[1][2], texturas[1][3], texturas[1][4], texturas[1][5],
                                            texturas[1][6], texturas[1][7], texturas[1][8], texturas[1][9], texturas[1][10], texturas[1][11]};

        animacionCorre_D = new Animation<TextureRegion>(0.08f, arrFramesCorrerDer);
        animacionCorre_D.setPlayMode(Animation.PlayMode.LOOP);

        animacionCorre_I = new Animation<TextureRegion>(0.08f, arrFramesCorrerIzq);
        animacionCorre_I.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        timerAnimation = 0;

        // IDLE
        idleD = new Sprite(texturas[1][3]);
        idleI = new Sprite(texturas[0][2]);

        sprite = new Sprite(texturas[1][3]);

        // Initial states
        estado = EstadoHeroe.DERECHA;
        estadoSalto = EstadoSalto.EN_PISO;

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
//            case SALTO:
//                actualizarVuelo();
//                switch (estadoPrev){
//                    case DERECHA:
//                    case IDLE_D:
//                        batch.draw(idleD, sprite.getX(), sprite.getY());
//                        break;
//                    case IZQUIERDA:
//                    case IDLE_I:
//                        batch.draw(idleI, sprite.getX(), sprite.getY());
//                        break;
//                }
//                break;
            default:
                Gdx.app.log("Caso no contemplado al dibujar ", estado.toString());
                break;
        }
    }


    public void cambiarEstado() {
        switch (estado){
            case IZQUIERDA:
                estado = EstadoHeroe.DERECHA;
                sprite.setTexture(texturaDerecha);
                break;
            case DERECHA:
                estado = EstadoHeroe.IZQUIERDA;
                sprite.setTexture(texturaIzquierda);
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

}