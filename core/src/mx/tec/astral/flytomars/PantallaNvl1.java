package mx.tec.astral.flytomars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaNvl1 extends Pantalla {
    private Juego juego;
    Texture texturaFondo;
    private Stage escenaMenuNiveles;
    private Sprite hero;

    public PantallaNvl1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        crearMenu();
    }

    private void crearMenu() {
        texturaFondo = new Texture("nivel1/lvlEarth.png");
        hero = new Sprite(new Texture("nivel1/character1.png"));


        // MENU, necesitamos una escena
        //Escena
        escenaMenuNiveles = new Stage(vista);

        hero.setPosition(100, 100);

        // Actores->Boton

        Button btnA = crearBoton("buttons/btn_A.png", "buttons/btn_A_press.png");
        Button btnB = crearBoton("buttons/btn_B.png", "buttons/btn_B_press.png");
        Button btnBack = crearBoton("Menu/btn_back.png", "Menu/btn_back_press.png");


        btnA.setPosition(ANCHO-400, 100, Align.center);
        btnB.setPosition(ANCHO-200, 100, Align.center);
        btnBack.setPosition(150, 50, Align.center);

        // Agrega los botones a escena
        escenaMenuNiveles.addActor(btnA);
        escenaMenuNiveles.addActor(btnB);
        escenaMenuNiveles.addActor(btnBack);

        btnA.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaNvl1(juego));
            }
        });

        btnB.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //juego.setScreen(new PantallaNvl2(juego));
            }
        });

        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                juego.setScreen(new PantallaMenu(juego));
            }
        });


        // La ESCENA se encarga de ATENDER LOS EVENTOS DE ENTRADA
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

        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo, 0, 0);
        hero.draw(batch);

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
