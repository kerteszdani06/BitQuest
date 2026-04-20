package com.example.bitquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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
    private GuildArchive archive;
    private Button btnSaveFile, btnLoadFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerHeroes = findViewById(R.id.recyclerHeroes);
        Button btnRecruit = findViewById(R.id.btnRecruit);
        Button btnTrain = findViewById(R.id.btnTrain);
        Button btnDungeon = findViewById(R.id.btnDungeon);
        Button btnStats = findViewById(R.id.btnStats);
        btnSaveFile = findViewById(R.id.btnSaveFile);
        btnLoadFile = findViewById(R.id.btnLoadFile);

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

        btnSaveFile.setOnClickListener(v -> {
            SaveManager.saveToFile(this, archive);
            Toast.makeText(this, "Crew saved to file", Toast.LENGTH_SHORT).show();
        });

        btnLoadFile.setOnClickListener(v -> {
            archive = SaveManager.loadFromFile(this);
            SaveManager.save(this, archive); // keep auto-save in sync
            recyclerHeroes.setAdapter(new HeroAdapter(archive.getAllHeroes()));
            Toast.makeText(this, "Crew loaded from file", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        archive = SaveManager.load(this);
        recyclerHeroes.setAdapter(new HeroAdapter(archive.getAllHeroes()));
    }
}