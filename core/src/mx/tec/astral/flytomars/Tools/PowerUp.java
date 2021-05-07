package mx.tec.astral.flytomars.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
Autor Alejandro Quintana
Creado el 4/Abril/2021
Esta clase definira los powerUps que tendra el jugador
 */
public class PowerUp extends Objeto{
    //Texturas
    private Texture textraVidaExtra;
    private Texture texturaEscudo;
    private Texture texturaMoneda;

    //Tiempo de vida en pantalla
    private int tiempoVida = 0;
    private int generacionPower = 3;

    public PowerUp(Texture textura, float x, float y)
    {
        super(textura, x, y);
    }

    public PowerUp(Texture texturaCorazon, Texture texturaEscudo, Texture texturaMoneda, float x, float y)
    {
        super(texturaCorazon, x, y);
        this.textraVidaExtra = texturaCorazon;
        this.texturaEscudo = texturaEscudo;
        this.texturaMoneda = texturaMoneda;
    }


    //crea un nuevo power up
    public void creaNuevoPowerUp(SpriteBatch batch, int PowetUp)
    {
        tiempoVida += Gdx.graphics.getDeltaTime();
        int x = random();
        int y = random();
        //if (tiempoVida == generacionPower) {
            switch (PowetUp) {
                case 0:
                    sprite.setTexture(textraVidaExtra);
                    batch.draw(sprite, x, y);
                    break;
                case 1:
                    sprite.setTexture(texturaEscudo);
                    batch.draw(sprite, x, y);
                    break;
                case 2:
                    sprite.setTexture(texturaMoneda);
                    batch.draw(sprite, x, y);
                    break;
                default:
                    System.out.println("No se pudo crear ningun objeto");
                    break;
            }
    }


    public void render(SpriteBatch batch) {
       int  numeroPower = (int)(Math.random()*3);
        creaNuevoPowerUp(batch, numeroPower);
    }

    private int random() {
        return (int)(Math.random()*600 + 100);
    }
}