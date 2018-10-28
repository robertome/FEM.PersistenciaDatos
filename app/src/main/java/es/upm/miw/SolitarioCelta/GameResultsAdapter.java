package es.upm.miw.SolitarioCelta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.upm.miw.SolitarioCelta.db.entities.GameResult;

/**
 * @link https://developer.android.com/guide/topics/ui/declaring-layout#AdapterViews
 */
public class GameResultsAdapter extends ArrayAdapter<GameResult> {

    private Context context;
    private List<GameResult> gameResults;
    private int resourceId;

    public GameResultsAdapter(@NonNull Context context, int resource, @NonNull List<GameResult> gameResults) {
        super(context, resource, gameResults);
        this.context = context;
        this.resourceId = resource;
        this.gameResults = gameResults;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LinearLayout view;
        if (null != convertView) {
            view = (LinearLayout) convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) inflater.inflate(resourceId, parent, false);
        }

        GameResult result = gameResults.get(position);
        TextView tokensNumberTextView = view.findViewById(R.id.tokensNumberTextView);
        tokensNumberTextView.setText(String.format("%d", result.getTokensNumber()));
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        dateTextView.setText(String.format("%tF %tR", result.getDate(), result.getDate()));
        TextView playerNameTextView = view.findViewById(R.id.playerNameTextView);
        playerNameTextView.setText(result.getPlayerName());

        return view;
    }

    public void refresh(List<GameResult> gameResults) {
        this.gameResults.clear();
        if (gameResults != null) {
            this.gameResults.addAll(gameResults);
        }

        this.notifyDataSetChanged();
        //this.notifyDataSetInvalidated();
    }

}
