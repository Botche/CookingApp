package com.example.cookingapp.Fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookingapp.Adapters.RecipeAdapter;
import com.example.cookingapp.Models.Recipe;
import com.example.cookingapp.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Recipe> recipes;
    private RecipeAdapter recipeAdapter;

    public RecipeFragment() {
        this.recipes = new ArrayList<>();
    }

    public RecipeFragment(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_recipes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recipeAdapter = new RecipeAdapter(getContext(), recipes, true);
        recyclerView.setAdapter(recipeAdapter);

        return view;
    }
}