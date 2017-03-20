package com.jian.android.changeskin;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    private ListView listView;
    private boolean isNight;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);

        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }

        navView.setCheckedItem(R.id.call);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        listView = (ListView) findViewById(R.id.list_view);

        String[] names = new String[] {"宋江", "卢俊义", "吴用",
                "公孙胜", "关胜", "林冲", "秦明", "呼延灼", "花荣", "柴进", "李应", "朱仝", "鲁智深",
                "武松", "董平", "张清", "杨志", "徐宁", "索超", "戴宗", "刘唐", "李逵", "史进", "穆弘",
                "雷横", "李俊", "阮小二", "张横", "阮小五", " 张顺", "阮小七", "杨雄", "石秀", "解珍",
                " 解宝", "燕青", "朱武", "黄信", "孙立", "宣赞", "郝思文", "韩滔", "彭玘", "单廷珪"
        };

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));

        /**
         * 设置动画效果
         */
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                View mContent=mDrawerLayout.getChildAt(0);
                View mMenu=drawerView;
                float scale=1-slideOffset;
                float rightScale=0.8f+scale*0.2f;

                if (drawerView.getTag().equals("LEFT")){

                    float leftScale=1-0.3f*scale;

                    ViewHelper.setScaleX(mMenu,leftScale);
                    ViewHelper.setScaleY(mMenu,leftScale);
                    ViewHelper.setAlpha(mMenu,0.6f+0.4f*(1-scale));
                    ViewHelper.setTranslationX(mContent,mMenu.getMeasuredWidth()*(1-scale));
                    ViewHelper.setPivotX(mContent,0);
                    ViewHelper.setPivotY(mContent,mContent.getMeasuredHeight()/2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent,rightScale);
                    ViewHelper.setScaleY(mContent,rightScale);
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        sp = getSharedPreferences("config",MODE_PRIVATE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.toolbar:

                isNight = sp.getBoolean("night", false);
                if (isNight) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sp.edit().putBoolean("night", false).commit();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sp.edit().putBoolean("night", true).commit();
                }
                recreate();

                break;

            default:
                break;
        }

        return true;
    }
}
