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

public class CitysC extends AppCompatActivity {

    ListView lvCity;
    final String ATTRIBUTE_NAME_CITY = "city";
    final String ATTRIBUTE_NAME_IMAGE = "image";
    String name_city, citys[];
    int language;

    static final String DATABASE_NAME = "database";
    static final String TABLE_NAME = "Shahar";
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citys);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent a = getIntent();
        language = a.getIntExtra("language", -1);

        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DATABASE_NAME);
        database = dbOpenHelper.openDataBase();

        citys = new String[4];
        int x = 0;

        int imgCity[] = {
                R.drawable.tashkent,
                R.drawable.bukhara,
                R.drawable.kharezm,
                R.drawable.samarkand
        };

        Cursor friendCursor = database.query(TABLE_NAME,
                new String[]
                        {"id_shahar", "sh_nomi_uz", "sh_nomi_eng", "sh_nomi_ru"},
                null, null, null, null, null);
        friendCursor.moveToFirst();

        if (!friendCursor.isAfterLast()) {
            do {

                switch (language) {
                    case 0:
                        name_city = friendCursor.getString(1);
                        break;
                    case 1:
                        name_city = friendCursor.getString(2);
                        break;
                    case 2:
                        name_city = friendCursor.getString(3);
                        break;
                }
                citys[x] = name_city;
                x++;

            } while (friendCursor.moveToNext());
        }
        friendCursor.close();

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                citys.length);
        Map<String, Object> m;


        for (int f = 0; f < citys.length; f++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_CITY, citys[f]);
            m.put(ATTRIBUTE_NAME_IMAGE, imgCity[f]);
            data.add(m);
        }

        String[] from = {ATTRIBUTE_NAME_CITY, ATTRIBUTE_NAME_IMAGE};
        int[] to = {R.id.tvCitys, R.id.ivImgCitys};;

        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_citys, from, to);

        lvCity = (ListView) findViewById(R.id.lvCity);
        lvCity.setAdapter(sAdapter);

        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CitysC.this,SectionsC.class);
                intent.putExtra("language", language);
                intent.putExtra("city", position);
                startActivity(intent);
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
