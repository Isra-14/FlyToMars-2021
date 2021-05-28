package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Pantallas.Pantalla;

public class PantallaNvl3 extends Pantalla {
    private Juego juego;
    BitmapFont font = new BitmapFont(); //or use alex answer to use custom font

    public PantallaNvl3(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

        Gdx.input.setCatchKey( Input.Keys.BACK, true );
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.begin();
        font.getData().setScale(3,3);
        font.draw(batch, "Pantalla Nivel 3:", 25, ALTO - 25 );
        batch.end();


        if ( Gdx.input.isKeyPressed(Input.Keys.BACK) )
            juego.setScreen( new PantallaJuego(juego) );
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
