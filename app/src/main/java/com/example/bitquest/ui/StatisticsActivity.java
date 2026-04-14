package com.example.bitquest.ui;

import android.os.Bundle;

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

    private AnyChartView anyChartView;
    private GuildArchive archive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        anyChartView = findViewById(R.id.any_chart_view);

        archive = SaveManager.load(this);

        // Prevent crash if no heroes exist
        if (archive == null || archive.getAllHeroes().isEmpty()) {
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
