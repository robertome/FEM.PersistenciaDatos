package es.upm.miw.SolitarioCelta;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

public class GameResultsActivity extends AppCompatActivity {

    private GameRepository gameRepository;
    private ListView resultsListView;
    private GameResultsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        // Mostrar el icono back en la ActionBar
        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        gameRepository = new GameRepository(getApplicationContext());

        // asociar recurso a la vista
        adapter = new GameResultsAdapter(
                this,
                R.layout.results_item,
                gameRepository.readAll());
        resultsListView = findViewById(R.id.resultsListView);
        //resultsListView.addHeaderView(getLayoutInflater().inflate(R.layout.results_header, null));
        resultsListView.setAdapter(adapter);

        View cleanResultsButton = findViewById(R.id.cleanResultsButton);
        cleanResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!adapter.isEmpty()) {
                    showCleanResultsDialog();
                }
            }
        });

        setResult(RESULT_OK);
    }

    private void showCleanResultsDialog() {
        new CleanGameResultsDialogFragment().show(getFragmentManager(), "CLEAN GAME RESULTS DIALOG");
    }

    public void cleanGameResults() {
        gameRepository.deleteAll();
        adapter.refresh(null);
    }

}
