package com.nutra_o.nutra_o.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.nutra_o.nutra_o.subMenu.categories.CategoriesFragment;
import com.nutra_o.nutra_o.mainMenu.NavigationDrawerFragment;
import com.nutra_o.nutra_o.subMenu.start.StartFragment;
import com.nutra_o.nutra_o.subMenu.tasks.TasksFragment;
import com.nutra_o.nutra_o.models.ApplicationImpl;
import com.nutra_o.nutra_o.models.ApplicationModel;
import com.nutra_o.nutra_o.R;

import java.util.Observable;
import java.util.Observer;


public class MainActivity extends ActionBarActivity implements Observer{


    private Toolbar toolbar;

    NavigationDrawerFragment drawerFragment;
    DrawerLayout Drawer;

    ApplicationImpl application;
    ApplicationModel model;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    Fragment currentMainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        application = (ApplicationImpl) getApplicationContext();
        model = application.getModel();
        model.addObserver(this);

        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view

        drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUpDrawerFragment(toolbar, Drawer);

        fragmentManager = getSupportFragmentManager();

       if(savedInstanceState == null){
           loadMenuFragment(0);
       }
       if(getIntent().getExtras()!= null){ // can specify witch menu fragment is shown when activity is called from another activity
           Bundle bundle = getIntent().getExtras();
           int menuNr = (Integer) bundle.getInt("SUB_MENU");
           loadMenuFragment(menuNr);
         }
    }

    public Fragment getCurrentMainFragment(){
        return currentMainFragment;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if(id == R.id.navigate){

            startActivity(new Intent(this,test.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigateUp() {

        System.out.println("hellllooo");
        return super.onNavigateUp();

    }

    // called when model is changed
    @Override
    public void update(Observable observable, Object data) {

    }

    // called back from recycler view adapter
    public void loadMenuFragment(int menuItem){

        System.out.println("Main activity catched event: "+menuItem);


        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = null;

        if(menuItem == 0){ // default

            fragment = new StartFragment();

        }else if(menuItem == 1){ // Tasks

            fragment = new TasksFragment();

        }else if (menuItem == 2) { // Projects



        } else if (menuItem == 3){ // Calender

        } else if (menuItem == 4){ // Clip board

        } else if (menuItem == 5){ // Categories

            fragment = new CategoriesFragment();

        } else if (menuItem == 6) { // Settings

        } else if (menuItem == 7){ // Log out

        }

        if (fragment != null){
            fragmentTransaction.add(R.id.root_frag, fragment).addToBackStack("FRAG");
            fragmentTransaction.commit();
            currentMainFragment = fragment;
        }

        Drawer.closeDrawer(Gravity.START);

    }



}
