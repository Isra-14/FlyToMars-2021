package mx.tec.astral.flytomars.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Pantallas.Pantalla;

/*
Autor(es) Alejandro Quintana
 */
public class PantallaNvl2 extends Pantalla {

    //Cancion nivel 2
    Music music;
    private Juego juego;
    BitmapFont font = new BitmapFont(); //or use alex answer to use custom font
    private Stage escenaMenuNiveles;

    public PantallaNvl2(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMenu();
        playMusic();

    }

    private void playMusic()
    {
        music = Gdx.audio.newMusic(Gdx.files.internal("Efectos/level2.wav"));
        music.play();
        music.setVolume(.05f);
        music.setLooping(true);
    }

    private void crearMenu() {

        //escena
        escenaMenuNiveles = new Stage(vista);

        Button btnBack = crearBoton("Menu/btn_back.png", "Menu/btn_back_press.png");

        //se le da un espacio en la pantalla
        btnBack.setPosition(ANCHO/2 - 100 , 85);


        //Agreagamos a la escena los actores
        escenaMenuNiveles.addActor(btnBack);

        //Listen to the user
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.soundBotones.play();
                music.stop();
                juego.setScreen(new PantallaJuego(juego));
            }
        });

        Gdx.input.setInputProcessor(escenaMenuNiveles);

    }

    private Button crearBoton(String archivo, String clickeado) {
        Texture texturaBoton = new Texture(archivo);
        TextureRegionDrawable trdBtn = new TextureRegionDrawable(texturaBoton);
        // Clickeado
        Texture texturaClickeada = new Texture(clickeado);
        TextureRegionDrawable trdBtnClick = new TextureRegionDrawable(texturaClickeada);
        return new Button(trdBtn, trdBtnClick);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0,0,0);
        batch.begin();
        font.getData().setScale(3,3);
        font.draw(batch, "Pantalla Nivel 2:", 25, ALTO - 25 );
        batch.end();
        escenaMenuNiveles.draw();
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
