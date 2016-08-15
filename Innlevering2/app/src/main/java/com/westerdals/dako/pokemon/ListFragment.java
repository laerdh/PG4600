package com.westerdals.dako.pokemon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.westerdals.dako.pokemon.db.PokemonDataSource;
import com.westerdals.dako.pokemon.model.Pokemon;

import java.util.List;

public class ListFragment extends Fragment {
    private ListView pokemons;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        pokemons = (ListView) view.findViewById(R.id.pokemonList);

        // Initialize list of fetched pokemons
        initPokemons();

        return view;
    }


    private void initPokemons() {
        // Fetch data from DB
        PokemonDataSource dataSource = new PokemonDataSource(getContext());

        // Fetch
        dataSource.open();
        List<Pokemon> catchedPokemons = dataSource.getPokemons();
        dataSource.close();

        // Set Listadapter
        PokemonDataSource.setAdapter(getActivity(), catchedPokemons);
        CustomListAdapter adapter = PokemonDataSource.getAdapter();
        pokemons.setAdapter(adapter);
    }
}
