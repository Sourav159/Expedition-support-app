package com.example.expeditionsupportapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


public class HomePage extends AppCompatActivity {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FirebaseAuth fAuth;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_page);
        navigationView = findViewById(R.id.nav_view3);
        drawer = findViewById(R.id.home_page);
        tabLayout = findViewById(R.id.tabLayout3);
        viewPager = findViewById(R.id.view_pager3);
        fAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(true); // enable hamburger menu icon
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        tabLayout.addTab(tabLayout.newTab().setText("Blogs"));
        tabLayout.addTab(tabLayout.newTab().setText("Expedition"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final HomePageFragmentAdapter homePageFragmentAdapter = new HomePageFragmentAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(homePageFragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing.
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.homepageNav:
                        Intent i = new Intent(getApplicationContext(),HomePage.class);
                        startActivity(i);
                        return  true;

                    case R.id.sponsorsNav:
                        Intent sponsors = new Intent(getApplicationContext(),sponsorsActivity.class);
                        startActivity(sponsors);
                        return  true;

                    case R.id.supportmenuItem:
                        Intent support = new Intent(getApplicationContext(), donations_activity.class);
                        startActivity(support);
                        return  true;

                    case R.id.aboutUsActivity:
                        Intent aboutUs = new Intent(getApplicationContext(), aboutUs_activity.class);
                        startActivity(aboutUs);
                        return  true;

                    case R.id.profileNav:
                        Intent profile = new Intent(getApplicationContext(), UserProfile.class);
                        startActivity(profile);
                        return  true;

                    case R.id.signOutNav:
                        fAuth.signOut();
                        Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(login);
                        return  true;
                }
                return false;
            }
        });

    }
}
