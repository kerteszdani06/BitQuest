package com.example.bitquest.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitquest.R;
import com.example.bitquest.model.Hero;

import java.util.List;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroViewHolder> {

    private final List<Hero> heroList;

    public HeroAdapter(List<Hero> heroList) {
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hero, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Hero hero = heroList.get(position);

        holder.tvHeroName.setText(hero.getName());
        holder.tvHeroClass.setText(hero.getHeroClass());
        holder.tvHeroStats.setText(
                "Energy: " + hero.getEnergy() + "/" + hero.getMaxEnergy()
                        + " | Skill: " + hero.getSkill()
                        + " | XP: " + hero.getExperience()
                        + " | Kills: " + hero.getKillCount()
        );
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    static class HeroViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeroName, tvHeroClass, tvHeroStats;

        public HeroViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeroName = itemView.findViewById(R.id.tvHeroName);
            tvHeroClass = itemView.findViewById(R.id.tvHeroClass);
            tvHeroStats = itemView.findViewById(R.id.tvHeroStats);
        }
    }
}
