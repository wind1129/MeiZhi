package com.example.wind.meizhi;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.ImageView;

import com.example.wind.meizhi.mvp.view.TabsFragment;
import com.example.wind.meizhi.ui.BaseActivity;
import com.example.wind.meizhi.utils.Imager;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.Bind;
import io.realm.Case;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private String currentType;

    @Override
    protected void initLayout() {
        layoutId = R.layout.activity_main;
    }

    @Override
    public void initViews() {
        super.initViews();
        setupDrawer();
        initNavigationView();
        replace(TabsFragment.MENU_NEWS);
    }

    private void replace(String type) {
        if (!type.equals(currentType)) {
            currentType = type;
            replaceFragment(TabsFragment.newInstance(type), type);
        }
    }


    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavigationView() {
        Imager.load(this, R.drawable.head, (ImageView) navView.getHeaderView(0).findViewById(R.id.headImage));
        navView.setNavigationItemSelectedListener(this);
        navView.inflateMenu(R.menu.main_drawer);

        Menu menu = navView.getMenu();
        menu.getItem(0).setChecked(true);

        menu.getItem(0).setIcon(
                new IconicsDrawable(this).
                        icon(GoogleMaterial.Icon.gmd_explore));
        menu.getItem(1).setIcon(
                new IconicsDrawable(this).
                        icon(GoogleMaterial.Icon.gmd_face)
                        .color(Color.RED));

        SubMenu sub = menu.getItem(2).getSubMenu();
        sub.getItem(0).setIcon(
                new IconicsDrawable(this).
                        icon(GoogleMaterial.Icon.gmd_share)
                        .color(Color.DKGRAY));
        sub.getItem(1).setIcon(
                new IconicsDrawable(this).
                        icon(GoogleMaterial.Icon.gmd_settings)
                        .color(Color.GRAY));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            //case R.id.nav_knowledge: replace(TabsFragment.MENU_NEWS);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }
}
