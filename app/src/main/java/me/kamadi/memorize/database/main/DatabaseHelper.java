package me.kamadi.memorize.database.main;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;

import me.kamadi.memorize.model.Group;
import me.kamadi.memorize.model.Word;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "memorize.db";
    private static final int DATABASE_VERSION = 4;
    private static final String LOG_TAG = "DatabaseHelper";
    private Dao<Word, String> wordDao = null;
    private Dao<Group, String> groupDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        super(context, Environment.getExternalStorageDirectory().getAbsolutePath()
//                + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
        Log.e(LOG_TAG, "database helper construct");
        DatabaseInitializer initializer = new DatabaseInitializer(context);
        try {
            initializer.createDatabase();
            initializer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {


    }


    @Override
    public void close() {
        super.close();
        wordDao = null;
        groupDao = null;
    }


    public Dao<Word, String> getWordDao() throws SQLException {
        if (wordDao == null)
            wordDao = getDao(Word.class);

        return wordDao;
    }

    public Dao<Group, String> getGroupDao() throws SQLException {
        if (groupDao == null)
            groupDao = getDao(Group.class);

        return groupDao;
    }
}