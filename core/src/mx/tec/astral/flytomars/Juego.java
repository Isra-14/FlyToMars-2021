package mx.tec.astral.flytomars;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game
{
	//Se pued eimplementar para aqi hacer los cambios e pantalla
	public static final  int PANTALLA_SPLASH=0;
	public static final  int PANTALLA_MENU=1;
	public Sound mp3;


	public Juego () {super(); }

	@Override
	public void create ()
	{
		//Se agrega la musica
		mp3 = Gdx.audio.newSound(Gdx.files.internal("Efectos/MusicaFondo.mp3"));
		mp3.play();

		//Se llama al metodo para camibiar de pantalla
		changeScreen(PANTALLA_SPLASH);

	}

	//Hace el cambio de pantalla
	public void changeScreen(int pantalla)
	{
		if (pantalla == PANTALLA_SPLASH)
		{
			this.setScreen(new PantallaSplash(this));
		}
		else
		{
			this.setScreen(new PantallaMenu(this));
		}
	}
}
