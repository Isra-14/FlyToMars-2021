package mx.tec.astral.flytomars;

import com.badlogic.gdx.Screen;

public class PantallaJuego extends Pantalla {
    private Juego juego;

    public PantallaJuego(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        borrarPantalla(1,0,1);
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