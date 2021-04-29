package mx.tec.astral.flytomars.Heroe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.tec.astral.flytomars.Tools.Objeto;

/*
Personaje controlado por el usuario
Autor(es) : Misael Delgado, Israel Sanchez
 */
public class Hero extends Objeto {
    private Texture texturaDerecha;
    private Texture texturaIzquierda;
//    private Texture texturaSalto;
//    private Texture texturaMuere;

    // Jump
    private final float yBase = 150;     // Floor
    private float tAire;                // Time in the air
    private float tVuelo;               // Fly time
    private final float v0y = 200;      // Y component of velocity
    private final float g = 150f;      // Pixels/s^2 -> Gravity


    private mx.tec.astral.flytomars.Heroe.EstadoHeroe estado;     //  States of the player (IZQUIERDA, DERECHA, SALTA, MUERE)
    private mx.tec.astral.flytomars.Heroe.EstadoHeroe estadoPrev;


    public Hero(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public Hero (Texture texturaDerecha, Texture texturaIzquierda, float x, float y){
        super( texturaDerecha, x, y);
        this.texturaDerecha = texturaDerecha;
        this.texturaIzquierda = texturaIzquierda;
        estado = mx.tec.astral.flytomars.Heroe.EstadoHeroe.DERECHA;
    }

    @Override
    public void render(SpriteBatch batch) {
        switch (estado){
            case DERECHA:
                sprite.setTexture(texturaDerecha);
                batch.draw(sprite, sprite.getX(), sprite.getY());
                break;
            case IZQUIERDA:
                sprite.setTexture(texturaIzquierda);
                batch.draw(sprite, sprite.getX(), sprite.getY());
                break;
            case SALTO:
                actualizarVuelo();
                switch (estadoPrev){
                    case DERECHA:
                        sprite.setTexture(texturaDerecha);
                        batch.draw(sprite, sprite.getX(), sprite.getY());
                        break;
                    case IZQUIERDA:
                        sprite.setTexture(texturaIzquierda);
                        batch.draw(sprite, sprite.getX(), sprite.getY());
                        break;
                }
                break;
        }
    }


    public void cambiarEstado() {
        switch (estado){
            case IZQUIERDA:
                estado = mx.tec.astral.flytomars.Heroe.EstadoHeroe.DERECHA;
                sprite.setTexture(texturaDerecha);
                break;
            case DERECHA:
                estado = mx.tec.astral.flytomars.Heroe.EstadoHeroe.IZQUIERDA;
                sprite.setTexture(texturaIzquierda);
                break;
        }
    }

    public void setEstado(mx.tec.astral.flytomars.Heroe.EstadoHeroe nuevoEstado){
        estado = nuevoEstado;
//        if(nuevoEstado == EstadoHeroe.MUERE)
//            sprite.setTexture(texturaMuere);
    }

    public mx.tec.astral.flytomars.Heroe.EstadoHeroe getEstado() { return estado; }

    public void mover (float dx){
        sprite.setX(sprite.getX() + dx);
    }
    public Sprite getSprite(){ return sprite; }

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
        tAire += 2*delta;
        float y = yBase + v0y * tAire - 0.5f * g * tAire *tAire;
        sprite.setY(y);

        if(tAire >= tVuelo || y <= yBase){
            estado = estadoPrev;
            sprite.setY(yBase);
        }
    }

}