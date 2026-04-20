package com.example.bitquest.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitquest.R;
import com.example.bitquest.logic.CombatManager;
import com.example.bitquest.logic.MissionType;
import com.example.bitquest.logic.SaveManager;
import com.example.bitquest.model.GuildArchive;
import com.example.bitquest.model.Hero;

public class DungeonGateActivity extends AppCompatActivity {

    private TextView tvFloor, tvMonsterName, tvLeadHero, tvPartnerHero, tvCombatLog;
    private TextView tvPotionCount;
    private ProgressBar progressMonster, progressLead;
    private ImageView imgMonster, imgLead;
    private GuildArchive archive;
    private CombatManager combatManager;
    private MissionType missionType;
    private int floor = 1;
    private int potions = 1;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dungeon_gate);

        tvFloor = findViewById(R.id.tvFloor);
        tvMonsterName = findViewById(R.id.tvMonsterName);
        tvLeadHero = findViewById(R.id.tvLeadHero);
        tvPartnerHero = findViewById(R.id.tvPartnerHero);
        tvCombatLog = findViewById(R.id.tvCombatLog);
        tvPotionCount = findViewById(R.id.tvPotionCount);
        progressMonster = findViewById(R.id.progressMonster);
        progressLead = findViewById(R.id.progressLead);
        Button btnSwap1 = findViewById(R.id.btnSwap1);
        Button btnSwap2 = findViewById(R.id.btnSwap2);
        Button btnAttack = findViewById(R.id.btnAttack);
        Button btnUsePotion = findViewById(R.id.btnUsePotion);
        Button btnDefend = findViewById(R.id.btnDefend);
        Button btnSpecial = findViewById(R.id.btnSpecial);
        TextView tvMissionDescription = findViewById(R.id.tvMissionDescription);
        imgMonster = findViewById(R.id.imgMonster);
        imgLead = findViewById(R.id.imgLead);

        archive = SaveManager.load(this);

        int leadId = getIntent().getIntExtra("lead_id", -1);
        int reserve1Id = getIntent().getIntExtra("reserve1_id", -1);
        int reserve2Id = getIntent().getIntExtra("reserve2_id", -1);

        String missionTypeString = getIntent().getStringExtra("mission_type");
        if (missionTypeString == null) {
            missionTypeString = "COMBAT";
        }
        missionType = MissionType.valueOf(missionTypeString);

        Hero lead = archive.getHero(leadId);
        Hero reserve1 = archive.getHero(reserve1Id);
        Hero reserve2 = null;

        if (reserve2Id != -1) {
            reserve2 = archive.getHero(reserve2Id);
        }

        if (lead == null || reserve1 == null) {
            Toast.makeText(this, "Heroes not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        combatManager = new CombatManager(lead, reserve1, reserve2, floor, missionType);
        updateUI();

        btnAttack.setOnClickListener(v -> handleAttack());
        btnSwap1.setOnClickListener(v -> handleSwap1());
        btnSwap2.setOnClickListener(v -> handleSwap2());
        btnUsePotion.setOnClickListener(v -> handlePotion());
        btnDefend.setOnClickListener(v -> handleDefend());
        btnSpecial.setOnClickListener(v -> handleSpecial());

        tvMissionDescription.setText(
                "Mission Type: " + missionType.name() +
                        "\nSpecialization bonuses may apply."
        );
    }

    @SuppressLint("SetTextI18n")
    private void handleAttack() {
        StringBuilder log = new StringBuilder();

        log.append(combatManager.attack()).append("\n");
        animateHit(imgMonster);

        if (combatManager.isVictory()) {
            rewardSurvivors();

            String potionRewardMessage = rewardPotionIfNeeded();
            if (!potionRewardMessage.isEmpty()) {
                log.append(potionRewardMessage).append("\n");
            }

            SaveManager.save(this, archive);
            tvCombatLog.setText(log.toString());
            updateUI();
            showVictoryDialog();
            return;
        }

        log.append(combatManager.enemyTurn()).append("\n");
        animateHit(imgLead);
        flashScreen();

        String deathLog = combatManager.checkDeaths();
        if (!deathLog.isEmpty()) {
            log.append(deathLog);
        }

        sendDefeatedHeroesToMedbay();

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

    private void handleSwap1() {
        StringBuilder log = new StringBuilder();

        log.append(combatManager.swapToReserve1()).append("\n");
        log.append(combatManager.enemyTurn()).append("\n");

        animateHit(imgLead);
        flashScreen();

        String deathLog = combatManager.checkDeaths();
        if (!deathLog.isEmpty()) {
            log.append(deathLog);
        }

        sendDefeatedHeroesToMedbay();

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

    private void handleSwap2() {
        StringBuilder log = new StringBuilder();

        log.append(combatManager.swapToReserve2()).append("\n");
        log.append(combatManager.enemyTurn()).append("\n");

        animateHit(imgLead);
        flashScreen();

        String deathLog = combatManager.checkDeaths();
        if (!deathLog.isEmpty()) {
            log.append(deathLog);
        }

        sendDefeatedHeroesToMedbay();

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

    @SuppressLint("SetTextI18n")
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

    @SuppressLint("SetTextI18n")
    private void handleDefend() {
        StringBuilder log = new StringBuilder();

        log.append(combatManager.defend()).append("\n");
        log.append(combatManager.enemyTurn()).append("\n");
        flashScreen();

        String deathLog = combatManager.checkDeaths();
        if (!deathLog.isEmpty()) {
            log.append(deathLog);
        }

        sendDefeatedHeroesToMedbay();

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

    @SuppressLint("SetTextI18n")
    private void handleSpecial() {
        StringBuilder log = new StringBuilder();

        log.append(combatManager.useSpecial()).append("\n");
        animateHit(imgMonster);

        if (combatManager.isVictory()) {
            rewardSurvivors();

            String potionRewardMessage = rewardPotionIfNeeded();
            if (!potionRewardMessage.isEmpty()) {
                log.append(potionRewardMessage).append("\n");
            }

            SaveManager.save(this, archive);
            tvCombatLog.setText(log.toString());
            updateUI();
            showVictoryDialog();
            return;
        }

        log.append(combatManager.enemyTurn()).append("\n");
        animateHit(imgLead);
        flashScreen();

        String deathLog = combatManager.checkDeaths();
        if (!deathLog.isEmpty()) {
            log.append(deathLog);
        }

        sendDefeatedHeroesToMedbay();

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
        Hero reserve1 = combatManager.getReserve1();
        Hero reserve2 = combatManager.getReserve2();

        if (lead != null && lead.isAlive()) {
            lead.incrementKillCount();
            lead.gainExperience(50);
            lead.recordVictory();
        }

        if (reserve1 != null && reserve1.isAlive()) {
            reserve1.gainExperience(50);
            reserve1.recordVictory();
        }

        if (reserve2 != null && reserve2.isAlive()) {
            reserve2.gainExperience(50);
            reserve2.recordVictory();
        }
    }

    private void sendDefeatedHeroesToMedbay() {
        for (Hero defeated : combatManager.getDefeatedHeroes()) {
            defeated.recordDefeat();
            defeated.recordLostMission();
            defeated.sendToMedbay();
        }
        combatManager.clearDefeatedHeroes();
    }

    private void returnToInnAndRestoreEnergy() {
        archive.restoreAllAvailableHeroesEnergy();
        SaveManager.save(this, archive);
        finish();
    }

    private void showGameOverDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("All active heroes have fallen and were sent to Medbay.")
                .setPositiveButton("Return to Inn", (dialog, which) -> {
                    archive.restoreAllAvailableHeroesEnergy();
                    SaveManager.save(this, archive);
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    @SuppressLint("SetTextI18n")
    private void showVictoryDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Victory")
                .setMessage("Monster defeated. Go deeper or return to the Inn?")
                .setPositiveButton("Go Deeper", (dialog, which) -> {
                    floor++;

                    Hero lead = combatManager.getLead();
                    Hero reserve1 = combatManager.getReserve1();
                    Hero reserve2 = combatManager.getReserve2();

                    combatManager = new CombatManager(
                            lead,
                            reserve1,
                            reserve2,
                            floor,
                            missionType
                    );

                    tvCombatLog.setText("A new monster appears.");
                    updateUI();
                })
                .setNegativeButton("Return to Inn", (dialog, which) -> returnToInnAndRestoreEnergy())
                .show();
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        tvFloor.setText("Floor " + floor);

        if (combatManager.getMonster() != null) {
            imgMonster.setImageResource(R.drawable.monster);
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
            imgLead.setImageResource(lead.getImageResId());
        } else {
            tvLeadHero.setText("Lead: None");
            progressLead.setMax(100);
            progressLead.setProgress(0);
        }

        Hero reserve1 = combatManager.getReserve1();
        Hero reserve2 = combatManager.getReserve2();

        String reserveText = "";

        if (reserve1 != null) {
            reserveText += "Reserve 1: " + reserve1.getName() + " (" + reserve1.getHeroClass() + ") HP: "
                    + reserve1.getEnergy() + "/" + reserve1.getMaxEnergy();
        } else {
            reserveText += "Reserve 1: None";
        }

        reserveText += "\n";

        if (reserve2 != null) {
            reserveText += "Reserve 2: " + reserve2.getName() + " (" + reserve2.getHeroClass() + ") HP: "
                    + reserve2.getEnergy() + "/" + reserve2.getMaxEnergy();
        } else {
            reserveText += "Reserve 2: None";
        }

        tvPartnerHero.setText(reserveText);
        tvPotionCount.setText("Potions: " + potions);}

    private String rewardPotionIfNeeded() {
        if (floor % 3 == 0) {
            potions++;
            return "You found a potion for clearing floor " + floor + "!";
        }
        return "";
    }


    private void flashScreen() {
        View root = findViewById(R.id.rootDungeon);
        root.setBackgroundColor(Color.RED);
        root.postDelayed(() -> root.setBackgroundColor(Color.TRANSPARENT), 150);
    }

    private void animateHit(View view) {
        view.animate()
                .translationX(20)
                .setDuration(50)
                .withEndAction(() -> view.animate().translationX(0).setDuration(50))
                .start();
    }
}
