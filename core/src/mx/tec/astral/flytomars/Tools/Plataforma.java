package mx.tec.astral.flytomars.Tools;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import mx.tec.astral.flytomars.Juego;
import mx.tec.astral.flytomars.Pantallas.PantallaMenu;

public class Plataforma extends Game
    {
        private Juego juego;
        // Constantes públicas
        public static final float ANCHO_CAMARA = 640;
        public static final float ALTO_CAMARA = 480;

        // Administra la carga de los assets del juego
        private final AssetManager assetManager = new AssetManager();

        @Override
        public void create() {
        // Agregamos un loader para los mapas
        assetManager.setLoader(TiledMap.class,
                new TmxMapLoader(new InternalFileHandleResolver()));
        // Pantalla inicial
        setScreen(new PantallaMenu(juego));
    }

        // Método accesor de assetManager
        public AssetManager getAssetManager() {
        return assetManager;
    }

        @Override
        public void dispose() {
        super.dispose();
        assetManager.clear();
    }
}
