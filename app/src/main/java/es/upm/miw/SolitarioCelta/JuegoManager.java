package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static es.upm.miw.SolitarioCelta.MainActivity.LOG_TAG;

public class JuegoManager {

    private Context context;

    JuegoManager(Context context) {
        this.context = context;
    }

    public boolean save(JuegoCelta juegoCelta) {
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

    public JuegoCelta load() {
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

    private String savedFilePath() {
        return context.getResources().getString(R.string.defaultSaveFile);
    }
}
