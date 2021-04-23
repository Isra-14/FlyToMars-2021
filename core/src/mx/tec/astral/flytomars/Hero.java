package mx.tec.astral.flytomars;

import com.badlogic.gdx.graphics.Texture;

/*
Personaje controlado por el usuario
Autor : Misael Delgado
 */
public class Hero extends Objeto{
    private Texture texturaDerecha;
    private Texture texturaIzquierda;
    private Texture texturaSalto;
    private Texture texturaMuere;

    private EstadoHeroe estado;     //  States of the player (IZQUIERDA, DERECHA, SALTA, MUERE)

    public Hero(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public Hero (Texture texturaDerecha, Texture texturaIzquierda, float x, float y){
        super( texturaDerecha, x, y);
        this.texturaDerecha = texturaDerecha;
        this.texturaIzquierda = texturaIzquierda;
        estado = EstadoHeroe.DERECHA;
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

    public void setEstado(EstadoHeroe nuevoEstado){
        estado = nuevoEstado;
        if(nuevoEstado == EstadoHeroe.MUERE)
            sprite.setTexture(texturaMuere);
    }

    public EstadoHeroe getEstado() { return estado; }

    public void mover (float dx){
        sprite.setX(sprite.getX() + dx);
    }
    public float getX(){
        return sprite.getX();
    }
    public float getWidth(){
        return sprite.getWidth();
    }
}