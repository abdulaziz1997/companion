package com.example.companion;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 08.06.15
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ExternalDbOpenHelper extends SQLiteOpenHelper {

    //ma'lumotlar bazasiga yo'l
    public static String DB_PATH;
    //baza fayli nomi
    public static String DB_NAME;

    public SQLiteDatabase database;
    public final Context context;

    public SQLiteDatabase getDb() {
        return database;
    }

    public ExternalDbOpenHelper(Context context, String databaseName) {
        super(context, databaseName, null, 1);
        this.context = context;
        //bazaga to'liq yo'l ko'rsatiladi
        String packageName = context.getPackageName();
        DB_PATH = String.format("//data//data//%s//databases//", packageName);
        DB_NAME = databaseName;
        openDataBase();
    }

    //baza hosil qilinmagan bo'lsa hosil qilamiz
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Nusxalashda xatolik sodir bo`ldi");
                throw new Error("Nusxalashda xatolik sodir bo`ldi!");
            }
        } else {
            Log.i(this.getClass().toString(), "Baza bor ekan");
        }
    }
    //Baza bor yo'qligini tekshirish
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Bazani tekshirishda xatolik");
        }
        //hamma ochilgan narsalarni yopish lozim
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }
    //Bazani ko'chirish
    private void copyDataBase() throws IOException {
        // o'qish uchun potok ochamiz
        // istochnik assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);

        // androiddagi bazaga yo'l
        String outFileName = DB_PATH + DB_NAME;

        // yozish uchun potok ochamiz
        OutputStream localDbStream = new FileOutputStream(outFileName);

        // nusxalaymiz
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        // oqimni yopamiz
        localDbStream.close();
        externalDbStream.close();

    }


    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }

        return database;
    }



    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
