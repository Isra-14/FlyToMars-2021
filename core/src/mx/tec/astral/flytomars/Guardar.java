package mx.tec.astral.flytomars;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Guardar
{
    public static DatosJuego dj;

    public static void save()
    {
        try
        {
            //Se escribe/guarda una instancia de la clase en un archivo
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("highScores.pdf"));
            out.writeObject(dj);
            out.close();

            //Si pasa un error se cierra la app y se imprime el error que se causo
        }catch(Exception e){
            e.printStackTrace();
            //Gdx.app.exit();
        }
    }

    public static  void load()
    {
        try{
            //de no existir se crea
            if (!saveFileExists())
            {
                start();
                return;
            }
            //Se carga el archivo
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("highScores.pdf"));
            dj = (DatosJuego) in.readObject();
            in.close();

        }catch (Exception e){
            e.printStackTrace();
            //dx.app.exit();
        }

    }

    public static boolean saveFileExists()
    {
        File f = new File("highScores.pdf");
        return f.exists();
    }

    public static void start(){
        dj = new DatosJuego();
        dj.creatTabla();
        save();
    }

}
