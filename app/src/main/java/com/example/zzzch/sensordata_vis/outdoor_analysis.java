package com.example.zzzch.sensordata_vis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class outdoor_analysis extends AppCompatActivity {
    private TextView quality_level;
    private TextView recommendation;

    DatabaseReference mPostReference;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outdoor_analysis);

        quality_level = findViewById(R.id.quality_evaluation_out);
        recommendation = findViewById(R.id.reco_out);

        mPostReference = FirebaseDatabase.getInstance().getReference("Outdoor");
        mPostReference.addValueEventListener(valueEventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float i =0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    i=i+1;
                    String TEM = ds.child("TEM").getValue().toString();
                    String CO2 = ds.child("CO2").getValue().toString();
                    String CO = ds.child("CO").getValue().toString();
                    String PM = ds.child("PM").getValue().toString();
                    String NO2 = ds.child("NO2").getValue().toString();
                    String SO2 = ds.child("SO2").getValue().toString();
                    float SensorValue_TEM = Float.parseFloat(TEM);
                    Float SensorValue_CO2 = Float.parseFloat(CO2);
                    Float SensorValue_CO = Float.parseFloat(CO);
                    Float SensorValue_PM = Float.parseFloat(PM);
                    Float SensorValue_NO2 = Float.parseFloat(NO2);
                    Float SensorValue_SO2 = Float.parseFloat(SO2);

                    float aqi_CO = (SensorValue_CO/9)*100;
                    float aqi_PM = (SensorValue_PM/40)*100;
                    float aqi_NO2 = (SensorValue_NO2/120)*100;
                    float aqi_SO2 = (SensorValue_SO2/200)*100;
                    float aqi_max = Math.max(Math.max(aqi_CO,aqi_PM),Math.max(aqi_NO2,aqi_SO2));

                    if(aqi_max>0 && aqi_max<33){
                        quality_level.setText("Very Good");
                        quality_level.setTextColor(getResources().getColor(R.color.verygood));
                        recommendation.setText("Suitable for outdoor activities");
                    }
                    else if (aqi_max>34 && aqi_max<66){
                        quality_level.setText("Good");
                        quality_level.setTextColor(getResources().getColor(R.color.good));
                        recommendation.setText("Suitable for outdoor activities");
                    }
                    else if (aqi_max>67 && aqi_max<99){
                        quality_level.setText("Fair");
                        quality_level.setTextColor(getResources().getColor(R.color.fair));
                        recommendation.setText("No big effect on health when going out");
                    }
                    else if (aqi_max>100 && aqi_max<149){
                        quality_level.setText("Poor");
                        quality_level.setTextColor(getResources().getColor(R.color.poor));
                        recommendation.setText("Protection needed when go outside");
                    }
                    else if (aqi_max>150){
                        quality_level.setText("Very Poor");
                        quality_level.setTextColor(getResources().getColor(R.color.verypoor));
                        recommendation.setText("Better to stay at home");
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navi);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bot_navi_home:
                        Intent intent1 = new Intent(outdoor_analysis.this,data_analysis.class);
                        startActivity(intent1);
                        break;

                    case R.id.bot_navi_in:
                        Intent intent2 = new Intent(outdoor_analysis.this,indoor_analysis.class);
                        startActivity(intent2);
                        break;

                    case R.id.bot_navi_out:

                        break;
                }
                return false;
            }
        });
    }
}
