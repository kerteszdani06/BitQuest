package com.example.bitquest.ui;

import android.content.Intent;
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

public class SelectDuoActivity extends AppCompatActivity {

    private Spinner spinnerLead, spinnerPartner;
    private Button btnStartDungeon;
    private GuildArchive archive;
    private List<Hero> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_duo);

        spinnerLead = findViewById(R.id.spinnerLead);
        spinnerPartner = findViewById(R.id.spinnerPartner);
        btnStartDungeon = findViewById(R.id.btnStartDungeon);

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

        spinnerLead.setAdapter(adapter);
        spinnerPartner.setAdapter(adapter);

        btnStartDungeon.setOnClickListener(v -> {
            if (heroList.size() < 2) {
                Toast.makeText(this, "Need at least 2 heroes", Toast.LENGTH_SHORT).show();
                return;
            }

            int leadPos = spinnerLead.getSelectedItemPosition();
            int partnerPos = spinnerPartner.getSelectedItemPosition();

            if (leadPos == partnerPos) {
                Toast.makeText(this, "Choose two different heroes", Toast.LENGTH_SHORT).show();
                return;
            }

            Hero leadHero = heroList.get(leadPos);
            Hero partnerHero = heroList.get(partnerPos);

            leadHero.recordMission();
            partnerHero.recordMission();
            SaveManager.save(this, archive);

            Intent intent = new Intent(SelectDuoActivity.this, DungeonGateActivity.class);
            intent.putExtra("lead_id", leadHero.getId());
            intent.putExtra("partner_id", partnerHero.getId());
            startActivity(intent);
        });
    }
}
