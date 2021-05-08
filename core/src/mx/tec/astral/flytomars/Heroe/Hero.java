package mx.tec.astral.flytomars.Heroe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    private float DX;
//    private Texture texturaSalto;
//    private Texture texturaMuere;

    // Jump
    private final float yBase = 64;     // Floor
    private float tAire;                // Time in the air
    private float tVuelo;               // Fly time
    private final float v0y = 225;      // Y component of velocity
    private final float g = 150f;      // Pixels/s^2 -> Gravity


    private mx.tec.astral.flytomars.Heroe.EstadoHeroe estado;     //  States of the player (IZQUIERDA, DERECHA, SALTA, MUERE)
    private mx.tec.astral.flytomars.Heroe.EstadoHeroe estadoPrev;


    public Hero(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public Hero (Texture texturaDerecha, Texture texturaIzquierda, float x, float y, float dx){
        super( texturaDerecha, x, y);
        this.texturaDerecha = texturaDerecha;
        this.texturaIzquierda = texturaIzquierda;
        DX = dx;
        estado = EstadoHeroe.DERECHA;
    }

    public Hero ( Texture texture, float x, float y, float dx){
        TextureRegion region = new TextureRegion(texture);
        TextureRegion[][] texturas = region.split(84, 128);

        // Frames to talk
        TextureRegion[] arrFramesCorrerIzq = { texturas[0][0], texturas[0][1], texturas[0][2], texturas[0][3], texturas[0][4], texturas[0][5],
                                            texturas[0][6], texturas[0][7], texturas[0][8], texturas[0][9], texturas[0][10], texturas[0][11]};

        TextureRegion[] arrFramesCorrerDer = { texturas[1][0], texturas[1][1], texturas[1][2], texturas[1][3], texturas[1][4], texturas[1][5],
                                            texturas[1][6], texturas[1][7], texturas[1][8], texturas[1][9], texturas[1][10], texturas[1][11]};
        animacionCorre_D = new Animation<TextureRegion>(0.15f, arrFramesCorrerDer);
        animacionCorre_D.setPlayMode(Animation.PlayMode.LOOP);

        animacionCorre_I = new Animation<TextureRegion>(0.15f, arrFramesCorrerIzq);
        animacionCorre_I.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimation = 0;

        // IDLE
        idleD = new Sprite(texturas[1][3]);
        idleI = new Sprite(texturas[0][3]);

        sprite = new Sprite(texturas[1][3]);
        sprite.setPosition(x, y);

        // Initial state
        estado = EstadoHeroe.DERECHA;

    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame;
        float delta = Gdx.graphics.getDeltaTime();
        switch (estado){
            case IDLE_D:
                super.render(batch);
                break;
            case IDLE_I:
                super.render(batch);
                break;
            case DERECHA:
//                sprite.setTexture(texturaDerecha);
//                batch.draw(sprite, sprite.getX(), sprite.getY());

                timerAnimation += delta;
                frame = animacionCorre_D.getKeyFrame(timerAnimation);
                batch.draw(frame, sprite.getX(), sprite.getY());
                break;
            case IZQUIERDA:
//                sprite.setTexture(texturaIzquierda);
//                batch.draw(sprite, sprite.getX(), sprite.getY());
                timerAnimation += delta;
                frame = animacionCorre_I.getKeyFrame(timerAnimation);
                batch.draw(frame, sprite.getX(), sprite.getY());
                break;
            case SALTO:
                actualizarVuelo();
                super.render(batch);
//                switch (estadoPrev){
//                    case DERECHA:
//                        sprite.setTexture(texturaDerecha);
//                        batch.draw(sprite, sprite.getX(), sprite.getY());
//                        break;
//                    case IZQUIERDA:
//                        sprite.setTexture(texturaIzquierda);
//                        batch.draw(sprite, sprite.getX(), sprite.getY());
//                        break;
//                }
//                break;
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

    public void setEstado(mx.tec.astral.flytomars.Heroe.EstadoHeroe nuevoEstado){
        estado = nuevoEstado;
//        if(nuevoEstado == EstadoHeroe.MUERE)
//            sprite.setTexture(texturaMuere);
    }

    public EstadoHeroe getEstado() { return estado; }

    //public void mover (float dx)
    public void mover (){
        switch (estado){
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
    public Sprite getSprite(){ return sprite; }

    public void setX(float newX){
        sprite.setX(newX);
    }

    public void saltar(){
        if(estado != mx.tec.astral.flytomars.Heroe.EstadoHeroe.SALTO){
            tAire = 0;
            tVuelo = 2 * v0y / g;
            estadoPrev = estado;
            estado = EstadoHeroe.SALTO;
        }
    }

    public void actualizarVuelo(){
        float delta = Gdx.graphics.getDeltaTime();
        tAire += 2.5f*delta;
        float y = yBase + v0y * tAire - 0.5f * g * tAire *tAire;
        sprite.setY(y);

        if(tAire >= tVuelo || y <= yBase){
            estado = estadoPrev;
            sprite.setY(yBase);
        }
    }

}