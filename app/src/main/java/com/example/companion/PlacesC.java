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

public class PlacesC extends AppCompatActivity {

    ListView lvPlace;
    final String ATTRIBUTE_NAME_PLACE = "place";
    final String ATTRIBUTE_NAME_IMAGE = "image";
    String places[], name_place;
    int language, city, city_id, section, place_id, img[], id_p[], n;

    static final String DATABASE_NAME = "database";
    static final String TABLE_NAME = "Obyekt";
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent a = getIntent();
        language = a.getIntExtra("language", -1);
        city = a.getIntExtra("city", -1);
        section = a.getIntExtra("section", -1);

        ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this, DATABASE_NAME);
        database = dbOpenHelper.openDataBase();

        int tHotel[] = {                                        // TOSHKENT ---------------------------------
                R.drawable.hotel_t_radisson_blu_tashkent,
                R.drawable.hotel_t_ramada_tashkent,
                R.drawable.hotel_t_intercontinental_tashkent,
                R.drawable.hotel_t_wyndham_tashkent,
                R.drawable.hotel_t_city_palace,
                R.drawable.hotel_t_tashkent_palace,
                R.drawable.hotel_t_hotel_uzbekistan,
                R.drawable.hotel_t_grand_mir_tashkent,
                R.drawable.hotel_t_asia_tashkent,
                R.drawable.hotel_t_le_grande_plaza
        };

        int tRestorant[] = {
                R.drawable.restaurant_t_pasado,
                R.drawable.restaurant_t_osh_markazi,
                R.drawable.restaurant_t_di_gavi,
                R.drawable.restaurant_t_april_verdat,
                R.drawable.restaurant_t_italiano_verdo,
                R.drawable.restaurant_t_caravan,
                R.drawable.restaurant_t_shedevr_garden,
                R.drawable.restaurant_t_city_grill,
                R.drawable.restaurant_t_assarti,
                R.drawable.restaurant_t_bassilic
        };

        int tMuseum[] = {
                R.drawable.museum_t_temuriylar_tarixi_muzeyi,
                R.drawable.museum_t_ozbekiston_tarixi_davlat_muzeyi,
                R.drawable.museum_t_sanat_muzeyi,
                R.drawable.museum_t_qatagon_qurbonlari_muzeyi,
                R.drawable.museum_t_navoiy_muzeyi,
                R.drawable.museum_t_amaliy_sanat_muzeyi,
                R.drawable.museum_t_qurolli_kuchlar_muzeyi,
                R.drawable.museum_t_aloqa_tarixi_muzeyi,
                R.drawable.museum_t_sanat_galereyasi,
                R.drawable.museum_t_toshkent_fotosuratlar_uyi
        };

        int tHisMon[] = {
                R.drawable.his_mon_t_shayx_zayniddin_buva_maqbarasi,
                R.drawable.his_mon_t_baroqxon_madrasi,
                R.drawable.his_mon_t_qaffol_shoshi_maqbarasi,
                R.drawable.his_mon_t_kokaldosh_madrasasi,
                R.drawable.his_mon_t_abulqosim_madrasasi,
                R.drawable.his_mon_t_xoja_ahror_vali,
                R.drawable.his_mon_t_zangi_ota_yodgorlik_majmuasi,
                R.drawable.his_mon_t_shayx_hovandi_tohur_maqbarasi,
                R.drawable.his_mon_t_xazrati_imom,
                R.drawable.his_mon_t_yunusxon_maqbarasi
        };

        int tPark[] = {
                R.drawable.park_t_furqat_bogi,
                R.drawable.park_t_gofur_gulom,
                R.drawable.park_t_navoiy_milliy,
                R.drawable.park_t_abdulla_qodiriy_bogi,
                R.drawable.park_t_hayvonot_bogi,
                R.drawable.park_t_yapon_bogi,
                R.drawable.park_t_botanika_bogi,
                R.drawable.park_t_aqua_bogi,
                R.drawable.park_t_bobur_bogi,
                R.drawable.park_t_lokomativ_bogi
        };

        int bHotel[] = {                                    // BUXORO ---------------------------------
                R.drawable.hotel_b_amelia_hotel,
                R.drawable.hotel_b_asia_bukhara_hotel,
                R.drawable.hotel_b_caravan_hotel,
                R.drawable.hotel_b_hotel_kukaldash,
                R.drawable.hotel_b_hotel_malika,
                R.drawable.hotel_b_hotel_new_moon,
                R.drawable.hotel_b_hovli_poyon,
                R.drawable.hotel_b_lyabi_house_hotel,
                R.drawable.hotel_b_minfiza_hotel,
                R.drawable.hotel_b_zargaron_plaza_hotel
        };

        int bRestorant[] = {
                R.drawable.restaurant_b_adras,
                R.drawable.restaurant_b_budreddin,
                R.drawable.restaurant_b_cafe_wishbone,
                R.drawable.restaurant_b_chasmai_mirob_restaurant,
                R.drawable.restaurant_b_chinar,
                R.drawable.restaurant_b_restaurant_old_bukhara,
                R.drawable.restaurant_b_rustam,
                R.drawable.restaurant_b_silk_road_tea_house,
                R.drawable.restaurant_b_steak_house_bukhara,
                R.drawable.restaurant_b_the_old_house
        };

        int bMuseum[] = {
                R.drawable.museum_b_ibn_sino_muzeyi,
                R.drawable.museum_b_tasviriy_sanat_muzeyi,
                R.drawable.museum_b_savdogar_uyi,
                R.drawable.museum_b_abdurauf_fitrat_muzeyi,
                R.drawable.museum_b_al_buxoriy_muzeyi,
                R.drawable.museum_b_poykent_shaharchasi,
                R.drawable.museum_b_gilam_muzeyi,
                R.drawable.museum_b_naqishbandiy_talimoti_muzeyi,
                R.drawable.museum_b_yogoch_oymakorligi_muzeyi,
                R.drawable.museum_b_sitorai_mohi_muzeyi
        };

        int bHisMon[] = {
                R.drawable.his_mon_b_abdulazizxon_madrasasi,
                R.drawable.his_mon_b_abdullaxon_madrasasi,
                R.drawable.his_mon_b_ark_qalasi,
                R.drawable.his_mon_b_chor_minor_majmuasi,
                R.drawable.his_mon_b_ismoil_samoniy_maqbarasi,
                R.drawable.his_mon_b_masjidi_kalon,
                R.drawable.his_mon_b_minorai_kalon,
                R.drawable.his_mon_b_mogaki_attor_masjidi,
                R.drawable.his_mon_b_ulugbek_madrasasai_buhoro,
                R.drawable.his_mon_b_vobkent_minorasi
        };

        int bPark[] = {
                R.drawable.park_b_samoniylar_bogi,
        };

        int xHotel[] = {                                    // XORAZM ---------------------------------
                R.drawable.hotel_x_hotel_arkanchi,
                R.drawable.hotel_x_hotel_asia_khiva,
                R.drawable.hotel_x_hotel_kheivak,
                R.drawable.hotel_x_hotel_khorezm_palace,
                R.drawable.hotel_x_malika_khiva_hotel,
                R.drawable.hotel_x_orient_star_khiva_hotel
        };

        int xRestorant[] = {
                R.drawable.restaurant_x_bir_gumbaz,
                R.drawable.restaurant_x_khorezm_art_restaurant,
                R.drawable.restaurant_x_malika_kheivak_restaurant,
                R.drawable.restaurant_x_mirza_bashi,
                R.drawable.restaurant_x_national_house_zaynap,
                R.drawable.restaurant_x_restoran_khiva,
                R.drawable.restaurant_x_silk_road,
                R.drawable.restaurant_x_zerafshan_chaikhana
        };

        int xMuseum[] = {
                R.drawable.museum_x_ichan_qala,
                R.drawable.museum_x_nurullaboy_saroyi,
                R.drawable.museum_x_tosh_hovli_saroyi
        };

        int xHisMon[] = {
                R.drawable.his_mon_x_muhammad_aminxon_madrasasi,
                R.drawable.his_mon_x_ota_darboza,
                R.drawable.his_mon_x_pahlavon_mahmud_maqbarasi,
                R.drawable.his_mon_x_qozi_kalon_madrasasi,
                R.drawable.his_mon_x_qutlug_murod_inoq_madrasasi,
                R.drawable.his_mon_x_kalta_minor,
                R.drawable.his_mon_x_shayx_movlon_bobo_majmuasi,
                R.drawable.his_mon_x_shergozixon_madrasasi,
                R.drawable.his_mon_x_shohimardon_minorasi,
                R.drawable.his_mon_x_chodra_xovli
        };

        int xPark[] = {
                R.drawable.park_x_bogot_tumani,
                R.drawable.park_x_gurlan_tumani,
                R.drawable.park_x_qoshkopir_tumani,
                R.drawable.park_x_shovot_tumani,
                R.drawable.park_x_xazorasp_tumani,
                R.drawable.park_x_xiva_tumani,
                R.drawable.park_x_xonqa_tumani,
                R.drawable.park_x_yangiariq_tumani
        };

        int sHotel[] = {                                        // SAMARQAND ---------------------------------
                R.drawable.hotel_s_asia_samarkand_hotel,
                R.drawable.hotel_s_city_hotel,
                R.drawable.hotel_s_hotel_bibihonum,
                R.drawable.hotel_s_hotel_emir_han,
                R.drawable.hotel_s_hotel_grand_samarkand,
                R.drawable.hotel_s_hotel_jahon_palace,
                R.drawable.hotel_s_hotel_kamila,
                R.drawable.hotel_s_hotel_registon,
                R.drawable.hotel_s_largamak,
                R.drawable.hotel_s_malika_prime_samarkand
        };

        int sRestorant[] = {
                R.drawable.restaurant_s_astoria,
                R.drawable.restaurant_s_dastarhan,
                R.drawable.restaurant_s_oasis_garden,
                R.drawable.restaurant_s_old_city,
                R.drawable.restaurant_s_oriental_sweets,
                R.drawable.restaurant_s_platan,
                R.drawable.restaurant_s_registon_garden,
                R.drawable.restaurant_s_restaurant_samarkand,
                R.drawable.restaurant_s_venezia
        };

        int sMuseum[] = {
                R.drawable.museum_s_afrosiyob_samarqand_tarixi_muzeyi,
                R.drawable.museum_s_olkashunoslik_muzeyi_samarkand_viloyati,
                R.drawable.museum_s_sadriddin_ayniy_uy_muzeyi
        };

        int sHisMon[] = {
                R.drawable.his_mon_s_bibixonim_majmuasi,
                R.drawable.his_mon_s_gori_amir_maqbarasi,
                R.drawable.his_mon_s_hazrati_hizr_masjidi,
                R.drawable.his_mon_s_registan,
                R.drawable.his_mon_s_sherdor_madrasasi,
                R.drawable.his_mon_s_shohazinda_majmuasi,
                R.drawable.his_mon_s_tilla_qori_masjidi,
                R.drawable.his_mon_s_ulugbek_rasadhonasi,
                R.drawable.his_mon_s_ulugbek_madrasi
        };

        if((city == 0) || (city == 1&& section != 4) || (city == 2 && section == 3) || (city == 3 && section == 0))
            n = 10;
        if((city == 2 && section == 1) || (city == 3 && section == 3) || (city == 3 && section == 1))
            n = 9;
        if((city == 2 && section == 4) || (city == 2 && section == 1))
            n = 8;
        if((city == 2 && section == 0))
            n = 6;
        if((city == 3 && section == 2) || ( city == 2 && section == 2))
            n = 3;
        if((city == 1 && section == 4))
            n = 1;

        places = new String[n];
        img = new int[n];
        id_p = new int[n];
        int x = 0;


//================= Toshkent ==================
        if(city == 0 && section == 0)
            img = tHotel;
        if(city == 0 && section == 1)
            img = tRestorant;
        if(city == 0 && section == 2)
            img = tMuseum;
        if(city == 0 && section == 3)
            img = tHisMon;
        if(city == 0 && section == 4)
            img = tPark;

//================= Buxoro ==================
        if(city == 1 && section == 0)
            img = bHotel;
        if(city == 1 && section == 1)
            img = bRestorant;
        if(city == 1 && section == 2)
            img = bMuseum;
        if(city == 1 && section == 3)
            img = bHisMon;
        if(city == 1 && section == 4)
            img = bPark;

//================= Xorazm ==================
        if(city == 2 && section == 0)
            img = xHotel;
        if(city == 2 && section == 1)
            img = xRestorant;
        if(city == 2 && section == 2)
            img = xMuseum;
        if(city == 2 && section == 3)
            img = xHisMon;
        if(city == 2 && section == 4)
            img = xPark;

//================= Buxoro ==================
        if(city == 3 && section == 0)
            img = sHotel;
        if(city == 3 && section == 1)
            img = sRestorant;
        if(city == 3 && section == 2)
            img = sMuseum;
        if(city == 3 && section == 3)
            img = sHisMon;

        switch (city){
            case 0: city_id = 1;break;
            case 1: city_id = 3;break;
            case 2: city_id = 6;break;
            case 3: city_id = 10;break;
        }

        String select = "o_id_guruh = " + String.valueOf(section + 1) + " and o_id_shahar = " + String.valueOf(city_id);

        Cursor friendCursor = database.query(TABLE_NAME,
                new String[]
                        {"id_obyekt", "o_nomi_uz", "o_nomi_eng", "o_nomi_ru"},
                select, null, null, null, null);
        friendCursor.moveToFirst();

        if(!friendCursor.isAfterLast()) {
            do {

                switch(language) {
                    case 0:
                        name_place = friendCursor.getString(1);
                        break;
                    case 1:
                        name_place = friendCursor.getString(2);
                        break;
                    case 2:
                        name_place = friendCursor.getString(3);
                        break;}
                places[x] = name_place;
                id_p[x] = friendCursor.getInt(0);
                x++;

            } while (friendCursor.moveToNext());
        }
        friendCursor.close();

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                places.length);

        Map<String, Object> m;

        for (int f = 0 ; f < places.length; f++){
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_PLACE, places[f]);
            m.put(ATTRIBUTE_NAME_IMAGE, img[f]);
            data.add(m);
        }

        String[] from = {ATTRIBUTE_NAME_PLACE, ATTRIBUTE_NAME_IMAGE};
        int[] to = {R.id.tvPlace, R.id.ivImgPlace};

        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_places, from, to);

        lvPlace = (ListView)findViewById(R.id.lvPlace);
        lvPlace.setAdapter(sAdapter);

        lvPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                place_id = id_p[position];
                Intent intent = new Intent(PlacesC.this, InformationC.class);
                intent.putExtra("language", language);
                intent.putExtra("city", city);
                intent.putExtra("section", section);
                intent.putExtra("place_id", place_id);
                intent.putExtra("place", position);
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
