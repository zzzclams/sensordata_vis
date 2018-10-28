package com.example.zzzch.sensordata_vis;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import com.example.zzzch.sensordata_vis.Custom.MyXAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.example.zzzch.sensordata_vis.Custom.MyPMAxisValueFormatter;
import com.example.zzzch.sensordata_vis.Custom.MyTEMAxisValueFormatter;
import com.example.zzzch.sensordata_vis.Custom.MyppmAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import android.view.MenuItem;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.XAxis;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import android.widget.Toast;


import android.support.design.widget.NavigationView;
import android.content.Intent;
import android.support.v4.view.GravityCompat;

public class indoor_data extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private LineChart Temp_lineChart;
    private LineChart CO2_lineChart;
    private LineChart CO_lineChart;
    private LineChart PM_lineChart;
    private LineChart Smoke_lineChart;
    private LineChart Formaldehyde_lineChart;

    ArrayList<Entry> yData_1;
    ArrayList<Entry> yData_2;
    ArrayList<Entry> yData_3;
    ArrayList<Entry> yData_4;
    ArrayList<Entry> yData_5;
    ArrayList<Entry> yData_6;


    ValueEventListener valueEventListener;
    DatabaseReference mPostReference;
    DrawerLayout mDrawer_layout;
    NavigationView navigationView;
    ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.indoor_layout);
        // The navigation bar
        mDrawer_layout = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer_layout, R.string.open, R.string.close);
        mDrawer_layout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // The chart part
        Temp_lineChart = findViewById(R.id.Data_of_Temp);
        CO2_lineChart = findViewById(R.id.Data_of_CO2);
        CO_lineChart = findViewById(R.id.Data_of_CO);
        PM_lineChart = findViewById(R.id.Data_of_PM);
        Smoke_lineChart = findViewById(R.id.Data_of_Smoke);
        Formaldehyde_lineChart = findViewById(R.id.Data_of_Formaldehyde);

        mPostReference = FirebaseDatabase.getInstance().getReference("Indoor");
    }

    @Override
    protected void onResume() {
        super.onResume();
        final long date_ref = System.currentTimeMillis()/1000;
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                yData_1 = new ArrayList<>();
                yData_2 = new ArrayList<>();
                yData_3 = new ArrayList<>();
                yData_4 = new ArrayList<>();
                yData_5 = new ArrayList<>();
                yData_6 = new ArrayList<>();

                long date_new = 0;

                for (DataSnapshot ds :dataSnapshot.getChildren()){

                    float ts_ref = Float.parseFloat(String.valueOf(date_new));
                    String TEM = ds.child("TEM").getValue().toString();
                    String CO2 = ds.child("CO2").getValue().toString();
                    String CO = ds.child("CO").getValue().toString();
                    String PM = ds.child("PM").getValue().toString();
                    String SM = ds.child("SM").getValue().toString();
                    String FO = ds.child("FO").getValue().toString();
                    Float SensorValue_TEM = Float.parseFloat(TEM);
                    Float SensorValue_CO2 = Float.parseFloat(CO2);
                    Float SensorValue_CO = Float.parseFloat(CO);
                    Float SensorValue_PM = Float.parseFloat(PM);
                    Float SensorValue_SM = Float.parseFloat(SM);
                    Float SensorValue_FO = Float.parseFloat(FO);

                    yData_1.add(new Entry(ts_ref,SensorValue_TEM));
                    yData_2.add(new Entry(ts_ref,SensorValue_CO2));
                    yData_3.add(new Entry(ts_ref,SensorValue_CO));
                    yData_4.add(new Entry(ts_ref,SensorValue_PM));
                    yData_5.add(new Entry(ts_ref,SensorValue_SM));
                    yData_6.add(new Entry(ts_ref,SensorValue_FO));
                    date_new = date_new + 15;
                }

                final LineDataSet lineDataSet_1 = new LineDataSet(yData_1,"Temp");
                final LineDataSet lineDataSet_2 = new LineDataSet(yData_2,"CO2");
                final LineDataSet lineDataSet_3 = new LineDataSet(yData_3,"CO");
                final LineDataSet lineDataSet_4 = new LineDataSet(yData_4,"PM");
                final LineDataSet lineDataSet_5 = new LineDataSet(yData_5,"Smoke");
                final LineDataSet lineDataSet_6 = new LineDataSet(yData_6,"Formaldehyde");
                LineData data_TEM = new LineData(lineDataSet_1);
                LineData data_CO2 = new LineData(lineDataSet_2);
                LineData data_CO = new LineData(lineDataSet_3);
                LineData data_PM = new LineData(lineDataSet_4);
                LineData data_SM = new LineData(lineDataSet_5);
                LineData data_FO = new LineData(lineDataSet_6);

                IAxisValueFormatter xAxisFormatter = new MyXAxisValueFormatter(date_ref);

                Temp_lineChart.setData(data_TEM);
                Temp_lineChart.setMaxVisibleValueCount(20);
                YAxis yAxis_TEM = Temp_lineChart.getAxisLeft();
                yAxis_TEM.setValueFormatter(new MyTEMAxisValueFormatter());
                YAxis right_TEM = Temp_lineChart.getAxisRight();
                right_TEM.setEnabled(false);

                XAxis xAxis_TEM = Temp_lineChart.getXAxis();
                xAxis_TEM.setValueFormatter(xAxisFormatter);

                CO2_lineChart.setData(data_CO2);
                CO2_lineChart.setMaxVisibleValueCount(20);
                YAxis yAxis_CO2 = CO2_lineChart.getAxisLeft();
                yAxis_CO2.setValueFormatter(new MyppmAxisValueFormatter());
                YAxis right_CO2 = CO2_lineChart.getAxisRight();
                right_CO2.setEnabled(false);

                XAxis xAxis_CO2 = CO2_lineChart.getXAxis();
                xAxis_CO2.setValueFormatter(xAxisFormatter);

                CO_lineChart.setData(data_CO);
                CO_lineChart.setMaxVisibleValueCount(20);
                YAxis yAxis_CO = CO_lineChart.getAxisLeft();
                yAxis_CO.setValueFormatter(new MyppmAxisValueFormatter());
                YAxis right_CO = CO_lineChart.getAxisRight();
                right_CO.setEnabled(false);

                XAxis xAxis_CO= CO_lineChart.getXAxis();
                xAxis_CO.setValueFormatter(xAxisFormatter);

                PM_lineChart.setData(data_PM);
                PM_lineChart.setMaxVisibleValueCount(20);
                YAxis yAxis_PM = PM_lineChart.getAxisLeft();
                yAxis_PM.setValueFormatter(new MyPMAxisValueFormatter());
                YAxis right_PM = PM_lineChart.getAxisRight();
                right_PM.setEnabled(false);

                XAxis xAxis_PM= PM_lineChart.getXAxis();
                xAxis_PM.setValueFormatter(xAxisFormatter);

                Smoke_lineChart.setData(data_SM);
                Smoke_lineChart.setMaxVisibleValueCount(20);
                YAxis yAxis_SM = Smoke_lineChart.getAxisLeft();
                yAxis_SM.setValueFormatter(new MyppmAxisValueFormatter());
                YAxis right_SM = Smoke_lineChart.getAxisRight();
                right_SM.setEnabled(false);

                XAxis xAxis_SM= Smoke_lineChart.getXAxis();
                xAxis_SM.setValueFormatter(xAxisFormatter);

                Formaldehyde_lineChart.setData(data_FO);
                Formaldehyde_lineChart.setMaxVisibleValueCount(20);
                YAxis yAxis_FO = Formaldehyde_lineChart.getAxisLeft();
                yAxis_FO.setValueFormatter(new MyppmAxisValueFormatter());
                YAxis right_FO = Formaldehyde_lineChart.getAxisRight();
                right_FO.setEnabled(false);

                XAxis xAxis_FO= Formaldehyde_lineChart.getXAxis();
                xAxis_FO.setValueFormatter(xAxisFormatter);

                Temp_lineChart.notifyDataSetChanged();
                CO2_lineChart.notifyDataSetChanged();
                CO_lineChart.notifyDataSetChanged();
                PM_lineChart.notifyDataSetChanged();
                Smoke_lineChart.notifyDataSetChanged();
                Formaldehyde_lineChart.notifyDataSetChanged();

                Temp_lineChart.invalidate();
                CO2_lineChart.invalidate();
                CO_lineChart.invalidate();
                PM_lineChart.invalidate();
                Smoke_lineChart.invalidate();
                Formaldehyde_lineChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(indoor_data.this, "Fail to load post", Toast.LENGTH_SHORT).show();
            }
        };
        mPostReference.addValueEventListener(valueEventListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        switch (id){

            case R.id.Home_page:
                Intent homepage= new Intent(indoor_data.this,MainActivity.class);
                startActivity(homepage);
                break;
            case R.id.indoor_data:
                Intent indoor= new Intent(indoor_data.this,indoor_data.class);
                startActivity(indoor);
                break;
            case R.id.outdoor_data:
                Intent outdoor= new Intent(indoor_data.this,outdoor_data.class);
                startActivity(outdoor);
                break;
            case R.id.analysis:
                Intent ana= new Intent(indoor_data.this,data_analysis.class);
                startActivity(ana);
                break;
        }

        DrawerLayout mDrawer_layout = findViewById(R.id.drawerLayout);
        mDrawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPostReference.removeEventListener(valueEventListener);
    }
}


