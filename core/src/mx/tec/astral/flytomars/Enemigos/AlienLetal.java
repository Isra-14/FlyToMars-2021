package mx.tec.astral.flytomars.Enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import mx.tec.astral.flytomars.Tools.Objeto;
import mx.tec.astral.flytomars.Heroe.EstadoHeroe;

public class AlienLetal extends Objeto {
    private float DX = 5;
    private Texture texturaDerecha;
    private Texture texturaIzquierda;
    EstadoAlien estado;

    public AlienLetal(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public AlienLetal(Texture texturaDerecha, Texture texturaIzquierda, float x, float y){
        super( texturaIzquierda, x, y);
        this.texturaDerecha = texturaDerecha;
        this.texturaIzquierda = texturaIzquierda;
        estado = EstadoAlien.IZQUIERDA;
    }
    public void moverHorizontal (){
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

    public EstadoAlien getEstado() { return estado; }

    public void cambiarEstado() {
        switch (estado){
            case IZQUIERDA:
                estado = EstadoAlien.DERECHA;
                sprite.setTexture(texturaDerecha);
                break;
            case DERECHA:
                estado = EstadoAlien.IZQUIERDA;
                sprite.setTexture(texturaIzquierda);
                break;
        }
    }

    public void setEstado(EstadoAlien estado){
        this.estado = estado;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public float getX(){
        return sprite.getX();
    }
    public void setX(float dx){
        sprite.setX(dx);
    }
}


