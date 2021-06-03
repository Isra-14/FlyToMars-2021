package mx.tec.astral.flytomars.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import mx.tec.astral.flytomars.EstadoPowerUps;

/*
Autor Alejandro Quintana, Israel Sanchez
Creado el 4/Abril/2021
Esta clase definira los powerUps que tendra el jugador
 */
public class PowerUp extends Objeto{
    //Texturas
    private Texture textraVidaExtra = new Texture("items/heart.png");
    private Texture texturaEscudo = new Texture("items/shield.png");
    private Texture texturaMoneda = new Texture("items/coin.png");
    private int tipo;
    private EstadoPowerUps estado;

    //Tiempo de vida en pantalla
//    private int tiempoVida = 0;
//    private int generacionPower = 3;

    public PowerUp(Texture textura, float x, float y)
    {
        super(textura, x, y);
    }

    public PowerUp( float x, float y, int tipo )
    {
        this.tipo = tipo;
        sprite = new Sprite(textraVidaExtra);
        switch (tipo) {
            case 0:
                sprite.setTexture(textraVidaExtra);
                break;
            case 1:
                sprite.setTexture(texturaEscudo);
                break;
            case 2:
                sprite.setTexture(texturaMoneda);
                break;
            default:
                System.out.println("No se pudo crear ningun objeto");
                break;
        }
        sprite.setPosition(x, y);
        estado = EstadoPowerUps.EN_PANTALLA;
    }


    public int getTipo(){
        return tipo;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public EstadoPowerUps getEstado(){
        return estado;
    }

    public void setEstado(EstadoPowerUps estado) {
        this.estado = estado;
    }
}
