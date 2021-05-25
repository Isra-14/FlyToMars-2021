package mx.tec.astral.flytomars.Enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import mx.tec.astral.flytomars.EstadoSalto;
import mx.tec.astral.flytomars.Tools.Objeto;
import mx.tec.astral.flytomars.Heroe.EstadoHeroe;

public class AlienTanque extends Objeto {
    private float DX = 2;
    private float DY = -4f;
    private Texture texturaDerecha;
    private Texture texturaIzquierda;
    EstadoAlien estado;
    EstadoSalto estadoSalto;


    // Mapa
    private int TAM_CELDA;
    private TiledMap mapa;

    // Jump
    private float yBase;     // Floor
    private float tAire;                // Time in the air
    private float tVuelo;               // Fly time
    private final float v0y = 225;      // Y component of velocity
    private final float g = 150f;      // Pixels/s^2 -> Gravity

    public AlienTanque(Texture textura, float x, float y){
        super(textura, x, y);
    }

    public AlienTanque(Texture texturaDerecha, Texture texturaIzquierda, float x, float y){
        super( texturaIzquierda, x, y);
        this.texturaDerecha = texturaDerecha;
        this.texturaIzquierda = texturaIzquierda;
        estado = EstadoAlien.IZQUIERDA;
    }
    public void moverHorizontal (){
        switch (estado){
            case DERECHA:
                sprite.setX(sprite.getX() + DX);
                break;
            case IZQUIERDA:
                sprite.setX(sprite.getX() - DX);
                break;
            default:
                break;
        }
    }

    public EstadoAlien getEstado() { return estado; }

    public void cambiarEstado() {
        switch (estado){
            case IZQUIERDA:
                estado = EstadoAlien.DERECHA;
                sprite.setTexture(texturaDerecha);
                break;
            case DERECHA:
                estado = EstadoAlien.IZQUIERDA;
                sprite.setTexture(texturaIzquierda);
                break;
        }
    }

    public void setEstado(EstadoAlien estado){
        this.estado = estado;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setX(float i) {
        sprite.setX(i);
    }

    public void setPosition (float newX, float newY){
        sprite.setPosition(newX, newY);
    }

    public void cargarMapa(TiledMap mapa, int TAM_CELDA){
        this.mapa = mapa;
        this.TAM_CELDA = TAM_CELDA;
    }

    public void setyBase(float newYbase) {
        yBase = newYbase;
    }

    public void caer(){
        sprite.setY(sprite.getY() + DY);
    }

    public EstadoSalto getEstadoSalto(){
        return estadoSalto;
    }
    public void setEstadoSalto (EstadoSalto estadoSalto){
        this.estadoSalto = estadoSalto;
    }

    public void verificarPlataforma(){
        if ( getEstadoSalto() != EstadoSalto.SUBIENDO ) {
            int celdaX = (int) (sprite.getX() / TAM_CELDA);
            int celdaY = (int) ( (sprite.getY() + DY) / TAM_CELDA);

            TiledMapTileLayer capa = (TiledMapTileLayer) mapa.getLayers().get(2);
            TiledMapTileLayer.Cell celdaAbajo = capa.getCell(celdaX, celdaY);
            TiledMapTileLayer.Cell celdaDerecha = capa.getCell(celdaX+1, celdaY);

            if( celdaAbajo==null && celdaDerecha==null ){
                caer();
                setEstadoSalto(EstadoSalto.CAIDA_LIBRE);
            }else {
                if( sprite.getY() >= 2* TAM_CELDA ) {
                    setPosition(sprite.getX(), (celdaY + 1) * TAM_CELDA);
                    setEstadoSalto(EstadoSalto.EN_PISO);
                    setyBase((celdaY + 1) * TAM_CELDA);
                }
            }
        }
    }
}
