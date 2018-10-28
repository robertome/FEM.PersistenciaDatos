package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import es.upm.miw.SolitarioCelta.db.AppDatabase;
import es.upm.miw.SolitarioCelta.db.daos.GameResultDao;
import es.upm.miw.SolitarioCelta.db.entities.GameResult;

import static es.upm.miw.SolitarioCelta.MainActivity.LOG_TAG;

public class GameRepository {

    private Context context;
    private GameResultDao gameResultDao;

    GameRepository(Context context) {
        this.context = context;
        this.gameResultDao = AppDatabase.getDatabase(context).gameResultDao();
    }

    public boolean saveGameState(JuegoCelta juegoCelta) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(savedFilePath(), Context.MODE_PRIVATE);
            fos.write(juegoCelta.serializaTablero().getBytes());
            return true;
        } catch (Exception e) {
            Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
                }
            }
        }

        return false;
    }

    public JuegoCelta loadGameState() {
        BufferedReader fin = null;
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(context.openFileInput(savedFilePath()));
            fin = new BufferedReader(is);

            String linea = fin.readLine();
            if (linea != null) {
                JuegoCelta juegoCelta = new JuegoCelta();
                juegoCelta.deserializaTablero(linea);

                return juegoCelta;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "FILE I/O ERROR: " + e.getMessage());
                }
            }
        }

        return null;
    }

    public boolean isSavedGameState() {
        return loadGameState() != null;
    }

    private String savedFilePath() {
        return context.getResources().getString(R.string.defaultSaveFile);
    }

    public void save(GameResult gameResult) {
        Log.i(LOG_TAG, "Saving game result: " + gameResult);
        gameResultDao.save(gameResult);
        Log.i(LOG_TAG, "Games results: \n" + readAll());
    }

    public List<GameResult> readAll() {
        return gameResultDao.readAll();
    }

    public void deleteAll() {
        gameResultDao.deleteAll();
    }
}
