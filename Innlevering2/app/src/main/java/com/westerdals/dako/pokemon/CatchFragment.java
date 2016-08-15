package com.westerdals.dako.pokemon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.westerdals.dako.pokemon.db.PokemonDataSource;
import com.westerdals.dako.pokemon.http.DataHandler;
import com.westerdals.dako.pokemon.http.ResponseWrapper;
import com.westerdals.dako.pokemon.http.ResultsListener;
import com.westerdals.dako.pokemon.model.Pokemon;

import java.io.InputStream;
import java.util.Scanner;

public class CatchFragment extends Fragment implements OnClickListener, ResultsListener {
    private View view;
    private EditText editText;
    private Button btnCatch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_catch, container, false);
        initListeners();
        return view;
    }


    private void initListeners() {
        editText = (EditText) view.findViewById(R.id.editTextKey);
        btnCatch = (Button) view.findViewById(R.id.btnCatch);
        editText.setOnClickListener(this);
        btnCatch.setOnClickListener(this);
    }


    @Override
    public void onResultsSucceeded(ResponseWrapper response) {
        handleResponse(response);
    }


    @Override
    public void onClick(View v) {
        // Build target URL
        final StringBuilder targetURL = new StringBuilder();
        targetURL.append(getString(R.string.API_URI_POKEMON));
        targetURL.append(editText.getText().toString());

        // Try to catch pokemon
        downloadPokemonData(targetURL.toString());
    }


    private void downloadPokemonData(String targetURL) {
        DataHandler dataHandler = new DataHandler(view.getContext());
        dataHandler.setOnResultsListener(this);
        dataHandler.setTargetURL(targetURL);
        dataHandler.setProgressMessage("Trying to catch pokemon...");
        dataHandler.execute();
    }


    private void handleResponse(ResponseWrapper response) {
        if (response != null) {
            switch (response.responseCode) {
                case 200:
                    displayAlert("You have already catched this pokemon!");
                    catchPokemon(inputStreamToPokemon(response.response));
                    break;
                case 201:
                    displayAlert("You have catched a pokemon, dude!");
                    catchPokemon(inputStreamToPokemon(response.response));
                    break;
                case 401:
                    displayAlert("You're not authorized!");
                    break;
                case 420:
                    displayAlert("Pokemon not found. Please check your input.");
                default:
                    break;
            }
        }
    }


    private void catchPokemon(Pokemon p) {
        PokemonDataSource dataSource = new PokemonDataSource(view.getContext());

        // Save to DB
        dataSource.open();
        if(!dataSource.getPokemons().contains(p)) {
            dataSource.createPokemon(p);
            PokemonDataSource.getAdapter().add(p);
            PokemonDataSource.getAdapter().notifyDataSetChanged();
        }
        dataSource.close();
    }


    private Pokemon inputStreamToPokemon(InputStream response) {
        Scanner in = new Scanner(response);
        Gson gson = new Gson();

        StringBuilder builder = new StringBuilder();
        while (in.hasNextLine()) {
            builder.append(in.nextLine());
        }

        Pokemon result = gson.fromJson(builder.toString(), Pokemon.class);
        return result;
    }


    private void displayAlert(String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
