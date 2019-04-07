package com.example.companion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class SectionsC extends AppCompatActivity {

    ListView lvSection;
    final String ATTRIBUTE_NAME_SECTION = "section";
    final String ATTRIBUTE_NAME_IMAGE = "image";
    String sections[], name_section;
    int language, city, img[];

    static final String DATABASE_NAME = "database";
    String TABLE_NAME;
    private SQLiteDatabase database;
    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent a = getIntent();
        language = a.getIntExtra("language", -1);
        city = a.getIntExtra("city", -1);

        if(city == 0){
            n = 7; TABLE_NAME = "GuruhT";
        }
        else if(city == 3){
            n = 5; TABLE_NAME = "GuruhS";
        } else{
            n = 6; TABLE_NAME = "Guruh";
        }

        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DATABASE_NAME);
        database = dbOpenHelper.openDataBase();

        sections = new String[n];
        img = new int[n];
        int x = 0;

        Cursor friendCursor = database.query(TABLE_NAME,
                new String[]
                        {"id_guruh", "g_nomi_uz", "g_nomi_eng", "g_nomi_ru"},
                null, null, null, null, null);
        friendCursor.moveToFirst();

        if(!friendCursor.isAfterLast()) {
            do {

                switch(language) {
                    case 0:
                        name_section = friendCursor.getString(1);
                        break;
                    case 1:
                        name_section = friendCursor.getString(2);
                        break;
                    case 2:
                        name_section = friendCursor.getString(3);
                        break;}
                sections[x] = name_section;
                x++;


            } while (friendCursor.moveToNext());
        }
        friendCursor.close();

        int img_1[] = {
                R.drawable.hotel,
                R.drawable.restoran,
                R.drawable.museum,
                R.drawable.his_mon,
                R.drawable.park,
                R.drawable.metro,
                R.drawable.map
        };

        int img_2[] = {
                R.drawable.hotel,
                R.drawable.restoran,
                R.drawable.museum,
                R.drawable.his_mon,
                R.drawable.park,
                R.drawable.map
        };

        int img_3[] = {
                R.drawable.hotel,
                R.drawable.restoran,
                R.drawable.museum,
                R.drawable.his_mon,
                R.drawable.map
        };

        if(city == 0)
            img = img_1;
        if(city == 3)
            img = img_3;
        if(city == 1 || city == 2)
            img = img_2;

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(sections.length);
        Map<String, Object> m;

        for (int f = 0 ; f < sections.length; f++){
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_SECTION, sections[f]);
            m.put(ATTRIBUTE_NAME_IMAGE, img[f]);
            data.add(m);
        }

        String[] from = {ATTRIBUTE_NAME_SECTION,ATTRIBUTE_NAME_IMAGE};
        int[] to = {R.id.tvLang, R.id.ivImg};

        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_section, from, to);

        lvSection = (ListView)findViewById(R.id.lvSection);
        lvSection.setAdapter(sAdapter);

        lvSection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if((position == 0 || position == 1 || position == 2 || position == 3) || (position == 4 && city != 3)){

                    Intent intent = new Intent(SectionsC.this,PlacesC.class);
                    intent.putExtra("language", language);
                    intent.putExtra("city", city);
                    intent.putExtra("section", position);
                    startActivity(intent);
                }
                if(position == 5 && city == 0){
                    Intent intent = new Intent(SectionsC.this,MetroC.class);
                    intent.putExtra("language", language);
                    startActivity(intent);
                }
                if((position == 5 && city != 0) || (position == 6 && city == 0) || (position == 4 && city == 3)){
                    Intent intent = new Intent(SectionsC.this,MapsActivity.class);
                    intent.putExtra("city", city);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
