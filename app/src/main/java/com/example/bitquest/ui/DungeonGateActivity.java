package com.example.bitquest.ui;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitquest.R;
import com.example.bitquest.logic.CombatManager;
import com.example.bitquest.logic.SaveManager;
import com.example.bitquest.model.GuildArchive;
import com.example.bitquest.model.Hero;

public class DungeonGateActivity extends AppCompatActivity {

    private TextView tvFloor, tvMonsterName, tvLeadHero, tvPartnerHero, tvCombatLog;
    private ProgressBar progressMonster, progressLead;
    private Button btnAttack, btnSwap, btnUsePotion, btnDefend, btnSpecial;

    private GuildArchive archive;
    private CombatManager combatManager;
    private int floor = 1;
    private int potions = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dungeon_gate);

        tvFloor = findViewById(R.id.tvFloor);
        tvMonsterName = findViewById(R.id.tvMonsterName);
        tvLeadHero = findViewById(R.id.tvLeadHero);
        tvPartnerHero = findViewById(R.id.tvPartnerHero);
        tvCombatLog = findViewById(R.id.tvCombatLog);
        progressMonster = findViewById(R.id.progressMonster);
        progressLead = findViewById(R.id.progressLead);
        btnAttack = findViewById(R.id.btnAttack);
        btnSwap = findViewById(R.id.btnSwap);
        btnUsePotion = findViewById(R.id.btnUsePotion);
        btnDefend = findViewById(R.id.btnDefend);
        btnSpecial = findViewById(R.id.btnSpecial);

        archive = SaveManager.load(this);

        int leadId = getIntent().getIntExtra("lead_id", -1);
        int partnerId = getIntent().getIntExtra("partner_id", -1);

        Hero lead = archive.getHero(leadId);
        Hero partner = archive.getHero(partnerId);

        if (lead == null || partner == null) {
            Toast.makeText(this, "Heroes not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        combatManager = new CombatManager(lead, partner, floor);
        updateUI();

        btnAttack.setOnClickListener(v -> handleAttack());
        btnSwap.setOnClickListener(v -> handleSwap());
        btnUsePotion.setOnClickListener(v -> handlePotion());
        btnDefend.setOnClickListener(v -> handleDefend());
        btnSpecial.setOnClickListener(v -> handleSpecial());
    }

    private void handleAttack() {
        StringBuilder log = new StringBuilder();

        log.append(combatManager.attack()).append("\n");

        if (combatManager.isVictory()) {
            rewardSurvivors();
            SaveManager.save(this, archive);
            tvCombatLog.setText(log.toString());
            updateUI();
            showVictoryDialog();
            return;
        }

        log.append(combatManager.enemyTurn()).append("\n");
        flashScreen();

        String deathLog = combatManager.checkDeaths();
        if (!deathLog.isEmpty()) {
            log.append(deathLog);
        }

        removeDefeatedHeroesFromArchive();

        if (combatManager.isGameOver()) {
            SaveManager.save(this, archive);
            tvCombatLog.setText(log + "\nGame Over.");
            showGameOverDialog();
            return;
        }

        SaveManager.save(this, archive);
        tvCombatLog.setText(log.toString());
        updateUI();
    }

    private void handleSwap() {
        StringBuilder log = new StringBuilder();

        log.append(combatManager.swap()).append("\n");
        log.append(combatManager.enemyTurn()).append("\n");
        flashScreen();

        String deathLog = combatManager.checkDeaths();
        if (!deathLog.isEmpty()) {
            log.append(deathLog);
        }

        removeDefeatedHeroesFromArchive();

        if (combatManager.isGameOver()) {
            SaveManager.save(this, archive);
            tvCombatLog.setText(log + "\nGame Over.");
            showGameOverDialog();
            return;
        }

        SaveManager.save(this, archive);
        tvCombatLog.setText(log.toString());
        updateUI();
    }

    private void handlePotion() {
        Hero lead = combatManager.getLead();

        if (potions <= 0) {
            Toast.makeText(this, "No potions left", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lead != null) {
            lead.heal(30);
            potions--;
            SaveManager.save(this, archive);
            tvCombatLog.setText(lead.getName() + " used a potion and recovered energy.");
            updateUI();
        }
    }

    private void handleDefend() {
        StringBuilder log = new StringBuilder();

        log.append(combatManager.defend()).append("\n");
        log.append(combatManager.enemyTurn()).append("\n");
        flashScreen();

        String deathLog = combatManager.checkDeaths();
        if (!deathLog.isEmpty()) {
            log.append(deathLog);
        }

        removeDefeatedHeroesFromArchive();

        if (combatManager.isGameOver()) {
            SaveManager.save(this, archive);
            tvCombatLog.setText(log + "\nGame Over.");
            showGameOverDialog();
            return;
        }

        SaveManager.save(this, archive);
        tvCombatLog.setText(log.toString());
        updateUI();
    }

    private void handleSpecial() {
        StringBuilder log = new StringBuilder();

        log.append(combatManager.useSpecial()).append("\n");

        if (combatManager.isVictory()) {
            rewardSurvivors();
            SaveManager.save(this, archive);
            tvCombatLog.setText(log.toString());
            updateUI();
            showVictoryDialog();
            return;
        }

        log.append(combatManager.enemyTurn()).append("\n");
        flashScreen();

        String deathLog = combatManager.checkDeaths();
        if (!deathLog.isEmpty()) {
            log.append(deathLog);
        }

        removeDefeatedHeroesFromArchive();

        if (combatManager.isGameOver()) {
            SaveManager.save(this, archive);
            tvCombatLog.setText(log + "\nGame Over.");
            showGameOverDialog();
            return;
        }

        SaveManager.save(this, archive);
        tvCombatLog.setText(log.toString());
        updateUI();
    }

    private void rewardSurvivors() {
        Hero lead = combatManager.getLead();
        Hero partner = combatManager.getPartner();

        if (lead != null && lead.isAlive()) {
            lead.incrementKillCount();
            lead.gainExperience(50);
            lead.recordVictory();
        }

        if (partner != null && partner.isAlive()) {
            partner.gainExperience(50);
            partner.recordVictory();
        }
    }

    private void removeDefeatedHeroesFromArchive() {
        for (Hero defeated : combatManager.getDefeatedHeroes()) {
            defeated.recordDefeat();
            archive.removeHero(defeated.getId());
        }
        combatManager.clearDefeatedHeroes();
    }

    private void returnToInnAndRestoreEnergy() {
        archive.restoreAllHeroesEnergy();
        SaveManager.save(this, archive);
        finish();
    }

    private void showGameOverDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("All active heroes have fallen and were removed from the guild.")
                .setPositiveButton("Return to Inn", (dialog, which) -> {
                    archive.restoreAllHeroesEnergy();
                    SaveManager.save(this, archive);
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    private void showVictoryDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Victory")
                .setMessage("Monster defeated. Go deeper or return to the Inn?")
                .setPositiveButton("Go Deeper", (dialog, which) -> {
                    floor++;
                    Hero lead = combatManager.getLead();
                    Hero partner = combatManager.getPartner();
                    combatManager = new CombatManager(lead, partner, floor);
                    tvCombatLog.setText("A new monster appears.");
                    updateUI();
                })
                .setNegativeButton("Return to Inn", (dialog, which) -> returnToInnAndRestoreEnergy())
                .show();
    }

    private void updateUI() {
        tvFloor.setText("Floor " + floor);

        if (combatManager.getMonster() != null) {
            tvMonsterName.setText(
                    combatManager.getMonster().getName() + " HP: "
                            + combatManager.getMonster().getEnergy() + "/"
                            + combatManager.getMonster().getMaxEnergy()
            );
            progressMonster.setMax(combatManager.getMonster().getMaxEnergy());
            progressMonster.setProgress(combatManager.getMonster().getEnergy());
        }

        Hero lead = combatManager.getLead();
        if (lead != null) {
            tvLeadHero.setText(
                    "Lead: " + lead.getName() + " (" + lead.getHeroClass() + ") HP: "
                            + lead.getEnergy() + "/" + lead.getMaxEnergy()
            );
            progressLead.setMax(lead.getMaxEnergy());
            progressLead.setProgress(lead.getEnergy());
        } else {
            tvLeadHero.setText("Lead: None");
            progressLead.setMax(100);
            progressLead.setProgress(0);
        }

        Hero partner = combatManager.getPartner();
        if (partner != null) {
            tvPartnerHero.setText(
                    "Partner: " + partner.getName() + " (" + partner.getHeroClass() + ") HP: "
                            + partner.getEnergy() + "/" + partner.getMaxEnergy()
            );
        } else {
            tvPartnerHero.setText("Partner: None");
        }
    }

    private void flashScreen() {
        View root = findViewById(R.id.rootDungeon);
        root.setBackgroundColor(Color.RED);
        root.postDelayed(() -> root.setBackgroundColor(Color.TRANSPARENT), 150);
    }
}
