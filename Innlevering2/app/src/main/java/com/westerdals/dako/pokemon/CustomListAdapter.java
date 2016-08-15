package com.westerdals.dako.pokemon;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.westerdals.dako.pokemon.model.Pokemon;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Pokemon> {
    private final Activity context;
    private final List<Pokemon> pokemons;


    public CustomListAdapter(Activity context, List<Pokemon> pokemons) {
        super(context, R.layout.custom_list, pokemons);
        this.pokemons = pokemons;
        this.context = context;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_list, null, true);

        TextView txtPokemonName = (TextView) rowView.findViewById(R.id.listPokemonName);
        ImageView imgPokemon = (ImageView) rowView.findViewById(R.id.listImage);

        Pokemon current = pokemons.get(position);
        txtPokemonName.setText(current.getName());
        loadBitmap(imgPokemon, current.getImageUrl());

        return rowView;
    }


    private void loadBitmap(ImageView iv, String url) {
        Picasso.with(context)
                .load(url)
                .resize(150, 150)
                .onlyScaleDown()
                .centerInside()
                .into(iv);
    }
}
