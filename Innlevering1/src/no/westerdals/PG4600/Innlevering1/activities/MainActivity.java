package no.westerdals.PG4600.Innlevering1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import no.westerdals.PG4600.Innlevering1.R;

public class MainActivity extends Activity {
    private EditText player1, player2;
    private Button btnPlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        initTextFields();
        initButtons();
    }

    private void initTextFields() {
        player1 = (EditText) findViewById(R.id.txtPlayer1);
        player2 = (EditText) findViewById(R.id.txtPlayer2);
    }

    private void initButtons() {
        btnPlay = (Button) findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
                    gameIntent.putExtra("player1", player1.getText().toString());
                    gameIntent.putExtra("player2", player2.getText().toString());
                    startActivity(gameIntent);
                }
            }
        });
    }

    private boolean validateInput() {
        String p1 = player1.getText().toString();
        String p2 = player2.getText().toString();

        if (p1.equals("") || p2.equals("")) {
            showMessage("Please enter name");
            return false;
        }
        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
