package com.example.companion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

public class MetroC extends AppCompatActivity {

    ImageView imageView;
    int language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView)findViewById(R.id.imageView);

        Intent a = getIntent();
        language = a.getIntExtra("language", -1);

        switch (language){
            case 0:imageView.setImageResource(R.drawable.metro_uz); break;
            case 1:imageView.setImageResource(R.drawable.metro_eng);break;
            case 2:imageView.setImageResource(R.drawable.metro_ru); break;
        }
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
