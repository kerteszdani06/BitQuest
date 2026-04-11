package com.example.bitquest.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitquest.R;
import com.example.bitquest.logic.SaveManager;
import com.example.bitquest.model.Archer;
import com.example.bitquest.model.Cleric;
import com.example.bitquest.model.GuildArchive;
import com.example.bitquest.model.Hero;
import com.example.bitquest.model.Knight;
import com.example.bitquest.model.Necromancer;
import com.example.bitquest.model.Wizard;
import com.example.bitquest.util.IdGenerator;

public class RecruitHeroActivity extends AppCompatActivity {

    private EditText etHeroName;
    private RadioGroup radioGroupClasses;
    private Button btnConfirmRecruit;
    private GuildArchive archive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_hero);

        etHeroName = findViewById(R.id.etHeroName);
        radioGroupClasses = findViewById(R.id.radioGroupClasses);
        btnConfirmRecruit = findViewById(R.id.btnConfirmRecruit);

        archive = SaveManager.load(this);

        btnConfirmRecruit.setOnClickListener(v -> {
            String name = etHeroName.getText().toString().trim();
            int selectedId = radioGroupClasses.getCheckedRadioButtonId();

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter a hero name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == -1) {
                Toast.makeText(this, "Select a class", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = IdGenerator.getNextId(this);
            Hero hero = null;

            if (selectedId == R.id.rbKnight) {
                hero = new Knight(id, name);
            } else if (selectedId == R.id.rbArcher) {
                hero = new Archer(id, name);
            } else if (selectedId == R.id.rbCleric) {
                hero = new Cleric(id, name);
            } else if (selectedId == R.id.rbWizard) {
                hero = new Wizard(id, name);
            } else if (selectedId == R.id.rbNecromancer) {
                hero = new Necromancer(id, name);
            }

            if (hero != null) {
                archive.addHero(hero);
                SaveManager.save(this, archive);
                Toast.makeText(this, "Hero recruited", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}