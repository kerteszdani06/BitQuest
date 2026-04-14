package com.example.bitquest.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitquest.R;
import com.example.bitquest.logic.SaveManager;
import com.example.bitquest.model.GuildArchive;
import com.example.bitquest.model.Hero;

import java.util.ArrayList;
import java.util.List;

public class TrainingGroundsActivity extends AppCompatActivity {

    private Spinner spinnerHeroes;
    private Button btnTrainHero;
    private GuildArchive archive;
    private List<Hero> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_grounds);

        spinnerHeroes = findViewById(R.id.spinnerHeroes);
        btnTrainHero = findViewById(R.id.btnTrainHero);

        archive = SaveManager.load(this);
        heroList = archive.getAllHeroes();

        List<String> heroNames = new ArrayList<>();
        for (Hero hero : heroList) {
            heroNames.add(hero.getName() + " (" + hero.getHeroClass() + ")");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                heroNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHeroes.setAdapter(adapter);

        btnTrainHero.setOnClickListener(v -> {
            if (heroList.isEmpty()) {
                Toast.makeText(this, "No heroes available", Toast.LENGTH_SHORT).show();
                return;
            }

            int position = spinnerHeroes.getSelectedItemPosition();
            Hero selectedHero = heroList.get(position);
            selectedHero.gainExperience(50);
            selectedHero.recordTrainingSession();

            SaveManager.save(this, archive);
            Toast.makeText(this,
                    selectedHero.getName() + " trained and gained XP",
                    Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
