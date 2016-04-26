package me.kamadi.memorize;

import com.facebook.stetho.Stetho;

/**
 * Created by Madiyar on 26.04.2016.
 */
public class DebugApp extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                        Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this))
                .build();
    }
}
