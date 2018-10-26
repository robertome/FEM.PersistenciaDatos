package es.upm.miw.SolitarioCelta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import es.upm.miw.SolitarioCelta.db.entities.GameResult;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "CELTA_LOG";
    public static final String DEFAULT_PLAYER = "DEFAULT_PLAYER";
    private static final String CLAVE_TABLERO = "TABLERO_SOLITARIO_CELTA";

    private JuegoCelta mJuego;
    private String playerName;

    private final int[][] ids = {
            {0, 0, R.id.p02, R.id.p03, R.id.p04, 0, 0},
            {0, 0, R.id.p12, R.id.p13, R.id.p14, 0, 0},
            {R.id.p20, R.id.p21, R.id.p22, R.id.p23, R.id.p24, R.id.p25, R.id.p26},
            {R.id.p30, R.id.p31, R.id.p32, R.id.p33, R.id.p34, R.id.p35, R.id.p36},
            {R.id.p40, R.id.p41, R.id.p42, R.id.p43, R.id.p44, R.id.p45, R.id.p46},
            {0, 0, R.id.p52, R.id.p53, R.id.p54, 0, 0},
            {0, 0, R.id.p62, R.id.p63, R.id.p64, 0, 0}
    };

    private GameRepository gameRepository;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJuego = new JuegoCelta();
        mostrarTablero();

        gameRepository = new GameRepository(getApplicationContext());

        if (recoverPlayerName(null) == null) {
            showPreferencias();
        }
    }

    private String recoverPlayerName(String defaultPlayerName) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPref.getString(getResources().getString(R.string.preferencesKeyPlayerName), defaultPlayerName);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CLAVE_TABLERO, mJuego.serializaTablero());
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String grid = savedInstanceState.getString(CLAVE_TABLERO);
        mJuego.deserializaTablero(grid);
        mostrarTablero();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAbout:
                showAbout();
                return true;
            case R.id.preferences:
                showPreferencias();
                return true;
            case R.id.reinitialize:
                showReinitialize();
                return true;
            case R.id.save:
                salvarPartida(mJuego);
                return true;
            case R.id.restore:
                showRestore();
                return true;
            case R.id.results:
                showGameResults();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showAbout() {
        startActivity(new Intent(this, About.class));
    }

    private void showPreferencias() {
        startActivity(new Intent(this, SCeltaPreferences.class));
    }

    private void showReinitialize() {
        new ReinitializeDialogFragment().show(getFragmentManager(), "REINITIZE DIALOG");
    }

    private void showRestore() {
        //TODO: Solo restaurar si existe partida guardada
        new RestoreDialogFragment().show(getFragmentManager(), "RESTORE DIALOG");
    }

    private void showGameResults() {
        startActivity(new Intent(this, GameResultsActivity.class));
    }


    /**
     * Se ejecuta al pulsar una posición
     *
     * @param v Vista de la posición pulsada
     */
    public void posicionPulsada(View v) {
        String resourceName = getResources().getResourceEntryName(v.getId());
        int i = resourceName.charAt(1) - '0';
        int j = resourceName.charAt(2) - '0';

        mJuego.jugar(i, j);

        mostrarTablero();

        if (mJuego.juegoTerminado()) {
            gameRepository.save(new GameResult(recoverPlayerName(DEFAULT_PLAYER), mJuego.contarFichas()));

            new AlertDialogFragment().show(getFragmentManager(), "ALERT DIALOG");
        }
    }

    /**
     * Visualiza el tablero
     */
    public void mostrarTablero() {
        RadioButton button;
        for (int i = 0; i < JuegoCelta.TAMANIO; i++) {
            for (int j = 0; j < JuegoCelta.TAMANIO; j++) {
                if (ids[i][j] != 0) {
                    button = findViewById(ids[i][j]);
                    button.setChecked(mJuego.obtenerFicha(i, j) == 1);
                }
            }
        }
    }

    public void reiniciarTablero() {
        mJuego.reiniciar();
        mostrarTablero();
    }

    private void salvarPartida(JuegoCelta juegoCelta) {
        if (gameRepository.saveGameState(juegoCelta)) {
            Toast.makeText(
                    this,
                    getString(R.string.savedGameText),
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void restaurarPartida() {
        JuegoCelta juegoCelta = gameRepository.loadGameState();
        if (juegoCelta != null) {
            this.mJuego = juegoCelta;
            mostrarTablero();
        }
    }
}
