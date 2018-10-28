package com.example.zzzch.sensordata_vis;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.widget.TextView;

import com.example.zzzch.sensordata_vis.Custom.MyPMAxisValueFormatter;
import com.example.zzzch.sensordata_vis.Custom.MyTEMAxisValueFormatter;
import com.example.zzzch.sensordata_vis.Custom.MyppmAxisValueFormatter;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class data_analysis extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout mDrawer_layout;
    NavigationView navigationView;
    ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_analysis);
        mDrawer_layout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawer_layout,R.string.open,R.string.close);
        mDrawer_layout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navi);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bot_navi_home:

                        break;

                    case R.id.bot_navi_in:
                        Intent intent1 = new Intent(data_analysis.this,indoor_analysis.class);
                        startActivity(intent1);
                        break;

                    case R.id.bot_navi_out:
                        Intent intent2 = new Intent(data_analysis.this,outdoor_analysis.class);
                        startActivity(intent2);
                        break;
                }
                return false;
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
                Intent homepage= new Intent(data_analysis.this,MainActivity.class);
                startActivity(homepage);
                break;
            case R.id.indoor_data:
                Intent indoor= new Intent(data_analysis.this,indoor_data.class);
                startActivity(indoor);
                break;
            case R.id.outdoor_data:
                Intent outdoor= new Intent(data_analysis.this,outdoor_data.class);
                startActivity(outdoor);
                break;
            case R.id.analysis:
                Intent ana= new Intent(data_analysis.this,data_analysis.class);
                startActivity(ana);
                break;
        }

        DrawerLayout mDrawer_layout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

}
