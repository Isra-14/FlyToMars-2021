package mx.tec.astral.flytomars;

import com.badlogic.gdx.Screen;

public class PantallaNvl3 extends Pantalla {
    private Juego juego;

    public PantallaNvl3(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
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
