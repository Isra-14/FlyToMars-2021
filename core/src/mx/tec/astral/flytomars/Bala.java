package mx.tec.astral.flytomars;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/*
    This class control the shot of the main character.
    Author(s): Israel Sanchez
*/


public class Bala extends Objeto {
    private Texture texturaDerecha;
    private Texture texturaIzquierda;


    EstadoBala estado;
    private float vX = 350;     // Px/Second

    public Bala(Texture textura, float x, float y){
        super (textura, x, y);
    }

    public Bala(Texture texturaIzquierda, Texture texturaDerecha, float x, float y){
        super (texturaDerecha, x, y);
        this.texturaDerecha = texturaDerecha;
        this.texturaIzquierda = texturaIzquierda;
    }

    public void setEstado(EstadoBala estado){
        this.estado = estado;
        if(estado == EstadoBala.DERECHA)
            sprite.setTexture(texturaDerecha);
        else
            sprite.setTexture(texturaIzquierda);
    }

    public void setPosition (float x, float y){
        sprite.setX(x);
        sprite.setY(y);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void mover(float delta){
        float dx = vX * delta;
        switch (estado){
            case IZQUIERDA:
                sprite.setX(sprite.getX() - dx);
                break;
            case DERECHA:
                sprite.setX(sprite.getX() + dx);
                break;
        }
    }

    public float getX(){
        return sprite.getX();
    }
}
