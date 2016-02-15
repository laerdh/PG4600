package no.westerdals.PG4600.Innlevering1.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import no.westerdals.PG4600.Innlevering1.R;
import no.westerdals.PG4600.Innlevering1.model.Scoreboard;

import java.util.List;
import java.util.Stack;

/**
 * Created by larsdahl on 14.02.2016.
 */
public class ResultActivity extends Activity {
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        initListView();
        initAdapter();
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.listView);
    }

    private void initAdapter() {
        List<String> results = Scoreboard.getResults();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.result_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Back:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}