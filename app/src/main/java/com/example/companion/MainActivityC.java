package com.example.companion;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivityC extends AppCompatActivity {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    ListView lvLanguage;

    final String ATTRIBUTE_NAME_LANGUAGE = "language";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.draverlayout);
        lvLanguage = (ListView)findViewById(R.id.lvLanguage);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.opendrawer, R.string.closedrawer);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //==========================================================================================

        String[] lang = {"O'zbekcha", "English", "Русский"/*, "한국어"*/};
        int img[] = {
                R.drawable.uz,
                R.drawable.uk,
                R.drawable.ru/*,
                R.drawable.kor*/
        };

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(lang.length);
        Map<String, Object> m;

        for (int i = 0 ; i < lang.length; i++){
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_LANGUAGE, lang[i]);
            m.put(ATTRIBUTE_NAME_IMAGE, img[i]);
            data.add(m);
        }

        String[] from = {ATTRIBUTE_NAME_LANGUAGE,ATTRIBUTE_NAME_IMAGE};
        int[] to = {R.id.tvLang, R.id.ivImg};

        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_main, from, to);

        lvLanguage.setAdapter(sAdapter);

        //==========================================================================================

        lvLanguage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivityC.this,CitysC.class);
                intent.putExtra("language", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home)
            if(drawerLayout.isDrawerOpen(lvLanguage))
                drawerLayout.closeDrawer(lvLanguage);
            else drawerLayout.openDrawer(lvLanguage);

        return super.onOptionsItemSelected(item);
    }
}
