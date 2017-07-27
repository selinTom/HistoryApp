package com.example.devov.historyapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.devov.historyapp.activity.NewsActivity;
import com.example.devov.historyapp.fragment.HistoryFragment;
import com.example.devov.historyapp.fragment.MainMenuFragment;
import com.example.devov.historyapp.fragment.audioRecord.AudioRecordFragment;
import com.example.devov.historyapp.interfaces.Constant;
import com.example.devov.historyapp.mvvm.viewTypeTest.ViewTypeTestFragment;
import com.example.devov.historyapp.utils.DataBaseHelper;

public class MainActivity extends AppCompatActivity implements Constant{
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private RadioGroup radioGroup;
    private FrameLayout frameLayout;
    private boolean flag=true;
    private String searchString;
    private Menu menu;
    private DataBaseHelper dataBaseHelper;
    private FloatingActionButton fab;
    public static  void LauncherActivity(int type, Activity activity){
        Intent intent=new Intent(activity,MainActivity.class);
        intent.putExtra("TYPE",type);
        activity.startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onCreate(Bundle savedInstanceState,PersistableBundle persistentState) {
        super.onCreate(savedInstanceState,persistentState);
        init();

    }
    public void closeMenu() {
        mDrawerLayout.closeDrawers();
    }
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
        } else
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.mian_menu,menu);
        menu.findItem(R.id.search).setVisible(false);
     /*   SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchableInfo si=searchManager.getSearchableInfo(getComponentName());
        Log.i("aaa",si.toString());
        if(searchView!=null)
            searchView.setSearchableInfo(si);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.search:
                onSearchRequested();
        }
        return true;
    }
    private void init(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent intent=getIntent();
        int tag=intent.getIntExtra("TYPE",0)+1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            setContentView(R.layout.activity_main);
            Slide slide = new Slide();
            slide.setDuration(1000);
            slide.setSlideEdge(Gravity.LEFT);
            getWindow().setExitTransition(slide);
        }
        else
            setContentView(R.layout.activity_main);
        radioGroup=(RadioGroup)findViewById(R.id.radio_group);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(v->finish());
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("生活助手");
        toolbar.setNavigationIcon(R.drawable.tag1);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        //   final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //    mDrawerLayout.setDrawerListener(drawerToggle);
        //    drawerToggle.syncState();
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                loadedFragment(i);
//            }
//        });
        frameLayout=(FrameLayout)findViewById(R.id.menu_fragment);
        loadedFragment(tag);
        searchString=getIntent().getStringExtra(SearchManager.QUERY);
        dataBaseHelper=new DataBaseHelper(this,1);
    }
    public DataBaseHelper getDataBaseHelper(){return dataBaseHelper;}

    private void loadedFragment(int id){

        MainMenuFragment mainMenuFragment=null;
        switch (id){
            case 1:
//                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mDrawerLayout.openDrawer(Gravity.LEFT);
//                    }
//                });
//                if(!flag)
//                    mDrawerLayout.addView(frameLayout);
//                flag=true;
//                NewsFragment fragment1=new NewsFragment();
//                mainMenuFragment=new MainMenuFragment();
//                Bundle args=new Bundle();
//                args.putString("TAG",DEFAULT);
//                fragment1.setArguments(args);
//                getSupportFragmentManager().beginTransaction().replace(R.id.context_fragment,fragment1)
//                        .add(R.id.menu_fragment,mainMenuFragment)
//                        .commit();
                Intent intent=new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){}
                });
                mDrawerLayout.removeView(frameLayout);
                flag = false;
                HistoryFragment fragment2=new HistoryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.context_fragment,fragment2).commit();
                break;
            case 3:
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){}
                });
                mDrawerLayout.removeView(frameLayout);
                flag = false;
//                WeatherFragment fragment3=new WeatherFragment();
//                Bundle arg=new Bundle();
//                arg.putString("SEARCH","aa");
//                fragment3.setArguments(arg);
                AudioRecordFragment fragment3=new AudioRecordFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.context_fragment,fragment3).commit();
                break;
            case 4:
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){}
                });
                mDrawerLayout.removeView(frameLayout);
                flag = false;
                ViewTypeTestFragment fragment4=new ViewTypeTestFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.context_fragment,fragment4).commit();
                break;
        }
    }
    private void startSettingsActivity(){
        Intent intent = new Intent(
                android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
//        Intent it=new Intent(MainActivity.this.getBaseContext(),SplashActivity.class);
//        startActivity(it);
    }
    public int getToolbarHeight(){
        return toolbar.getHeight();
    }
}
