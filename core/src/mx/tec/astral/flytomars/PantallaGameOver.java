package mx.tec.astral.flytomars;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
/*
Autor Alejandro Quintana
 */
public class PantallaGameOver extends Pantalla
{
    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenuNiveles;
    Texto texto;

    private ShapeRenderer sr;

    private boolean newHighScore;
    private char[] newName;
    private int currentChar;

    public PantallaGameOver(Juego juego){this.juego = juego;}

    public void show(){crearMenu(); }

    //aqui se inicializa 
    private void crearMenu()
    {
        texto = new Texto();
        sr = new ShapeRenderer();
        texturaFondo = new Texture("");
        newHighScore = Guardar.dj.isHighScore(Guardar.dj.getScore());
        ingresarNombre(newHighScore);
    }

    private void ingresarNombre(boolean newHighScore)
    {
        if (newHighScore){
            newName = new char[] { 'A', 'A', 'A'};
            currentChar = 0;
        }
    }

    @Override
    public void render(float delta)
    {
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        texto.mostrarMensaje(batch, "Game Over", ANCHO/2, ALTO/2);
        if(!newHighScore)
        {
            batch.end();
            return;
        }
        texto.mostrarMensaje(batch, "Nuevo High Score: " + Guardar.dj.getScore(), ANCHO/2, .45F*ALTO);
        dibujarNombre(batch);
        batch.end();

    }

    private void dibujarNombre(SpriteBatch batch)
    {
        int i;
        for(i = 0; i < newName.length; i++)
        {
            texto.mostrarMensaje(this.batch, Character.toString(newName[i]), ANCHO + 14 * i, .35f*ALTO);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
