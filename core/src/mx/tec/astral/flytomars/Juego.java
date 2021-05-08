package mx.tec.astral.flytomars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import mx.tec.astral.flytomars.Pantallas.PantallaMenu;
import mx.tec.astral.flytomars.Pantallas.PantallaSplash;
import mx.tec.astral.flytomars.Tools.Texto;

/*
	Main class/controller
	Author(s): Alejandro Quintana

*/

public class Juego extends Game
{
	//Se pued eimplementar para aqi hacer los cambios e pantalla
	public static final  int PANTALLA_SPLASH=0;
	public static final  int PANTALLA_MENU=1;

	public Music mp3;

	//cargamos sonido
	public Sound sound;

	//Se carga el texto
	public Texto texto;
	public Juego () {super(); }

	@Override
	public void create ()
	{
		texto = new Texto();
		//Sonido botones
		sound = Gdx.audio.newSound(Gdx.files.internal("Efectos/Selection_sound.wav"));
			//Se agrega la musica
			mp3 = Gdx.audio.newMusic(Gdx.files.internal("Efectos/MusicaFondo.mp3"));
			mp3.setVolume(.02f);
			mp3.play();
			mp3.setLooping(true);


		//Se llama al metodo para camibiar de pantalla
		changeScreen(PANTALLA_SPLASH);

	}

	public boolean getMusica(){
		return mp3.isPlaying();
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
