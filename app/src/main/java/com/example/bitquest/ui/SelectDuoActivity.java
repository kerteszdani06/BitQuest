package com.example.bitquest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitquest.R;
import com.example.bitquest.logic.MissionType;
import com.example.bitquest.logic.SaveManager;
import com.example.bitquest.model.GuildArchive;
import com.example.bitquest.model.Hero;

import java.util.ArrayList;
import java.util.List;

public class SelectDuoActivity extends AppCompatActivity {

    private Spinner spinnerLead, spinnerReserve1, spinnerReserve2, spinnerMissionType;
    private Button btnStartDungeon;
    private GuildArchive archive;
    private List<Hero> heroList;
    private List<String> heroNames;
    private List<String> reserve2Options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_duo);

        spinnerLead = findViewById(R.id.spinnerLead);
        spinnerReserve1 = findViewById(R.id.spinnerPartner); // keep existing ID if you already use it
        spinnerReserve2 = findViewById(R.id.spinnerReserve2);
        spinnerMissionType = findViewById(R.id.spinnerMissionType);
        btnStartDungeon = findViewById(R.id.btnStartDungeon);

        archive = SaveManager.load(this);
        heroList = archive.getAvailableHeroes();

        if (heroList.size() < 2) {
            Toast.makeText(this, "Need at least 2 available heroes", Toast.LENGTH_SHORT).show();
        }

        heroNames = new ArrayList<>();
        for (Hero hero : heroList) {
            heroNames.add(hero.getName() + " (" + hero.getHeroClass() + ")");
        }

        ArrayAdapter<String> heroAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                heroNames
        );
        heroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLead.setAdapter(heroAdapter);
        spinnerReserve1.setAdapter(heroAdapter);

        reserve2Options = new ArrayList<>();
        reserve2Options.add("None");
        reserve2Options.addAll(heroNames);

        ArrayAdapter<String> reserve2Adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                reserve2Options
        );
        reserve2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReserve2.setAdapter(reserve2Adapter);

        ArrayAdapter<MissionType> missionAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                MissionType.values()
        );
        missionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMissionType.setAdapter(missionAdapter);

        btnStartDungeon.setOnClickListener(v -> {
            if (heroList.size() < 2) {
                Toast.makeText(this, "Need at least 2 available heroes", Toast.LENGTH_SHORT).show();
                return;
            }

            int leadPos = spinnerLead.getSelectedItemPosition();
            int reserve1Pos = spinnerReserve1.getSelectedItemPosition();
            int reserve2SpinnerPos = spinnerReserve2.getSelectedItemPosition();

            if (leadPos == reserve1Pos) {
                Toast.makeText(this, "Choose two different heroes", Toast.LENGTH_SHORT).show();
                return;
            }

            Hero leadHero = heroList.get(leadPos);
            Hero reserve1Hero = heroList.get(reserve1Pos);
            Hero reserve2Hero = null;

            if (reserve2SpinnerPos > 0) {
                int reserve2Pos = reserve2SpinnerPos - 1;
                if (reserve2Pos == leadPos || reserve2Pos == reserve1Pos) {
                    Toast.makeText(this, "Third hero must be different", Toast.LENGTH_SHORT).show();
                    return;
                }
                reserve2Hero = heroList.get(reserve2Pos);
            }

            leadHero.recordMission();
            reserve1Hero.recordMission();
            if (reserve2Hero != null) {
                reserve2Hero.recordMission();
            }
            SaveManager.save(this, archive);

            MissionType missionType = (MissionType) spinnerMissionType.getSelectedItem();

            Intent intent = new Intent(SelectDuoActivity.this, DungeonGateActivity.class);
            intent.putExtra("lead_id", leadHero.getId());
            intent.putExtra("reserve1_id", reserve1Hero.getId());

            if (reserve2Hero != null) {
                intent.putExtra("reserve2_id", reserve2Hero.getId());
            }

            intent.putExtra("mission_type", missionType.name());
            startActivity(intent);
        });
    }
}
