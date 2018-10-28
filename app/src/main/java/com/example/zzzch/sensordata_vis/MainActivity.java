package com.example.zzzch.sensordata_vis;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.content.Intent;
import android.support.v4.view.GravityCompat;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout mDrawer_layout;
    NavigationView navigationView;
    ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mDrawer_layout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawer_layout,R.string.open,R.string.close);
        mDrawer_layout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                Intent homepage= new Intent(MainActivity.this,MainActivity.class);
                startActivity(homepage);
                break;
            case R.id.indoor_data:
                Intent indoor= new Intent(MainActivity.this,indoor_data.class);
                startActivity(indoor);
                break;
            case R.id.outdoor_data:
                Intent outdoor= new Intent(MainActivity.this,outdoor_data.class);
                startActivity(outdoor);
                break;
            case R.id.analysis:
                Intent ana= new Intent(MainActivity.this,data_analysis.class);
                startActivity(ana);
                break;
        }

        DrawerLayout mDrawer_layout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
