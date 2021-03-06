package com.example.gamehub.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gamehub.DetailActivity;
import com.example.gamehub.Game;
import com.example.gamehub.GameListActivity;
import com.example.gamehub.R;
import com.example.gamehub.SearchActivity;

import org.parceler.Parcels;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    Context context;
    List<Game> games;

    public GameAdapter(GameListActivity context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    public GameAdapter(SearchActivity context, List<Game> games) {
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

    public void clear() {
        games.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Game> gameList) {
        games.addAll(gameList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvGameTitle;
        TextView tvGameDesc;
        ImageView ivGamePoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGameTitle = itemView.findViewById(R.id.tvGameTitle);
            tvGameDesc = itemView.findViewById(R.id.tvGameDesc);
            ivGamePoster = itemView.findViewById(R.id.ivGamePoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Game game) {
            tvGameTitle.setText(game.getTitle());
            Log.i("Adapter", game.getOverview());
            tvGameDesc.setText(game.getOverview());
            Glide.with(context)
                    .load(game.getImage())
                    .apply(new RequestOptions()
                            .override(1000, 500)
                            .placeholder(R.drawable.placeholder))
                    .into(ivGamePoster);

            // 1. Register click listener on whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("game", Parcels.wrap(game));
                    context.startActivity(i);
                }
            });
        }
    }
}
