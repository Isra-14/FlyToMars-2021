package mx.tec.astral.flytomars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Texto {
    private BitmapFont font;

    public Texto()
    {
        font = new BitmapFont(Gdx.files.internal("Efectos/fontFlyToMars.fnt"));
    }

    public void mostrarMensaje(SpriteBatch batch, String mensaje, float x, float y)
    {
        GlyphLayout glyph = new GlyphLayout();
        glyph.setText(font, mensaje);
        float anchoTexto = glyph.width;
        font.draw(batch, glyph, x-anchoTexto/2, y);
    }
}
