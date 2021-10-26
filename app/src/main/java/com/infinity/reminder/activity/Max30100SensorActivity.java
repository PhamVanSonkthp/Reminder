package com.infinity.reminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.infinity.reminder.R;

import java.util.ArrayList;

public class Max30100SensorActivity extends AppCompatActivity {

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max30100_sensor);
        addController();
    }

    private void addController() {
        lineChart = findViewById(R.id.line_chart);

        lineChart.setData(generateDataLine(1 ));
        Description description = new Description();
        description.setText("Max30100 Sensor");
        lineChart.setDescription(description);

        lineChart.getAxisRight().setDrawLabels(true);
        //lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getLegend().setEnabled(true);
//        lineChart.getAxisRight().setDrawGridLines(false);
//        lineChart.getAxisLeft().setDrawGridLines(false);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis = lineChart.getAxisLeft();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //yAxis.setLabelCount(5);
//        xAxis.setLabelCount(7,true);
//        yAxis.setLabelCount(5,true);
        //xAxis.enableAxisLineDashedLine(10,10,0);

    }

    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> values1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            values1.add(new Entry(i, (float)(Math.random() * 65) + 100));
        }
        LineDataSet d1 = new LineDataSet(values1, "IR");
        d1.setLineWidth(1f);
        d1.setColor(getResources().getColor(R.color.red));
        d1.setDrawValues(false);
        d1.setDrawCircles(false);
        d1.setDrawCircleHole(false);

        ArrayList<Entry> values2 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            values2.add(new Entry(i, (float)(Math.random() * 65) + 100));
        }
        LineDataSet d2 = new LineDataSet(values2, "BPM");
        d2.setLineWidth(1f);
        d2.setColor(getResources().getColor(R.color.green));
        d2.setDrawValues(false);
        d2.setDrawCircles(false);
        d2.setDrawCircleHole(false);

        ArrayList<Entry> values3 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            values3.add(new Entry(i, (float)(Math.random() * 65) + 100));
        }
        LineDataSet d3 = new LineDataSet(values3, "SPO2");
        d3.setLineWidth(1f);
        d3.setColor(getResources().getColor(R.color.yellow));
        d3.setDrawValues(false);
        d3.setDrawCircles(false);
        d3.setDrawCircleHole(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);
        sets.add(d3);

        return new LineData(sets);
    }
}