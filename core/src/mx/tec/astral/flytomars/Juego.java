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
	Author(s): Israel Sanchez
	Alejandro Quintana
	sonidos recopilados de
	https://freesound.org/

 */

public class Juego extends Game
{
	//Se pued eimplementar para aqi hacer los cambios e pantalla
	public static final  int PANTALLA_SPLASH=0;

	public Music mp3;

	public boolean isViewedStory1 = false;
	public boolean isViewedStory2 = false;
	public boolean isViewedStory3 = false;

	public boolean isPassedLvl1 = false;
	public boolean isPassedLvl2 = false;
	public boolean isCompleted = false;

	public boolean isSoundMuted = false;

	//cargamos sonido
	public Music soundBotones;
	public Sound soundDisparo;
	public Sound soundSalto;
	public  Sound perder;
	public  Music error;

	//Se carga el texto
	public Texto texto;
	public Juego () {super(); }

	@Override
	public void create ()
	{
		texto = new Texto();

		//carga los efectos de sonido
		cargarSonidos();

		//Se agrega la musica
		mp3 = Gdx.audio.newMusic(Gdx.files.internal("Efectos/menuProposal.mp3"));
		if ( !isSoundMuted) {
			mp3.setVolume(.12f);
			mp3.play();
		}
		mp3.setLooping(true);


		//Se llama al metodo para camibiar de pantalla
		setScreen(new PantallaSplash(this));
		changeScreen(PANTALLA_SPLASH);

	}

	private void cargarSonidos()
	{
		//Sonido botones y disparo
		soundBotones = Gdx.audio.newMusic(Gdx.files.internal("Efectos/Selection_sound.wav"));
		soundDisparo = Gdx.audio.newSound(Gdx.files.internal("Efectos/bubble_shot.wav"));
		soundSalto = Gdx.audio.newSound(Gdx.files.internal("Efectos/Jump.wav"));
		perder = Gdx.audio.newSound(Gdx.files.internal("Efectos/Lost_sound.wav"));
		error = Gdx.audio.newMusic(Gdx.files.internal("Efectos/error.mp3"));
	}

	//checa si se paro la musica
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
