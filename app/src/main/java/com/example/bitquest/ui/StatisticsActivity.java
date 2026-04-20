package com.example.bitquest.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.bitquest.R;
import com.example.bitquest.logic.SaveManager;
import com.example.bitquest.model.GuildArchive;
import com.example.bitquest.model.Hero;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        TextView tvStatsSummary = findViewById(R.id.tvStatsSummary);

        GuildArchive archive = SaveManager.load(this);

        StringBuilder stats = new StringBuilder();

        for (Hero hero : archive.getAllHeroes()) {
            stats.append(hero.getName())
                    .append(" | Missions: ").append(hero.getMissionsPlayed())
                    .append(" | Wins: ").append(hero.getVictories())
                    .append(" | Lost: ").append(hero.getLostMissions())
                    .append(" | Training: ").append(hero.getTrainingSessions())
                    .append("\n");
        }

        tvStatsSummary.setText(stats.toString());

        // Prevent crash if no heroes exist
        if (archive.getAllHeroes().isEmpty()) {
            tvStatsSummary.setText("No statistics available yet.");
            return;
        }

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();

        for (Hero hero : archive.getAllHeroes()) {
            data.add(new ValueDataEntry(
                    hero.getName(),
                    hero.getTrainingSessions()
            ));
        }

        pie.data(data);
        pie.title("Training Sessions");

        anyChartView.setChart(pie);
    }
}
