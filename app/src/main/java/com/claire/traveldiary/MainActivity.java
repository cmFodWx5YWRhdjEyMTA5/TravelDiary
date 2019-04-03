package com.claire.traveldiary;

import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.claire.traveldiary.base.BaseActivity;
import com.claire.traveldiary.edit.EditFragment;
import com.claire.traveldiary.edit.EditPresenter;
import com.claire.traveldiary.mainpage.MainPageFragment;
import com.claire.traveldiary.mainpage.MainPagePresenter;
import com.claire.traveldiary.settings.SettingsFragment;
import com.claire.traveldiary.settings.SettingsPresenter;


public class MainActivity extends BaseActivity {

    private MainPagePresenter mMainPagePresenter;
    private EditPresenter mEditPresenter;
    private SettingsPresenter mSettingsPresenter;

    //BottomNavigation
    private BottomNavigationView mBottomNavigation;

    //Toolbar
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private SearchView mToolbarSearch;
    private ImageButton mToolbarMenu;
    private ImageButton mToolbarBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //startActivity(new Intent(this, LaunchActivity.class));

        init();

    }

    private void init() {

        setToolbar();
        setBottomNavigation();
        openMainPage();
    }



    private void setToolbar() {

        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        mToolbarSearch = findViewById(R.id.toolbar_search);
        mToolbarBack = findViewById(R.id.toolbar_back);
        mToolbarBack.setOnClickListener(v -> {
            openMainPage();
            updateToolbar(getResources().getString(R.string.toolbar_title));
        });
        mToolbarMenu = findViewById(R.id.toolbar_menu);
        mToolbarMenu.setOnClickListener(v -> {
            openSettings();
            updateToolbar("");
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setBottomNavigation() {
        mBottomNavigation = findViewById(R.id.bottom_navigation_main);
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {

        switch (item.getItemId()) {
            case R.id.navigation_main:

                openMainPage();
                updateToolbar(getResources().getString(R.string.toolbar_title));
                return true;

            case R.id.navigation_edit:

                openEdit();
                updateToolbar("");
                return true;


            case R.id.navigation_map:

                openMap();
                //updateToolbar("");
                return true;

            default:
                return false;
        }
    };

    private void openMainPage() {

        MainPageFragment mainPageFragment = (MainPageFragment) getSupportFragmentManager().findFragmentByTag("MainPage");

        if (mainPageFragment == null) {
            MainPageFragment fragment = MainPageFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, fragment, "MainPage").commit();
            mMainPagePresenter = new MainPagePresenter(fragment);
        }

    }

    private void openEdit() {

        EditFragment editFragment = (EditFragment) getSupportFragmentManager().findFragmentByTag("Edit");

        if (editFragment == null) {
            EditFragment fragment = EditFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, fragment, "Edit").commit();
            mEditPresenter = new EditPresenter(fragment);

        }

    }

    private void openMap() {
    }

    private void openSettings() {

        //hideBottomNavigation();

        SettingsFragment settingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag("Settings");

        if (settingsFragment == null) {
            SettingsFragment fragment = SettingsFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_container, fragment, "Settings").commit();
            mSettingsPresenter = new SettingsPresenter(fragment);
        }
    }

    private void updateToolbar(String title) {

        if ("".equals(title)) {
            mToolbarTitle.setVisibility(View.GONE);
            mToolbarSearch.setVisibility(View.GONE);
            mToolbarBack.setVisibility(View.VISIBLE);
            mToolbarMenu.setVisibility(View.GONE);
        } else {
            mToolbarTitle.setVisibility(View.VISIBLE);
            mToolbarSearch.setVisibility(View.VISIBLE);
            mToolbarBack.setVisibility(View.GONE);
            mToolbarMenu.setVisibility(View.VISIBLE);
            mToolbarTitle.setText(title);

        }
    }

    public void hideBottomNavigation() {
        mBottomNavigation.setVisibility(View.GONE);
    }

    public void showBottomNavigation() {
        mBottomNavigation.setVisibility(View.VISIBLE);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
