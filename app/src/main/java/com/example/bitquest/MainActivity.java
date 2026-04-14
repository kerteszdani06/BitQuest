package com.example.bitquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitquest.logic.SaveManager;
import com.example.bitquest.model.GuildArchive;
import com.example.bitquest.ui.DungeonGateActivity;
import com.example.bitquest.ui.RecruitHeroActivity;
import com.example.bitquest.ui.SelectDuoActivity;
import com.example.bitquest.ui.StatisticsActivity;
import com.example.bitquest.ui.TrainingGroundsActivity;
import com.example.bitquest.ui.adapter.HeroAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerHeroes;
    private Button btnRecruit, btnTrain, btnDungeon, btnStats;
    private GuildArchive archive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerHeroes = findViewById(R.id.recyclerHeroes);
        btnRecruit = findViewById(R.id.btnRecruit);
        btnTrain = findViewById(R.id.btnTrain);
        btnDungeon = findViewById(R.id.btnDungeon);
        btnStats = findViewById(R.id.btnStats);

        archive = SaveManager.load(this);

        recyclerHeroes.setLayoutManager(new LinearLayoutManager(this));
        recyclerHeroes.setAdapter(new HeroAdapter(archive.getAllHeroes()));

        btnRecruit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecruitHeroActivity.class);
            startActivity(intent);
        });

        btnTrain.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TrainingGroundsActivity.class);
            startActivity(intent);
        });

        btnDungeon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SelectDuoActivity.class);
            startActivity(intent);
        });

        btnStats.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        archive = SaveManager.load(this);
        recyclerHeroes.setAdapter(new HeroAdapter(archive.getAllHeroes()));
    }
}