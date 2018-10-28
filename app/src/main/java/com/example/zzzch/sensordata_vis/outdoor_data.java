package com.example.zzzch.sensordata_vis;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import com.example.zzzch.sensordata_vis.Custom.MyPMAxisValueFormatter;
import com.example.zzzch.sensordata_vis.Custom.MyTEMAxisValueFormatter;
import com.example.zzzch.sensordata_vis.Custom.MyXAxisValueFormatter;
import com.example.zzzch.sensordata_vis.Custom.MyppbAxisValueFormatter;
import com.example.zzzch.sensordata_vis.Custom.MyppmAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.view.MenuItem;
import java.lang.*;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import android.support.design.widget.NavigationView;
import android.content.Intent;
import android.support.v4.view.GravityCompat;

public class outdoor_data extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private LineChart Temp_lineChart_out;
    private LineChart CO2_lineChart_out;
    private LineChart CO_lineChart_out;
    private LineChart PM_lineChart_out;
    private LineChart NO2_lineChart;
    private LineChart SO2_lineChart;


    ArrayList<Entry> yData_1_out;
    ArrayList<Entry> yData_2_out;
    ArrayList<Entry> yData_3_out;
    ArrayList<Entry> yData_4_out;
    ArrayList<Entry> yData_5_out;
    ArrayList<Entry> yData_6_out;


    DatabaseReference mPostReference;
    ValueEventListener valueEventListener2;
    DrawerLayout mDrawer_layout;
    NavigationView navigationView;
    ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outdoor_layout);

        // The navigation bar
        mDrawer_layout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer_layout, R.string.open, R.string.close);
        mDrawer_layout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // The chart part
        Temp_lineChart_out = (LineChart) findViewById(R.id.Data_of_Temp_out);
        CO2_lineChart_out = (LineChart) findViewById(R.id.Data_of_CO2_out);
        CO_lineChart_out = (LineChart) findViewById(R.id.Data_of_CO_out);
        PM_lineChart_out = (LineChart) findViewById(R.id.Data_of_PM_out);
        NO2_lineChart = (LineChart) findViewById(R.id.Data_of_NO2);
        SO2_lineChart = (LineChart) findViewById(R.id.Data_of_SO2);

        mPostReference = FirebaseDatabase.getInstance().getReference("Outdoor");

    }

    @Override
    protected void onResume() {
        super.onResume();
        final long date_ref = System.currentTimeMillis()/1000;
        mPostReference.addValueEventListener(valueEventListener2= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                yData_1_out = new ArrayList<>();
                yData_2_out = new ArrayList<>();
                yData_3_out = new ArrayList<>();
                yData_4_out = new ArrayList<>();
                yData_5_out = new ArrayList<>();
                yData_6_out = new ArrayList<>();

                long date_new = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    float ts_ref = Float.parseFloat(String.valueOf(date_new));
                    String TEM = ds.child("TEM").getValue().toString();
                    String CO2 = ds.child("CO2").getValue().toString();
                    String CO = ds.child("CO").getValue().toString();
                    String PM = ds.child("PM").getValue().toString();
                    String NO2 = ds.child("NO2").getValue().toString();
                    String SO2 = ds.child("SO2").getValue().toString();
                    Float SensorValue_TEM = Float.parseFloat(TEM);
                    Float SensorValue_CO2 = Float.parseFloat(CO2);
                    Float SensorValue_CO = Float.parseFloat(CO);
                    Float SensorValue_PM = Float.parseFloat(PM);
                    Float SensorValue_NO2 = Float.parseFloat(NO2);
                    Float SensorValue_SO2 = Float.parseFloat(SO2);
                    yData_1_out.add(new Entry(ts_ref,SensorValue_TEM));
                    yData_2_out.add(new Entry(ts_ref,SensorValue_CO2));
                    yData_3_out.add(new Entry(ts_ref,SensorValue_CO));
                    yData_4_out.add(new Entry(ts_ref,SensorValue_PM));
                    yData_5_out.add(new Entry(ts_ref,SensorValue_NO2));
                    yData_6_out.add(new Entry(ts_ref,SensorValue_SO2));
                    date_new = date_new + 15;
                }
                final LineDataSet lineDataSet_1 = new LineDataSet(yData_1_out,"Temp");
                final LineDataSet lineDataSet_2 = new LineDataSet(yData_2_out,"CO2");
                final LineDataSet lineDataSet_3 = new LineDataSet(yData_3_out,"CO");
                final LineDataSet lineDataSet_4 = new LineDataSet(yData_4_out,"PM");
                final LineDataSet lineDataSet_5 = new LineDataSet(yData_5_out,"NO2");
                final LineDataSet lineDataSet_6 = new LineDataSet(yData_6_out,"SO2");
                LineData data_TEM_out = new LineData(lineDataSet_1);
                LineData data_CO2_out = new LineData(lineDataSet_2);
                LineData data_CO_out = new LineData(lineDataSet_3);
                LineData data_PM_out = new LineData(lineDataSet_4);
                LineData data_NO2 = new LineData(lineDataSet_5);
                LineData data_SO2 = new LineData(lineDataSet_6);

                IAxisValueFormatter xAxisFormatter = new MyXAxisValueFormatter(date_ref);

                Temp_lineChart_out.setData(data_TEM_out);
                Temp_lineChart_out.setMaxVisibleValueCount(15);
                YAxis yAxis_TEM = Temp_lineChart_out.getAxisLeft();
                yAxis_TEM.setValueFormatter(new MyTEMAxisValueFormatter());
                YAxis right_TEM = Temp_lineChart_out.getAxisRight();
                right_TEM.setEnabled(false);

                XAxis xAxis_TEM = Temp_lineChart_out.getXAxis();
                xAxis_TEM.setValueFormatter(xAxisFormatter);

                CO2_lineChart_out.setData(data_CO2_out);
                CO2_lineChart_out.setMaxVisibleValueCount(15);
                YAxis yAxis_CO2 = CO2_lineChart_out.getAxisLeft();
                yAxis_CO2.setValueFormatter(new MyppmAxisValueFormatter());
                YAxis right_CO2 = CO2_lineChart_out.getAxisRight();
                right_CO2.setEnabled(false);

                XAxis xAxis_CO2 = CO2_lineChart_out.getXAxis();
                xAxis_CO2.setValueFormatter(xAxisFormatter);

                CO_lineChart_out.setData(data_CO_out);
                CO_lineChart_out.setMaxVisibleValueCount(15);
                YAxis yAxis_CO = CO_lineChart_out.getAxisLeft();
                yAxis_CO.setValueFormatter(new MyppmAxisValueFormatter());
                YAxis right_CO = CO_lineChart_out.getAxisRight();
                right_CO.setEnabled(false);

                XAxis xAxis_CO= CO_lineChart_out.getXAxis();
                xAxis_CO.setValueFormatter(xAxisFormatter);

                PM_lineChart_out.setData(data_PM_out);
                PM_lineChart_out.setMaxVisibleValueCount(15);
                YAxis yAxis_PM = PM_lineChart_out.getAxisLeft();
                yAxis_PM.setValueFormatter(new MyPMAxisValueFormatter());
                YAxis right_PM = PM_lineChart_out.getAxisRight();
                right_PM.setEnabled(false);

                XAxis xAxis_PM = PM_lineChart_out.getXAxis();
                xAxis_PM.setValueFormatter(xAxisFormatter);

                NO2_lineChart.setData(data_NO2);
                NO2_lineChart.setMaxVisibleValueCount(15);
                YAxis yAxis_NO2 = NO2_lineChart.getAxisLeft();
                yAxis_NO2.setValueFormatter(new MyppbAxisValueFormatter());
                YAxis right_NO2 = NO2_lineChart.getAxisRight();
                right_NO2.setEnabled(false);

                XAxis xAxis_NO2 = NO2_lineChart.getXAxis();
                xAxis_NO2.setValueFormatter(xAxisFormatter);

                SO2_lineChart.setData(data_SO2);
                SO2_lineChart.setMaxVisibleValueCount(15);
                YAxis yAxis_SO2 = SO2_lineChart.getAxisLeft();
                yAxis_SO2.setValueFormatter(new MyppbAxisValueFormatter());
                YAxis right_SO2 = SO2_lineChart.getAxisRight();
                right_SO2.setEnabled(false);

                XAxis xAxis_SO2 = SO2_lineChart.getXAxis();
                xAxis_SO2.setValueFormatter(xAxisFormatter);

                Temp_lineChart_out.notifyDataSetChanged();
                CO2_lineChart_out.notifyDataSetChanged();
                CO_lineChart_out.notifyDataSetChanged();
                PM_lineChart_out.notifyDataSetChanged();
                NO2_lineChart.notifyDataSetChanged();
                SO2_lineChart.notifyDataSetChanged();

                Temp_lineChart_out.invalidate();
                CO2_lineChart_out.invalidate();
                CO_lineChart_out.invalidate();
                PM_lineChart_out.invalidate();
                NO2_lineChart.invalidate();
                SO2_lineChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                Intent homepage= new Intent(outdoor_data.this,MainActivity.class);
                startActivity(homepage);
                break;
            case R.id.indoor_data:
                Intent indoor= new Intent(outdoor_data.this,indoor_data.class);
                startActivity(indoor);
                break;
            case R.id.outdoor_data:
                Intent outdoor= new Intent(outdoor_data.this,outdoor_data.class);
                startActivity(outdoor);
                break;
            case R.id.analysis:
                Intent ana= new Intent(outdoor_data.this,data_analysis.class);
                startActivity(ana);
                break;
        }

        DrawerLayout mDrawer_layout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

}


