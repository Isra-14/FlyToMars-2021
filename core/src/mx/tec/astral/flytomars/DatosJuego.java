package mx.tec.astral.flytomars;

import com.badlogic.gdx.math.MathUtils;

import java.io.Serializable;

public class DatosJuego implements Serializable
{
    private static final long serialVersionUID = 1;//para control de versiones

    //Se guardan un total de 10 scores
    private  final int MAX_SCORES = 10;

    //Aqui se guarda el score y el nomrbe
    private long[] highScores;
    private  String[] names;

    private long Score;

    public DatosJuego()
    {
        highScores = new long[MAX_SCORES];
        names = new String[MAX_SCORES];
    }

    //crea una tabla bacia con los scores
    public void creatTabla()
    {
        for (int i = 0; i < MAX_SCORES; i++)
        {
            highScores[i] = 0;
            names[i] = "...";
        }
    }

    //Regresa los highScors
    public long[] getHighScores() {return highScores;}
    //regresa los nombres
    public String[] getNames() {return names; }

    public long getScore()
    {return Score;}
    public void setScore(long score){this.Score = score; }

    //Checa si el valor guardado en score es mayor al guardado en highScores, regresa true de ser asi, sino false
    public boolean isHighScore(long score) {return score > highScores[MAX_SCORES - 1];}

    public void addHighScore(long score, String name)
    {
        if (isHighScore(score)) {
            highScores[MAX_SCORES - 1] = score;
            names[MAX_SCORES - 1] = name;
            sortHighScore();
        }
    }

    //Acomoda los scores, usando bubblesort
    private void sortHighScore()
    {
        for(int i= 0; i < MAX_SCORES; i++)
        {
            long score = highScores[i];
            String name = names[i];
            int j;
            for ( j = i -1 ; j >= 0 && highScores[j] < score; j--)
            {
                highScores[j + 1] = highScores[j];
                names[j + 1] = names[j];
            }
            highScores[j + 1] = score;
            names[j + 1] = name;
        }
    }


}
