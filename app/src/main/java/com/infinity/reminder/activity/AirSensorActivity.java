package com.infinity.reminder.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.infinity.reminder.R;
import com.infinity.reminder.model.DataAir;
import com.infinity.reminder.model.DataListUserByManager;
import com.infinity.reminder.model.UserData;
import com.infinity.reminder.retrofit2.APIUtils;
import com.infinity.reminder.retrofit2.DataClient;
import com.infinity.reminder.storage.Storager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirSensorActivity extends AppCompatActivity {

    private LineChart lineChart;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_sensor);
        addController();
        getData();
    }

    private void getData() {
        DataClient dataClient = APIUtils.getData();
        Call<DataAir> callback = dataClient.getDataAir("Bearer " + Storager.USER_APP.getAccess_token() , id);
        callback.enqueue(new Callback<DataAir>() {
            @Override
            public void onResponse(@NonNull Call<DataAir> call, @NonNull Response<DataAir> response) {
                lineChart.setData(generateDataLine(response.body().getData().getData() ));
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();
            }

            @Override
            public void onFailure(@NonNull Call<DataAir> call, @NonNull Throwable t) {

            }
        });
    }

    private void addController() {
        id = getIntent().getIntExtra("id", 0);
        lineChart = findViewById(R.id.line_chart);

        //lineChart.setData(generateDataLine(1 ));
        Description description = new Description();
        description.setText("Air Sensor");
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

    private LineData generateDataLine(List<DataAir.DataListAir> dataListAirs) {
        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < dataListAirs.size(); i++) {
            values1.add(new Entry(i, (float)(dataListAirs.get(i).getCo())));
        }

        LineDataSet d1 = new LineDataSet(values1, "CO");
        d1.setLineWidth(1f);
        d1.setColor(getResources().getColor(R.color.green));
        d1.setDrawValues(false);
        d1.setDrawCircles(false);
        d1.setDrawCircleHole(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();

        sets.add(d1);

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0 ; i < dataListAirs.size() ; i++){
            values2.add(new Entry(i, (float)(dataListAirs.get(i).getGas())));
        }

        LineDataSet d2 = new LineDataSet(values2, "GAS");
        d2.setLineWidth(1f);
        d2.setColor(getResources().getColor(R.color.red));
        d2.setDrawValues(false);
        d2.setDrawCircles(false);
        d2.setDrawCircleHole(false);

        sets.add(d2);

        return new LineData(sets);
    }
}