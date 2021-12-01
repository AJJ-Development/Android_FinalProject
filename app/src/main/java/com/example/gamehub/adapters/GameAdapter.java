package com.example.gamehub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamehub.R;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    Context context;
    List<Game> games;

    public GameAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    // Inflate the layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View gameView = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        return new ViewHolder(gameView);
    }

    // Populating the data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the game at the passed in position
        Game game = games.get(position);
        //Bind the movie data into the view holder
        holder.bind(game);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGameTitle;
        TextView tvGameDesc;
        ImageView ivGamePoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGameTitle = itemView.findViewById(R.id.tvGameTitle);
            tvGameDesc = itemView.findViewById(R.id.tvGameDesc);
            ivGamePoster = itemView.findViewById(R.id.ivGamePoster);
        }

        public void bind(Game game) {
            tvGameTitle.setText(game.getTitle());
            tvGameDesc.setText(game.getOverview());
            Glide.with(context).load(game.getPosterPath()).into(ivGamePoster);
        }
    }
}
