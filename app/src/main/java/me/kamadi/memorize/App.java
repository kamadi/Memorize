package me.kamadi.memorize;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.kamadi.memorize.model.Language;

/**
 * Created by Madiyar on 26.04.2016.
 */
public class App extends Application {

    private static App app;
    private SharedPreferences sharedPrefs;

    public synchronized static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
    }

    public String getLanguage() {
        return sharedPrefs.getString(Language.KEY, Language.ENGLISH);
    }
}
