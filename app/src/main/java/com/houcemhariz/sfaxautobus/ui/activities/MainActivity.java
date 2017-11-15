package com.houcemhariz.sfaxautobus.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.houcemhariz.sfaxautobus.R;
import com.houcemhariz.sfaxautobus.ui.fragments.HomeFragment;
import com.houcemhariz.sfaxautobus.ui.fragments.LinesFragment;
import com.houcemhariz.sfaxautobus.ui.fragments.MySpaceFragment;
import com.houcemhariz.sfaxautobus.ui.fragments.RouteFragment;
import com.houcemhariz.sfaxautobus.ui.fragments.ScheduleFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnFragmentInteractionListener,
        RouteFragment.OnFragmentInteractionListener,LinesFragment.OnFragmentInteractionListener,
        ScheduleFragment.OnFragmentInteractionListener,MySpaceFragment.OnFragmentInteractionListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new HomeFragment());
        fragmentTransaction.commit();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle(R.string.home_fragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        switch (item.getItemId()) {
            case R.id.home:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new HomeFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(R.string.home_fragment);
                item.setChecked(true);
                break;
            case R.id.route:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new RouteFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(R.string.route_fragment);
                item.setChecked(true);
                break;
            case R.id.lines:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new LinesFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(R.string.lines_fragment);
                item.setChecked(true);
                break;
            case R.id.schedule:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new ScheduleFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(R.string.schedule_fragment);
                item.setChecked(true);
                break;
            case R.id.myspace:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container,new MySpaceFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(R.string.my_space_fragment);
                item.setChecked(true);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

        TableLayout table = (TableLayout) findViewById(R.id.idTable); // on prend le tableau défini dans le layout
        TableRow row; // création d'un élément : ligne

    }
}
