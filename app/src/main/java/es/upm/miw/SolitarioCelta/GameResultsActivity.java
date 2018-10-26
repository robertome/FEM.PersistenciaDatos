package es.upm.miw.SolitarioCelta;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import es.upm.miw.SolitarioCelta.db.AppDatabase;

public class GameResultsActivity extends AppCompatActivity {

    private ListView resultsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        // Mostrar el icono back en la ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Recupero el bundle con los datos
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {

        }

        // asociar recurso a la vista
        resultsListView = findViewById(R.id.resultsListView);
        resultsListView.addHeaderView(getLayoutInflater().inflate(R.layout.results_header, null));
        resultsListView.setAdapter(new GameResultsAdapter(
                this,
                R.layout.results_item,
                AppDatabase.getDatabase(getApplicationContext()).gameResultDao().readAll()));

        setResult(RESULT_OK);
    }
}
